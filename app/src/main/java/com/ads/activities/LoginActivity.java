package com.ads.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ads.activities.client.HomeUserActivity;
import com.ads.activities.client.RegisterActivity;
import com.ads.activities.worker.HomeWorkerActivity;
import com.ads.activities.worker.RegisterWorkerActivity;
import com.ads.includes.MyToolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.ads.R;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences mPref;
    private ProgressDialog progressDialog;
    Toolbar mToolbar;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonUserLogin;
    Button mButtonUserRegister;
    FirebaseAuth mAuth;
    DatabaseReference mDataBase;
    AlertDialog mDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);


        String typeUser = mPref.getString("user", "");
        typeUser = capitalizeFirstLetter(typeUser);
        MyToolbar.showTransparent(this, "" + typeUser, true);

        // Inicialización de las vistas
        mTextInputEmail = findViewById(R.id.editTextTextEmailAddress2);
        mTextInputPassword = findViewById(R.id.editTextTextPassword);
        mButtonUserLogin = findViewById(R.id.login);
        mButtonUserRegister = findViewById(R.id.button2);

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.setCancelable(false);


        mButtonUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                progressDialog.show();
            }

        });
        mButtonUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }

        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
    }

    private void login() {
        String email = mTextInputEmail.getText().toString();
        String password = mTextInputPassword.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 8) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            String user = mPref.getString("user", "");
                            if(user.equals("cliente")){
                                Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeUserActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeWorkerActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Correo o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private void register(){
        String typeUser = mPref.getString("user", "");
        if (typeUser.equals("cliente") ){
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(LoginActivity.this, RegisterWorkerActivity.class);
            startActivity(intent);
        }
    }
    private String capitalizeFirstLetter(String typeUser) {
        if (typeUser == null || typeUser.isEmpty()) {
            return typeUser; // Retorna null o cadena vacía si el input es así
        }
        return typeUser.substring(0, 1).toUpperCase() + typeUser.substring(1).toLowerCase();
    }
}
