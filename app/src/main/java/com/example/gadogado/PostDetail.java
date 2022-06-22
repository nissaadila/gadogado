package com.example.gadogado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class PostDetail extends AppCompatActivity {

    TextView desc, like, username, date;
    ImageView profilePic, img;

    String detail_desc, detail_username, detail_date, detail_img, detail_id;
    Integer detail_like;
    String urlProfilePic=null;

    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        desc = findViewById(R.id.detail_description);
        like = findViewById(R.id.detail_like);
        username = findViewById(R.id.detail_username);
        date = findViewById(R.id.detail_date);
        profilePic = findViewById(R.id.detail_profile_image);
        img = findViewById(R.id.detail_pic);

        detail_desc = getIntent().getStringExtra("postDesc");
        detail_date = getIntent().getStringExtra("postDate");
        detail_like = getIntent().getIntExtra("postLike", 0);
        detail_img = getIntent().getStringExtra("postPic");
        detail_username = getIntent().getStringExtra("username");
        detail_id = getIntent().getStringExtra("postId");

        desc.setText(detail_desc);
        like.setText(String.valueOf(detail_like));
        username.setText(detail_username);
        date.setText(detail_date);
        Glide.with(this).load(detail_img).into(img);

        //set profile pic
        databaseReference.child("users").child(detail_username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("profilePic")){
                    urlProfilePic = snapshot.child("profilePic").getValue().toString();
                    Log.d("profiletTest", urlProfilePic);
                    if(urlProfilePic!=null){
                        Glide.with(getApplicationContext()).load(urlProfilePic).into(profilePic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("profiletTest", error.getDetails());
            }
        });
    }

}