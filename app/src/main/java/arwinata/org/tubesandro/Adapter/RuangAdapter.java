package arwinata.org.tubesandro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import arwinata.org.tubesandro.Class.Ruangan;
import arwinata.org.tubesandro.DaftarRuangActivity;
import arwinata.org.tubesandro.JadwalActivity;
import arwinata.org.tubesandro.R;

//Step 2:
//menerapkan inner-class yang telah dibuat ke dalam adapter, kemudian menerapkan semua method
public class RuangAdapter extends RecyclerView.Adapter<RuangAdapter.RuangViewHolder> {

    Context mContext;
    List<Ruangan> mRuangan;

    public RuangAdapter(Context mContext, List<Ruangan> mRuangan) {
        this.mContext = mContext;
        this.mRuangan = mRuangan;
    }

    @NonNull
    @Override
    public RuangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Step 3:
        //membuat LayoutInflater untuk menentukan activity mana yang akan digunakan dan layout mana..
        //..yang akan diterapkan ke activity tersebut.
        //kemudian, Mereturn RuangViewHolder Baru dengan parameter View.
        View view = LayoutInflater.from(mContext).inflate(R.layout.ruang_item, parent, false);
        return new RuangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RuangViewHolder holder, int position) {
        //step 4: mengset nilai dari tiap-tiap list ke dalam layout
        final Ruangan ruanganItem = mRuangan.get(position);
        holder.namaRuang.setText(ruanganItem.getNama());

        //step 5 (Opsional): menambahkan onClickListener agar item memiliki aksi saat diklik
        holder.namaRuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jadwalIntent = new Intent(mContext, JadwalActivity.class);
                jadwalIntent.putExtra("namaRuang", ruanganItem.getNama());
                jadwalIntent.putExtra("lantai", ruanganItem.getLantai());
                jadwalIntent.putExtra("imageJadwal", ruanganItem.getImageJadwal());
                mContext.startActivity(jadwalIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //step 6: mengubah return menjadi size/banyak item pada List
        return mRuangan.size();
    }

    //Step 1:
    // membuat inner-Class dari kelas Adapter yang dibuat untuk menempatkan komponen yang ada dalam..
    //..Layout item yang telah dibuat
    public class RuangViewHolder extends RecyclerView.ViewHolder{

        public TextView namaRuang;

        public RuangViewHolder(View itemView) {
            super(itemView);
            namaRuang = itemView.findViewById(R.id.item_namaRuang);
        }
    }
}
