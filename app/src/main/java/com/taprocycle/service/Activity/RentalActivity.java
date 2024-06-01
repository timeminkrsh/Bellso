package com.taprocycle.service.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.taprocycle.service.Adapter.RentalAdapter;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.RentalModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentalActivity extends AppCompatActivity {
ProgressDialog progressDialog;
RecyclerView recyclerView;
    private List<RentalModel> productsalelist = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accsys_tourism);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating.....");
        recyclerView=findViewById(R.id.recycleview);
        toolbar();
        products();
    }

    private void products() {
        final Map<String, String> params = new HashMap<>();
        String para_str7 = "?user_id=" + BSession.getInstance().getUser_id(getApplicationContext());
        String baseUrl = ProductConfig.rental   +para_str7;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        productsalelist = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            RentalModel model = new RentalModel();
                            model.setTitile(array.getJSONObject(i).getString("name"));
                            model.setAddress(array.getJSONObject(i).getString("address"));
                            model.setPrice(array.getJSONObject(i).getString("rentalprice"));
                            model.setPhone(array.getJSONObject(i).getString("contact"));
                            model.setImage(array.getJSONObject(i).getString("imagepath"));
                            productsalelist.add(model);
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(RentalActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        RentalAdapter recentlyAdapter = new RentalAdapter(RentalActivity.this, productsalelist);
                        recyclerView.setAdapter(recentlyAdapter);
                        recyclerView.setHasFixedSize(true);

                        //progressDialog.dismiss();

                    } else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    // progressDialog.dismiss();
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
        RequestQueue requestQueue = Volley.newRequestQueue(RentalActivity.this);
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
        getSupportActionBar().setTitle("Rental");
        toolbar.setTitleTextColor(Color.WHITE);
    }

}