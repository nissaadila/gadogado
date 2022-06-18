package com.example.gadogado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText emailTxt, passwordTxt;
    TextView textViewRegister;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        // firebase

        textViewRegister.setOnClickListener(v ->{
            Intent moveRegister = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(moveRegister);
        });

        buttonLogin.setOnClickListener(v -> {
            validasi();
            // firebase
        });


    }

    private void validasi(){
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();

        if(email.isEmpty()){
            emailTxt.setError("Email must field");
            emailTxt.requestFocus();
        }
        if(password.isEmpty()){
            passwordTxt.setError("Password must field");
            passwordTxt.requestFocus();
        }
        // validasi firebase
    }

    private void init(){
        emailTxt = findViewById(R.id.editTextEmailLogin);
        passwordTxt = findViewById(R.id.editTextPasswordLogin);
        textViewRegister = findViewById(R.id.buttonRegisterDariLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
    }
}