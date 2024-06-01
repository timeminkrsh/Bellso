package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import com.taprocycle.service.Adapter.MyOrderAdapter;
import com.taprocycle.service.R;
import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.OrderModel;
import com.taprocycle.service.test.model.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class MyOrdersActivity extends AppCompatActivity {
    RecyclerView rv_orders;
    private List<OrderModel> orderlist = new ArrayList<>();
    GifImageView gif;
    String payment_from,yousave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        /////////NETWORK CONDITION CHECK///////////

        String status = NetworkUtil.getConnectivityStatusString(this);

        rv_orders=findViewById(R.id.rv_orders);
        gif=findViewById(R.id.gif);
        Bundle extras = getIntent().getExtras();
        toolbar();
        orders();

    }
    public void orders() {
        orderlist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str1 = "?user_id=" + BSession.getInstance().getUser_id(MyOrdersActivity.this);
        System.out.println("userid"+para_str1);
        String baseUrl   = ProductConfig.myorder + para_str1;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        orderlist = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            OrderModel model = new OrderModel();
                            model.setOrder_no(array.getJSONObject(i).getString("order_id"));
                            model.setOrder_netamount(array.getJSONObject(i).getString("order_total"));
                            model.setOrder_status(array.getJSONObject(i).getString("order_status"));
                            model.setMrp_total(array.getJSONObject(i).getString("order_mrp"));
                            model.setPcp(array.getJSONObject(i).getString("order_pcp"));
                            //model.setDiscountpoints(array.getJSONObject(i).getString("discount_bonus_used"));
                            model.setOrder_date(array.getJSONObject(i).getString("create_date"));
                            model.setCustomer_ack(array.getJSONObject(i).getString("customer_ack"));
                            model.setName(array.getJSONObject(i).getString("name"));
                            model.setPhone(array.getJSONObject(i).getString("phone"));
                            // model.setAddress(array.getJSONObject(i).getString("address"));
                            model.setLandmark(array.getJSONObject(i).getString("lanmark"));
                            model.setAddress1(array.getJSONObject(i).getString("address1"));
                            model.setAddress2(array.getJSONObject(i).getString("address2"));
                            model.setAddress3(array.getJSONObject(i).getString("address3"));
                            model.setAddress4(array.getJSONObject(i).getString("address4"));
                            model.setPincode(array.getJSONObject(i).getString("pincode"));
                            model.setShipping_fee(array.getJSONObject(i).getString("shipping_fee"));
                            model.setPayment_from(array.getJSONObject(i).getString("payment_from"));
                            model.setOrder_status(array.getJSONObject(i).getString("order_status"));
                            model.setDiscountpoints(yousave);
                            orderlist.add(model);

                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(MyOrdersActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_orders.setLayoutManager(layoutManager);
                        MyOrderAdapter productListAdapter = new MyOrderAdapter(MyOrdersActivity.this, orderlist);
                        rv_orders.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyOrdersActivity.this, 1);
                        rv_orders.setLayoutManager(gridLayoutManager);
                        rv_orders.setHasFixedSize(true);

                    } else {

                        Toast.makeText(MyOrdersActivity.this, "No ordersFound", Toast.LENGTH_SHORT).show();
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
                //progressDialog.dismiss();
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
        RequestQueue requestQueue = Volley.newRequestQueue(MyOrdersActivity.this);
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
        getSupportActionBar().setTitle("My Orders");
        toolbar.setTitleTextColor(Color.WHITE);
    }

}