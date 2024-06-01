package com.taprocycle.service.test.model;

import android.content.Context;
import android.content.SharedPreferences;

/*public class Pincode {
}*/
public class DeliveryAddressSession {
    public DeliveryAddressSession() {

    }

    public static DeliveryAddressSession bSession;

    public static DeliveryAddressSession getInstance() {

        if (bSession == null) {
            synchronized (DeliveryAddressSession.class) {
                bSession = new DeliveryAddressSession();
            }
        }
        return bSession;
    }

    private static String TAG = DeliveryAddressSession.class.getSimpleName();
    SharedPreferences.Editor editor;
    Context _context;

    SharedPreferences prefs;

    private String sessionId;
    private String address,pincode,line1,line2,line3,line4,lanmark,mobile,name;

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public void initialize(Context context,
                           String line1,
                           String line2,
                           String line3,
                           String pincode,
                           String lanmark,
                           String mobile,
                           String name,
                           String sessionId) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("line1", line1);
        editor.putString("line2", line2);
        editor.putString("line3", line3);
        editor.putString("pincode", pincode);
        editor.putString("lanmark", lanmark);
        editor.putString("mobile", mobile);
        editor.putString("name", name);
        editor.putString("sessionId", sessionId);

        editor.apply();
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.pincode = pincode;
        this.lanmark = lanmark;
        this.mobile = mobile;
        this.name = name;
        this.sessionId = sessionId;
    }


    public void destroy(Context context) {

        SharedPreferences sharedpreferences = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit().clear();
        editor.apply();
        this.pincode = null;
        this.address = null;
        this.sessionId = null;
    }

    public String[] getSession(Context context) {

        SharedPreferences prefs = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        String[] sharedValues = new String[9];

        sharedValues[0] = this.line1 = prefs.getString("line1", "");
        sharedValues[1] = this.line2 = prefs.getString("line2", "");
        sharedValues[2] = this.line3 = prefs.getString("line3", "");
        sharedValues[3] = this.pincode = prefs.getString("pincode", "");
        sharedValues[4] = this.lanmark = prefs.getString("lanmark", "");
        sharedValues[5] = this.pincode = prefs.getString("pincode", "");
        sharedValues[6] = this.mobile= prefs.getString("mobile", "");
        sharedValues[7] = this.name= prefs.getString("name", "");
        sharedValues[8] = this.sessionId = prefs.getString("sessionId", "");

        return sharedValues;
    }

    public String getPincode(Context context) {
        getSession(context);
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress(Context context) {
        getSession(context);

        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getLanmark(Context context) {
        getSession(context);

        return lanmark;
    }

    public void setLanmark(String lanmark) {
        this.lanmark = lanmark;
    }

    public String getMobile(Context context) {
        getSession(context);
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName(Context context) {
        getSession(context);
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        String key = sharedPreferences.getString("key", "noKey");
        return key;

    }





}
