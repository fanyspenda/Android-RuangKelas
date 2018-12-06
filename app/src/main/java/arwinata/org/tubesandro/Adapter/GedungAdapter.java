package arwinata.org.tubesandro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.List;

import arwinata.org.tubesandro.DaftarRuangActivity;
import arwinata.org.tubesandro.Class.Gedung;
import arwinata.org.tubesandro.R;

public class GedungAdapter extends RecyclerView.Adapter<GedungAdapter.GedungViewHolder> {

    private Context mContext;
    private List<Gedung> mGedung;

    public GedungAdapter(Context mContext, List<Gedung> mGedung) {
        this.mContext = mContext;
        this.mGedung = mGedung;
    }

    @NonNull
    @Override
    public GedungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gedung_item, parent, false);
        return new GedungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GedungViewHolder holder, int position) {
        final Gedung gdgItem = mGedung.get(position);
        holder.namaGedung.setText(gdgItem.getNama());
        Picasso.get().load(gdgItem.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.gambarGedung);

        holder.gambarGedung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DaftarRuangActivity.class);
                i.putExtra("namaGedung", gdgItem.getNama());
                mContext.startActivity(i);
                Toast.makeText(mContext, gdgItem.getNama(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGedung.size();
    }

    public class GedungViewHolder extends RecyclerView.ViewHolder{

        public TextView namaGedung;
        public ImageView gambarGedung;

        public GedungViewHolder(View itemView) {
            super(itemView);
            namaGedung = itemView.findViewById(R.id.item_namaGedung);
            gambarGedung = itemView.findViewById(R.id.item_gambarGedung);
        }
    }
}