package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taprocycle.service.Adapter.DeliveryAddressAdapter;
import com.taprocycle.service.Adapter.PincodeAdapter;
import com.taprocycle.service.Adapter.SuggestionAdapter;
import com.taprocycle.service.AddAddressActivity;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.OrderConfirm;
import com.taprocycle.service.R;
import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.DeliveryAddressModel;
import com.taprocycle.service.test.model.DeliveryAddressSession;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductsModel;
import com.taprocycle.service.test.model.ZipcodeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.droidsonroids.gif.GifImageView;

public class DeliveryAddressActivity extends AppCompatActivity {
    RecyclerView rv_delivery;
    private List<DeliveryAddressModel> deliveryaddress = new ArrayList<>();
    ImageView arrow,search,cart,home;
    TextView add_address,txt;
    BottomSheetDialog bottomSheetDialog;
    ProgressDialog progressDialog;
    TextView badge_notification;
    GifImageView gif;
    LinearLayout arrow_lin;
    AutoCompleteTextView search_txt;
    public ArrayList<ProductsModel> spinnerList = new ArrayList<>();
    SuggestionAdapter suggestionAdapter;
    Dialog dialogg;
    AlertDialog alertDialog;
    List<ZipcodeModel> apiZipcodeList = new ArrayList<>();
    ZipcodeModel zipcodeModel = new ZipcodeModel();
    ArrayList<String> pincodelist = new ArrayList<String>();
    EditText et_pincode;
    AutoCompleteTextView atv_pincode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        /////////NETWORK CONDITION CHECK///////////

        String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(DeliveryAddressActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating.....");
        gif=findViewById(R.id.gif);
        rv_delivery=findViewById(R.id.rv_delivery);
        arrow=findViewById(R.id.arrow);
        search=findViewById(R.id.search);
        cart=findViewById(R.id.cart);
        search_txt=findViewById(R.id.search_txt);
        txt=findViewById(R.id.txt);
        txt.setText("My Address");
        add_address=findViewById(R.id.add_address);
        badge_notification=findViewById(R.id.badge_notification);
        arrow_lin=findViewById(R.id.arrow_lin);
        home=findViewById(R.id.home);

        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeliveryAddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
                //showBottomSheetDialog();
            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

/*
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeliveryAddressActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
*/
        String customer_id = BSession.getInstance().getUser_id(DeliveryAddressActivity.this);
        System.out.println("customer_idcustomer_id"+customer_id);
        if (customer_id.equalsIgnoreCase("")) {
            add_address.setVisibility(View.VISIBLE);
            gif.setVisibility(View.GONE);
        } else {
            add_address.setVisibility(View.VISIBLE);
            deliveryaddress();
            getCartCount();
        }

    }

