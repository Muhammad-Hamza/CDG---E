package com.example.hamza.finalyearproject1;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class JsonParser {


    public String getJson(String Url)
    {

        StringBuilder builder = new StringBuilder();
        String line;
        String responseMessage="";



        try {
            URL url = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Get All Complain","");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
             line = "";
            while ((line=rd.readLine()) !=null)
            {
                builder.append(line);
            }

           responseMessage =  builder.toString();



        }
        catch (IOException e) {
            // Writing exception to log
            e.printStackTrace();
        }

        return responseMessage;



    }


}
