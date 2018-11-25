package arwinata.org.tubesandro;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfilActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText nama, alamat, nomorHp, username, password, confPassword;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        nama = (EditText) findViewById(R.id.evEditNama);
        alamat = (EditText) findViewById(R.id.evEditAlamat);
        nomorHp = (EditText) findViewById(R.id.evEditNoHp);
        username = (EditText) findViewById(R.id.evEditUsername);
        password = (EditText) findViewById(R.id.evEditPassword);
        confPassword = (EditText) findViewById(R.id.evEditConfirmPassword);

        btnSimpan = (Button) findViewById(R.id.btnSimpanProfil);

        loadData();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validasiInput()){
                    //coding input ke firebase
                    String documentId = getIntent().getStringExtra("documentId");
                    simpanData(documentId);
                }
            }
        });

    }

    private void loadData(){
        nama.setText(getIntent().getStringExtra("nama"));
        alamat.setText(getIntent().getStringExtra("alamat"));
        nomorHp.setText(getIntent().getStringExtra("nomorHp"));
        username.setText(getIntent().getStringExtra("username"));
        password.setText(getIntent().getStringExtra("password"));
        confPassword.setText(getIntent().getStringExtra("password"));
    }

    private boolean validasiInput(){
        String nama = this.nama.getText().toString().trim();
        String alamat = this.alamat.getText().toString().trim();
        String password  = this.password.getText().toString().trim();

        if(nama.equals("") || nama.length()>30 || nama.length()<4){
            Toast.makeText(getApplicationContext(), "bagian nama Error!",
                    Toast.LENGTH_LONG).show();
            this.nama.requestFocus();
            return false;
        }
        else if(alamat.equals("") || alamat.length()>90 || alamat.length()<10) {
            Toast.makeText(getApplicationContext(), "bagian alamat Error!",
                    Toast.LENGTH_LONG).show();
            this.alamat.requestFocus();
            return false;
        }
        else if(password.equals("") || password.length()>13 || password.length()<8 ||
                !password.equals(confPassword.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "bagian password Error!",
                    Toast.LENGTH_LONG).show();
            this.password.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }

    private void simpanData(String docId){
        db.collection("mahasiswa").document(docId).update(
                "nama", this.nama.getText().toString(),
                "alamat", this.alamat.getText().toString(),
                "nomorhp", this.nomorHp.getText().toString(),
                "username", this.username.getText().toString(),
                "password", this.password.getText().toString()

        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Data Terupdate!", Toast.LENGTH_LONG).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
