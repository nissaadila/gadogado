//package com.example.gadogado;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.gadogado.model.Post;
//import com.example.gadogado.model.User;
//
//import java.util.Vector;
//
//public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
//    Context ctx;
//    Vector<Post> post;
//    String url;
//
//    public ProfileAdapter(Context ctx){
//        this.ctx = ctx;
//    }
//
//    public void setProfile(Vector<Post> post) {
//        this.post = post;
//    }
//
//    @NonNull
//    @Override
//    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(ctx).inflate(R.layout.post_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
//        url = post.get(position).getImage();
//        Glide.with(ctx).load(url).into(holder.postPic);
//        holder.postId = post.get(position).getPostId();
//        holder.desc = post.get(position).getDesc();
//        holder.like = post.get(position).getLike();
//        holder.postDate = post.get(position).getPostDate();
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return post.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        ImageView postPic;
//        Integer postId;
//        String desc;
//        Integer like;
//        String postDate;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            postPic = itemView.findViewById(R.id.imagePostItem);
//            postPic.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            //pindah ke detail post
//        }
//    }
//}
