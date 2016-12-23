package com.example.hamza.finalyearproject1;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.accountkit.AccountKit;

public class AccountKitActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Kitkat and lower has a bug that can cause in correct strict mode
            // warnings about expected activity counts
            enableStrictMode();
        }

        AccountKit.initialize(getApplicationContext());
    }

    public void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}