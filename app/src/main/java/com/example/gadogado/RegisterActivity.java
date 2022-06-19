package com.example.gadogado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    EditText usernameText, emailText, passwordText;
    TextView textViewLogin;
    Button buttonRegister;
    RadioGroup radioGroupStatus;
    RadioButton radioButtonStatusSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        textViewLogin.setOnClickListener(v -> {
            Intent moveLogin = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(moveLogin);
        });

        buttonRegister.setOnClickListener(v -> {
            validasi();

            //firebase

        });
    }

    private void validasi(){

        String username = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (username.isEmpty()){
            usernameText.setError("Username must field");
            usernameText.requestFocus();
        }
        if (email.isEmpty()){
            emailText.setError("Username must field");
            emailText.requestFocus();
        }
        if (password.isEmpty()){
            passwordText.setError("Username must field");
            passwordText.requestFocus();
        }

    }
    private void init(){
        usernameText = findViewById(R.id.editTextUsernameRegister);
        emailText = findViewById(R.id.editTextEmailRegister);
        passwordText = findViewById(R.id.editTextPasswordRegister);
        textViewLogin = findViewById(R.id.buttonLoginDariRegister);
        buttonRegister = findViewById(R.id.buttonRegister);
        radioGroupStatus = findViewById(R.id.radioGroupStatusRegister);
        radioGroupStatus.clearCheck();

    }
}