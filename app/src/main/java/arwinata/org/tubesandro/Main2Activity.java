package arwinata.org.tubesandro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Main2Activity extends AppCompatActivity {

    ImageButton imgbtnProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imgbtnProfil = (ImageButton) findViewById(R.id.imgbtnProfil);

        final String documentId;
        documentId = getIntent().getStringExtra("documentId");

        imgbtnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProfilActivity.class);
                i.putExtra("documentId", documentId);
                startActivity(i);
            }
        });
    }
}
