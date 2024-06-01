package com.taprocycle.service.test.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.taprocycle.service.MainActivity;


public class BSession {
    public BSession() {

    }

    public static BSession bSession;

    public static BSession getInstance() {

        if (bSession == null) {
            synchronized (BSession.class) {
                bSession = new BSession();
            }
        }
        return bSession;
    }

    private static String TAG = BSession.class.getSimpleName();
    SharedPreferences.Editor editor;
    Context _context;

    SharedPreferences prefs;

    private String sessionId;
    private String user_id;
    private String user_name;
    private String user_mobile;
    private String email;
    private String user_address;
    private String profileimage,pincode;
    private String type;
    private String payment;

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public void initialize(Context context,
                           String user_id,
                           String user_name,
                           String user_mobile,
                           String user_address,
                           String email,
                           String profileimage,
                           String pincode,
                           String sessionId,
                           String type,
                           String payment) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("user_id", user_id);
        editor.putString("user_name", user_name);
        editor.putString("user_mobile", user_mobile);
        editor.putString("user_address", user_address);
        editor.putString("pincode", pincode);
        editor.putString("email", email);
        editor.putString("profileimage", profileimage);
        editor.putString("sessionId", sessionId);
        editor.putString("type",type);
        editor.putString("payment",payment);

        editor.apply();

        this.user_id = user_id;
        this.user_name = user_name;
        this.user_mobile = user_mobile;
        this.user_address = user_address;
/*
        this.user_pincode = user_pincode;
*/
        this.profileimage = profileimage;
        this.pincode=pincode;
        this.email=email;
        this.sessionId = sessionId;
        this.type = type;
        this.payment = payment;

    }


    public void destroy(Context context) {

        SharedPreferences sharedpreferences = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit().clear();
        editor.apply();
        this.user_id = null;
        this.user_name = null;
        this.user_mobile = null;
        this.user_address = null;
        this.email = null;
        this.profileimage = null;
        this.pincode = null;
        this.sessionId = null;
        this.type = null;
        this.payment = null;

    }

    public String[] getSession(Context context) {

        SharedPreferences prefs = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        String[] sharedValues = new String[10];

        sharedValues[0] = this.user_id = prefs.getString("user_id", "");
        sharedValues[1] = this.user_name = prefs.getString("user_name", "");
        sharedValues[2] = this.user_mobile = prefs.getString("user_mobile", "");
        sharedValues[3] = this.user_address = prefs.getString("user_address", "");
        sharedValues[4] = this.pincode = prefs.getString("pincode", "");
        sharedValues[5] = this.email = prefs.getString("email", "");
        sharedValues[6] = this.profileimage = prefs.getString("profileimage", "");
        sharedValues[7] = this.sessionId = prefs.getString("sessionId", "");
        sharedValues[8] = this.type = prefs.getString("type", "");
        sharedValues[9] = this.payment = prefs.getString("payment", "");

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

    public Boolean isAuthenticated(Context context) {

        if (this.sessionId == null || this.user_name == null || this.sessionId.isEmpty() || this.user_name.isEmpty() || this.sessionId.equals("NosessionId") || this.user_name.equals("Nouser_name")) {
            Intent intent = null;
            intent = new Intent(context.getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return false;
        }
        return true;
    }

    public Boolean isApplicationExit(Context context) {

        getSession(context);

        System.out.println("====" + this.sessionId + "UN" + this.user_name);
        if (this.user_name == null || this.user_name.isEmpty() || this.user_name.equals("Nouser_name")) {
            return false;

        }
        System.out.println("====" + this.sessionId + "UN--true" + this.user_name);
        return true;
    }


    public String getSessionId(Context context) {
        getSession(context);
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUser_id(Context context) {
        getSession(context);
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name(Context context) {
        getSession(context);
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mobile(Context context) {
        getSession(context);
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_email(Context context) {
        getSession(context);
        return email;
    }

    public void setUser_email(String user_email) {
        this.email = user_email;
    }

    public String getUser_address(Context context) {
        getSession(context);
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getProfileimage(Context context) {
        getSession(context);
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getType(Context context) {
        getSession(context);
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayment(Context context) {
        getSession(context);
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
