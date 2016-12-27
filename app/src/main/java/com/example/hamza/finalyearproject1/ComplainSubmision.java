package com.example.hamza.finalyearproject1;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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



    private  static final  int FRAMEWORK_REQUEST_CODE = 1;
    private int nextPermisionRequestCode = 4000;
    Button buttonLogin;


    private final Map<Integer, OnCompleteListener> permissionsListeners = new HashMap<>();

    private  interface  OnCompleteListener {

        void onComplete();

    }

   /** protected void onActivityResult(int requestCode,int resultCode,Intent intent)

    {
        if(requestCode==IMAGE_REQUEST && resultCode == Activity.RESULT_OK)

        {


            image = (Bitmap) intent.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 0, stream);
            imageInByte = stream.toByteArray();



        }
    }
**/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountKit.initialize(getApplicationContext());
        setContentView(R.layout.activity_submit_complain);
        connectionClass = new ConnectionClass();




        //fetchingID's

        unioncouncils = (EditText)findViewById(R.id.ucField);
        desc = (EditText) findViewById(R.id.descField);
        townTextView = (AutoCompleteTextView) findViewById(R.id.townfield);
        complainTypetextView = (AutoCompleteTextView) findViewById(R.id.complainlist);
        complainNextButton = (Button) findViewById(R.id.button_next);

      //  imageview = (ImageView) findViewById(R.id.imageview);
      //  imageview.setImageBitmap(image);


        //AcountKit Integration
        if(AccountKit.getCurrentAccessToken() != null)
        {
            startActivity(new Intent(this,TokenActivity.class));
        }






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
   /**     complainNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InsertComplain insertComp = new InsertComplain();
                insertComp.execute("");
                //InsertDAta into Database
           insertData();
               Intent intent = new Intent(ComplainSubmision.this,ComplainSubmisionAuthentication.class);
                startActivity(intent);
            }
        });**/












        }


    //<--------------------------------------------------AccountKit------------------------------------------------->
    public void onLoginPhone(final View view) { onLogin(LoginType.PHONE);}

    @Override
    protected void onActivityResult (
            final  int requestCode,
            final int resultCode,
            final Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode != FRAMEWORK_REQUEST_CODE){
            return;
        }

        final String toastMessage;
        final AccountKitLoginResult loginResult = AccountKit.loginResultWithIntent(data);
        if(loginResult == null || loginResult.wasCancelled()) {
            toastMessage = "Login Cancelled";

        }else if (loginResult.getError() != null){
            toastMessage = loginResult.getError().getErrorType().getMessage();
            final Intent intent = new Intent (this,ErrorActivity.class);
            intent.putExtra(ErrorActivity.HELLO_TOKEN_ACTIVITY_ERROR_EXTRA,loginResult.getError());
            startActivity(intent);
        }else {
            final AccessToken accessToken = loginResult.getAccessToken();
            final long tokenRefreshIntervalInSeconds = loginResult.getTokenRefreshIntervalInSeconds();
            if(accessToken !=null) {
                toastMessage = "Success: " + accessToken.getAccountId()
                        + tokenRefreshIntervalInSeconds;
                startActivity(new Intent(this, TokenActivity.class));
            }else {
                toastMessage = "Unknown response Type";
            }
        }
        Toast.makeText(
                this,
                toastMessage,
                Toast.LENGTH_SHORT
        ).show();
    }
    private void onLogin(final  LoginType loginType)
    {
        final Intent intent = new Intent(this, com.facebook.accountkit.ui.AccountKitActivity.class);
        final AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder
                = new  AccountKitConfiguration.AccountKitConfigurationBuilder(
                loginType,
                com.facebook.accountkit.ui.AccountKitActivity.ResponseType.TOKEN);

        final  AccountKitConfiguration configuration = configurationBuilder.build();
        intent.putExtra(
                com.facebook.accountkit.ui.AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configuration);

        OnCompleteListener completeListener = new OnCompleteListener() {
            @Override
            public void onComplete() {
                startActivityForResult(intent,FRAMEWORK_REQUEST_CODE);
            }
        };
        switch(loginType){
            case PHONE:
                if(configuration.isReceiveSMSEnabled()){
                    final OnCompleteListener receiveSMSCompleteListener = completeListener;

                    completeListener = new OnCompleteListener() {
                        @Override
                        public void onComplete() {
                            requestPermisions( Manifest.permission.RECEIVE_SMS,
                                    R.string.permissions_receive_sms_title,
                                    R.string.permissions_receive_sms_message,
                                    receiveSMSCompleteListener);

                        }



                    };
                }
                if(configuration.isReadPhoneStateEnabled())
                { final OnCompleteListener readPhoneStateCompleteListener = completeListener;
                    completeListener = new OnCompleteListener() {
                        @Override
                        public void onComplete() {
                            requestPermisions( Manifest.permission.READ_PHONE_STATE,
                                    R.string.permissions_read_phone_state_title,
                                    R.string.permissions_read_phone_state_message,
                                    readPhoneStateCompleteListener);

                        }
                    };
                    break;

                }



        }
        completeListener.onComplete();





    }
    private void  requestPermisions(
            final  String permission,
            final int rationaleTitleResourceId,
            final int rationaleMessageResourceId,
            final OnCompleteListener listener){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            if(listener != null){
                listener.onComplete();

            }
            return;
        }
        checkRequestPermissions(
                permission,
                rationaleTitleResourceId,
                rationaleMessageResourceId,listener);
    }

    @TargetApi(23)
    private void checkRequestPermissions(
            final String permission,
            final int rationaleTitleResourceId,
            final int rationaleMessageResourceId,
            final OnCompleteListener listener)
    {
        if(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
        {
            if(listener!=null){
                listener.onComplete();
            }
            return;
        }
        final int requestCode = nextPermisionRequestCode++;
        permissionsListeners.put(requestCode,listener);

        if(shouldShowRequestPermissionRationale(permission)){
            new android.app.AlertDialog.Builder(this)
                    .setTitle(rationaleTitleResourceId)
                    .setMessage(rationaleMessageResourceId)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[] {permission}, requestCode);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            // ignore and clean up the listener
                            permissionsListeners.remove(requestCode);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            requestPermissions(new String[]{ permission }, requestCode);
        }
    }

    @TargetApi(23)
    @SuppressWarnings("unused")
    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           final @NonNull String permissions[],
                                           final @NonNull int[] grantResults) {
        final OnCompleteListener permissionsListener = permissionsListeners.remove(requestCode);
        if (permissionsListener != null
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionsListener.onComplete();
        }
    }




//<-------------------------------------------------------AccountKitEnds---------------------------------------------->

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

