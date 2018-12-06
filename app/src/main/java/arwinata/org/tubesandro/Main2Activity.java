package arwinata.org.tubesandro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import arwinata.org.tubesandro.Adapter.GedungAdapter;
import arwinata.org.tubesandro.Class.Gedung;

public class Main2Activity extends AppCompatActivity {
    private RecyclerView rvGedung;
    private GedungAdapter gedAdapter;

    private CollectionReference dbGedung;
    private List<Gedung> mGedung;

    ImageButton imgbtnProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imgbtnProfil = (ImageButton) findViewById(R.id.imgbtnProfil);

        final String documentId;

        rvGedung = findViewById(R.id.rvGedung);
        rvGedung.setHasFixedSize(true);
        rvGedung.setLayoutManager(new LinearLayoutManager(this));

        mGedung = new ArrayList<>();
        dbGedung = FirebaseFirestore.getInstance().collection("gedung");

        documentId = getIntent().getStringExtra("documentId");

        loadDataGedung();

        imgbtnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menujuLihatProfil(documentId);
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
                return;
            }
        });
    }

    private void  menujuLihatProfil(String docId){
        Intent i = new Intent(getApplicationContext(), ProfilActivity.class);
        i.putExtra("documentId", docId);
        startActivity(i);
    }
}