package com.example.ditebattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {
    ImageView splash_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        splash_logo=(ImageView)findViewById(R.id.splash_logo3);
        Glide.with(this).load(R.raw.splash).into(splash_logo);

        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run(){
                Intent intent = new Intent(getBaseContext(), LoginPage.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}