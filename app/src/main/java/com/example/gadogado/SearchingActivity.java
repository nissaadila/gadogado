package com.example.gadogado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.gadogado.model.Search;
import com.example.gadogado.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchingActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<Search> list;
    RecyclerView recyclerView;
    SearchView searchView;
    Search temp;
    String profilepic, status, username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        recyclerView = findViewById(R.id.rv_search);
        searchView = findViewById(R.id.searchView);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gadogado-5a13c-default-rtdb.firebaseio.com/");
//        list.clear();
        if (databaseReference != null){
            databaseReference.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list = new ArrayList<>();
                        for(DataSnapshot data: snapshot.getChildren()){
                            username = data.getKey().toString();
                            if(data.child("profilePic").exists()){
                                profilepic = data.child("profilePic").getValue().toString();
                            }
                            status = data.child("status").getValue().toString();

                            temp = new Search(username, profilepic, status);
                            Log.wtf("username", username + profilepic + status);
                            list.add(temp);
                        }
                        SearchingAdapter searchingAdapter = new SearchingAdapter(getApplicationContext(), list);
                        recyclerView.setAdapter(searchingAdapter);
                        searchingAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SearchingActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return false;
                }
            });
        }
    }
    private void search(String str){
        ArrayList<Search> mylist = new ArrayList<>();
        for(Search object : list){
            if(object.getUsernameUser().toLowerCase().startsWith(str.toLowerCase())){
                mylist.add(object);
            }
        }

        SearchingAdapter searchingAdapter = new SearchingAdapter(getApplicationContext(),mylist);
        recyclerView.setAdapter(searchingAdapter);
        searchingAdapter.notifyDataSetChanged();
    }
}