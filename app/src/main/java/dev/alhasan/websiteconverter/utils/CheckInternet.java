package dev.alhasan.websiteconverter.utils;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import dev.alhasan.websiteconverter.MainActivity;

public class CheckInternet {

    private Context context;
    private MainActivity home = new MainActivity();
    final AlertDialog.Builder dialogBuilder;

    public CheckInternet(Context context) {
        this.context = context;
        dialogBuilder = new AlertDialog.Builder(context);
    }


    public boolean isConnected() {

        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Service.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }

    public void connection() {
        if (!isConnected()) {
            dialogBuilder.setTitle("Internet")
                    .setMessage("No Internet Connection Available! Turn On Data First!")
                    .setCancelable(false).
                    setNegativeButton("Mobile Data", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    context.startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                }
            }).setPositiveButton("WiFi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            }).setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    home.onBackPressed();
                }
            }).show();

        }
    }

}
