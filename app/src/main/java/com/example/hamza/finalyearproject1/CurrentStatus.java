package com.example.hamza.finalyearproject1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class CurrentStatus extends AppCompatActivity {

    int num1 = 20;
    int num2 = 20;
    int num3 = 20;
    int num4 = 20;
    int num5 = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_status);

        WebView webview = (WebView)findViewById(R.id.web);
        webview.addJavascriptInterface(new WebAppInterface(),"Android");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/chart.html");
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);

    }


    public class WebAppInterface{



        @JavascriptInterface
        public int getNum1()
        {
            return num1;
        }


        @JavascriptInterface
        public int getNum2()
        {
            return num2;
        }
        @JavascriptInterface
        public int getNum3()
        {
            return num3;
        }
        @JavascriptInterface
        public int getNum4()
        {
            return num4;
        }
        @JavascriptInterface
        public int getNum5()
        {
            return num5;
        }


    }
}
