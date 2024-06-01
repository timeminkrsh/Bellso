package com.taprocycle.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.taprocycle.service.Activity.CartActivity;
import com.taprocycle.service.Activity.SearchActivity;
import com.taprocycle.service.Adapter.RecentlyAdapter;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView rv_products;
    TextView txt_titile;
    ProgressDialog progressDialog;
    RecentlyAdapter productListAdapter ;
    ImageView arrow,wislist,search,cart,search_remove;
    AutoCompleteTextView search_txt;
    RelativeLayout searh;
    SwipeRefreshLayout swipeRefreshLayout;

    private List<ProductsModel> recentlyaddedwiselist = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        rv_products = findViewById(R.id.rv_products);
        search_txt = findViewById(R.id.search_txt);
        searh = findViewById(R.id.searh);
        txt_titile = findViewById(R.id.txt_titile);
        txt_titile.setText("Offer Products");
        search_txt.setVisibility(View.GONE);
        searh.setVisibility(View.GONE);
        arrow = findViewById(R.id.arrow);
        wislist = findViewById(R.id.wislist);
        search = findViewById(R.id.search);
        cart = findViewById(R.id.cart);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OfferActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        wislist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OfferActivity.this,WishlistActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OfferActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        accesories();
    }
    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(OfferActivity.this, "Refreshed!", Toast.LENGTH_SHORT).show();
                accesories();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
    public void accesories() {
        rv_products.setVisibility(View.VISIBLE);
        recentlyaddedwiselist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();

        String baseUrl = ProductConfig.offer_list;
        progressDialog.show();

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        recentlyaddedwiselist = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i <array.length(); i++) {
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
                            model.setExclusive_product(array.getJSONObject(i).getString("exclusive_product"));
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            JSONArray array1 = product.getJSONArray("image");
                            model.setImage(array1.getJSONObject(0).getString("image"));

                            String stocks =  model.setStock(array.getJSONObject(i).getString("stock"));

                            if (!stocks.equalsIgnoreCase("")) {
                                // Toast.makeText(MainActivity.this, " out of stock!!!!!!!", Toast.LENGTH_LONG).show();
                                recentlyaddedwiselist.add(model);
                            }

                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(OfferActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_products.setLayoutManager(layoutManager);
                        productListAdapter = new RecentlyAdapter(OfferActivity.this, recentlyaddedwiselist);
                        rv_products.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(OfferActivity.this, 2);
                        rv_products.setLayoutManager(gridLayoutManager);
                        rv_products.setHasFixedSize(true);

                        progressDialog.dismiss();

                    } else {
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
                //   progressDialog.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(OfferActivity.this);
        requestQueue.add(jsObjRequest);
    }
}