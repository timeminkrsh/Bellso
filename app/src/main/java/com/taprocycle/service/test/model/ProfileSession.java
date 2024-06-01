package com.taprocycle.service.test.model;

import android.content.Context;
import android.content.SharedPreferences;


public class ProfileSession {
    public ProfileSession() {

    }

    public static ProfileSession bSession;

    public static ProfileSession getInstance() {

        if (bSession == null) {
            synchronized (ProfileSession.class) {
                bSession = new ProfileSession();
            }
        }
        return bSession;
    }

    private static String TAG = ProfileSession.class.getSimpleName();
    SharedPreferences.Editor editor;
    Context _context;

    SharedPreferences prefs;

    private String sessionId,user_name,user_mobile,user_email,user_address,user_fathername,user_gender,user_dob,user_nominee,user_relationship;
    private String address_line1,address_line2,address_line3,address_line4,user_pincode,user_landmark,user_state;
    private String user_pannumber,pan_img;
    private String bankname,ifsc,branch,account_number,bank_img;
    private String user_aadharnumber,aadhar_img;
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public void initialize(Context context,
                           String user_name,
                           String user_fathername,
                           String user_email,
                           String user_mobile,
                           String user_gender,
                           String user_dob,
                           String user_nominee,
                           String user_relationship,
                           String address_line1,
                           String address_line2,
                           String address_line3,
                           String address_line4,
                           String user_pincode,
                           String user_landmark,
                           String user_state,
                           String user_pannumber,
                           String pan_img,
                           String bankname,
                           String ifsc,
                           String branch,
                           String account_number,
                           String bank_img,
                           String user_aadharnumber,
                           String aadhar_img,
                           String sessionId) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("user_name", user_name);
        editor.putString("user_fathername", user_fathername);
        editor.putString("user_email", user_email);
        editor.putString("user_mobile", user_mobile);
        editor.putString("user_gender", user_gender);
        editor.putString("user_dob", user_dob);
        editor.putString("user_nominee", user_nominee);
        editor.putString("user_relationship", user_relationship);
        editor.putString("address_line1", address_line1);
        editor.putString("address_line2", address_line2);
        editor.putString("address_line3", address_line3);
        editor.putString("address_line4", address_line4);
        editor.putString("user_pincode", user_pincode);
        editor.putString("user_landmark", user_landmark);
        editor.putString("user_state", user_state);
        editor.putString("user_pannumber", user_pannumber);
        editor.putString("pan_img", pan_img);
        editor.putString("bankname", bankname);
        editor.putString("ifsc", ifsc);
        editor.putString("branch", branch);
        editor.putString("account_number", account_number);
        editor.putString("bank_img", bank_img);
        editor.putString("user_aadharnumber", user_aadharnumber);
        editor.putString("aadhar_img", aadhar_img);

        editor.apply();

        this.user_name = user_name;
        this.user_fathername = user_fathername;
        this.user_email = user_email;
        this.user_mobile = user_mobile;
        this.user_gender = user_gender;
        this.user_dob = user_dob;
        this.user_nominee = user_nominee;
        this.user_relationship = user_relationship;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.address_line3 = address_line3;
        this.address_line4 = address_line4;
        this.user_pincode = user_pincode;
        this.user_landmark = user_landmark;
        this.user_state = user_state;
        this.user_pannumber = user_pannumber;
        this.pan_img = pan_img;
        this.bankname = bankname;
        this.ifsc = ifsc;
        this.branch = branch;
        this.account_number = account_number;
        this.bank_img = bank_img;
        this.user_aadharnumber = user_aadharnumber;
        this.aadhar_img = aadhar_img;

