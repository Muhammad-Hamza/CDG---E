package com.example.hamza.finalyearproject1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FollowUp extends AppCompatActivity {


    JSONObject jobj;

    JsonParser jsonparser;
     ListView ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up);

         ls = (ListView) findViewById(R.id.listView);

        LoadJS js = new LoadJS();
        js.execute("");
    }


    private class LoadJS extends AsyncTask<String,String,String>
    {
        String resultedData = null;
        @Override
        protected String doInBackground(String...params)
        {
            try {

                String h = "http://localhost:8028/paralellCodes1.asmx/HelloJSON";
                resultedData = jsonparser.getJson(h);
            }
            catch (Exception ex )
            {
                resultedData = "There is an error";

            }
           return resultedData;
        }

        @Override
        protected  void  onPreExecute()
        {


        }


        @Override

        protected  void onPostExecute(String r)
        {

            try {
                ArrayList<Map<String,String>> data = new ArrayList<Map<String, String>>();
                JSONArray jarray = new JSONArray(r);

                for (int i =0 ; i<jarray.length();i++)
                {
                    Map<String,String> datanum = new HashMap<String, String>();
                    jobj = jarray.getJSONObject(i);
                    datanum.put("Town" , jobj.getString("Town"));
                    datanum.put("ComplainType",jobj.getString("ComplainType"));
                    data.add(datanum);
                }
                String[] from = {"Town","ComplainType"};
                int[] views ={R.id.headline,R.id.time};
                final SimpleAdapter ADA = new SimpleAdapter(FollowUp.this,data,R.layout.news,from,views);
                ls.setAdapter(ADA);
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                Log.d("Error Message" , ex.getMessage().toString());
            }
        }
    }
}
