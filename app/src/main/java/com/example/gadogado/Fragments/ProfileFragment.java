package com.example.gadogado.Fragments;
import android.media.Image;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gadogado.R;


public class ProfileFragment extends Fragment implements View.OnClickListener{

    TextView username, status_culinary, status_restaurant;
    ImageButton camera;
    ImageView profilePic;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        username = view.findViewById(R.id.profile_username);
        status_culinary = view.findViewById(R.id.profile_status_culinary_hunter);
        status_restaurant = view.findViewById(R.id.profile_status_restaurant);
        camera = view.findViewById(R.id.profile_upload_photo);
        profilePic = view.findViewById(R.id.profile_image);
        recyclerView = view.findViewById(R.id.profile_rv);


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