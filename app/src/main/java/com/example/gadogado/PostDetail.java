package com.example.gadogado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class PostDetail extends AppCompatActivity {

    TextView desc, like, username, date;
    ImageView profilePic, img;

    String detail_desc, detail_username, detail_date, detail_img, detail_id;
    Integer detail_like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        desc = findViewById(R.id.detail_description);
        like = findViewById(R.id.detail_like);
        username = findViewById(R.id.detail_username);
        date = findViewById(R.id.detail_date);
        profilePic = findViewById(R.id.detail_profile_image);
        img = findViewById(R.id.detail_pic);

        detail_desc = getIntent().getStringExtra("postDesc");
        detail_date = getIntent().getStringExtra("postDate");
        detail_like = getIntent().getIntExtra("postLike", 0);
        detail_img = getIntent().getStringExtra("postPic");
        detail_username = getIntent().getStringExtra("username");
        detail_id = getIntent().getStringExtra("postId");

        desc.setText(detail_desc);
        like.setText(String.valueOf(detail_like));
        username.setText(detail_username);
        Glide.with(this).load(detail_img).into(img);

        //date

        String date_now = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.getDefault()).format(new Date());
        int dateDiff = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), detail_date, date_now);
        if (dateDiff<=1){
            date.setText("today");
        }else{
            date.setText(dateDiff + " days ago");
        }

        //set profile pic


    }

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }



}