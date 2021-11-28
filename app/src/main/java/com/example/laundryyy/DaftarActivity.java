package com.example.laundryyy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DaftarActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        btnExit = (Button) findViewById(R.id.BtnexitDaftar);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent exit = new Intent(getApplicationContext(),LoginDaftar.class);
        startActivity(exit);
    }
}