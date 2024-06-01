package com.taprocycle.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taprocycle.service.Activity.AccountActivity;
import com.taprocycle.service.Activity.CartActivity;
import com.taprocycle.service.Activity.DeliveryAddressActivity;
import com.taprocycle.service.Activity.OrdersDetailActivity;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.DeliveryAddressModel;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ZipcodeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.parser.ParseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditAddressActivity extends AppCompatActivity {
    EditText et_name,et_phone,et_line1,et_line2,et_line3,et_line4,et_landmark;
    Button sumbit;
    AutoCompleteTextView atv_pincode;
    ProgressDialog progressDialog;
    boolean cartaddress,deliveryaddress;
    List<ZipcodeModel> apiZipcodeList = new ArrayList<>();
    ArrayList<String> pincodelist = new ArrayList<String>();
    String etname,etphone,etline1,etline2,etline3,pincode,landmark,id;
    List<DeliveryAddressModel> deliveryAddressModelList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");

        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_line1 = findViewById(R.id.et_line1);
        et_line2 = findViewById(R.id.et_line2);
        et_line3 = findViewById(R.id.et_line3);
        atv_pincode = findViewById(R.id.atv_pincode);
        et_landmark = findViewById(R.id.et_landmark);
        sumbit = findViewById(R.id.sumbit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");

            etname = bundle.getString("etname");
            et_name.setText(etname);

            etphone = bundle.getString("etphone");
            et_phone.setText(etphone);

            etline1 = bundle.getString("etline1");
            et_line1.setText(etline1);

            etline2 = bundle.getString("etline2");
            et_line2.setText(etline2);

            etline3 = bundle.getString("etline3");
            et_line3.setText(etline3);

            pincode = bundle.getString("pincode");
            atv_pincode.setText(pincode);

            landmark = bundle.getString("landmark");
            et_landmark.setText(landmark);

        }

        deliveryaddress = getIntent().getBooleanExtra("deliveryaddress", false);
        cartaddress = getIntent().getBooleanExtra("cartaddress", false);

        pincode();
        toolbar();

        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  name = et_name.getText().toString().trim();
                String  phone = et_phone.getText().toString().trim();
                String  line1 = et_line1.getText().toString().trim();
                String  line2 = et_line2.getText().toString().trim();
                String  line3 = et_line3.getText().toString().trim();
                String  pincode = atv_pincode.getText().toString().trim();
                String  landmark = et_landmark.getText().toString().trim();

                if (name.isEmpty()) {
                    et_name.setError("*Enter your name");
                    et_name.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    et_phone.setError("*Enter your mobile number");
                    et_phone.requestFocus();
                    return;
                }
                if (line1.isEmpty()) {
                    et_line1.setError("*Enter your address line1");
                    et_line1.requestFocus();
                    return;
                }
                if (line2.isEmpty()) {
                    et_line2.setError("*Enter your address line2");
                    et_line2.requestFocus();
                    return;
                }
                if (line3.isEmpty()) {
                    et_line3.setError("*Enter your address line2");
                    et_line3.requestFocus();
                    return;
                }
                if (pincode.isEmpty()) {
                    atv_pincode.setError("*Enter your pincode");
                    atv_pincode.requestFocus();
                    return;
                }
                if (landmark.isEmpty()) {
                    et_landmark.setError("*Enter your landmark");
                    et_landmark.requestFocus();
                    return;
                }
                if (name != null && name != "" && phone != null && phone != "" & line1 != null && line1 != ""& line2 != null && line2 != ""& pincode != null &&pincode != ""& landmark != null &&landmark != "") {
                    final Map<String, String> params = new HashMap<>();

                    String para1="?method="+"3";
                    String para2="&id="+id;
                    String para3="&user_id="+ BSession.getInstance().getUser_id(getApplicationContext());
                    String para4="&name="+et_name.getText().toString().trim();
                    String para5="&phone="+et_phone.getText().toString().trim();
                    String para6="&email="+" - ";
                    String para7="&line1="+et_line1.getText().toString().trim();
                    String para8="&line2="+et_line2.getText().toString().trim();
                    String para9="&line3="+et_line3.getText().toString().trim();
                    String para11="&pincode="+atv_pincode.getText().toString().trim();
                    String para12="&lanmark="+et_landmark.getText().toString().trim();
                    String para13="&type="+"Home";

                    progressDialog.show();

                    String baseUrl = ProductConfig.user_address+para1+para2+para3+para4+para5+para6+para7+para8+para9+para11+para12+para13;

                    StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                progressDialog.dismiss();
                                JSONObject jsonResponse = new JSONObject(response);

                                if (jsonResponse.has("message") && jsonResponse.getString("message").equals("Successfully Registered")) {
                                    deliveryaddress = getIntent().getBooleanExtra("deliveryaddress", false);
                                    if (deliveryaddress){
                                        Intent intent = new Intent(EditAddressActivity.this,DeliveryAddressActivity.class);
                                        startActivity(intent);
                                        return;
                                    }
                                    cartaddress = getIntent().getBooleanExtra("cartaddress", false);
                                    if (cartaddress){
                                        Intent intent = new Intent(EditAddressActivity.this, CartActivity.class);
                                        startActivity(intent);
                                        return;
                                    }

                                    Toast.makeText(getApplicationContext(), "Your adddress has been updated", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Update failed", Toast.LENGTH_LONG).show();
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
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(jsObjRequest);
                    jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                            220000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                } else {
                    Toast.makeText(getApplicationContext(), "kindly enter all details", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void pincode() {
        final Map<String, String> params = new HashMap<>();

        String baseUrl = ProductConfig.pincode;

        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {

                        apiZipcodeList = new ArrayList<>();

                        JSONArray jsonlist = jsonResponse.getJSONArray("pincodeList");

                        for (int j = 0; j < jsonlist.length(); j++) {

                            JSONObject jsonlistObject = jsonlist.getJSONObject(j);
                            ZipcodeModel zipcodeModel = new ZipcodeModel();

                            zipcodeModel.setId(jsonlistObject.getString("id").toString());
                            zipcodeModel.setZipcode(jsonlistObject.getString("pincode").toString());

                            pincodelist.add(jsonlistObject.getString("pincode"));

                            apiZipcodeList.add(zipcodeModel);

                            ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.pincode_list,R.id.pincode,pincodelist);
                            atv_pincode.setAdapter(adapter);
                            atv_pincode.setThreshold(1);
                            atv_pincode.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //atv_pincode.showDropDown();
                                }
                            });
                            atv_pincode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //////////////////////////
                                    for (ZipcodeModel model:apiZipcodeList) {
                                        if(atv_pincode.getText().toString().equals(model.getZipcode())){

                                        }

                                    }
                                }
                            });



                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Pincode records not found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        },
                new Response.ErrorListener() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsObjRequest);
    }

    private void toolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
            startActivity(intent);
            finish(); }});
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
    }

}