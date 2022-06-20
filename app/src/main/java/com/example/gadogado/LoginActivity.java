package com.example.gadogado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText usernameTxt, passwordTxt;
    TextView textViewRegister;
    Button buttonLogin;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();


        textViewRegister.setOnClickListener(v ->{
            Intent moveRegister = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(moveRegister);
        });

        buttonLogin.setOnClickListener(v -> {
            validasi();
        });


    }

    private void validasi(){
        String username = usernameTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(username.isEmpty()){
            usernameTxt.setError("username must field");
            usernameTxt.requestFocus();
        }
        else if(password.isEmpty()){
            passwordTxt.setError("Password must field");
            passwordTxt.requestFocus();
        }
        else{
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // check if username exist in firebase database
                    if (snapshot.hasChild(username)){
                        // mobile is exist in firebase database
                        String getPassword = snapshot.child(username).child("password").getValue(String.class);
                        if(getPassword.equals(password)){
                            editor.putString("username", username);
                            editor.apply();
                            Toast.makeText(LoginActivity.this, "Success Login", Toast.LENGTH_SHORT).show();
                            Intent moveHomePage = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(moveHomePage);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Wrong password",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void init(){
        usernameTxt = findViewById(R.id.editTexUsernameLogin);
        passwordTxt = findViewById(R.id.editTextPasswordLogin);
        textViewRegister = findViewById(R.id.buttonRegisterDariLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        sharedPreferences = getSharedPreferences("LOG_IN",MODE_PRIVATE);
    }
}