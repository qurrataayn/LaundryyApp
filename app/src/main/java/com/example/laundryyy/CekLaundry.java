package com.example.laundryyy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CekLaundry extends AppCompatActivity implements View.OnClickListener {

    Button Exite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_laundry);

        Exite = (Button) findViewById(R.id.btnSelesai);
        Exite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v.getId()==R.id.btnExitCekLaundry){
            Intent exit = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(exit);
        }
    }
}