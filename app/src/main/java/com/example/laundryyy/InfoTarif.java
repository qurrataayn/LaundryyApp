package com.example.laundryyy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoTarif extends AppCompatActivity implements View.OnClickListener {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_tarif);

        next = (Button) findViewById(R.id.btnSelesai);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent exit = new Intent(getApplicationContext(),LoginDaftar.class);
        startActivity(exit);
    }
}