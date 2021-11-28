package com.example.laundryyy.AddData;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laundryyy.R;

import java.util.List;

public class DaftarListPelanggan extends ArrayAdapter <DataLaundry>{

    private Activity context;
    List<DataLaundry> admin;

    public DaftarListPelanggan(Activity context, List<DataLaundry> admin){
        super(context, R.layout.activity_daftar_pelanggan,admin);
        this.context = context;
        this.admin = admin;
    }

    public View getView (int Position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_daftar_pelanggan, null, true);

        TextView nama = (TextView) listViewItem.findViewById(R.id.tvNama);
        TextView alamat = (TextView) listViewItem.findViewById(R.id.tvAlamat);
        TextView latitude = (TextView) listViewItem.findViewById(R.id.textVlangitude);
        TextView longitude = (TextView) listViewItem.findViewById(R.id.textVlongitude);

        DataLaundry admin1 = admin.get(Position);
        double result1 = admin1.getLatitude();
        double result2 = admin1.getLongitude();

        String finalresult1 = new Double(result1).toString();
        String finalresult2 = new Double(result2).toString();

        nama.setText(admin1.getNama());
        alamat.setText(admin1.getAlamat());
        latitude.setText(finalresult1);
        longitude.setText(finalresult2);

        return listViewItem;
    }
}
