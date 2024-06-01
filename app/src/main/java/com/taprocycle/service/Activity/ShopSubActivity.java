package com.taprocycle.service.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.taprocycle.service.Adapter.ShopsubAdapter;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;
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

public class ShopSubActivity extends AppCompatActivity {
    RecyclerView rv_products;
    private List<ProductsModel> subwiselist = new ArrayList<>();
    String scid="",sid_name="";
    TextView badge_notification;
    ProgressDialog progressDialog;
    ImageView arrow, cart, search,home;
    EditText search_txt;
    LinearLayout arrow_lin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_sub);

        progressDialog = new ProgressDialog(ShopSubActivity.this);
        progressDialog.setMessage("Loading.....");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            scid = (extras.getString("scid"));
            sid_name = (extras.getString("sid_name"));


        } else {
        }
        rv_products=findViewById(R.id.rv_products);
        search_txt=findViewById(R.id.search_txt);
        badge_notification=findViewById(R.id.badge_notification);
        cart = findViewById(R.id.cart);
        arrow = findViewById(R.id.arrow);
        search = findViewById(R.id.search);
        arrow_lin=findViewById(R.id.arrow_lin);
        home=findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopSubActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopSubActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopSubActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        arrow_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    System.out.println("search_txt==```");
                    searchadd();
                 /*   Intent intent=new Intent(SubcategoryActivity.this,SearchableProductActivity.class);
                    intent.putExtra("keyword",search_txt.getText().toString().trim());
                    startActivity(intent);*/
                    return true;
                }
                return false;
            }
        });
        getCartCount();
        sublist();
        // toolbar();
    }
    private void searchadd() {

        final Map<String, String> params = new HashMap<>();
        String para1="?user_id="+ BSession.getInstance().getUser_id(getApplicationContext());
        String para2="&title="+search_txt.getText().toString().trim();
        progressDialog.show();
        String baseUrl = ProductConfig.user_search+para1+para2;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("message") && jsonResponse.getString("message").equals("Successfully Registered")) {
                        Intent intent=new Intent(ShopSubActivity.this,SearchableProductActivity.class);
                        intent.putExtra("keyword",search_txt.getText().toString().trim());
                        startActivity(intent);

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
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
    public void getCartCount() {

        final Map<String, String> params = new HashMap<>();
        String customer_id = BSession.getInstance().getUser_id(ShopSubActivity.this);
        String  para_str = "?user_id=" + customer_id;
        String  para_str1 = "&user_type=" + BSession.getInstance().getType(getApplicationContext());

        String   baseUrl = ProductConfig.cartlist + para_str+para_str1;

        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {

                        badge_notification.setText(jsonResponse.getString("total_cnt"));
                        cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(ShopSubActivity.this,CartActivity.class);
                                startActivity(intent);
                            }
                        });
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
    /*private void toolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setTitle(sid_name);
        toolbar.setTitleTextColor(Color.WHITE);
    }
*/
    public void sublist() {

        subwiselist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str3 = "?scid=" + scid;

        String baseUrl   = ProductConfig.productlist + para_str3;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        subwiselist = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            ProductsModel model = new ProductsModel();
                            model.setPid(array.getJSONObject(i).getString("pid"));
                            model.setCid(array.getJSONObject(i).getString("cid"));
                            model.setScid(array.getJSONObject(i).getString("scid"));
                            model.setProductname(array.getJSONObject(i).getString("productname"));
                            model.setDescription(array.getJSONObject(i).getString("description"));
                            model.setShort_description(array.getJSONObject(i).getString("short_description"));
                            model.setRatting(array.getJSONObject(i).getString("ratting"));
                            model.setPcode(array.getJSONObject(i).getString("pcode"));
                            model.setPrice(array.getJSONObject(i).getString("price"));
                            model.setStock(array.getJSONObject(i).getString("stock"));
                            model.setQty(array.getJSONObject(i).getString("qty"));
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            model.setExclusive_product(array.getJSONObject(i).getString("exclusive_product"));
                            JSONArray array1 = product.getJSONArray("image");
                            model.setImage(array1.getJSONObject(0).getString("image"));
                            subwiselist.add(model);
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(ShopSubActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_products.setLayoutManager(layoutManager);
                        ShopsubAdapter productListAdapter = new ShopsubAdapter(ShopSubActivity.this, subwiselist);
                        rv_products.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ShopSubActivity.this, 2);
                        rv_products.setLayoutManager(gridLayoutManager);
                        rv_products.setHasFixedSize(true);


                    } else {
                         Toast.makeText(ShopSubActivity.this, "No Product Result Found", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(ShopSubActivity.this);
        requestQueue.add(jsObjRequest);
    }
    @Override
    protected void onResume() {
        super.onResume();
        String customer_id = BSession.getInstance().getUser_id(ShopSubActivity.this);
        if (customer_id.equalsIgnoreCase("")) {

        } else {
            getCartCount();
        }

    }
}

