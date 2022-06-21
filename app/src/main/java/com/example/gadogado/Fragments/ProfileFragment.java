package com.example.gadogado.Fragments;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.example.gadogado.ProfileAdapter;
import com.example.gadogado.R;
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


public class ProfileFragment extends Fragment implements View.OnClickListener{

    TextView username, status_culinary, status_restaurant;
    ImageView profilePic;
    RecyclerView rv;
    ImageButton camera;
    private String curr_username, curr_status;
    ProfileAdapter adapt;
    public Uri profileUri = null;
    Post temp;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");

    public ProfileFragment(String username, String status) {
        this.curr_username = username;
        this.curr_status = status;
    }

    String urlProfilePic;

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
        profilePic = view.findViewById(R.id.profile_image);
        rv = view.findViewById(R.id.profile_rv);
        camera = (ImageButton) view.findViewById(R.id.profile_upload_photo);
        camera.setOnClickListener(this);
        adapt = new ProfileAdapter(getContext());
        getPhoto();

        statusUpdate();
        username.setText(curr_username);

        //upload profile pic
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //get profile pic
        getProfilePic();


        //recycler view
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
        if(view==camera){
            //Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(i, 1);
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            profileUri = data.getData();
            uploadProfilePic();
            getProfilePic();

        }

    }

    private void uploadProfilePic() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading Profile Picture...");
        progressDialog.show();

        StorageReference imageRef = storageReference.child("profilePics/" + curr_username + "/" + profileUri.getLastPathSegment());

        imageRef.putFile(profileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String photoUrl = uri.toString();
                                Log.wtf("image url", photoUrl);
                                databaseReference.child("users").child(curr_username).child("profilePic").setValue(photoUrl);
                            }
                        });

                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Profile Pic Uploaded!", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), "Failed to Upload", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercentage = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage((int) progressPercentage + "% Uploaded");
                    }
                });
    }

    private void getProfilePic(){
        databaseReference.child("users").child(curr_username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("profilePic")){
                    urlProfilePic = snapshot.child("profilePic").getValue().toString();
                    Log.d("profiletTest", urlProfilePic);
                    Glide.with(getContext()).load(urlProfilePic).into(profilePic);
                    camera.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("profiletTest", error.getDetails());
            }

        });
    }
}