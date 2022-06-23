package com.example.gadogado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gadogado.model.User;

import java.util.ArrayList;

public class SearchingAdapter extends RecyclerView.Adapter<SearchingAdapter.SearchViewHolder> {
    ArrayList<User> list;
    Context ctx;
    String urlprofile;
    public SearchingAdapter(ArrayList<User> list){
        this.list = list;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.username.setText(list.get(position).getUsernameUser());
        holder.status.setText(list.get(position).getAccountStatus());
        urlprofile = list.get(position).getProfilePicPath();
        Glide.with(ctx).load(urlprofile).into(holder.profile);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView username,status;
        ImageView profile;
        String url;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textViewSearchUsername);
            status = itemView.findViewById(R.id.textViewSearchStatus);
            profile = itemView.findViewById(R.id.search_profile_image);
        }
    }
}
