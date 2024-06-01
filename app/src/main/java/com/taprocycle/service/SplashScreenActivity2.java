package com.taprocycle.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.taprocycle.service.Activity.LoginActivity;
import com.taprocycle.service.test.model.BSession;

import pl.droidsonroids.gif.GifImageView;

public class SplashScreenActivity2 extends AppCompatActivity {
    GifImageView ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        ss = findViewById(R.id.ss);
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BSession.getInstance().getUser_id(getApplicationContext()).equals("")){
                    //  startActivity(new Intent(Splash.this, PaymentProcessActivity.class));
                    Intent intent = new Intent(SplashScreenActivity2.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashScreenActivity2.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(BSession.getInstance().getUser_id(getApplicationContext()).equals("")){
                    //  startActivity(new Intent(Splash.this, PaymentProcessActivity.class));
                    Intent intent = new Intent(SplashScreenActivity2.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashScreenActivity2.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }, 3000);


    }
}