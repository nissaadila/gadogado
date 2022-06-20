package com.example.gadogado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.database.Transaction;
import com.squareup.okhttp.internal.framed.FrameReader;

public class SplashScreen extends AppCompatActivity {

    private  static  int SPLASH_SCREEN =  2000;

    Animation topAnim , bottomAnim;
    ImageView logo, logoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //Animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo = findViewById(R.id.logoApp);
        logoName = findViewById(R.id.nameLogoApp);

        logo.setAnimation(topAnim);
        logoName.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent move = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(move);
                finish();
            }
        }, SPLASH_SCREEN);


    }
}