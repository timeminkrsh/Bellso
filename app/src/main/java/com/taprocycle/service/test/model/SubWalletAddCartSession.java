package com.taprocycle.service.test.model;

import android.content.Context;
import android.content.SharedPreferences;


public class SubWalletAddCartSession {
    public SubWalletAddCartSession() {

    }

    public static SubWalletAddCartSession bSession;

    public static SubWalletAddCartSession getInstance() {

        if (bSession == null) {
            synchronized (SubWalletAddCartSession.class) {
                bSession = new SubWalletAddCartSession();
            }
        }
        return bSession;
    }

    private static String TAG = SubWalletAddCartSession.class.getSimpleName();
    SharedPreferences.Editor editor;
    Context _context;

    SharedPreferences prefs;

    String sessionId,subwallet_id;

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";



    public void initialize(Context context,
                           String subwallet_id,
                           String sessionId) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("sub_wallet", subwallet_id);
        editor.putString("sessionId", sessionId);

        editor.apply();

        this.subwallet_id = subwallet_id;
        this.sessionId=sessionId;
    }



    public String[] getSession(Context context) {

        SharedPreferences prefs = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        String[] sharedValues = new String[2];


        sharedValues[0] = this.subwallet_id = prefs.getString("sub_wallet", "");
        sharedValues[1] = this.sessionId = prefs.getString("sessionId", "");

        return sharedValues;
    }



    public String getSessionId(Context context) {
        getSession(context);
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSubwallet_id(Context context) {
        getSession(context);
        return subwallet_id;
    }

    public void setSubwallet_id(String subwallet_id) {
        this.subwallet_id = subwallet_id;
    }
}
