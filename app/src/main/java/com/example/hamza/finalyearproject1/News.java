package com.example.hamza.finalyearproject1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class News extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        ArrayList<newsClass> newslist = new ArrayList<newsClass>();

        newslist.add(new newsClass("Water Line broke","10-10-10",R.drawable.city));

        newsCustomArrayAdapter customArrayAdapter = new newsCustomArrayAdapter(this,newslist);

        ListView listView = (ListView) findViewById(R.id.activity_news);
        listView.setAdapter(customArrayAdapter);

    }
}
