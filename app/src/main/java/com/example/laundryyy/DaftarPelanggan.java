package com.example.laundryyy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryyy.AddData.ListPelangganL;
import com.example.laundryyy.AddData.Reservasi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DaftarPelanggan extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private DatabaseReference database;
    ListView ListPelanggan;
    List<Reservasi>Reservasis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pelanggan);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        String tanggal = sdf.format(cal.getTime());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("reservasi").child(user.getUid()).child(tanggal);
        ListPelanggan = (ListView) findViewById(R.id.lvPelanggan);
        Reservasis = new ArrayList<>();

        ListPelanggan.setOnItemClickListener(DaftarPelanggan.this);
        ListPelanggan.setOnItemLongClickListener(this);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                Reservasis.clear();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    if (snapshot.getValue(Reservasi.class)==null){
                        Toast.makeText(DaftarPelanggan.this, "List Kosong", Toast.LENGTH_SHORT).show();
                    } else{
                        Reservasi reservasi = snapshot.getValue(Reservasi.class);
                        Reservasis.add(reservasi);
                    }
                    ListPelangganL adapter = new ListPelangganL(DaftarPelanggan.this,Reservasis);
                    ListPelanggan.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick (AdapterView<?> adapterView, View view, int i, long l){
        TextView nama = (TextView) view.findViewById(R.id.tvNama);
        TextView alamat = (TextView) view.findViewById(R.id.tvAlamat);
        TextView longi = (TextView) view.findViewById(R.id.textVLongitude);
        TextView lang = (TextView) view.findViewById(R.id.textVLangitude);
        Bundle bundle = new Bundle();
        bundle.putString("data1", nama.getText().toString());
        bundle.putString("data2", alamat.getText().toString());
        bundle.putString("data3", longi.getText().toString());
        bundle.putString("data4", lang.getText().toString());
        Intent intent1 = new Intent(DaftarPelanggan.this, MapsLaundry.class);
        intent1.putExtras(bundle);
        startActivity(intent1);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l){
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture){

    }
}