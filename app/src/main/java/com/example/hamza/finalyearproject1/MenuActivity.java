package com.example.hamza.finalyearproject1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    Button compbutton,currentStatus,news,poll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        compbutton = (Button) findViewById(R.id.compButton);
        currentStatus = (Button) findViewById(R.id.currentStatus);
        news = (Button) findViewById(R.id.news);
        poll = (Button) findViewById(R.id.poll);






        compbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,ComplainSubmision.class);
                startActivity(intent);
            }
        });

        currentStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,CurrentStatus.class);
                startActivity(intent);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,News.class);
                startActivity(intent);
            }
        });

        poll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,FollowUp.class);
                startActivity(intent);
            }
        });



    }
}
