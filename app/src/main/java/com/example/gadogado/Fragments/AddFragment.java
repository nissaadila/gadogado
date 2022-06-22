package com.example.gadogado.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gadogado.MainActivity;
import com.example.gadogado.R;
import com.example.gadogado.model.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddFragment extends Fragment {
    ImageButton upload;
    ImageView photo;
    EditText desc;
    Post post = new Post();

    private String username, date;
    public Uri imageUri = null;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");

    public AddFragment(String username) {
        this.username = username;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add, container, false);
        init(view);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 1);
            }
        });

        upload.setOnClickListener(v -> {
            if(desc.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Description cannot Empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(imageUri == null){
                Toast.makeText(getContext(), "Please Select Photo!", Toast.LENGTH_SHORT).show();
                return;
            }

            saveToFireBase();
            uploadPhoto();

            DatabaseReference postReference = databaseReference.child("users").child(username).child("post").push();
            post.setImage("");
            postReference.setValue(post);
            String key = postReference.getKey();
            post.setPostId(key);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }, 5000);
        });

        return view;
    }

    private void saveToFireBase() {
        post.setDesc(desc.getText().toString());
        post.setLike(0);
        post.setPostDate(date);
    }

    private void init(View view){
        upload = view.findViewById(R.id.upload_button_addPage);
        photo = view.findViewById(R.id.photo_addPage);
        desc = view.findViewById(R.id.description_addPage);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        date = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        Log.wtf("date", date);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            imageUri = data.getData();
            photo.setImageURI(imageUri);
        }

    }

    private void uploadPhoto() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading Image...");
        progressDialog.show();

        StorageReference imageRef = storageReference.child("images/" + username + "/" + imageUri.getLastPathSegment());

        imageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String photoUrl = uri.toString();
                                Log.wtf("image url", photoUrl);
                                databaseReference.child("users").child(username).child("post").child(post.getPostId()).child("image").setValue(photoUrl);
                                post.setImage(photoUrl);
                            }
                        });

                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Image Uploaded!", Snackbar.LENGTH_LONG).show();
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
}