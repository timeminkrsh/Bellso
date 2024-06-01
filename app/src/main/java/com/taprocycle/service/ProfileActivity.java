package com.taprocycle.service;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.taprocycle.service.Activity.AccountActivity;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import java.util.Map;
import java.util.regex.Pattern;



public class ProfileActivity extends AppCompatActivity{
    Button update,personal_sumbit,primary_sumbit,pan_sumbit,bank_sumbit,aadhar_sumbit;
    EditText et_name,et_emailid,et_mobile,et_state;

    String names,mobils,emails,citys;
    ProgressDialog progressDialog;
    Button update_m;
    Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("uploading.....");

        init();
        toolbar();
    }
    private void toolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setTitle("My Profile");
        toolbar.setTitleTextColor(Color.WHITE);
    }
    public void init() {
        et_name = findViewById(R.id.et_name);
        et_emailid = findViewById(R.id.et_emailid);
        et_mobile = findViewById(R.id.et_mobile);
        et_state = findViewById(R.id.et_state);
        update_m = findViewById(R.id.update_m);

        String mobileobji = BSession.getInstance().getUser_mobile(ProfileActivity.this);
        final String nameobjs = BSession.getInstance().getUser_name(ProfileActivity.this);
        final String emailobj = BSession.getInstance().getUser_email(ProfileActivity.this);
        final String cittt = BSession.getInstance().getUser_address(ProfileActivity.this);

        et_mobile.setText(mobileobji);
        et_name.setText(nameobjs);
        et_emailid.setText(emailobj);
        et_state.setText(cittt);
        System.out.println("namess"+mobileobji);
        System.out.println("namess"+nameobjs);
        System.out.println("namess"+emailobj);

        update_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                names = et_name.getText().toString().trim();
                mobils = et_mobile.getText().toString().trim();
                emails = et_emailid.getText().toString().trim();
                citys = et_state.getText().toString().trim();


                if (names.isEmpty()) {
                    et_name.setError("*Enter your name");
                    et_name.requestFocus();
                    return;
                }
                if (mobils.isEmpty()) {
                    et_emailid.setError("*Enter your Mobile number");
                    et_mobile.requestFocus();
                    return;
                }
                if (emails.isEmpty()) {
                    et_emailid.setError("*Enter your Email");
                    et_emailid.requestFocus();
                    return;
                }

                if (names != null && names != "" && mobils != null && mobils != "" && emails !=null && emails!="" && citys !=null && citys!="" ) {
                    register();

                } else {
                    Toast.makeText(ProfileActivity.this, "Please enter your details!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void register() {


        final Map<String, String> params = new HashMap<>();

        String customer_id = BSession.getInstance().getUser_id(this);
        String mobileobji =BSession.getInstance().getUser_mobile(ProfileActivity.this);
        System.out.println("mobilenumber" +mobileobji);
        String para1="?user_id="+customer_id;
        String para2="&user_name="+names;
        String para3="&user_mobile="+mobils;
        String para4="&user_address="+citys;
        String para5="&pincode="+"";
        String para6="&email="+emails;
        String para7="&profileimage="+"";
        String para8="&doj="+"";
        progressDialog.show();

        String baseUrl = ProductConfig.profileupdate+para1+para2+para3+para4+para5+para6+para7+para8;

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("message") && jsonResponse.getString("message").equals("Successfully Registered")) {
                        BSession.getInstance().initialize(ProfileActivity.this,
                                jsonResponse.getString("user_id"),
                                jsonResponse.getString("user_name"),
                                jsonResponse.getString("user_mobile"),
                                jsonResponse.getString("user_address"),
                                jsonResponse.getString("email"),
                                "", "","","","");

                        Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(ProfileActivity.this, "Update failed", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("Error", error.toString());
                progressDialog.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



    }

}