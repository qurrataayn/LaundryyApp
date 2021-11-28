package com.example.laundryyy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laundryyy.AddData.DataLaundry;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DaftarAdmin extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference database;
    private Button lokasi, simpan;
    private EditText nama, alamat, nomer, informasi;
    private Location mLastLocation;
    private GoogleApi mGoogleApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_admin);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("admin").child(user.getUid());

        nama = (EditText) findViewById(R.id.editTextNama);
        alamat = (EditText) findViewById(R.id.editTextAlamat);
        nomer = (EditText) findViewById(R.id.editTextNoHP);
        lokasi = (Button) findViewById(R.id.buttonMasukLokasi);
        informasi = (EditText) findViewById(R.id.etinfo);
        simpan = (Button) findViewById(R.id.btnSimpan);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()==null){
                    Toast.makeText(DaftarAdmin.this, "Silahkan isi data dengan benar", Toast.LENGTH_SHORT).show();
                } else{
                    DataLaundry dataLaundry = dataSnapshot.getValue(DataLaundry.class);
                    nama.setText(dataLaundry.getNama());
                    alamat.setText(dataLaundry.getAlamat());
                    nomer.setText(dataLaundry.getNomer());
                    informasi.setText(dataLaundry.getInformasi());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        lokasi.setOnClickListener(this);
        simpan.setOnClickListener(this);

        setupGoogleAPI();
    }

    private void setupGoogleAPI(){
        mGoogleApi = new GoogleApi.Settings.Builder(this)
                .addApi(LocationServices.API)
                .addConnecionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.btnSimpan){
            simpan();
        } else if (view.getId()==R.id.buttonMasukLokasi){
            if(mLastLocation!=null){
                Toast.makeText(this, "get Lokasi \nlongitude: "+mLastLocation.getLongitude()+
                        "\n latitude: "+mLastLocation.getLatitude(), Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "Lokasi tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void simpan(){
        try{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String id = user.getUid();
            String email = user.getEmail().toString();
            String name = nama.getText().toString();
            String address = alamat.getText().toString();
            String numb = nomer.getText().toString();
            String info = informasi.getText().toString();
            double loni = mLastLocation.getLongitude();
            double lati = mLastLocation.getLatitude();

            DataLaundry data = new DataLaundry(id, email, name, address, numb, info, loni, lati);
            database.setValue(data);

            Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(this, "Data Gagal Dimasukkan", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onStart(){
        super.onStart();
        mGoogleApi.connect();
    }

    @Override
    public void onStop(){
        super.onStop();
        mGoogleApi.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApi);
        if(mLastLocation != null){
            Toast.makeText(this,"Anda Sudah Mendaftar", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i){

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){

    }
}