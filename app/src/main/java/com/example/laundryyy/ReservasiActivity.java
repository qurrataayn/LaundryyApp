package com.example.laundryyy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laundryyy.AddData.Reservasi;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReservasiActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText namaTempatLaundy, Nama, Alamat, NoTelp;
    private Button btnDaftar, lokasi;
    private DatabaseReference database;
    private DatabaseReference dataCounter;
    private GoogleApi mGoogleApi;
    private Location mLaslocation;
    String bundle;
    String tanggal;
    String angka;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        tanggal = sdf.format(cal.getTime());

        bundle = getIntent().getBundleExtra("reservasi").getString("nama");
        database = FirebaseDatabase.getInstance().getReference("reservasi").child(bundle).child(tanggal);
        dataCounter = FirebaseDatabase.getInstance().getReference("counter").child(bundle).child(tanggal);

        namaTempatLaundy = (EditText) findViewById(R.id.etNamaLaundry);
        Nama = (EditText) findViewById(R.id.etNamaPelanggan);
        Alamat = (EditText) findViewById(R.id.etAlamatPelanggan);
        NoTelp = (EditText) findViewById(R.id.etNoHP);
        btnDaftar = (Button) findViewById(R.id.btnKirim);
        lokasi = (Button) findViewById(R.id.btnKirimLokPel);

        namaTempatLaundy.setText("DATA PELANGGAN");
        btnDaftar.setOnClickListener(this);
        lokasi.setOnClickListener(this);

        setupGoogleAPI();

        dataCounter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    if (snapshot.getValue() == null) {
                        count = 1;
                    } else {
                        count = Integer.valueOf(snapshot.getValue().toString());
                        count++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupGoogleAPI() {
        mGoogleApi = new GoogleApi.Settings.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApi.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApi.dissconnect();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnDaftar) {
            simpandata();
            startActivity(new Intent(ReservasiActivity.this, DaftarClient.class));
            finish();
        } else if (view.getId() == R.id.btnKirimLokPel) {
            if (mLaslocation != null) {
                Toast.makeText(this, "get Lokasi \nlongitude: " + mLaslocation.getLongitude() +
                        "\n latitude: " + mLaslocation.getLatitude(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Lokasi tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void simpandata() {
        try {
            String nama = Nama.getText().toString();
            String alamat = Alamat.getText().toString();
            String nohp = NoTelp.getText().toString();
            double longitude = mLaslocation.getLongitude();
            double latitude = mLaslocation.getLatitude();
            if (TextUtils.isEmpty(nama) && TextUtils.isEmpty(alamat) && TextUtils.isEmpty(nohp)) {
                Toast.makeText(this, "Data Harap Dimasukkan", Toast.LENGTH_SHORT).show();
            } else {
                dataCounter.child(tanggal).setValue(count);
                Reservasi reservasi = new Reservasi(nama, alamat, nohp, longitude, latitude, count);
                Toast.makeText(this, "Berhasil Terdaftar", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Gagal Terdaftar", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION != PackageManager.PERMISSION_GRANTED)) {
            return;
        }
        mLaslocation = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(this, location -> {
            if (mLaslocation != null) {
                Toast.makeText(this, "Anda Sudah Mendaftar??", Toast.LENGTH_LONG).show();
            }
        });

        @Override
        public void onConnectionSuspended ( int i){

        }

        @Override
        public void onConnectionFailed (@NonNull ConnectionResult connectionResult){

        }
    }
}