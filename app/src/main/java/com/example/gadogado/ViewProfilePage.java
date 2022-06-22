package com.example.gadogado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gadogado.model.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Vector;

public class ViewProfilePage extends AppCompatActivity{

    TextView username, status_culinary, status_restaurant;
    ImageView profilePic;
    RecyclerView rv;
    private String curr_username, curr_status;
    ProfileAdapter adapt;
    Post temp;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");
    final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");


    String urlProfilePic=null, desc, image, postDate, postId;
    Integer like;
    Vector<Post> posts = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_page);
        username = findViewById(R.id.profile_username);
        status_culinary = findViewById(R.id.profile_status_culinary_hunter);
        status_restaurant = findViewById(R.id.profile_status_restaurant);
        profilePic = findViewById(R.id.profile_image);
        rv = findViewById(R.id.profile_rv);
        adapt = new ProfileAdapter(this);

        //get current username
        curr_username = getIntent().getStringExtra("username");
        Log.d("testUsername", curr_username);
        username.setText(curr_username);

        //get status
        getPhoto();

        //get profile details
        getProfileDetails();

        //recycler view
        adapt.setProfile(posts);
        rv.setAdapter(adapt);
        rv.setLayoutManager(new GridLayoutManager(this, 3));

    }

    public void getPhoto(){
        posts.clear();
        databaseReference1.child("users").child(curr_username).child("post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    postId = data.getKey().toString();
                    desc=data.child("desc").getValue().toString();
                    image = data.child("image").getValue().toString();
                    like = ((Long)data.child("like").getValue()).intValue();
                    postDate = data.child("postDate").getValue().toString();
                    Log.v("profileTest", postId + desc + image + like.toString() +postDate);

                    temp = new Post(curr_username, postId, image, desc, like, postDate);

                    posts.add(temp);
                }

                adapt.notifyDataSetChanged();

                for (Post i: posts) {
                    Log.d("test1231", "test");
                    Log.d("test1231", i.getDesc());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("profiletTest", error.getDetails());
            }

        });
    }

    private void getProfileDetails(){
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(curr_username)){
                    urlProfilePic = snapshot.child(curr_username).child("profilePic").getValue(String.class);
                    curr_status = snapshot.child(curr_username).child("status").getValue(String.class);
                    if(urlProfilePic!=null){
                        Glide.with(getApplicationContext()).load(urlProfilePic).into(profilePic);
                    }

                    if(curr_status.equals("Restaurant")){
                            status_culinary.setVisibility(View.GONE);
                        }else{
                           status_restaurant.setVisibility(View.GONE);
                        }
                    Log.d("profiletTest", curr_username + urlProfilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("profiletTest", error.getDetails());
            }

        });
    }
}