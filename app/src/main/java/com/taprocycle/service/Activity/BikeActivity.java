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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.taprocycle.service.Adapter.RecentlyAdapter;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.OfferModel;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductModel;
import com.taprocycle.service.test.model.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BikeActivity extends AppCompatActivity {
    RecyclerView rv_offers;
    private List<ProductModel> alloffers = new ArrayList<>();
    private List<OfferModel> rvsalelist = new ArrayList<>();
    private List<ProductsModel> productsalelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_offers);
        /////////NETWORK CONDITION CHECK///////////

        String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(BikeActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }
        rv_offers=findViewById(R.id.rv_offers);
        toolbar();
        bikes();
    }

    private void toolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish(); }});
        getSupportActionBar().setTitle("Bikes");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    public void bikes() {
        productsalelist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str3 = "?cid=" + "42";
        String para_str7 = "&user_id=" + BSession.getInstance().getUser_id(getApplicationContext());

        String baseUrl = ProductConfig.productlist + para_str3  +para_str7;

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
                            ProductsModel model = new ProductsModel();
                            model.setQty(array.getJSONObject(i).getString("qty"));
                            model.setPid(array.getJSONObject(i).getString("pid"));
                            model.setCid(array.getJSONObject(i).getString("cid"));
                            model.setScid(array.getJSONObject(i).getString("scid"));
                            model.setProductname(array.getJSONObject(i).getString("productname"));
                            model.setDescription(array.getJSONObject(i).getString("description"));
                            //   model.setShort_description(array.getJSONObject(i).getString("short_description"));
                            model.setRatting(array.getJSONObject(i).getString("ratting"));
                            model.setPcode(array.getJSONObject(i).getString("pcode"));
                            model.setPrice(array.getJSONObject(i).getString("price"));
                            model.setStock(array.getJSONObject(i).getString("stock"));
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            model.setExclusive_product(array.getJSONObject(i).getString("exclusive_product"));
                            JSONArray array1 = product.getJSONArray("image");
                            model.setImage(array1.getJSONObject(0).getString("image"));

                            String stocks =  model.setStock(array.getJSONObject(i).getString("stock"));

                            if (!stocks.equalsIgnoreCase("")) {
                                // Toast.makeText(MainActivity.this, " out of stock!!!!!!!", Toast.LENGTH_LONG).show();
                                productsalelist.add(model);
                            }

                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(BikeActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_offers.setLayoutManager(layoutManager);
                        RecentlyAdapter  productListAdapter = new RecentlyAdapter(BikeActivity.this, productsalelist);
                        rv_offers.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(BikeActivity.this, 2);
                        rv_offers.setLayoutManager(gridLayoutManager);
                        rv_offers.setHasFixedSize(true);


                    } else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
        RequestQueue requestQueue = Volley.newRequestQueue(BikeActivity.this);
        requestQueue.add(jsObjRequest);
    }



}