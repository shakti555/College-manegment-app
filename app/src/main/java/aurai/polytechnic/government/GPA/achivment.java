package aurai.polytechnic.government.GPA;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class achivment extends AppCompatActivity {

    WebView webview;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achivment);
        webview = (WebView) findViewById(R.id.webview3);
        progressDialog=new ProgressDialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog.setMessage("Loading...");

        if (!isConnected(achivment.this)) buildDialog(achivment.this).show();
        else {
            webview.loadUrl("https://www.youtube.com/channel/UCRWnecX_mHVPN7nxDznaR9A/videos");
            WebSettings set = webview.getSettings();


            set.setJavaScriptEnabled(true);

        }
        webview.setWebChromeClient(new Chormebro());


        webview.setWebViewClient(new WebViewClient() {

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

        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        webview.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webview.onResume();
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


    private class Chormebro extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout frame;

        // Initially mOriginalOrientation is set to Landscape
        private int mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        private int mOriginalSystemUiVisibility;

        // Constructor for CustomWebClient
        public Chormebro() {
        }

        public Bitmap getDefaultVideoPoster() {
            if (achivment.this == null) {
                return null;
            }
            return BitmapFactory.decodeResource(achivment.this.getApplicationContext().getResources(), 2130837573);
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback viewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility =achivment.this.getWindow().getDecorView().getSystemUiVisibility();
            // When CustomView is shown screen orientation changes to mOriginalOrientation (Landscape).
            achivment.this.setRequestedOrientation(this.mOriginalOrientation);
            // After that mOriginalOrientation is set to portrait.
            this.mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            this.mCustomViewCallback = viewCallback;
            ((FrameLayout)achivment.this.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            achivment.this.getWindow().getDecorView().setSystemUiVisibility(3846);
        }

        public void onHideCustomView() {
            ((FrameLayout) achivment.this.getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            achivment.this.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            // When CustomView is hidden, screen orientation is set to mOriginalOrientation (portrait).
            achivment.this.setRequestedOrientation(this.mOriginalOrientation);
            // After that mOriginalOrientation is set to landscape.
            this.mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;

        }
    }
}