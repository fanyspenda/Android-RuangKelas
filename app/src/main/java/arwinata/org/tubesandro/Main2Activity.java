package arwinata.org.tubesandro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import arwinata.org.tubesandro.Adapter.GedungAdapter;
import arwinata.org.tubesandro.Class.Gedung;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity {
    private RecyclerView rvGedung;
    private GedungAdapter gedAdapter;

    private CollectionReference dbGedung;
    private CollectionReference dbMahasiswa;
    private List<Gedung> mGedung;

    String documentIdMahasiswa;

    CircleImageView imgbtnProfil;

    String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imgbtnProfil = findViewById(R.id.imgbtnProfil);

        rvGedung = findViewById(R.id.rvGedung);
        rvGedung.setHasFixedSize(true);
        rvGedung.setLayoutManager(new LinearLayoutManager(this));

        mGedung = new ArrayList<>();
        dbGedung = FirebaseFirestore.getInstance().collection("gedung");
        dbMahasiswa = FirebaseFirestore.getInstance().collection("mahasiswa");

        documentIdMahasiswa = getIntent().getStringExtra("documentId");

        loadDataGedung();
        loadFotoProfil(documentIdMahasiswa);

        imgbtnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menujuLihatProfil(documentIdMahasiswa);
            }
        });
    }

    public void loadFotoProfil(String docId) {
        dbMahasiswa.document(docId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.get("imageUrl").toString().equals("kosong")) {
                            Toast.makeText(getApplicationContext(), "Foto Profil Belum Diset!",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Picasso.get().load(documentSnapshot.get("imageUrl").toString())
                                    .rotate(90)
                                    .into(imgbtnProfil);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void loadDataGedung(){
        dbGedung.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        Gedung gedung = documentSnapshot.toObject(Gedung.class);
                        mGedung.add(gedung);
                    }
                    gedAdapter = new GedungAdapter(getApplicationContext(), mGedung);
                    rvGedung.setAdapter(gedAdapter);

                } else {
                    Toast.makeText(getApplicationContext(), "Tidak Ada Data", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error Mendapatkan Data: " + e, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void  menujuLihatProfil(String docId){
        Intent i = new Intent(getApplicationContext(), ProfilActivity.class);
        i.putExtra("documentId", docId);
        startActivity(i);
    }
}