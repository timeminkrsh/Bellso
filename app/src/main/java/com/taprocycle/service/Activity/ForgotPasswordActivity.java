package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import com.taprocycle.service.test.model.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {
    Button forgot;
    ProgressDialog progressDialog;
    EditText et_memberid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sms send.....");
        forgot = findViewById(R.id.forgot);
        et_memberid=findViewById(R.id.et_memberid);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String member= et_memberid.getText().toString().trim();
                if (member.isEmpty()) {
                    et_memberid.setError("*Enter your Member Id");
                    et_memberid.requestFocus();
                    return;
                }
                if (member != null && member != "") {
                    forgot();
                } else {

                }
            }


        });

        toolbar();
    }

    private void toolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setTitle("Forgot Password");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void forgot() {

        final Map<String, String> params = new HashMap<>();
        String para1="?user_id="+ et_memberid.getText().toString().trim();
        progressDialog.show();
        String baseUrl = ProductConfig.forgot_password+para1;

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("success")) {

                        Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(ForgotPasswordActivity.this, "Your password sended to your mobile number", Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(ForgotPasswordActivity.this, "Something went wrong your Your password not sended .. Try it again", Toast.LENGTH_LONG).show();
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