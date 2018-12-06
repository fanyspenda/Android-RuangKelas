package arwinata.org.tubesandro;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import arwinata.org.tubesandro.Adapter.RuangAdapter;
import arwinata.org.tubesandro.Class.Ruangan;

public class DaftarRuangActivity extends AppCompatActivity {

    private String gedungPilihan;
    private RecyclerView rvRuangan;
    private CollectionReference dbRuangan;

    private List<Ruangan> mRuangan;
    private RuangAdapter ruangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_ruang);

        gedungPilihan = getIntent().getStringExtra("namaGedung");

        //buat 3 baris ini tiap menggunakan RecyclerView
        rvRuangan = findViewById(R.id.rvRuangan);
        rvRuangan.setHasFixedSize(true);
        rvRuangan.setLayoutManager(new LinearLayoutManager(this));

        mRuangan = new ArrayList<>();
        simpanDataRuangKeList();
    }

    public void simpanDataRuangKeList(){
        dbRuangan = FirebaseFirestore.getInstance().collection("ruangan");
        Query query = dbRuangan.whereEqualTo("lokasi", gedungPilihan);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        Ruangan ruangan = documentSnapshot.toObject(Ruangan.class);
                        mRuangan.add(ruangan);
                    }
                    ruangAdapter = new RuangAdapter(getApplicationContext(), mRuangan);
                    rvRuangan.setAdapter(ruangAdapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error Mendapatkan Data Ruang!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
