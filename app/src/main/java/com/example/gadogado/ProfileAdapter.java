package com.example.gadogado;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gadogado.model.Post;

import java.util.Vector;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    Context ctx;
    Vector<Post> post;

    public ProfileAdapter(Context ctx){
        this.ctx = ctx;
    }

    public void setProfile(Vector<Post> post) {
        this.post = post;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        holder.url = post.get(position).getImage();
        Glide.with(ctx).load(holder.url).into(holder.postPic);
        Log.v("resiskleee", "test");
        holder.postDate = post.get(position).getPostDate();
        holder.like = post.get(position).getLike();
        holder.desc = post.get(position).getDesc();
        holder.postId = post.get(position).getPostId();
        holder.username = post.get(position).getUsername();
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;

        ImageView postPic;
        String postId;
        String desc;
        Integer like;
        String postDate, username, url, profilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.postItem);
            postPic = itemView.findViewById(R.id.imagePostItem);
            postPic.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //pindah ke detail post

            Intent intent = new Intent(view.getContext(), PostDetail.class);
            intent.putExtra("postPic", url);
            intent.putExtra("postId", postId);
            intent.putExtra("postDesc", desc);
            intent.putExtra("postDate", postDate);
            intent.putExtra("postLike", like);
            intent.putExtra("username", username);

            view.getContext().startActivity(intent);
        }
    }
}
