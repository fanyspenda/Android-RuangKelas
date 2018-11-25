package arwinata.org.tubesandro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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

import java.io.File;

public class ProfilActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView imgVUploadFoto;
    TextView tvnim, tvnama, tvalamat, tvnohp;
    Button btnEditProfil;
    String username, password;
    public static final int PICK_IMAGE_REQUEST = 1;

    private Uri mImageUri;

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

        btnEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keEditProfil(documentId);
            }
        });

        imgVUploadFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                imageChooser();
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

    public void imageChooser(){
//        //membuat intent dari kamera untuk mengambil gambar
//        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(camera_intent, PICK_IMAGE_REQUEST);

        //mengambil gambar dari galeri
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    //method ini dijalankan ketika startActivityForResult berjalan
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        //menyimpan gambar dari kamera ke imageView
//        if(data.getExtras().get("data")!=null){
//            Bitmap bm = (Bitmap) data.getExtras().get("data");
//            imgVUploadFoto.setImageBitmap(bm);
//        }

        //menyimpan gambar dari galeri ke ImageView
        if(requestCode == PICK_IMAGE_REQUEST && resultCode==RESULT_OK
                && data != null && data.getData() != null){
            mImageUri = data.getData();
            imgVUploadFoto.setImageURI(mImageUri);
        }


    }
}