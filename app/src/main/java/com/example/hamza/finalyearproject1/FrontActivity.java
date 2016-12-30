package com.example.hamza.finalyearproject1;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;

import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.AccountKit;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission_group.PHONE;
import static android.R.attr.permission;


public class FrontActivity extends FragmentActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    Button buttonLogin;



    GoogleApiClient mGoogleApiClient;
    private static final String TAG = "Login Activity";

    TextView _linkSignup;
    ProgressDialog mProgressDialog;
    private static final int RC_SIGN_IN = 9001;
    SignInButton googleSignin;
    Button fbButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //Finding IDS

        googleSignin = (SignInButton) findViewById(R.id.google_sign_in);
        googleSignin.setSize(SignInButton.SIZE_WIDE);
        fbButton = (Button) findViewById(R.id.fb);
        googleSignin.setOnClickListener(this);


        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontActivity.this,ComplainSubmision.class);
                startActivity(intent);
            }
        });
        //GOOGLE SIGN-IN

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(getApplicationContext(), "" + connectionResult, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Signed in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            showProgressDialogue();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });

        }
    }


    private void showProgressDialogue()

    {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignin Result" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount signin_Acount = result.getSignInAccount();

            updateUI(true);
            Intent intent = new Intent(FrontActivity.this, MenuActivity

                    .class);
            startActivity(intent);
        } else {
            updateUI(false);
        }

    }

    private void signIn() {
        Intent signIn_Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signIn_Intent, RC_SIGN_IN);


    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    private void revokeAcces() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    public void updateUI(boolean signedIn) {

        if (signedIn) {
            findViewById(R.id.google_sign_in).setVisibility(View.GONE);
            findViewById(R.id.fb).setVisibility(View.VISIBLE);
        } else {
            //findViewById(R.id.google_sign_in).setVisibility(View.VISIBLE);
            findViewById(R.id.fb).setVisibility(View.GONE);
        }

    }
    //Exiting From Activity
    @Override
    public void onBackPressed() {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        FrontActivity.super.onBackPressed();
                        finish();
                    }
                }).create().show();

    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_sign_in:

                signIn();
                break;


        }

    }


}




      /**  buttonLogin = (Button) findViewById(R.id.loginButton);


        //Wiring up the login button to fragment
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FrontActivity.this, Login_Activity.class);
                startActivity(intent);


            }
        });**/
