package com.example.pagoplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;

public class Login extends AppCompatActivity {

    Button btnIngresar;
    EditText txtUser, txtPassword;

    TextView textRegistrar;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        txtUser = findViewById(R.id.inpuEmail);
        txtPassword = findViewById(R.id.inputPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        textRegistrar = findViewById(R.id.textRegistrar); // Inicialización del TextView

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtUser.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                } else {
                    LoginUser(email, password);
                }
            }
        });

        // OnClickListener para el TextView "textRegistrar"
        textRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad de registro
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
    }

    private void LoginUser(String email,String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login.this, "BIENVENIDO", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, MainActivity.class)); // Aquí inicias MainActivity
                            finish(); // Finaliza la actividad actual después de iniciar MainActivity
                        } else {
                            //Si el inicio de sesion falla, mostrar un mensaje al usuario
                            Toast.makeText(Login.this, "Error al iniciar sesion: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}