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

import com.taprocycle.service.Adapter.NoticeBoardAdapter;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;

import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.test.model.NoticeBoard;
import com.taprocycle.service.test.model.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeBoardActivity extends AppCompatActivity {
    RecyclerView rv_noticeboard;
    private List<NoticeBoard> alloffers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
        /////////NETWORK CONDITION CHECK///////////

        String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(NoticeBoardActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }
        rv_noticeboard=findViewById(R.id.rv_noticeboard);
        alloffers();
        toolbar();;
    }
    private void toolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish(); }});
        getSupportActionBar().setTitle("Notice Board");
        toolbar.setTitleTextColor(Color.WHITE);
    }


    public void alloffers() {
        alloffers = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String baseUrl   = ProductConfig.noticeboard ;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        alloffers = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            NoticeBoard model = new NoticeBoard();
                            model.setShortt(array.getJSONObject(i).getString("short"));
                            model.setDescription(array.getJSONObject(i).getString("description"));
                            model.setBanner_image(array.getJSONObject(i).getString("banner_image"));
                            alloffers.add(model);
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(NoticeBoardActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_noticeboard.setLayoutManager(layoutManager);
                        NoticeBoardAdapter productListAdapter = new NoticeBoardAdapter(NoticeBoardActivity.this, alloffers);
                        rv_noticeboard.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(NoticeBoardActivity.this, 1);
                        rv_noticeboard.setLayoutManager(gridLayoutManager);
                        rv_noticeboard.setHasFixedSize(true);

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
               // progressDialog.dismiss();
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
        RequestQueue requestQueue = Volley.newRequestQueue(NoticeBoardActivity.this);
        requestQueue.add(jsObjRequest);
    }

    /*public void alloffers(){

        alloffers = new ArrayList<>();
        ProductModel best = new ProductModel("Bag", "₹ 150", "₹ 1500", "₹ 2500",R.drawable.bag1);alloffers.add(best);
        ProductModel bestt = new ProductModel("Wallet", "₹ 150", "₹ 1500", "₹ 2500",R.drawable.wallet);alloffers.add(bestt);
        ProductModel besttt = new ProductModel("Bag", "₹ 150", "₹ 1500", "₹ 2500",R.drawable.bagc3);alloffers.add(besttt);
        ProductModel bestttt = new ProductModel("Mobile", "₹ 150", "₹ 1500", "₹ 2500",R.drawable.mobile);alloffers.add(bestttt);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);rv_noticeboard.setLayoutManager(linearLayoutManager1);
        NoticeBoardAdapter bestSaleAdapter = new NoticeBoardAdapter(this, alloffers);
        rv_noticeboard.setAdapter(bestSaleAdapter);
        final LayoutAnimationController controller1 = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);rv_noticeboard.setLayoutAnimation(controller1);

    }*/
}