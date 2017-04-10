package com.example.caiogalvani.remotecontrolarduino;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash_Activity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent acMain = new Intent(Splash_Activity.this, MainActivity.class);
                startActivity(acMain);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
