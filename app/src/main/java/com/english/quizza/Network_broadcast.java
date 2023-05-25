package com.english.quizza;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.english.quizza.databinding.InternetConnectionBinding;

public class Network_broadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
             if (!isNetworkConnected(context)){
                 InternetConnectionBinding binding = InternetConnectionBinding.inflate(LayoutInflater.from(context));
                 AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 builder.setView(binding.getRoot());
                 builder.setCancelable(false);
                 Dialog dialog = builder.create();
                 dialog.show();
              binding.btnCheck.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if (isNetworkConnected(context)){
                          dialog.dismiss();
                      }
                  }
              });

             }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isNetworkConnected(Context context){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return  networkInfo != null && networkInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
