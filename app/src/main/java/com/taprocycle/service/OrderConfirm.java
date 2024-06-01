package com.taprocycle.service;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.taprocycle.service.Activity.CartActivity;
import com.taprocycle.service.Activity.MyOrdersActivity;
import com.taprocycle.service.Activity.OrdersDetailActivity;
import com.taprocycle.service.Adapter.CategoryAdapter;
import com.taprocycle.service.Adapter.OrderConfirmationAdapter;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.CategoryModel;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class OrderConfirm extends AppCompatActivity {
    Button btn_submit,bt_shopping;
    TextView total_amount,orderid,paymentmode;
    String order,amount,payment,yousave;

    private List<ProductsModel> cartlist = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        btn_submit = findViewById(R.id.bt_submit);
        bt_shopping = findViewById(R.id.bt_shopping);
        paymentmode = findViewById(R.id.paymentmode);
        total_amount = findViewById(R.id.totalamount);
        orderid = findViewById(R.id.orderid);
        toolbar();
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            order=bundle.getString("order_id");
            amount=bundle.getString("totalamount");
            yousave=bundle.getString("yousave");
            orderid.setText(order);
            total_amount.setText( "₹"+amount);
            paymentmode.setText(payment);
        }

        paymentmode.setText(BSession.getInstance().getPayment(OrderConfirm.this));
        System.out.println("totalamount=="+BSession.getInstance().getPayment(OrderConfirm.this));

        bt_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderConfirm.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderConfirm.this, OrdersDetailActivity.class);
                intent.putExtra("order_no",order);
                intent.putExtra("yousave",yousave);

                startActivity(intent);
            }
        });


    }

    private void toolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish(); }});
        getSupportActionBar().setTitle("Order Confirm");
        toolbar.setTitleTextColor(Color.WHITE);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderConfirm.this, MainActivity.class);
        startActivity(intent);
    }

}