        this.sessionId = sessionId;
    }




    public String[] getSession(Context context) {

        SharedPreferences prefs = context.getSharedPreferences("Krishnan Store", Context.MODE_PRIVATE);
        String[] sharedValues = new String[25];


        sharedValues[0] = this.user_name = prefs.getString("user_name", "");
        sharedValues[1] = this.user_fathername = prefs.getString("user_fathername", "");
        sharedValues[2] = this.user_email = prefs.getString("user_email", "");
        sharedValues[3] = this.user_mobile = prefs.getString("user_mobile", "");
        sharedValues[4] = this.user_gender = prefs.getString("user_gender", "");
        sharedValues[5] = this.user_dob = prefs.getString("user_dob", "");
        sharedValues[6] = this.user_nominee = prefs.getString("user_nominee", "");
        sharedValues[7] = this.user_relationship = prefs.getString("user_relationship", "");
        sharedValues[8] = this.address_line1 = prefs.getString("address_line1", "");
        sharedValues[9] = this.address_line2 = prefs.getString("address_line2", "");
        sharedValues[10] = this.address_line3 = prefs.getString("address_line3", "");
        sharedValues[11] = this.address_line4 = prefs.getString("address_line4", "");
        sharedValues[12] = this.user_pincode = prefs.getString("user_pincode", "");
        sharedValues[13] = this.user_landmark = prefs.getString("user_landmark", "");
        sharedValues[14] = this.user_state = prefs.getString("user_state", "");
        sharedValues[15] = this.user_pannumber = prefs.getString("user_pannumber", "");
        sharedValues[16] = this.pan_img = prefs.getString("pan_img", "");
        sharedValues[17] = this.bankname = prefs.getString("bankname", "");
        sharedValues[18] = this.ifsc = prefs.getString("ifsc", "");
        sharedValues[19] = this.branch = prefs.getString("branch", "");
        sharedValues[20] = this.account_number = prefs.getString("account_number", "");
        sharedValues[21] = this.bank_img = prefs.getString("bank_img", "");
        sharedValues[22] = this.user_aadharnumber = prefs.getString("user_aadharnumber", "");
        sharedValues[23] = this.aadhar_img = prefs.getString("aadhar_img", "");
        sharedValues[24] = this.sessionId = prefs.getString("sessionId", "");

        return sharedValues;
    }



    public String getSessionId(Context context) {
        getSession(context);
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_address(Context context) {
        getSession(context);
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_fathername(Context context) {
        getSession(context);
        return user_fathername;
    }

    public void setUser_fathername(String user_fathername) {
        this.user_fathername = user_fathername;
    }

    public String getUser_gender(Context context) {
        getSession(context);
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_dob(Context context) {
        getSession(context);
        return user_dob;
    }

    public void setUser_dob(String user_dob) {
        this.user_dob = user_dob;
    }

    public String getUser_nominee(Context context) {
        getSession(context);
        return user_nominee;
    }

    public void setUser_nominee(String user_nominee) {
        this.user_nominee = user_nominee;
    }

    public String getUser_relationship(Context context) {
        getSession(context);
        return user_relationship;
    }

    public void setUser_relationship(String user_relationship) {
        this.user_relationship = user_relationship;
    }

    ////


    public String getAddress_line1(Context context) {
        getSession(context);
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2(Context context) {
        getSession(context);
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getAddress_line3(Context context) {
        getSession(context);
        return address_line3;
    }

    public void setAddress_line3(String address_line3) {
        this.address_line3 = address_line3;
    }

    public String getAddress_line4(Context context) {
        getSession(context);
        return address_line4;
    }

    public void setAddress_line4(String address_line4) {
        this.address_line4 = address_line4;
    }

    public String getUser_pincode(Context context) {
        getSession(context);
        return user_pincode;
    }

    public void setUser_pincode(String user_pincode) {
        this.user_pincode = user_pincode;
    }

    public String getUser_landmark(Context context) {
        getSession(context);
        return user_landmark;
    }

    public void setUser_landmark(String user_landmark) {
        this.user_landmark = user_landmark;
    }

    public String getUser_state(Context context) {
        getSession(context);
        return user_state;
    }

    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }

    //


    public String getUser_pannumber(Context context) {
        getSession(context);
        return user_pannumber;
    }

    public void setUser_pannumber(String user_pannumber) {
        this.user_pannumber = user_pannumber;
    }

    public String getPan_img(Context context) {
        getSession(context);
        return pan_img;
    }

    public void setPan_img(String pan_img) {
        this.pan_img = pan_img;
    }

    public String getBankname(Context context) {
        getSession(context);
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getIfsc(Context context) {
        getSession(context);
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getBranch(Context context) {
        getSession(context);
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAccount_number(Context context) {
        getSession(context);
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getBank_img(Context context) {
        getSession(context);
        return bank_img;
    }

    public void setBank_img(String bank_img) {
        this.bank_img = bank_img;
    }

    public String getUser_aadharnumber(Context context) {
        getSession(context);
        return user_aadharnumber;
    }

    public void setUser_aadharnumber(String user_aadharnumber) {
        this.user_aadharnumber = user_aadharnumber;
    }

    public String getAadhar_img(Context context) {
        getSession(context);
        return aadhar_img;
    }

    public void setAadhar_img(String aadhar_img) {
        this.aadhar_img = aadhar_img;
    }
}
