package dev.alhasan.websiteconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import dev.alhasan.websiteconverter.utils.AppWebClient;
import dev.alhasan.websiteconverter.utils.CheckInternet;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private CheckInternet check;

    private AlertDialog.Builder dialogBuilder;
    private WebSettings webSettings;
    private AppWebClient webClient;

    private AdView adView;
    private AdRequest adRequest;


    // Change your Website URL Here
    private String WEB_URL = "http://www.familymart.xyz/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("WebURL",WEB_URL);
        init();
        adInit();
    }

    private void init(){
        webView = findViewById(R.id.webView);
        check = new CheckInternet(this);

        dialogBuilder = new AlertDialog.Builder(this);
        webSettings = webView.getSettings();
        webClient = new AppWebClient(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        webInit();
    }

    private void adInit(){

        adView = findViewById(R.id.adView);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        adRequest = new AdRequest.Builder()
                .build();

        adView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            showMessage("Do You Want To Exit?");
        }
    }

    private void showMessage(String message){

        dialogBuilder.setTitle("Alert")
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false).
                setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void webInit(){
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadsImagesAutomatically(true);
        loadPortal();
    }

    private void loadPortal() {
        webView.loadUrl(WEB_URL);
        webView.setWebViewClient(webClient);
        check.connection();

    }
}
