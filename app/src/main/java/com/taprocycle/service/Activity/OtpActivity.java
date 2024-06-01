package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends AppCompatActivity {
    Button login;
    String phone;
    String message;
    String otp;
    PinView pinview;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressDialog progressDialog = new ProgressDialog(OtpActivity.this);
        progressDialog.setMessage("Loading....");


        setContentView(R.layout.activity_otp);
        login=findViewById(R.id.login);
        pinview = findViewById(R.id.pinview);

        /*EditText edit_otp1 =findViewById(R.id.edit_otp1);
        EditText edit_otp2 =findViewById(R.id.edit_otp2);
        EditText edit_otp3 =findViewById(R.id.edit_otp3);
        EditText edit_otp4 =findViewById(R.id.edit_otp4);




        edit_otp1.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() == 1) {
                    edit_otp1.requestFocus();
                } else {
                    if (!isChild())
                        edit_otp1.requestFocus();
                }
            }

            public void afterTextChanged(Editable editable) {
                String edit1 = edit_otp1.getText().toString().trim();
                String edit2 = edit_otp2.getText().toString().trim();
                String edit3 = edit_otp3.getText().toString().trim();
                String edit4 = edit_otp4.getText().toString().trim();
                if (edit1.length() == 1) {
                    edit_otp2.requestFocus();
                }

            }
        });
        edit_otp2.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            public void afterTextChanged(Editable editable) {
                String edit1 = edit_otp1.getText().toString().trim();
                String edit2 = edit_otp2.getText().toString().trim();
                String edit3 = edit_otp3.getText().toString().trim();
                String edit4 = edit_otp4.getText().toString().trim();
                if (edit2.length() == 1) {
                    edit_otp3.requestFocus();
                }

            }
        });
        edit_otp3.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            public void afterTextChanged(Editable editable) {
                String edit1 = edit_otp1.getText().toString().trim();
                String edit2 = edit_otp2.getText().toString().trim();
                String edit3 = edit_otp3.getText().toString().trim();
                String edit4 = edit_otp4.getText().toString().trim();
                if (edit3.length() == 1) {
                    edit_otp4.requestFocus();
                }

            }
        });
        edit_otp4.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            public void afterTextChanged(Editable editable) {
                String edit1 = edit_otp1.getText().toString().trim();
                String edit2 = edit_otp2.getText().toString().trim();
                String edit3 = edit_otp3.getText().toString().trim();
                String edit4 = edit_otp4.getText().toString().trim();
                if (edit4.length() == 1) {
                    edit_otp4.requestFocus();
                }

            }
        });*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            /*public void onClick(View v) {
                phone = BSession.getInstance().getUser_mobile(OtpActivity.this);
                System.out.println("phone;;" +phone);
                final Map<String, String> params = new HashMap<>();

                progressDialog.show();

                String para_str  = "?user_mobile=" + phone;
                String para_str1 = "&otp=" + edit_otp1.getText().toString()+edit_otp2.getText().toString()+edit_otp3.getText().toString()+edit_otp4.getText().toString();

                String baseUrl = ProductConfig.userotpverify + para_str + para_str1;

                StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonResponse = new JSONObject(response);

                            if (jsonResponse.has("success") && jsonResponse.getString("success").equals("1")) {
                                BSession.getInstance().initialize(OtpActivity.this,
                                        jsonResponse.getString("user_id"),
                                        "",

                                        jsonResponse.getString("user_mobile"),
                                        "",
                                        "",
                                        "","","","");
                                if (jsonResponse.has("status") && jsonResponse.getString("status").equals("1")) {

                                    BSession.getInstance().initialize(OtpActivity.this,
                                            jsonResponse.getString("user_id"),
                                            jsonResponse.getString("user_name"),
                                            jsonResponse.getString("user_mobile"),
                                            "",
                                            "",
                                            "","","","");

                                    Intent i1 = new Intent(OtpActivity.this, MainActivity.class);
                                    startActivity(i1);
                                    finish();
                                }else{
                                    Intent i = new Intent(OtpActivity.this, RegisterActivity.class);
                                    i.putExtra("user_mobile",phone);
                                    startActivity(i);
                                    finish();
                                }
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
                RequestQueue requestQueue = Volley.newRequestQueue(OtpActivity.this);
                requestQueue.add(jsObjRequest);
            }*/
            public void onClick(View v) {
                String otpstr = String.valueOf(pinview.getText().toString());
                otp = otpstr;
                if (otpstr != null && otpstr != "" && otpstr.length() >= 4) {

                    phone = BSession.getInstance().getUser_mobile(OtpActivity.this);
                    System.out.println("phone;;" + phone);
                    final Map<String, String> params = new HashMap<>();
                    progressDialog.show();

                    String para_str = "?user_mobile=" + phone;
                    String para_str1 = "&otp=" + pinview.getText().toString();
                    System.out.println("responee==" + pinview.getText().toString());
                    String baseUrl = ProductConfig.userotpverify + para_str + para_str1;

                    StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                progressDialog.dismiss();
                                JSONObject jsonResponse = new JSONObject(response);

                                if (jsonResponse.has("success") && jsonResponse.getString("success").equals("1")) {
                                    BSession.getInstance().initialize(OtpActivity.this,
                                            jsonResponse.getString("user_id"),
                                            "",

                                            jsonResponse.getString("user_mobile"),
                                            "",
                                            "",
                                            "","","","","");
                                    if (jsonResponse.has("status") && jsonResponse.getString("status").equals("1")) {

                                        BSession.getInstance().initialize(OtpActivity.this,
                                                jsonResponse.getString("user_id"),
                                                jsonResponse.getString("user_name"),
                                                jsonResponse.getString("user_mobile"),
                                                "",
                                                "",
                                                "","","","","");

                                        Intent i1 = new Intent(OtpActivity.this, MainActivity.class);
                                        startActivity(i1);
                                        finish();
                                    }else{
                                        Intent i = new Intent(OtpActivity.this, RegisterActivity.class);
                                        i.putExtra("user_mobile",phone);
                                        startActivity(i);
                                        finish();
                                    }
                                }else {
                                    Toast.makeText(OtpActivity.this, "Incorrect OTP entered", Toast.LENGTH_LONG).show();
                                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(OtpActivity.this, R.style.AlertDialogTheme);
                                    dialog2.setTitle("' "  + OtpActivity.this.pinview.getText().toString().trim() + " ' Incorrect OTP !");
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("You have entered the wrong OTP. Please enter correct OTP");
                                    dialog2.setMessage(sb.toString()).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    }).show();
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
                    RequestQueue requestQueue = Volley.newRequestQueue(OtpActivity.this);
                    requestQueue.add(jsObjRequest);
                } else {
                    Toast.makeText(OtpActivity.this, "Invalid OTP..", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}