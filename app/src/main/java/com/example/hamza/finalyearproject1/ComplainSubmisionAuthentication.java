package com.example.hamza.finalyearproject1;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.facebook.appevents.AppEventsLogger;



public class ComplainSubmisionAuthentication extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_complain_submision_authentication);

        AppEventsLogger.activateApp(this);




    }
}
