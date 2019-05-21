package aurai.polytechnic.government.GPA;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class onlinetest extends AppCompatActivity {
   String mylink;

    WebView test;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinetest);
        test = findViewById(R.id.onlinetest);

         mylink=getIntent().getStringExtra("Link");
        progressDialog = new ProgressDialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog.setMessage("Loading...");

        if (!isConnected(onlinetest.this)) buildDialog(onlinetest.this).show();
        else {
           test.loadUrl(mylink);
            WebSettings set = test.getSettings();


            set.setJavaScriptEnabled(true);

        }



       test.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                super.onPageStarted(view, url, favicon);


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
                super.onPageFinished(view, url);

            }
        });


    }


    @Override
    public void onBackPressed() {

        if (test.canGoBack()) {
            test.goBack();
        } else {
            super.onBackPressed();
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        test.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
       test.onResume();
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }


    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null &&
                netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;


    }










}




