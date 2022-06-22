package com.example.gadogado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gadogado.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText searchTextField;
    ImageButton buttonSearch;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        searchTextField = findViewById(R.id.editTextSearchContoh);
        buttonSearch = findViewById(R.id.search_button_contoh);
        recyclerView = findViewById(R.id.rv_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String searchText = searchTextField.getText().toString();
        buttonSearch.setOnClickListener(v -> {
            firebaseSearch(searchText);
        });



    }

    private  void firebaseSearch(String searchText){
        Query firebaseQuery = databaseReference.orderByChild("users").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerAdapter<User, SearchViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, SearchViewHolder>(

                User.class,
                R.layout.search_item,
                SearchViewHolder.class,
                firebaseQuery
        ) {
            @Override
            protected void populateViewHolder(SearchViewHolder searchViewHolder, User user, int i) {
                searchViewHolder.setSearch(getApplicationContext(),user.getUsernameUser(), user.getAccountStatus(),user.getProfilePicPath());


            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder{
        View view;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setSearch(Context ctx, String userName,String userStatus, String userImage){
            TextView username = findViewById(R.id.textViewSearchUsername);
            TextView status = findViewById(R.id.textViewSearchStatus);
            ImageView profile = findViewById(R.id.search_profile_image);

            username.setText(userName);
            status.setText(userStatus);
            Glide.with(ctx).load(userImage).into(profile);
        }

    }




}