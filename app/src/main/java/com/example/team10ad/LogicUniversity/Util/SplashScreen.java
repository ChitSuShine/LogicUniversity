package com.example.team10ad.LogicUniversity.Util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.team10ad.LogicUniversity.LoginActivity;
import com.example.team10ad.team10ad.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(3*1000);

                    Intent i=new Intent(getBaseContext(),LoginActivity.class);
                    startActivity(i);

                    finish();
                } catch (Exception e) {
                }
            }
        };
        background.start();
    }
}
