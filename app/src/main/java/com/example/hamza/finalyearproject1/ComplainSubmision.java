package com.example.hamza.finalyearproject1;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ComplainSubmision extends AppCompatActivity {

    AutoCompleteTextView townTextView,complainTypetextView;
    EditText unioncouncils,desc;
    Button complainNextButton,databaseManagerButton;
    private static final int IMAGE_REQUEST = 1888;
    DBHelper complainDB;
    SQLiteDatabase db;
    Bitmap image;
    ImageView imageview;
    byte[] imageInByte;




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

        complainDB = new DBHelper(this);

        //fetchingID's
        unioncouncils = (EditText)findViewById(R.id.ucField);
        desc = (EditText) findViewById(R.id.descField);
        townTextView = (AutoCompleteTextView) findViewById(R.id.townfield);
        complainTypetextView = (AutoCompleteTextView) findViewById(R.id.complainlist);
        complainNextButton = (Button) findViewById(R.id.button_next);
        databaseManagerButton = (Button) findViewById(R.id.databasemanager);
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
                //InsertDAta into Database
              insertData();
               Intent intent = new Intent(ComplainSubmision.this,ComplainSubmisionAuthentication.class);
                startActivity(intent);
            }
        });
        databaseManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbmanager = new Intent(ComplainSubmision.this,AndroidDatabaseManager.class);
                startActivity(dbmanager);

            }
        });











        }


    public void insertData()
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





    //On camera Intent Result




}

