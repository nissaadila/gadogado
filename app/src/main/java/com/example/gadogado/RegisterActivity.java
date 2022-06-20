package com.example.gadogado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
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
    String stringCulinerOrRestaurant;
    RadioGroup culinerOrRestaurant;
    RadioButton culineOrRestaurantOption;

    //create object of DataReference class to access firebase'realtime database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        culinerOrRestaurant.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                culineOrRestaurantOption = culinerOrRestaurant.findViewById(checkedId);
                switch (checkedId){
                    case R.id.status_culiner_hunter:
                        stringCulinerOrRestaurant = culineOrRestaurantOption.getText().toString().trim();
                        break;
                    case R.id.status_restaurant:
                        stringCulinerOrRestaurant = culineOrRestaurantOption.getText().toString().trim();
                        break;
                    default:
                }
            }

        });

        textViewLogin.setOnClickListener(v -> {
            Intent moveLogin = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(moveLogin);
        });

        buttonRegister.setOnClickListener(v -> {
            validasi();
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
        else if (email.isEmpty()){
            emailText.setError("Email must field");
            emailText.requestFocus();
        }
        else if(!email.endsWith(".com")){
            emailText.setError("Email must endswith .com");
            emailText.requestFocus();
        }
        else if (password.isEmpty()){
            passwordText.setError("password must field");
            passwordText.requestFocus();
        }
        //sending data to firebase
        else{

            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(username)){
                        Toast.makeText(RegisterActivity.this, "Isername is already registered", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        databaseReference.child("users").child(username).child("email").setValue(email);
                        databaseReference.child("users").child(username).child("password").setValue(password);
                        databaseReference.child("users").child(username).child("status").setValue(stringCulinerOrRestaurant);
                        //show a success
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
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
        culinerOrRestaurant = findViewById(R.id.radioGroupStatusRegister);

    }
}