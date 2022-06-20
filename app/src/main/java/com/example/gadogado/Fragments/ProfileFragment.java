package com.example.gadogado.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gadogado.LoginActivity;
import com.example.gadogado.MainActivity;
import com.example.gadogado.ProfileAdapter;
import com.example.gadogado.R;
import com.example.gadogado.model.Post;
import com.example.gadogado.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Vector;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    TextView username, status_culinary, status_restaurant;
    Button camera;
    ImageView profilePic;
    RecyclerView recyclerView;

    ProfileAdapter profileAdapter;
    Vector<Post> posts = new Vector<>();
    User curr_user;

    FirebaseFirestore firebaseFirestore;
    DatabaseReference databaseReference;
    //String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        username = view.findViewById(R.id.profile_username);
        status_culinary = view.findViewById(R.id.profile_status_culinary_hunter);
        status_restaurant = view.findViewById(R.id.profile_status_restaurant);
        camera = view.findViewById(R.id.profile_upload_photo);
        profilePic = view.findViewById(R.id.profile_upload_photo);
        recyclerView = view.findViewById(R.id.profile_rv);
        camera.setOnClickListener(this);


//        profileAdapter = new ProfileAdapter(getContext());
//        profileAdapter.setProfile(posts);
//        recyclerView.setAdapter(profileAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }


}