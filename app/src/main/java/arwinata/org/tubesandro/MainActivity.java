package arwinata.org.tubesandro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import arwinata.org.tubesandro.Class.Mahasiswa;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button btnLogin;
    EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText) findViewById(R.id.etusername);
        etPassword = (EditText) findViewById(R.id.etpassword);

        btnLogin = (Button) findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                checkLogin(username, password);
            }
        });
    }

    public void checkLogin(String insertedUsername, String insertedPassword){

        Toast.makeText(getApplicationContext(), "Please Wait...",
                Toast.LENGTH_SHORT).show();

        //query menuju ke collection mahasiswa, mencari data dengan username dan password tertentu
        Query query = db.collection("mahasiswa")
                .whereEqualTo("username", insertedUsername).whereEqualTo("password", insertedPassword);

        //jika query berhasil dijalankan, menjalankan onSuccessListener
        //Snapshot mengandung semua return dari query
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty()){

                    Toast.makeText(getApplicationContext(), "Wrong Username and Password",
                            Toast.LENGTH_LONG).show();
                }else {


                    //setiap ada data dari variable queryDocumentSnapshots, maka data akan..
                    //..diparsing ke variable documentSnapshot agar bisa menggunakan..
                    //..method - method yang ada dalam documentSnapshot

                    //jangan terbalik! karena secara default, documentSnapshot merupakan..
                    //..variable dari Class QueryDocumentSnapshot, dan begitu pula sebaliknya!
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                        //memindah data dari variable documentSnapshot ke objek dari kelas Mahasiswa
                        Mahasiswa m1 = documentSnapshot.toObject(Mahasiswa.class);

                        //memindah data masing - masing atribut dari objek Mahasiswa ke variable
                        String nim = m1.getNim();
                        String nama = m1.getNama();
                        String alamat = m1.getAlamat();
                        String username = m1.getUsername();
                        String password = m1.getPassword();
                        String nomorhp = m1.getNomorhp();
                        String imageUrl = m1.getImageUrl();

                        //mengambil ID dari document
                        String documentId = documentSnapshot.getId();

                        String data = "NIM: "+nim
                                +"\nNama: "+nama
                                +"\nAlamat: "+alamat
                                +"\nusername: "+username
                                +"\npassword: "+password
                                +"\nnomor hp: "+nomorhp
                                +"\nUrl Gambar: "+imageUrl;

                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                        goToMain2Activity(documentId);
                    }
                }
            }
        });
    }

    public void goToMain2Activity (String documentId){
        Intent i = new Intent (getApplicationContext(), Main2Activity.class);
        i.putExtra("documentId", documentId);
        startActivity(i);
    }
}
