package com.taprocycle.service.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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
import com.bumptech.glide.Glide;

import com.taprocycle.service.Adapter.SearchAdapter;
import com.taprocycle.service.Adapter.SearchDataAdapter;
import com.taprocycle.service.Adapter.SuggestionAdapter;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.WishlistActivity;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.CategoryModel;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductModel;
import com.taprocycle.service.test.model.ProductsModel;
import com.taprocycle.service.test.model.SearchModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    RecyclerView rv_search,rv_searchdata,rv_category;
    private List<ProductModel> categorylist = new ArrayList<>();
    private List<SearchModel> searchlist = new ArrayList<>();
    private List<SearchModel> searchdatalist = new ArrayList<>();
    ImageView arrow,cart,search,home,search_remove;
    AutoCompleteTextView search_txt;
    ImageView wislist;
    private List<CategoryModel> categoryModelList = new ArrayList<>();
    ProgressDialog progressDialog;
    TextView badge_notification;
    LinearLayout arrow_lin;
    AutoCompleteTextView auto_search;
    public SearchAdapter fruitAdapter;
    public ArrayList<ProductsModel> spinnerList = new ArrayList<>();
    SuggestionAdapter suggestionAdapter;
    String name ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2); /////////NETWORK CONDITION CHECK///////////

        String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(SearchActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }


        progressDialog = new ProgressDialog(SearchActivity.this);
        progressDialog.setMessage("Loading.....");
        search_txt=findViewById(R.id.search_txt);
        search_txt.requestFocus();
        rv_category=findViewById(R.id.rv_category);
        rv_search=findViewById(R.id.rv_search);
        rv_searchdata=findViewById(R.id.rv_searchdata);
        arrow=findViewById(R.id.arrow);
        cart=findViewById(R.id.cart);
        search=findViewById(R.id.search);
        wislist=findViewById(R.id.wislist);
        search_remove=findViewById(R.id.search_remove);
        search.setVisibility(View.GONE);
        search_remove.setVisibility(View.GONE);
        arrow_lin=findViewById(R.id.arrow_lin);
        home=findViewById(R.id.home);
        badge_notification=findViewById(R.id.badge_notification);
       /* InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(search_txt, InputMethodManager.SHOW_IMPLICIT);*/
      /*  InputMethodManager keyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(mUserNameEdit, 0);*/
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        search_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchadd(search_txt.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

        search_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_txt.getText().clear();
                search_remove.setVisibility(View.GONE);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

        wislist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchActivity.this, WishlistActivity.class);
                startActivity(intent);
            }
        });

        arrow_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        search();
        searchdata();
        String customer_id = BSession.getInstance().getUser_id(SearchActivity.this);
        if (customer_id.equalsIgnoreCase("")) {

        } else {
            getCartCount();
        }
        loadserach();

        search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                search_remove.setVisibility(View.VISIBLE);
                if(cs.length()>1) {
                    suggestionAdapter.getFilter().filter(cs);
                }
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
                            suggestionAdapter = new SuggestionAdapter(SearchActivity.this, R.layout.suggestion_items, spinnerList);
                            search_txt.setAdapter(suggestionAdapter);

                            search_txt.setThreshold(1);
                            search_txt.setOnItemClickListener(
                                    new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            search_txt.setText(model.getWeb_title());
                                            ProductsModel model = spinnerList.get(position);
                                            String title = model.getWeb_title();

                                           /* Intent intent = new Intent(getApplicationContext(), SearchableProductActivity.class);
                                            intent.putExtra("keyword", title);
                                            startActivity(intent);*/
                                            search_txt.setText("");
                                            String suggestion_title=  spinnerList.get(position).getWeb_title();
                                            searchadd(suggestion_title);
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

    public void getCartCount() {
        final Map<String, String> params = new HashMap<>();
        String customer_id = BSession.getInstance().getUser_id(SearchActivity.this);
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

    private void searchadd(String suggestion_title) {
        final Map<String, String> params = new HashMap<>();
        String para1="?user_id="+BSession.getInstance().getUser_id(getApplicationContext());
        String para2="&title="+suggestion_title;
        System.out.println("titledd"+suggestion_title);
        System.out.println("titledusertd"+BSession.getInstance().getUser_id(getApplicationContext()));

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
                        Intent intent=new Intent(SearchActivity.this,SearchableProductActivity.class);
                        intent.putExtra("keyword",suggestion_title);
                        System.out.println("titlee=="+suggestion_title);
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


    public void search() {
        searchlist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str1 = "?user_id=" + BSession.getInstance().getUser_id(getApplicationContext());
        String para_str2 = "&type=" ;

        System.out.println("BSession.getInstance().getUser_id(getApplicationContext())"+BSession.getInstance().getUser_id(getApplicationContext()));
        String baseUrl   = ProductConfig.usersearchkeyword + para_str1+para_str2;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        searchlist = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            SearchModel model = new SearchModel();
                            model.setId(array.getJSONObject(i).getString("count"));
                            model.setTitle(array.getJSONObject(i).getString("web_title"));
                            searchlist.add(model);
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        rv_search.setLayoutManager(layoutManager);
                        SearchAdapter productListAdapter = new SearchAdapter(SearchActivity.this, searchlist);
                        rv_search.setAdapter(productListAdapter);
                        rv_search.setHasFixedSize(true);
                    } else {
                        //Toast.makeText(SearchActivity.this, "No Product Result Found", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
        requestQueue.add(jsObjRequest);
    }

    public void searchdata() {
        searchdatalist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str1 = "?user_id=" + BSession.getInstance().getUser_id(getApplicationContext());
        String para_str2 = "&type=" + "2";
        String baseUrl   = ProductConfig.usersearchkeyword + para_str1+para_str2;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        searchdatalist = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            SearchModel model = new SearchModel();
                            model.setTitle(array.getJSONObject(i).getString("web_title"));
                            searchdatalist.add(model);
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        rv_searchdata.setLayoutManager(layoutManager);
                        SearchDataAdapter productListAdapter = new SearchDataAdapter(SearchActivity.this, searchdatalist);
                        rv_searchdata.setAdapter(productListAdapter);
                        rv_searchdata.setHasFixedSize(true);

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
        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
        requestQueue.add(jsObjRequest);
    }


    public class SCategoryAdapter extends RecyclerView.Adapter<SCategoryAdapter.MailViewHolder> {

        List<CategoryModel> bannerlist;
        private Context mContext;
        AlertDialog dialog;


        public SCategoryAdapter(Context mContext, List<CategoryModel> bannerlist) {
            this.mContext = mContext;
            this.bannerlist = bannerlist;
        }

        @NonNull
        @Override
        public SCategoryAdapter.MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorylist, parent, false);
            return new SCategoryAdapter.MailViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final SCategoryAdapter.MailViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            final CategoryModel model = bannerlist.get(position);

            holder.title.setText(model.getWeb_title());
            String img = bannerlist.get(position).getWeb_image();
            Glide.with(mContext)
                    .load(img)
                    .placeholder(R.drawable.logo)
                    .into(holder.bannerimg);

            holder.lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,SubcategoryActivity.class);
                    intent.putExtra("cid",bannerlist.get(position).getCat_id());
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return bannerlist.size();
        }


        public class MailViewHolder extends RecyclerView.ViewHolder {

            ImageView bannerimg;
            TextView title;
            LinearLayout lin;

            public MailViewHolder(@NonNull View itemView) {
                super(itemView);


                bannerimg     = itemView.findViewById(R.id.cat_img);
                title         = itemView.findViewById(R.id.title);
                lin           = itemView.findViewById(R.id.lin);
            }
        }
    }

}