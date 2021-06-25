package com.example.electrofix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        Thread th = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent obj = new Intent(Splash_Screen.this, Login.class);
                    startActivity(obj);
                }
                }
            };
        th.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