    public void getCartCount() {

        final Map<String, String> params = new HashMap<>();
        String customer_id = BSession.getInstance().getUser_id(DeliveryAddressActivity.this);
        String para_str = "?user_id=" + customer_id;
        String para_str1 = "&user_type=" + BSession.getInstance().getType(getApplicationContext());

        String baseUrl = ProductConfig.cartlist+para_str+para_str1;

        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {

                        //badge_notification.setText(jsonResponse.getString("total_cnt"));
                        gif.setVisibility(View.GONE);

                    } else {
                        //badge_notification.setText("0");
                        gif.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
    }

    private void showBottomSheetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.deliveryaddress_add, viewGroup, false);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in.....");
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        EditText et_name = dialogView.findViewById(R.id.et_name);
        EditText et_phone = dialogView.findViewById(R.id.et_phone);
        EditText et_line1 = dialogView.findViewById(R.id.et_line1);
        EditText et_line2 = dialogView.findViewById(R.id.et_line2);
        EditText et_line3 = dialogView.findViewById(R.id.et_line3);
        EditText et_line4 = dialogView.findViewById(R.id.et_line4);
        // et_pincode = bottomSheetDialog.findViewById(R.id.et_pincode);
        atv_pincode = dialogView.findViewById(R.id.atv_pincode);
        EditText et_landmark = dialogView.findViewById(R.id.et_landmark);
        Button sumbit = dialogView.findViewById(R.id.sumbit);
        et_name.setText(BSession.getInstance().getUser_name(getApplicationContext()));
        et_phone.setText(BSession.getInstance().getUser_mobile(getApplicationContext()));
        pincoddeAPI();
       /* et_pincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             pincodde();
            }
        });*/
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                String  phone = et_phone.getText().toString().trim();
                String   line1 = et_line1.getText().toString().trim();
                String   line2 = et_line2.getText().toString().trim();
                String   line3 = et_line3.getText().toString().trim();
                String   line4 = et_line4.getText().toString().trim();
                String   pincode = atv_pincode.getText().toString().trim();
                String   landmark = et_landmark.getText().toString().trim();

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
                    Pattern p = Pattern.compile("[7-9][0-9]{9}");
                    Matcher match = p.matcher(phone);
                    if ((match.find())) {
                        final Map<String, String> params = new HashMap<>();
                        String para1="?method="+"1";
                        String para2="&user_id="+BSession.getInstance().getUser_id(DeliveryAddressActivity.this);
                        String para3="&name="+et_name.getText().toString().trim();
                        String para4="&phone="+et_phone.getText().toString().trim();
                        String para5="&email="+" - ";
                        String para6="&line1="+et_line1.getText().toString().trim();
                        String para7="&line2="+et_line2.getText().toString().trim();
                        String para8="&line3="+et_line3.getText().toString().trim();
                        String para9="&line4="+et_line4.getText().toString().trim();
                        String para10="&pincode="+atv_pincode.getText().toString().trim();
                        String para11="&lanmark="+et_landmark.getText().toString().trim();
                        String para12="&type="+"Home";
                        progressDialog.show();
                        String baseUrl = ProductConfig.user_address+para1+para2+para3+para4+para5+para6+para7+para8+para9+para10+para11+para12;

                        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("Response", response.toString());
                                try {
                                    progressDialog.dismiss();
                                    JSONObject jsonResponse = new JSONObject(response);

                                    if (jsonResponse.has("message") && jsonResponse.getString("message").equals("Successfully Registered")) {

                                        DeliveryAddressSession.getInstance().initialize(DeliveryAddressActivity.this,
                                                et_line1.getText().toString().trim(),
                                                et_line2.getText().toString().trim(),
                                                et_line3.getText().toString().trim(),
                                                atv_pincode.getText().toString().trim()
                                                , et_landmark.getText().toString().trim(),
                                                et_phone.getText().toString().trim(),et_name.getText().toString().trim(),"");

                                        deliveryaddress();
                                        alertDialog.dismiss();
                                        Toast.makeText(DeliveryAddressActivity.this, "Your adddress has been added", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(DeliveryAddressActivity.this, "Something went wrong your address not added .. Try it again", Toast.LENGTH_LONG).show();
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
                        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressActivity.this);
                        requestQueue.add(jsObjRequest);
                        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                                220000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    }else {
                        Toast.makeText(getApplicationContext(), "Kindly enter the valid mobile number ", Toast.LENGTH_LONG).show();

                    }


                } else {
                    Toast.makeText(DeliveryAddressActivity.this, "kindly enter all details", Toast.LENGTH_LONG).show();

                }
            }
        });

        alertDialog.show();

    }
    public  void pincoddeAPI(){

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

                            //rv_mega.setLayoutManager(layoutManager);

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
                        Toast.makeText(DeliveryAddressActivity.this, "Pincode records not found", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressActivity.this);
        requestQueue.add(jsObjRequest);





    }
  public  void pincodde(){

        dialogg = new Dialog(DeliveryAddressActivity.this);
        dialogg.setCancelable(true);
        dialogg.setContentView(R.layout.pincode_options);
        Window window = dialogg.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final GridView rel = dialogg.findViewById(R.id.rv_slider1);

        final Map<String, String> params = new HashMap<>();

        // zipcodeModel.setAction("get_pincode");

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

                            //rv_mega.setLayoutManager(layoutManager);

                            PincodeAdapter sliderListAdapter1 = new PincodeAdapter(DeliveryAddressActivity.this, R.layout.pincode_list, apiZipcodeList);
                            rel.setAdapter(sliderListAdapter1);
                            rel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    ZipcodeModel model = apiZipcodeList.get(i);
                                    String zip=apiZipcodeList.get(i).getZipcode();
                                    atv_pincode.setText(zip);
                                    dialogg.dismiss();


                                }
                            });

                        }
                    } else {
                        Toast.makeText(DeliveryAddressActivity.this, "Pincode records not found", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressActivity.this);
        requestQueue.add(jsObjRequest);




        dialogg.show();


    }



    public void  deliveryaddress() {

        deliveryaddress = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str1 = "?method=" + "2";
        String para_str2 = "&user_id=" + BSession.getInstance().getUser_id(DeliveryAddressActivity.this);
        String baseUrl   = ProductConfig.user_address + para_str1+para_str2;

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        deliveryaddress = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            DeliveryAddressModel model = new DeliveryAddressModel();
                            model.setId(array.getJSONObject(i).getString("id"));
                            model.setName(array.getJSONObject(i).getString("name"));
                            model.setPhone(array.getJSONObject(i).getString("phone"));
                            model.setLine1(array.getJSONObject(i).getString("line1"));
                            model.setLine2(array.getJSONObject(i).getString("line2"));
                            model.setLine3(array.getJSONObject(i).getString("line3"));
                            model.setLine5(array.getJSONObject(i).getString("line5"));
                            model.setPincode(array.getJSONObject(i).getString("pincode"));
                            model.setLandmark(array.getJSONObject(i).getString("lanmark"));
                            deliveryaddress.add(model);

                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(DeliveryAddressActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_delivery.setLayoutManager(layoutManager);
                        DeliveryAddressAdapter productListAdapter = new DeliveryAddressAdapter(DeliveryAddressActivity.this, deliveryaddress);
                        rv_delivery.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(DeliveryAddressActivity.this, 1);
                        rv_delivery.setLayoutManager(gridLayoutManager);
                        rv_delivery.setHasFixedSize(true);

                    } else {
                        // Toast.makeText(getContext(), "No Product Result Found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //progressDialog.dismiss();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressActivity.this);
        requestQueue.add(jsObjRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DeliveryAddressActivity.this, MainActivity.class);
        startActivity(intent);
    }

}