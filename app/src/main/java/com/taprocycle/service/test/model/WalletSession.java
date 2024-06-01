package com.taprocycle.service.test.model;

import android.content.Context;
import android.content.SharedPreferences;


public class WalletSession {
    public WalletSession() {

    }

    public static WalletSession bSession;

    public static WalletSession getInstance() {

        if (bSession == null) {
            synchronized (WalletSession.class) {
                bSession = new WalletSession();
            }
        }
        return bSession;
    }

    private static String TAG = WalletSession.class.getSimpleName();
    SharedPreferences.Editor editor;
    Context _context;

    SharedPreferences prefs;

    private String sessionId;

    private String wallet,payment_advance,discount_bonus;

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public void initialize(Context context,
                           String wallet,
                           String payment_advance,
                           String discount_bonus,

                           String sessionId) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("wallet", wallet);
        editor.putString("payment_advance", payment_advance);
        editor.putString("discount_bonus", discount_bonus);

        editor.putString("sessionId", sessionId);

        editor.apply();

        this.wallet = wallet;
        this.payment_advance = payment_advance;
        this.discount_bonus = discount_bonus;

        this.sessionId = sessionId;
    }


    public void destroy(Context context) {

        SharedPreferences sharedpreferences = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit().clear();
        editor.apply();
        this.wallet = null;
        this.sessionId = null;
    }

    public String[] getSession(Context context) {

        SharedPreferences prefs = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        String[] sharedValues = new String[4];


        sharedValues[0] = this.wallet = prefs.getString("wallet", "");
        sharedValues[1] = this.payment_advance = prefs.getString("payment_advance", "");
        sharedValues[2] = this.discount_bonus = prefs.getString("discount_bonus", "");
        sharedValues[3] = this.sessionId = prefs.getString("sessionId", "");

        return sharedValues;
    }



    public String getWallet(Context context) {
        getSession(context);

        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }


    public String getSessionId(Context context) {
        getSession(context);
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public String getPayment_advance(Context context) {
        getSession(context);

        return payment_advance;
    }

    public void setPayment_advance(String payment_advance) {
        this.payment_advance = payment_advance;
    }

    public String getDiscount_bonus(Context context) {
        getSession(context);
        return discount_bonus;
    }

    public void setDiscount_bonus(String discount_bonus) {
        this.discount_bonus = discount_bonus;
    }
}
