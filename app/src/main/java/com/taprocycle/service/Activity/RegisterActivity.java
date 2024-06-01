package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
  EditText name,email,mobile,Address;
  ProgressDialog progressDialog;
  ImageView show_1;
  Button register;
  String names,emails,mobiles,address;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating.....");
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        mobile=findViewById(R.id.mobile);
        Address=findViewById(R.id.Address);
        show_1=findViewById(R.id.show_1);
        register=findViewById(R.id.register);

        String mobileobji =BSession.getInstance().getUser_mobile(RegisterActivity.this);
        mobile.setText(mobileobji);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 names = name.getText().toString().trim();
                 emails = email.getText().toString().trim();
                 mobiles = mobile.getText().toString().trim();
                 address = Address.getText().toString().trim();
        System.out.println("email"+emails);
                if (names.isEmpty()) {
                    name.setError("*Enter your name");
                    name.requestFocus();
                    return;
                }
                if (emails.isEmpty()) {
                    email.setError("*Enter your email-id");
                    email.requestFocus();
                    return;
                }
                if (mobiles.isEmpty()) {
                    mobile.setError("*Enter your mobile number");
                    mobile.requestFocus();
                    return;
                }
                if (address.isEmpty()) {
                    Address.setError("*Enter your address");
                    Address.requestFocus();
                    return;
                }

                if (names != null && names != "" && emails != null && emails != "" && mobiles != null && mobiles != ""&& address != null && address != "") {

                    login();
                } else {
                }

            }
        });

    }



  private void login()

  {

      final Map<String, String> params = new HashMap<>();

      String customer_id = BSession.getInstance().getUser_id(this);
      String mobileobji =BSession.getInstance().getUser_mobile(RegisterActivity.this);
      mobile.setText(mobileobji);
      System.out.println("mobilenumber" +mobileobji);
      String para1="?user_id="+customer_id;
      String para2="&user_name="+names;
      String para3="&user_mobile="+mobiles;
      String para4="&user_address="+address;
      String para5="&pincode="+"";
      String para6="&email="+emails;
      String para7="&profileimage="+"";
      String para8="&doj="+"";
      System.out.println("email"+para6);

      progressDialog.show();

      String baseUrl = ProductConfig.userregister+para1+para2+para3+para4+para5+para6+para7+para8;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("success") && jsonResponse.getString("success").equals("1")) {

                        BSession.getInstance().initialize(RegisterActivity.this,
                                jsonResponse.getString("user_id"),
                                jsonResponse.getString("user_name"),
                                jsonResponse.getString("user_mobile"),
                                jsonResponse.getString("user_address"),
                                jsonResponse.getString("email"),"","","","","");

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(RegisterActivity.this, "Your account successfully login", Toast.LENGTH_LONG).show();

                    } else  if (jsonResponse.has("result") && jsonResponse.getString("result").equals("info")) {

                        Toast.makeText(RegisterActivity.this,  jsonResponse.getString("text"), Toast.LENGTH_LONG).show();

                    }
                    else {

                        Toast.makeText(RegisterActivity.this, "\n" +
                                "Something went wrong your account not Login.. Try it again ", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                progressDialog.dismiss();
                String message = null;
                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_LONG).show();

                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(),"Cannot connect to Internet...Please check your connection!" , Toast.LENGTH_LONG).show();

                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(),"Parsing error! Please try again after some time!!" , Toast.LENGTH_LONG).show();

                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),"Cannot connect to Internet...Please check your connection!" , Toast.LENGTH_LONG).show();

                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(),"Connection TimeOut! Please check your internet connection." , Toast.LENGTH_LONG).show();

                    message = "Connection TimeOut! Please check your internet connection.";
                }

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