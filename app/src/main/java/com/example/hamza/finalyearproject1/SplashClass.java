package com.example.hamza.finalyearproject1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by hamza on 12/27/2016.
 */

public class SplashClass extends Activity {

   private final int SPLASH_DISPLAY_LENGTH = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
        Intent intent = new Intent(SplashClass.this, FrontActivity.class);
        startActivity(intent);
        finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
