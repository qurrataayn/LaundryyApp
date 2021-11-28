package com.example.laundryyy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginDaftar extends AppCompatActivity implements View.OnClickListener{

    private Button buttonmasuk;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttondaftar;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_daftar);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){

        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonmasuk = (Button) findViewById(R.id.btnLogin);
        buttondaftar = (Button) findViewById(R.id.btnDaftar);

        progressDialog = new ProgressDialog(this);
        buttonmasuk.setOnClickListener(this);
        buttondaftar.setOnClickListener(this);
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Masukkan Email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Masukkan Password", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Daftarkan Pengguna ...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(LoginDaftar.this,"Sukses Mendaftar...", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        } else{
                            Toast.makeText(LoginDaftar.this, "Gagal Mendaftar...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Masukkan Email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Masukkan Password", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Terdaftar, mohon tunggu ...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View view){
        if (view == buttonmasuk){
            userLogin();
        }
        if (view == buttondaftar){
            registerUser();
        }
    }
}