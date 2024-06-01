package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.taprocycle.service.Adapter.ExpandableListAdapter;
import com.taprocycle.service.Adapter.ShopDataAdapter;
import com.taprocycle.service.Adapter.SuggestionAdapter;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductsModel;
import com.taprocycle.service.test.model.ShopData;
import com.taprocycle.service.test.model.ShopDataDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class ShopbyCategoryActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    ArrayList<ShopData> listData = new ArrayList<>();
    String scid="",sid_name="";
    TextView badge_notification;
    ProgressDialog progressDialog;
    ImageView arrow, cart, search,home;
    AutoCompleteTextView search_txt;
    GifImageView gif;
    LinearLayout arrow_lin;
    public ArrayList<ProductsModel> spinnerList = new ArrayList<>();
    SuggestionAdapter suggestionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopby_category);
        /////////NETWORK CONDITION CHECK///////////

        String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(ShopbyCategoryActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }
        progressDialog = new ProgressDialog(ShopbyCategoryActivity.this);
        progressDialog.setMessage("Loading.....");
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        gif=findViewById(R.id.gif);
        search_txt=findViewById(R.id.search_txt);

        shopby();
        loadserach();

        search_txt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if(cs.length()>1) {
                    suggestionAdapter.getFilter().filter(cs);
                }
                //final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(HomeActivity.this, R.anim.move);auto_search_lin.setLayoutAnimation(controller);
                                   /* Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                                            R.anim.move);
                                    auto_search_lin.startAnimation(animation1);*/
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
/*
                Intent intent=new Intent(ShopbyCategoryActivity.this, MainActivity.class);
                startActivity(intent);*/
                 //  ShopDataDetail mItem = listData.get(groupPosition).getListInnerMenu().get(childPosition);
                // You can get your listView child click event and data here.

                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int position, long l) {

                ShopData mItem = listData.get(position);

                // You can get your listView header click event and data here.

                return false;
            }
        });

        badge_notification=findViewById(R.id.badge_notification);
        cart = findViewById(R.id.cart);
        arrow = findViewById(R.id.arrow);
        arrow_lin=findViewById(R.id.arrow_lin);
        search = findViewById(R.id.search);
        home=findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopbyCategoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopbyCategoryActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopbyCategoryActivity.this, SearchActivity.class);
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
        String customer_id = BSession.getInstance().getUser_id(ShopbyCategoryActivity.this);
        if (customer_id.equalsIgnoreCase("")) {
            gif.setVisibility(View.GONE);
        } else {
            getCartCount();

        }

    }
    private void loadserach() {

        String userid = BSession.getInstance().getUser_id(getApplicationContext());
        spinnerList = new ArrayList<>();

        final Map<String, String> params = new HashMap<>();

        String baseUrl = ProductConfig.keywords;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {

                        JSONArray jsonArray = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            final JSONObject jsonObject = jsonArray.getJSONObject(i);

                            ProductsModel model = new ProductsModel();
                            model.setId(jsonObject.getString("id"));
                            model.setWeb_title(jsonObject.getString("web_title"));
                            spinnerList.add(model);

                             suggestionAdapter = new SuggestionAdapter(ShopbyCategoryActivity.this, R.layout.suggestion_items, spinnerList);
                            search_txt.setAdapter(suggestionAdapter);
                            search_txt.setThreshold(1);
                            search_txt.setOnItemClickListener(
                                    new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            search_txt.setText(model.getWeb_title());
                                            ProductsModel model = spinnerList.get(position);
                                            String title = model.getWeb_title();
                                            Intent intent = new Intent(getApplicationContext(), SearchableProductActivity.class);
                                            intent.putExtra("keyword", title);
                                            startActivity(intent);
                                            search_txt.setText("");

                                        }
                                    });



                        }
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsObjRequest);
    }

    public void shopby() {
        final Map<String, String> params = new HashMap<>();
        String baseUrl   = ProductConfig.menu ;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {

                    listData = new ArrayList<>();

                    JSONObject jObjMain = new JSONObject(response);
                    JSONArray jArrData = jObjMain.getJSONArray("storeList");

                    for (int i = 0; i < jArrData.length(); i++) {

                        ShopData bData = new ShopData();

                        JSONObject jObjIn = new JSONObject(jArrData.getString(i));

                        bData.setWeb_title(jObjIn.getString("web_title"));
                        bData.setWeb_image(jObjIn.getString("web_image"));
                        //bData.setWeb_image(jObjIn.getString("web_image"));

                        JSONArray jArrItemData = new JSONArray(jObjIn.getString("subcategory"));
                           for (int j = 0; j < jArrItemData.length(); j++) {

                               JSONObject jObjItem = new JSONObject(jArrItemData.getString(j));

                               try {
                                   String item_id = jObjItem.getString("scid");
                                   String item_name = jObjItem.getString("subcategoryname");

                                   bData.getCh_list().add(new ShopDataDetail(item_id, item_name));
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                        System.out.println("Response=="+response);
                        listData.add(bData);
                    }

                    expandableListView.setAdapter(new ShopDataAdapter(ShopbyCategoryActivity.this, listData));

                } catch (JSONException e) {
                    e.printStackTrace();
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
        RequestQueue requestQueue = Volley.newRequestQueue(ShopbyCategoryActivity.this);
        requestQueue.add(jsObjRequest);
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
                        Intent intent=new Intent(ShopbyCategoryActivity.this,SearchableProductActivity.class);
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
        String customer_id = BSession.getInstance().getUser_id(ShopbyCategoryActivity.this);
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
                                Intent intent=new Intent(ShopbyCategoryActivity.this,CartActivity.class);
                                startActivity(intent);
                            }
                        });
                        gif.setVisibility(View.GONE);
                    } else {
                        badge_notification.setText("0");
                        gif.setVisibility(View.GONE);

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
    @Override
    protected void onResume() {
        super.onResume();
        String customer_id = BSession.getInstance().getUser_id(ShopbyCategoryActivity.this);
        if (customer_id.equalsIgnoreCase("")) {

        } else {
            getCartCount();
        }
    }
}