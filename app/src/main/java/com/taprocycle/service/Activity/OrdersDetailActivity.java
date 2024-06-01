package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.taprocycle.service.Adapter.MyOrderDetailsAdapter;
import com.taprocycle.service.Adapter.OrderdetailAdapter;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.OrderConfirm;
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

public class OrdersDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button bt_shopping;
    RecyclerView recycle_myorder;
    private List<OrderModel> myListData = new ArrayList<OrderModel>();
    String date,amount,no,name,address,yousave,youusave,status,payment,landmark,address1,address2,address3,address4,pincode,mobile,pcp,payment_from;
    TextView DeliveryAddress,text_address, order_status, value_order_id,value_orderdate,tv_pPrice,tv_dfee,BK_price,savigs_amount,payment_mode_values;
    TextView tv_sgst,tv_cgst;
    String shipping=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_detail);
        /////////NETWORK CONDITION CHECK///////////

        String status = NetworkUtil.getConnectivityStatusString(this);
        if (status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(OrdersDetailActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        } else {

        }
        recycle_myorder = findViewById(R.id.recycle_myorder);
        value_order_id = findViewById(R.id.value_order_id);
        value_orderdate = findViewById(R.id.value_orderdate);
        tv_pPrice = findViewById(R.id.tv_pPrice);
        BK_price = findViewById(R.id.BK_price);
        tv_sgst = findViewById(R.id.tv_sgst);
        tv_cgst = findViewById(R.id.tv_cgst);
        savigs_amount = findViewById(R.id.savigs_amount);
        tv_dfee = findViewById(R.id.tv_dfee);
        payment_mode_values = findViewById(R.id.payment_mode_values);
        order_status = findViewById(R.id.order_status);
        text_address = findViewById(R.id.text_address);
        bt_shopping = findViewById(R.id.bt_shopping);
        DeliveryAddress = findViewById(R.id.DeliveryAddress);
        toolbar();

        bt_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrdersDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        payment_mode_values.setText(BSession.getInstance().getPayment(OrdersDetailActivity.this));
        System.out.println("totalamount=="+BSession.getInstance().getPayment(OrdersDetailActivity.this));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            no = (extras.getString("order_no"));
            date = (extras.getString("order_date"));
            amount = (extras.getString("order_netamount"));
            status = (extras.getString("order_status"));
            name = (extras.getString("name"));
            address = (extras.getString("address"));
            System.out.println("saveamount==" + address);
            landmark = (extras.getString("landmark"));
            address1 = (extras.getString("address1"));
            address2 = (extras.getString("address2"));
            address3 = (extras.getString("address3"));
            address4 = (extras.getString("address4"));

            pcp = (extras.getString("order_pcp"));
            payment_from = (extras.getString("payment_from"));

            order_status.setText(status);
            value_order_id.setText(no);
            value_orderdate.setText(date);
            //payment_mode_values.setText(payment);
            BK_price.setText("₹ " + amount);
            yousave = extras.getString("yousave");
            System.out.println("youdsvee=="+yousave);
            savigs_amount.setText("₹ "+yousave);
            System.out.println("paymentfrom=,," + payment_from);

        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }

        orders();

    }

    private void toolbar() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdersDetailActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        getSupportActionBar().setTitle("My Order Details");
        toolbar.setTitleTextColor(Color.WHITE);
    }


    public void orders() {

        myListData = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str1 = "?order_id=" + no;
        String baseUrl = ProductConfig.myorderdetails + para_str1;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        myListData = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            OrderModel model = new OrderModel();
                            model.setProduct_name(array.getJSONObject(i).getString("product_name"));
                            model.setDiscount_price(array.getJSONObject(i).getString("dicount_total"));
                            model.setProduct_price(array.getJSONObject(i).getString("mrp_total"));
                            model.setProduct_quantity(array.getJSONObject(i).getString("product_quantity"));
                            model.setAddress(array.getJSONObject(i).getString("address"));
                            model.setLandmark(array.getJSONObject(i).getString("lanmark"));
                            model.setProduct_id(array.getJSONObject(i).getString("product_id"));
                            model.setAddress1(array.getJSONObject(i).getString("address1"));
                            model.setAddress2(array.getJSONObject(i).getString("address2"));
                            model.setAddress3(array.getJSONObject(i).getString("address3"));
                            model.setAddress4(array.getJSONObject(i).getString("address4"));
                            model.setPincode(array.getJSONObject(i).getString("pincode"));
                            model.setOrder_date(array.getJSONObject(i).getString("order_date"));
                            model.setOrder_status(array.getJSONObject(i).getString("order_status"));
                            model.setOrder_total(array.getJSONObject(i).getString("order_total"));
                            model.setColor_name(array.getJSONObject(i).getString("color_name"));
                            model.setColor(array.getJSONObject(i).getString("color"));
                            model.setExclusive_product(array.getJSONObject(i).getString("exclusive_product"));
                            model.setImage(array.getJSONObject(i).getString("pimage"));

                            tv_pPrice.setText("₹ " + jsonResponse.getString("total_mpr"));

                            shipping = jsonResponse.getString("shipping_fee");
                            if(shipping.equals("0")){
                                tv_dfee.setText("Free");
                            }else{
                                tv_dfee.setText("₹ " + jsonResponse.getString("shipping_fee"));
                            }

                            savigs_amount.setText("₹ " + jsonResponse.getString("savetotal"));
                            order_status.setText(model.getOrder_status());
                            value_order_id.setText(array.getJSONObject(i).getString("order_id"));
                            value_orderdate.setText(model.getOrder_date());
                            BK_price.setText("₹ " + model.getOrder_total());
                            BK_price.setText("₹ " + model.getOrder_total());
                            tv_sgst.setText("₹ " +jsonResponse.getString("total_inter_state_tax"));
                            tv_cgst.setText("₹ " +jsonResponse.getString("total_intra_state_tax"));

                            if(model.getLandmark().isEmpty()){
                                text_address.setText("Pickup From Store");
                                DeliveryAddress.setText(model.getAddress1() + "\n"+model.getAddress2() + "\n" +
                                        model.getAddress3() + "\n" + ("Mobile - " +model.getAddress4())+ "\n"+("Pincode - "+model.getPincode()));
                            }else{
                                text_address.setText("Delivery Address");
                                DeliveryAddress.setText(model.getAddress1() + "\n"+model.getAddress2() + "\n" +
                                        model.getAddress3() + "\n"+model.getLandmark()+ "\n" +("Mobile - " +model.getAddress4())+ "\n"+("Pincode - "+model.getPincode()));
                            }

                            myListData.add(model);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(OrdersDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                            recycle_myorder.setLayoutManager(layoutManager);
                            MyOrderDetailsAdapter productListAdapter = new MyOrderDetailsAdapter(OrdersDetailActivity.this, myListData);
                            recycle_myorder.setAdapter(productListAdapter);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(OrdersDetailActivity.this, 1);
                            recycle_myorder.setLayoutManager(gridLayoutManager);
                            recycle_myorder.setHasFixedSize(true);
                        }

                    } else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_LONG).show();

                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_LONG).show();

                    message = "Connection TimeOut! Please check your internet connection.";
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(OrdersDetailActivity.this);
        requestQueue.add(jsObjRequest);
    }
}