package com.example.hamza.finalyearproject1;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ComplainSubmision extends AppCompatActivity {

    AutoCompleteTextView townTextView,complainTypetextView;
    EditText unioncouncils,desc;
    Button complainNextButton;
    private static final int IMAGE_REQUEST = 1888;
    DBHelper complainDB;
    Bitmap image;
    ImageView imageview;
    byte[] imageInByte;

    ConnectionClass connectionClass;
    ProgressBar progressBar;


    String[] towns ={"Baldia Town","Bin Qasim Town","Kimari Town","Korangi Town","New karachi Town","North Nazimabad Town","Gadap Town","Gulshan Town",
            "Gulberg Town","Landhi town","Lyari town","Liaquatabad town","Orangi Town","Jamshed town","Malir town","Sadar Town",
            "Shah faisal Town","SITE town"};
    String[] complainTypes = {"Complain1","Complain2","Complain3","Complain4","Complain5","Complain6"};




    protected void onActivityResult(int requestCode,int resultCode,Intent intent)

    {
        if(requestCode==IMAGE_REQUEST && resultCode == Activity.RESULT_OK)

        {


            image = (Bitmap) intent.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 0, stream);
            imageInByte = stream.toByteArray();



        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_complain);
        connectionClass = new ConnectionClass();




        //fetchingID's
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        unioncouncils = (EditText)findViewById(R.id.ucField);
        desc = (EditText) findViewById(R.id.descField);
        townTextView = (AutoCompleteTextView) findViewById(R.id.townfield);
        complainTypetextView = (AutoCompleteTextView) findViewById(R.id.complainlist);
        complainNextButton = (Button) findViewById(R.id.button_next);

      //  imageview = (ImageView) findViewById(R.id.imageview);
      //  imageview.setImageBitmap(image);


//Parsing UnionCouncilEdit Text To int




        //Item Town List Autocomplete TextvIew

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,towns);
        townTextView.setAdapter(arrayAdapter);
        townTextView.setThreshold(1);
        final String text = townTextView.getText().toString();
        Log.v("MyActivity",text);


        //ComplainType List


        ArrayAdapter<String> complainTypesadapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,complainTypes);
        complainTypetextView.setAdapter(complainTypesadapter);
        complainTypetextView.setThreshold(1);



        //Camera Intent
       ImageView cameraIntent = (ImageView) findViewById(R.id.camera);
      cameraIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, IMAGE_REQUEST);
                }
            }
        });

        //NextButton Of Complainsubmision
        complainNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InsertComplain insertComp = new InsertComplain();
                insertComp.execute("");
                //InsertDAta into Database
            /**  insertData();
               Intent intent = new Intent(ComplainSubmision.this,ComplainSubmisionAuthentication.class);
                startActivity(intent);**/
            }
        });












        }

    public class InsertComplain extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        String towntext = townTextView.getText().toString();
        String uc = unioncouncils.getText().toString();
        String compType = complainTypetextView.getText().toString();
        String description = desc.getText().toString();



       @Override
        protected void onPreExecute()
       {
           progressBar.setVisibility(View.VISIBLE);
       }

        @Override
        protected void onPostExecute(String r)
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),r,Toast.LENGTH_SHORT).show();

            if(isSuccess)
            {
                Toast.makeText(getApplicationContext(),"Inserted", Toast.LENGTH_SHORT).show();
            }
            else
            {Toast.makeText(getApplicationContext(),"NOt Inserted",Toast.LENGTH_SHORT).show();}

        }

        @Override
        protected String doInBackground(String...params)
        {
            if(towntext.trim().equals(""))
            {
                z= "Plesae enter Town ";
            }
            else
            {
                try
                {
                    Connection conn = connectionClass.CONN();
                    if(conn==null)
                    {
                        z = "Error In Connection";

                    }
                    else
                    {
                        String dates = new SimpleDateFormat("MM/DD/YY", Locale.ENGLISH)
                                .format(Calendar.getInstance().getTime());
                        String query = "insert into ComplainTable(Town,UC,ComplainType,Description) " +
                                "values('"+towntext+"','"+uc+"','"+compType+"','"+description+"')";
                        PreparedStatement preparedStatement = conn.prepareStatement(query);
                        preparedStatement.executeUpdate();
                        z= "Added Successfully";
                        isSuccess = true ;

                    }


                }
                catch (Exception ex)
                {
                    isSuccess = false ;
                    z = "Exception";

                }
            }
            return z;

        }
    }


   /**  public void insertData()
    {
                Boolean isInserted =  complainDB.insertComplain(townTextView.getText().toString(),
                        unioncouncils.getText().toString(),
                        complainTypetextView.getText().toString(),desc.getText().toString(),imageInByte);

                if(isInserted)
                {
                    Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }


**/


    //On camera Intent Result




}

