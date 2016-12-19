package com.example.hamza.finalyearproject1;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.facebook.accountkit.AccountKit;

public class FrontActivity extends FragmentActivity {



    Button buttonLogin;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccountKit.initialize(getApplicationContext());

        
        //Finding IDS

        buttonLogin = (Button) findViewById(R.id.loginButton);


        //Wiring up the login button to fragment
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FrontActivity.this, Login_Activity.class);
                startActivity(intent);


            }
        });


    }
}