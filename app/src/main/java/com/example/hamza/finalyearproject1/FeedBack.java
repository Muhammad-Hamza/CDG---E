package com.example.hamza.finalyearproject1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class FeedBack extends AppCompatActivity {

    AutoCompleteTextView complainRes,serviceRender,serviceQuality;
    EditText feedBAck;
    String[] complainsReslvd = {"option1","option2","option3"};
    String[] srviceRender = {"option1","option2","option3"};
    String[] serviceQualty = {"option1","option2","option3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        //IDS
        complainRes = (AutoCompleteTextView) findViewById(R.id.resolved);
        serviceRender = (AutoCompleteTextView) findViewById(R.id.serviceRend);
        serviceQuality = (AutoCompleteTextView) findViewById(R.id.qualityService);
        feedBAck  =(EditText) findViewById(R.id.feedbackk);




        //Adapters
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,complainsReslvd);
        complainRes.setAdapter(arrayAdapter);
        complainRes.setThreshold(1);


        ArrayAdapter<String> arryAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,srviceRender);
        serviceRender.setAdapter(arryAdapter);
        serviceRender.setThreshold(1);

        ArrayAdapter<String> arrrayAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,serviceQualty);
        serviceQuality.setAdapter(arrrayAdapter);
        serviceQuality.setThreshold(1);
    }
}
