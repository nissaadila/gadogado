package com.example.gadogado.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.gadogado.HomeAdapter;
import com.example.gadogado.R;
import com.example.gadogado.SearchActivity;
import com.example.gadogado.model.Post;
import com.example.gadogado.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Vector;

public class HomeFragment extends Fragment {

    ImageButton searchBtn;
    RecyclerView rv;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");
    private String curr_username, urlProfilePic, username;
    Post temp;
    HomeAdapter adapt;

    String desc, image, postDate, postId;
    Integer like;
    ArrayList<Post> posts = new ArrayList<>();

    public HomeFragment(String username) {
        curr_username = username;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchBtn = view.findViewById(R.id.search_button_homePage);
        rv = view.findViewById(R.id.rv_homePage);
        adapt = new HomeAdapter(getContext());

        posts.clear();
        getPhoto();

        if(posts != null){
            adapt.setHome(posts);
            rv.setAdapter(adapt);
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        searchBtn.setOnClickListener(v -> {
            Intent moveSearch = new Intent(getContext(), SearchActivity.class);
            startActivity(moveSearch);
        });

        return view;
    }

    public void getPhoto(){
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    urlProfilePic = null;
                    if(data.child("post").exists()){
                        username = data.getKey();
                        if(data.child("profilePic").exists()){
                            urlProfilePic = data.child("profilePic").getValue().toString();
                        }
                        collectPhoneNumbers((Map<String,Object>) data.child("post").getValue(), username, urlProfilePic);
                    }
                }
                adapt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("profiletTest", error.getDetails());
            }
        });
    }

    private void collectPhoneNumbers(Map<String,Object> users, String username, String urlProfilePic) {

        for (Map.Entry<String, Object> entry : users.entrySet()){

            Map singleUser = (Map) entry.getValue();

            desc = (String) singleUser.get("desc");
            image = (String) singleUser.get("image");
            like = ((Long) singleUser.get("like")).intValue();
            postDate = (String) singleUser.get("postDate");
            postId = null;

            temp = new Post(urlProfilePic, username, postId, image, desc, like, postDate);

            posts.add(temp);

            Collections.sort(posts, new Comparator<Post>() {
                DateFormat f = new SimpleDateFormat("E, dd MMM yyyy");
                @Override
                public int compare(Post post, Post t1) {
                    if(post.getPostDate() == null || t1.getPostDate() == null)
                        return 0;
                    try {
                        return -1 * (f.parse(post.getPostDate()).compareTo(f.parse(t1.getPostDate())));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });
        }
    }
}