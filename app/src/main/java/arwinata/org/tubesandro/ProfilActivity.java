package arwinata.org.tubesandro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfilActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView imgVUploadFoto;
    TextView tvnim, tvnama, tvalamat, tvnohp;
    Button btnEditProfil;
    String username, password;
    File simpanGambarDir = null;
    File mFileURI;
    public static final int PICK_IMAGE_REQUEST = 1;

    //variable untuk fungsi imageChooser
//    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        tvnim = (TextView) findViewById(R.id.tvNim);
        tvnama = (TextView) findViewById(R.id.tvNama);
        tvalamat = (TextView) findViewById(R.id.tvAlamat);
        tvnohp = (TextView) findViewById(R.id.tvNoHp);
        btnEditProfil = (Button) findViewById(R.id.btnEditProfil);
        btnEditProfil.setClickable(false);
        btnEditProfil.setEnabled(false);

        imgVUploadFoto = findViewById(R.id.imgvUploadFoto);

        final String documentId = getIntent().getStringExtra("documentId");

        //load Data
        loadDataMahasiswa(documentId);

        //menuju Activity EditProfil
        btnEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keEditProfil(documentId);
            }
        });

        //membuka kamera
        imgVUploadFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
    }

    public void loadDataMahasiswa(final String documentId){
        db.collection("mahasiswa").document(documentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        //menyimpan data ke component-component layout
                        tvnim.setText(documentSnapshot.get("nim").toString());
                        tvnama.setText(documentSnapshot.get("nama").toString());
                        tvalamat.setText(documentSnapshot.get("alamat").toString());
                        tvnohp.setText(documentSnapshot.get("nomorhp").toString());
                        username = documentSnapshot.get("username").toString();
                        password = documentSnapshot.get("password").toString();

                        //mengenable button Edit ketika data sudah disimpan pada textView
                        int kuning = Color.parseColor("#FFAA00");
                        btnEditProfil.setBackgroundColor(kuning);
                        btnEditProfil.setClickable(true);
                        btnEditProfil.setEnabled(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Ambil Data Error!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void keEditProfil(String documentId){

        Intent editProfil_Intent = new Intent(getApplicationContext(), EditProfilActivity.class);
        editProfil_Intent.putExtra("nama", tvnama.getText().toString());
        editProfil_Intent.putExtra("alamat", tvalamat.getText().toString());
        editProfil_Intent.putExtra("nomorHp", tvnohp.getText().toString());
        editProfil_Intent.putExtra("username", username);
        editProfil_Intent.putExtra("password", password);
        editProfil_Intent.putExtra("documentId", documentId);

        startActivity(editProfil_Intent);
    }

//    public void imageChooser(){
//        //mengambil gambar dari galeri
//        Intent i = new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(i, PICK_IMAGE_REQUEST);
//    }
    //mengambil foto
    private void takePhoto(){
        //membuat intent dari kamera untuk mengambil gambar

        //mengallow access kamera
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //jika kamera mengambil gambar
        if(camera_intent.resolveActivity(getPackageManager())!=null){
            mFileURI = getNamaPhoto();
            camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFileURI));
            startActivityForResult(camera_intent, 100);
        }
    }

    //method ini dijalankan ketika startActivityForResult berjalan
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        //(koding dari imageChooser) menyimpan gambar dari galeri ke ImageView
//        if(requestCode == PICK_IMAGE_REQUEST && resultCode==RESULT_OK
//                && data != null && data.getData() != null){
//            mImageUri = data.getData();
//            imgVUploadFoto.setImageURI(mImageUri);
//        }

        //koding dari takePhoto
        if (requestCode == 100 && resultCode == RESULT_OK) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            // rescale bitmap jika aplikasi force close
            // semakin besar ukuran rescale maka image/gambar yang ditampilkan semakin kecil
            bmOptions.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeFile(mFileURI.getPath(), bmOptions);

            Picasso.get().load(mFileURI).
                    memoryPolicy(MemoryPolicy.NO_CACHE).
                    networkPolicy(NetworkPolicy.NO_CACHE).
                    resize(120, 120).
                    centerCrop().
                    into(imgVUploadFoto);
        }
    }

    //fungsi untuk mengset Direktori penyimpanan foto dan penamaan file
    private File getNamaPhoto(){
        //meng-get lokasi memori eksternal untuk dijadikan tempat menyimpan foto
        simpanGambarDir = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES), "RuangKelasPolinema");

        //cek apakah sudah ada folder.
        if(!simpanGambarDir.exists()){
            //jika gagal membuat direktori, tampilkan toast
            if(!simpanGambarDir.mkdirs()){
                Toast.makeText(getApplicationContext(), "Gagal membuat Direktori!",
                        Toast.LENGTH_LONG).show();
            }
        }

        File mediaFile = null;
        //membuat file kosong pada direktori
        mediaFile = new File(simpanGambarDir.getPath() + File.separator + "IMG_Profilku.jpg");
        if (mediaFile.exists()){
            mediaFile.delete();
        }
        return mediaFile;
    }
}