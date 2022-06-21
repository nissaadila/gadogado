package com.example.gadogado.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.example.gadogado.HomeAdapter;
import com.example.gadogado.ProfileAdapter;
import com.example.gadogado.R;
import com.example.gadogado.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Vector;

public class HomeFragment extends Fragment {

    ImageButton searchBtn;
    RecyclerView rv;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");
    private String curr_username;
    Post temp;
    HomeAdapter adapt;
    String urlProfilePic;

    String desc, image, postDate, postId;
    Integer like;
    Vector<Post> posts = new Vector<>();

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

        getprofilepic();
        getPhoto();

        if(posts != null){
            adapt.setHome(posts, urlProfilePic);
            rv.setAdapter(adapt);
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        searchBtn.setOnClickListener(v -> {
            // ke search page
        });

        return view;
    }

    private void getprofilepic() {
        databaseReference.child("users").child(curr_username).child("profilePic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                urlProfilePic = snapshot.getValue(String.class);
                Log.wtf("url Profilepic", urlProfilePic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.wtf("error url", "gagal set username");
            }
        });
    }

    public void getPhoto(){
        posts.clear();
        databaseReference.child("users").child(curr_username).child("post").addListenerForSingleValueEvent(new ValueEventListener() {
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
}