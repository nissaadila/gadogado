package com.example.gadogado;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gadogado.model.Post;
import com.example.gadogado.model.User;

import java.util.ArrayList;
import java.util.Vector;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context ctx;
    ArrayList<Post> post;
    String url, urlprofile;

    public HomeAdapter(Context ctx){
        this.ctx = ctx;
    }

    public void setHome(ArrayList<Post> post) {
        this.post = post;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        url = post.get(position).getImage();
        urlprofile = post.get(position).getProfilePic();
        Glide.with(ctx).load(url).into(holder.postPic);
        holder.postDate.setText("" + post.get(position).getPostDate());
        holder.like.setText("" + post.get(position).getLike());
        holder.desc.setText("" + post.get(position).getDesc());
        holder.username.setText("" + post.get(position).getUsername());
        holder.postId = post.get(position).getPostId();
        if(urlprofile != null){
            Glide.with(ctx).load(urlprofile).into(holder.profilePic);
        }
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cardView;

        ImageView postPic, profilePic;
        String postId;
        TextView desc;
        TextView like;
        TextView postDate, username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.itemHome);
            postPic = itemView.findViewById(R.id.post_image);
            profilePic = itemView.findViewById(R.id.profile_homeItem);
            postDate = itemView.findViewById(R.id.date_homePage);
            desc = itemView.findViewById(R.id.desc_homePage);
            like = itemView.findViewById(R.id.home_like);
            username = itemView.findViewById(R.id.username_homeItem);
        }

        @Override
        public void onClick(View view) {
            //ke detail
        }
    }
}

