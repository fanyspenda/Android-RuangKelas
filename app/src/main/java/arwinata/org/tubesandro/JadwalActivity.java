package arwinata.org.tubesandro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.squareup.picasso.Picasso;

public class JadwalActivity extends AppCompatActivity {

    PhotoView imgvFotoJadwal;
    TextView tvNamaRuang, tvLantai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        imgvFotoJadwal = (PhotoView) findViewById(R.id.imgvJadwalRuang);
        tvNamaRuang = findViewById(R.id.tvNamaRuang);
        tvLantai = findViewById(R.id.tvLantaiRuang);

        String namaRuang = getIntent().getStringExtra("namaRuang");
        int lantai = getIntent().getIntExtra("lantai", 0);
        String imageJadwal = getIntent().getStringExtra("imageJadwal");

        tvNamaRuang.setText(namaRuang);
        tvLantai.setText(String.valueOf(lantai));

        Picasso.get().load(imageJadwal).rotate(90).into(imgvFotoJadwal);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imgvFotoJadwal);
        photoViewAttacher.update();
    }
}