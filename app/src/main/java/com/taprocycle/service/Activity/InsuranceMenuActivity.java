package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsuranceMenuActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener  {
 Spinner spinner;
 Button submit;
    String[] courses = { "Health Insurance", "Two wheeler / Three wheeler",
            "Four wheeler", "Heavy vehicle",
            "Life", "General" };

    EditText name,mobilenumber;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_menu);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating.....");
        spinner=findViewById(R.id.spinner);
        mobilenumber=findViewById(R.id.mobilenumber);
        name=findViewById(R.id.name);
        name.setText(BSession.getInstance().getUser_name(getApplicationContext()));
        mobilenumber.setText(BSession.getInstance().getUser_mobile(getApplicationContext()));
        spinner.setOnItemSelectedListener(this);

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                courses);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spinner.setAdapter(ad);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namee = name.getText().toString().trim();
                String  mobile = mobilenumber.getText().toString().trim();

                if (namee.isEmpty()) {
                    name.setError("*Enter your Name");
                    name.requestFocus();
                    return;
                }
                if (mobile.isEmpty()) {
                    mobilenumber.setError("*Enter your mobilenumber");
                    mobilenumber.requestFocus();
                    return;
                }

                if (namee != null && namee != "" && mobile != null && mobile != "" ) {
                    String customer_id = BSession.getInstance().getUser_id(InsuranceMenuActivity.this);
                    if (customer_id.equalsIgnoreCase("")) {
                        Toast.makeText(InsuranceMenuActivity.this, "Kindly login!", Toast.LENGTH_LONG).show();

                    } else {
                        Pattern p = Pattern.compile("[7-9][0-9]{9}");
                        Matcher match = p.matcher(mobile);
                        if ((match.find())) {
                            insurance();
                        }else{
                            Toast.makeText(getApplicationContext(), "Not a valid mobile number", Toast.LENGTH_LONG).show();
                        }
                    }

                } else {
                }

            }
        });

        toolbar();
    }
    private void insurance() {

        final Map<String, String> params = new HashMap<>();
        String para1="?user_id="+BSession.getInstance().getUser_id(getApplicationContext());
        String para2="&user_name="+name.getText().toString().trim();
        String para3="&insurance_type="+spinner.toString().trim();

        progressDialog.show();

        String baseUrl = ProductConfig.insuranceadd+para1+para2+para3;

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("message") && jsonResponse.getString("message").equals("Successfully Registered")) {

                        Intent intent = new Intent(InsuranceMenuActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(InsuranceMenuActivity.this, "Your insurance has been updated", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(InsuranceMenuActivity.this, "Update failed", Toast.LENGTH_LONG).show();
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

    private void toolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish(); }});
        getSupportActionBar().setTitle("Insurance");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}