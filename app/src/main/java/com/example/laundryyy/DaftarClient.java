package com.example.laundryyy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class DaftarClient extends AppCompatActivity implements View.OnClickListener {

    private TextView daftarAdmin, Client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_client);

        daftarAdmin = (TextView) findViewById(R.id.textDaftar);
        Client = (TextView) findViewById(R.id.txtpelanggan);

        daftarAdmin.setOnClickListener(this);
        Client.setOnClickListener(this);

//        buttonexit = (Button) findViewById(R.id.btnExit);
//        daftarAdmin = (TextView) findViewById(R.id.textDaftarAdmin);
//        buttonexit.setOnClickListener(this);
//        daftarAdmin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if (view.getId()==R.id.textDaftar){
            Intent masuk = new Intent(getApplicationContext(), DaftarAdmin.class);
            startActivity(masuk);
        } else if (view.getId()==R.id.txtpelanggan){
            Intent masuk = new Intent(getApplicationContext(), DaftarPelanggan.class);
            startActivity(masuk);
        }
    }
}