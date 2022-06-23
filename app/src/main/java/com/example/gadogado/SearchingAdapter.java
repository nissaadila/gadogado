package com.example.gadogado;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gadogado.model.Search;
import com.example.gadogado.model.User;

import java.util.ArrayList;

public class SearchingAdapter extends RecyclerView.Adapter<SearchingAdapter.SearchViewHolder> {
    ArrayList<Search> list;
    Context ctx;
    String urlprofile;

    public SearchingAdapter(Context ctx, ArrayList<Search> list){
        this.ctx = ctx;
        this.list = list;
        Log.wtf("data search", String.valueOf(list));
    }


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.curr_username = list.get(position).getUsernameUser();
        holder.username.setText(list.get(position).getUsernameUser());
        holder.status.setText(list.get(position).getAccountStatus());
        urlprofile = list.get(position).getProfilePicPath();
        if(urlprofile != null) {
        Log.wtf("urlProfile", urlprofile);
        Glide.with(ctx).load(urlprofile).into(holder.profile);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView username,status;
        ImageView profile;
        String curr_username;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textViewSearchUsername);
            status = itemView.findViewById(R.id.textViewSearchStatus);
            profile = itemView.findViewById(R.id.search_profile_image);
            username.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), ViewProfilePage.class);
            i.putExtra("username", curr_username);
            view.getContext().startActivity(i);
        }
    }
}
