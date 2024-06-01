package com.taprocycle.service.test.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.taprocycle.service.MainActivity;

public class StoredetailsSession {
    public StoredetailsSession() {

    }

    public static StoredetailsSession bSession;

    public static StoredetailsSession getInstance() {

        if (bSession == null) {
            synchronized (StoredetailsSession.class) {
                bSession = new StoredetailsSession();
            }
        }
        return bSession;
    }

    private static String TAG = BSession.class.getSimpleName();
    SharedPreferences.Editor editor;
    Context _context;

    SharedPreferences prefs;

    private String sessionId;
    private String line1;
    private String line2;
    private String line3;
    private String line4;
    private String pincode;

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public void initialize(Context context,
                           String line1,
                           String line2,
                           String line3,
                           String line4,
                           String pincode) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("line1", line1);
        editor.putString("line2", line2);
        editor.putString("line3", line3);
        editor.putString("line4", line4);
        editor.putString("pincode", pincode);

        editor.apply();

        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
        this.pincode = pincode;

    }


    public void destroy(Context context) {

        SharedPreferences sharedpreferences = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit().clear();
        editor.apply();
        this.line1 = null;
        this.line2 = null;
        this.line3 = null;
        this.line4 = null;
        this.pincode = null;

    }

    public String[] getSession(Context context) {

        SharedPreferences prefs = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        String[] sharedValues = new String[7];

        sharedValues[0] = this.line1 = prefs.getString("line1", "");
        sharedValues[1] = this.line2 = prefs.getString("line2", "");
        sharedValues[2] = this.line3 = prefs.getString("line3", "");
        sharedValues[3] = this.line4 = prefs.getString("line4", "");
        sharedValues[4] = this.pincode = prefs.getString("pincode", "");

        return sharedValues;
    }

    public String getPincode(Context context) {
        getSession(context);
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getKey(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        String key = sharedPreferences.getString("key", "noKey");
        return key;

    }


    public String getSessionId(Context context) {
        getSession(context);
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLine1(Context context) {
        getSession(context);
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2(Context context) {
        getSession(context);
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3(Context context) {
        getSession(context);
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine4(Context context) {
        getSession(context);
        return line4;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }

    public String getPincode() {
        return pincode;
    }
}
