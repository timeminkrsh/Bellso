package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taprocycle.service.Adapter.EventsAdapter;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.test.model.BannerModel;
import com.taprocycle.service.test.model.EventModel;
import com.taprocycle.service.test.model.ProductConfig;

public class EventActivity extends AppCompatActivity {
TabLayout tabLayout;
ViewPager viewPager;
RecyclerView recycleview;
    private List<EventModel> photoslist = new ArrayList<>();
    private List<BannerModel> photoslists = new ArrayList<>();
    String images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        /////////NETWORK CONDITION CHECK///////////

        String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(EventActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }
        recycleview=findViewById(R.id.recycleview);
        toolbar();
        events();



    }


    public void events() {
        photoslist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str1 = "?type=" +"1";
        String baseUrl   = ProductConfig.events + para_str1;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        JSONArray array = jsonResponse.getJSONArray("storeList");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            EventModel model = new EventModel();
                            model.setId(array.getJSONObject(i).getString("id"));
                            model.setTitle(array.getJSONObject(i).getString("title"));
                            model.setImage(array.getJSONObject(i).getString("image"));
                            model.setLocation(array.getJSONObject(i).getString("location"));
                            model.setHall(array.getJSONObject(i).getString("hall"));
                            model.setDate(array.getJSONObject(i).getString("date"));

                           JSONArray jsonResarray1 = product.getJSONArray("images");

                            for (int j = 0; j < jsonResarray1.length(); j++) {
                                JSONObject jsonObject = array.getJSONObject(j);
                                model.setMultipleimages(jsonObject.getString("images"));
                            }
                            photoslist.add(model);


                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(EventActivity.this, LinearLayoutManager.VERTICAL, false);
                        recycleview.setLayoutManager(layoutManager);
                        EventsAdapter productListAdapter = new EventsAdapter(EventActivity.this, photoslist);
                        recycleview.setAdapter(productListAdapter);
                        recycleview.setHasFixedSize(true);
                       // gif.setVisibility(View.GONE);

                    } else {
                      //  gif.setVisibility(View.GONE);

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
                // TODO Auto-generated method stub
                Log.e("Error", error.toString());
                //   progressDialog.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EventActivity.this);
        requestQueue.add(jsObjRequest);
    }


    private void toolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish(); }});
        getSupportActionBar().setTitle("Events");
        toolbar.setTitleTextColor(Color.WHITE);
    }
}