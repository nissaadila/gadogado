package com.example.gadogado.Fragments;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gadogado.ProfileAdapter;
import com.example.gadogado.R;
import com.example.gadogado.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;


public class ProfileFragment extends Fragment implements View.OnClickListener{

    TextView username, status_culinary, status_restaurant;
    ImageButton camera;
    ImageView profilePic;
    RecyclerView rv;
    private String curr_username, curr_status;
    ProfileAdapter adapt;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");

    public ProfileFragment(String username, String status) {
        this.curr_username = username;
        this.curr_status = status;
    }

    String desc, image, postDate, postId;
    Integer like;
    Vector<Post> posts = new Vector<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        username = view.findViewById(R.id.profile_username);
        status_culinary = view.findViewById(R.id.profile_status_culinary_hunter);
        status_restaurant = view.findViewById(R.id.profile_status_restaurant);
        camera = view.findViewById(R.id.profile_upload_photo);
        profilePic = view.findViewById(R.id.profile_image);
        rv = view.findViewById(R.id.profile_rv);
        adapt = new ProfileAdapter(getContext());
        getPhoto();
        Log.d("test1231", "hjal");
        Log.d("statustest", curr_status);

        statusUpdate();

        username.setText(curr_username);

        adapt.setProfile(posts);
        rv.setAdapter(adapt);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 3));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }

    public void statusUpdate(){
        if(curr_status.equals("Restaurant")){
            status_culinary.setVisibility(View.GONE);
        }else{
            status_restaurant.setVisibility(View.GONE);
        }
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

                    Post temp = new Post(curr_username, postId, image, desc, like, postDate);
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