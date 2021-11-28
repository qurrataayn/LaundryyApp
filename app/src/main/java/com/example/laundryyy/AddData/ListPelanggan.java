package com.example.laundryyy.AddData;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laundryyy.R;

import java.util.List;

public class ListPelanggan extends ArrayAdapter <Reservasi> {
    private Activity context;
    List<Reservasi> reservasi;

    public ListPelanggan (Activity context, List<Reservasi> reservasi){
        super(context, R.layout.activity_daftar_pelanggan,reservasi);
        this.context = context;
        this.reservasi = reservasi;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.activity_daftar_pelanggan, null, true);

        TextView nama = (TextView) listViewItem.findViewById(R.id.tvNama);
        TextView alamat = (TextView) listViewItem.findViewById(R.id.tvAlamat);
        TextView latitude = (TextView) listViewItem.findViewById(R.id.textVlangitude);
        TextView longitude = (TextView) listViewItem.findViewById(R.id.textVlongitude);

        Reservasi reservasi1=reservasi.get(position);
        double result1 = reservasi1.getLatitude();
        double result2 = reservasi1.getLongitude();

        String finalresult1 = new Double(result1).toString();
        String finalresult2 = new Double(result2).toString();

        nama.setText(reservasi1.getNama());
        alamat.setText(reservasi1.getAlamat());
        latitude.setText(finalresult1);
        longitude.setText(finalresult2);

        return listViewItem;
    }
}
