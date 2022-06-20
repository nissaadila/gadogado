package com.example.gadogado.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gadogado.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.UUID;

public class AddFragment extends Fragment {
    ImageButton upload;
    ImageView photo;
    EditText desc;

    private String username, date;
    public Uri imageUri = null;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");

    public AddFragment(String username) {
        this.username = username;
        Log.wtf("username", username);
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
                Toast.makeText(getContext(), "Please Choose a Photo!", Toast.LENGTH_SHORT).show();
                return;
            }

//            databaseReference.child("users").child(username).child("post").child(randomKey).child("description").setValue(desc);
//            databaseReference.child("users").child(username).child("post").child(randomKey).child("like").setValue(0);
//            databaseReference.child("users").child(username).child("post").child(randomKey).child("postDate").setValue(date);
//            uploadPhoto();

        });

        return view;
    }

    private void init(View view){
        upload = view.findViewById(R.id.upload_button_addPage);
        photo = view.findViewById(R.id.photo_addPage);
        desc = view.findViewById(R.id.description_addPage);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        date = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.getDefault()).format(new Date());
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

        final String randomKey = UUID.randomUUID().toString();
        StorageReference mountainsRef = storageReference.child("images/" + randomKey);

        mountainsRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
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