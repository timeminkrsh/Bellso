package com.taprocycle.service.Util;


import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import android.view.WindowManager;

import com.taprocycle.service.R;

public  class NetworkUtil {
    static Dialog dialog;
    static  String status = null;

    public static String getConnectivityStatusString(Context context) {
        ConnectivityManager cm = (ConnectivityManager)           context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
                return status;
            }
        } else {
            status = "No internet is available";

            return status;
        }
        return status;
    }


    public static void Show(Context context){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.nonetworklayout);

      //  Button Retry = (Button) dialog.findViewById(R.id.Retry);
       // String status1 = NetworkUtil.getConnectivityStatusString(context);

      /*  Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status1.equalsIgnoreCase("No internet is available")){
                     Toast.makeText(context.getApplicationContext(), "Network: " + status1, Toast.LENGTH_LONG).show();

                }else {
                    //dialog.dismiss();
                    Hide();
                }
            }
        });*/

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
    public static void Hide(){
        dialog.dismiss();
    }

}