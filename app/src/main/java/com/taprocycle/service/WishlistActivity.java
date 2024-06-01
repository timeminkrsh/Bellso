package com.taprocycle.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.taprocycle.service.Activity.CartActivity;
import com.taprocycle.service.WishlistActivity;
import com.taprocycle.service.WishlistActivity;
import com.taprocycle.service.WishlistActivity;
import com.taprocycle.service.Adapter.WishAdapter;
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

public class WishlistActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView rv_wishlist;
    ProgressDialog progressDialog;
    WishAdapter productListAdapter ;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView txt_titile;
    AutoCompleteTextView search_txt;
    ImageView wislist,search,cart,arrow,not_found;
    LinearLayout arrow_lin;
    TextView rel_count_add,badge_notification;
    String customer_id;
    private List<ProductsModel> recentlyaddedwiselist = new ArrayList<>();
    RelativeLayout searh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        rv_wishlist = findViewById(R.id.rv_wishlist);
        search_txt = findViewById(R.id.search_txt);
        searh = findViewById(R.id.searh);
        wislist = findViewById(R.id.wislist);
        arrow_lin = findViewById(R.id.arrow_lin);
        arrow = findViewById(R.id.arrow);
        search = findViewById(R.id.search);
        not_found = findViewById(R.id.not_found);
        badge_notification = findViewById(R.id.badge_notification);
        cart = findViewById(R.id.cart);
        txt_titile = findViewById(R.id.txt_titile);
        txt_titile.setText("My Wishlist");
        search_txt.setVisibility(View.GONE);
        searh.setVisibility(View.GONE);
        wislist.setVisibility(View.GONE);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WishlistActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WishlistActivity.this, WishlistActivity.class);
                startActivity(intent);
            }
        });
        arrow_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WishlistActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        /*String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(WishlistActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }*/

         customer_id = BSession.getInstance().getUser_id(WishlistActivity.this);
        if (customer_id.equalsIgnoreCase("")) {

        } else {
            getCartCount();
        }
        wishList();
    }
    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WishlistActivity.this, "Refreshed!", Toast.LENGTH_SHORT).show();
                wishList();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
    public void getCartCount() {
        final Map<String, String> params = new HashMap<>();
        String customer_id = BSession.getInstance().getUser_id(WishlistActivity.this);
        String  para_str   = "?user_id=" + customer_id;
        String  para_str1  = "&user_type=" + BSession.getInstance().getType(getApplicationContext());
        String  baseUrl    = ProductConfig.cartlist + para_str+para_str1;

        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        badge_notification.setText(jsonResponse.getString("total_cnt"));
                    } else {
                        badge_notification.setText("0");
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

    public void wishList() {
        recentlyaddedwiselist = new ArrayList<>();
        rv_wishlist.setVisibility(View.VISIBLE);
        final Map<String, String> params = new HashMap<>();
        String customer_id = BSession.getInstance().getUser_id(WishlistActivity.this);
        String para_str = "?user_id=" + customer_id;
        System.out.println("userid=="+customer_id);
        String baseUrl = ProductConfig.whishlist+para_str;
        progressDialog.show();

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    progressDialog.dismiss();
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        recentlyaddedwiselist = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            ProductsModel model = new ProductsModel();
                            model.setQty(array.getJSONObject(i).getString("min_qty"));
                            model.setPid(array.getJSONObject(i).getString("pid"));
                            model.setCid(array.getJSONObject(i).getString("cid"));
                            model.setScid(array.getJSONObject(i).getString("scid"));
                            model.setProductname(array.getJSONObject(i).getString("productname"));
                            model.setDescription(array.getJSONObject(i).getString("description"));
                            //model.setShort_description(array.getJSONObject(i).getString("short_description"));
                            model.setRatting(array.getJSONObject(i).getString("ratting"));
                            model.setPcode(array.getJSONObject(i).getString("pcode"));
                            model.setPrice(array.getJSONObject(i).getString("basic_price"));
                            System.out.println("price=="+model.getPrice());
                            model.setStock(array.getJSONObject(i).getString("stock"));
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            model.setExclusive_product(array.getJSONObject(i).getString("exclusive_product"));
                            JSONArray array1 = product.getJSONArray("image");
                            model.setImage(array1.getJSONObject(0).getString("image"));

                            String stocks =  model.setStock(array.getJSONObject(i).getString("stock"));

                            if (!stocks.equalsIgnoreCase("")) {
                                recentlyaddedwiselist.add(model);
                            }

                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(WishlistActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_wishlist.setLayoutManager(layoutManager);
                        productListAdapter = new WishAdapter(WishlistActivity.this, recentlyaddedwiselist);
                        rv_wishlist.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(WishlistActivity.this, 2);
                        rv_wishlist.setLayoutManager(gridLayoutManager);
                        rv_wishlist.setHasFixedSize(true);
                        System.out.println("Reposnse=="+response);
                        progressDialog.dismiss();

                    } else {
                        rv_wishlist.setVisibility(View.GONE);
                        not_found.setVisibility(View.VISIBLE);
                        Toast.makeText(WishlistActivity.this, "No Product list found", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(WishlistActivity.this);
        requestQueue.add(jsObjRequest);
    }

}