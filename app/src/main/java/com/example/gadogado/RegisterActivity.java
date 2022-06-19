package com.example.gadogado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    EditText usernameText, emailText, passwordText;
    TextView textViewLogin;
    Button buttonRegister;
    RadioGroup radioGroupStatus;
    RadioButton radioButtonStatusSelected;

    //create object of DataReference class to access firebase'realtime database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");

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
        //sending data to firebase
        else{

            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(username)){
                        Toast.makeText(RegisterActivity.this, "username is already registered", Toast.LENGTH_LONG).show();

                    }
                    else{
                        databaseReference.child("users").child(username).child("email").setValue(email);
                        databaseReference.child("users").child(username).child("password").setValue(password);

                        //show a success
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

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