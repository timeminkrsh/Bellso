package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import com.taprocycle.service.Adapter.ProductsAdapter;
import com.taprocycle.service.Adapter.SearchDataAdapter;
import com.taprocycle.service.Adapter.SearchableProductAdapter;
import com.taprocycle.service.Adapter.SuggestionAdapter;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.WishlistActivity;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductsModel;
import com.taprocycle.service.test.model.SearchModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchableProductActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private List<ProductsModel> productwiselist = new ArrayList<>();
    RecyclerView rv_menuList;
    String key="";
    ImageView cart,home,not_found;
    ImageView arrow,search,search_remove,wislist;
    AutoCompleteTextView search_txt;
    TextView badge_notification;
    LinearLayout arrow_lin;
    private List<ProductsModel> suggestionlist = new ArrayList<>();
    SuggestionAdapter suggestionAdapter;
    public ArrayList<ProductsModel> spinnerList = new ArrayList<>();
    LinearLayout sort, lin_filter;
    String sub = "", scid = "", cid = "",first,second;
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private ArrayList<String> mFragmentscidList = new ArrayList<>();

    private ArrayList<String> mFragmentcidList = new ArrayList<>();
    String pricefrom="",priceto="";
    private int selectedRadioButtonId = R.id.defaultt;
    SwipeRefreshLayout swipeRefreshLayout;
    String stock="0";
    private TabLayout tabLayout;
    ProgressDialog progressDialog;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable_product);
        /////////NETWORK CONDITION CHECK///////////

        String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(SearchableProductActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");


        search_txt = findViewById(R.id.search_txt);
        badge_notification = findViewById(R.id.badge_notification);
        search=findViewById(R.id.search);
        tabLayout = findViewById(R.id.tabs);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        viewPager = findViewById(R.id.viewpager);
        View root = tabLayout.getChildAt(0);
        LinearLayout linearLayout = (LinearLayout) root;
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.TRANSPARENT);
        drawable.setSize(20, 0);
        linearLayout.setDividerDrawable(drawable);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = (extras.getString("keyword"));
            search_txt.setText(key);
        } else {
        }
        loadserach();
        rv_menuList = findViewById(R.id.rv_menuList);
        cart = findViewById(R.id.cart);
        not_found = findViewById(R.id.not_found);
        arrow = findViewById(R.id.arrow);
        arrow_lin = findViewById(R.id.arrow_lin);
        home=findViewById(R.id.home);
        sort = findViewById(R.id.sort);
        lin_filter = findViewById(R.id.lin_filter);
        wislist=findViewById(R.id.wislist);
        search_remove = findViewById(R.id.search_remove);
        search_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_txt.getText().clear();
                search_remove.setVisibility(View.GONE);
            }
        });
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
        wislist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchableProductActivity.this, WishlistActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchableProductActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        String customer_id = BSession.getInstance().getUser_id(SearchableProductActivity.this);
        if (customer_id.equalsIgnoreCase("")) {

        } else {
            getCartCount();
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchableProductActivity.this, SearchableProductActivity.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchableProductActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        arrow_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lin_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog1();
            }
        });
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

        loadProductList();
        //return ;
    }
    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SearchableProductActivity.this, "Refreshed!", Toast.LENGTH_SHORT).show();
                loadProductList();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SearchableProductActivity.this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
        RadioGroup radioGroup = bottomSheetDialog.findViewById(R.id.radiogroup);
        LinearLayout lin = bottomSheetDialog.findViewById(R.id.lin);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                selectedRadioButtonId = id;
                RadioButton defaultt  = radioGroup.findViewById(R.id.defaultt);
                RadioButton price_l_h = radioGroup.findViewById(R.id.price_l_h);
                RadioButton price_h_l = radioGroup.findViewById(R.id.price_h_l);
                RadioButton newest    = radioGroup.findViewById(R.id.newest);
                boolean isChecked  = defaultt.isChecked();
                boolean isChecked1 = price_l_h.isChecked();
                boolean isChecked2 = price_h_l.isChecked();
                boolean isChecked3 = newest.isChecked();

                if (isChecked) {
                    first = "";
                    second = "";
                } else if (isChecked1) {
                    first = "asc";
                    second = "";
                } else if (isChecked2) {
                    first = "dsc";
                    second = "";
                } else if (isChecked3) {
                    first = "asc";
                    second = "asc";
                }

                // Uncheck all radio buttons
                /*defaultt.setChecked(false);
                price_l_h.setChecked(false);
                price_h_l.setChecked(false);
                newest.setChecked(false);*/

                // Check the selected radio button
                RadioButton selectedRadioButton1 = arg0.findViewById(id);
                selectedRadioButton1.setChecked(true);
                if (selectedRadioButtonId == R.id.defaultt) {
                    defaultt.setChecked(true);
                } else if (selectedRadioButtonId == R.id.price_l_h) {
                    price_l_h.setChecked(true);
                } else if (selectedRadioButtonId == R.id.price_h_l) {
                    price_h_l.setChecked(true);
                } else if (selectedRadioButtonId == R.id.newest) {
                    newest.setChecked(true);
                }

                loadProductsList(defaultt,price_l_h,price_h_l,newest);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }


    @SuppressLint("NonConstantResourceId")
    private void loadProductsList(RadioButton defaultt, RadioButton price_l_h, RadioButton price_h_l, RadioButton newest) {
        productwiselist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        progressDialog.show();
        rv_menuList.setVisibility(View.VISIBLE);
        String para_str1 = "?keywords=" + key;
        String para_str2 = "&user_id=" + BSession.getInstance().getUser_id(getApplicationContext());
        String para_str3 = "&priceorder=" + first;
        String para_str4 = "&neworder=" + second;
        String para_str5 = "&pricefrom=" + pricefrom;
        String para_str6 = "&priceto=" + priceto;
        String baseUrl   = ProductConfig.allproducts + para_str1+para_str2+para_str3+para_str4+para_str5+para_str6;
        System.out.println("baseUrl=="+baseUrl);
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        productwiselist = new ArrayList<>();
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
                            model.setShort_description(array.getJSONObject(i).getString("short_description"));
                            model.setRatting(array.getJSONObject(i).getString("ratting"));
                            model.setPcode(array.getJSONObject(i).getString("pcode"));
                            model.setPrice(array.getJSONObject(i).getString("price"));
                            model.setStock(array.getJSONObject(i).getString("stock"));
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            model.setExclusive_product(array.getJSONObject(i).getString("exclusive_product"));
                            System.out.println("totalamount=="+array.getJSONObject(i).getString("discount_price"));
                            JSONArray array1 = product.getJSONArray("image");
                            model.setImage(array1.getJSONObject(0).getString("image"));
                            String stocks =  model.setStock(array.getJSONObject(i).getString("stock"));
                            //productwiselist.add(model);

                            if (!stocks.equalsIgnoreCase("")) {
                                productwiselist.add(model);
                            }
                            //productwiselist.clear();
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchableProductActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_menuList.setLayoutManager(layoutManager);
                        SearchableProductAdapter productListAdapter = new SearchableProductAdapter(SearchableProductActivity.this, productwiselist);
                        rv_menuList.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchableProductActivity.this, 2);
                        rv_menuList.setLayoutManager(gridLayoutManager);
                        rv_menuList.setHasFixedSize(true);
                        progressDialog.dismiss();
                        not_found.setVisibility(View.GONE);
                    } else {
                        rv_menuList.setVisibility(View.GONE);
                        not_found.setVisibility(View.VISIBLE);
                        Toast.makeText(SearchableProductActivity.this, "No Product Result Found", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
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
                //progressDialog.dismiss();
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
        RequestQueue requestQueue = Volley.newRequestQueue(SearchableProductActivity.this);
        requestQueue.add(jsObjRequest);
    }

    private void showBottomSheetDialog1() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SearchableProductActivity.this);
        bottomSheetDialog.setContentView(R.layout.bottom_filter);
        RadioGroup radioGroup1 = bottomSheetDialog.findViewById(R.id.radiogroup1);
        LinearLayout lin = bottomSheetDialog.findViewById(R.id.lin);
        RadioGroup radioGroup2 = bottomSheetDialog.findViewById(R.id.radiogroup2);
        RadioGroup radioGroup3 = bottomSheetDialog.findViewById(R.id.radiogroup3);
        RadioGroup radioGroup4 = bottomSheetDialog.findViewById(R.id.radiogroup4);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                RadioButton two_below=radioGroup1.findViewById(R.id.two_below);
                RadioButton three_four=radioGroup1.findViewById(R.id.three_four);

                boolean isChecked  = two_below.isChecked();
                boolean isChecked1 = three_four.isChecked();

                if (isChecked) {
                    pricefrom = "100";
                    priceto = "299";
                    rv_menuList.setAdapter(null);
                    productwiselist.clear();
                    loadProductList();
                    bottomSheetDialog.dismiss();
                } else if (isChecked1) {
                    pricefrom = "300";
                    priceto = "499";
                    rv_menuList.setAdapter(null);
                    productwiselist.clear();
                    loadProductList();
                    bottomSheetDialog.dismiss();
                }
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                RadioButton five_six=radioGroup2.findViewById(R.id.five_six);
                RadioButton seven_nine=radioGroup2.findViewById(R.id.seven_nine);

                boolean isChecked = five_six.isChecked();
                boolean isChecked1 = seven_nine.isChecked();


                if (isChecked) {
                    pricefrom = "500";
                    priceto = "699";
                    rv_menuList.setAdapter(null);
                    productwiselist.clear();
                    loadProductList();
                    bottomSheetDialog.dismiss();
                } else if (isChecked1) {
                    pricefrom = "700";
                    priceto = "999";
                    rv_menuList.setAdapter(null);
                    productwiselist.clear();
                    loadProductList();
                    bottomSheetDialog.dismiss();
                }
            }
        });
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                RadioButton ten_forteen=radioGroup3.findViewById(R.id.ten_forteen);
                RadioButton fifteen_thousand=radioGroup3.findViewById(R.id.fifteen_thousand);

                boolean isChecked = ten_forteen.isChecked();
                boolean isChecked1 = fifteen_thousand.isChecked();


                if (isChecked) {
                    pricefrom = "1000";
                    priceto = "1499";
                    rv_menuList.setAdapter(null);
                    productwiselist.clear();
                    loadProductList();
                    bottomSheetDialog.dismiss();
                } else if (isChecked1) {
                    pricefrom = "1500";
                    priceto = "1999";
                    rv_menuList.setAdapter(null);
                    productwiselist.clear();
                    loadProductList();
                    bottomSheetDialog.dismiss();
                }
            }
        });
        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                RadioButton two_two  = radioGroup4.findViewById(R.id.two_two);
                RadioButton two_above= radioGroup4.findViewById(R.id.two_above);

                boolean isChecked = two_two.isChecked();
                boolean isChecked1 = two_above.isChecked();

                if (isChecked) {
                    pricefrom = "2000";
                    priceto = "2499";
                    rv_menuList.setAdapter(null);
                    productwiselist.clear();
                    loadProductList();
                    bottomSheetDialog.dismiss();
                } else if (isChecked1) {
                    pricefrom = "2500";
                    priceto = "";
                    rv_menuList.setAdapter(null);
                    productwiselist.clear();
                    loadProductList();
                    bottomSheetDialog.dismiss();
                }
            }
        });
        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();


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


       // loadProductLists();
       // getSubcategoryList();

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

                            suggestionAdapter = new SuggestionAdapter(SearchableProductActivity.this, R.layout.suggestion_items, spinnerList);
                            search_txt.setAdapter(suggestionAdapter);
                            search_txt.setThreshold(1);
                            search_txt.setOnItemClickListener(
                                    new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            search_txt.setText(model.getWeb_title());
                                            ProductsModel model = spinnerList.get(position);
                                            String title = model.getWeb_title();
                                            /*Intent intent = new Intent(getApplicationContext(), SearchableProductActivity.class);
                                            intent.putExtra("keyword", title);
                                            startActivity(intent);*/
                                            search_txt.setText("");
                                            String suggestion_title=  spinnerList.get(position).getWeb_title();
                                            key = spinnerList.get(position).getWeb_title();
                                            System.out.println("search=="+key);
                                            searchadd(suggestion_title);
                                            System.out.println("search=="+suggestion_title);

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
                        //progressDialog.dismiss();
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
        String customer_id = BSession.getInstance().getUser_id(SearchableProductActivity.this);
        String  para_str   = "?user_id=" + customer_id;
        String  para_str1  = "&user_type=" + BSession.getInstance().getType(getApplicationContext());
        String   baseUrl = ProductConfig.cartlist + para_str+para_str1;

        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {

                        badge_notification.setText(jsonResponse.getString("total_cnt"));
                        System.out.println("badge=="+jsonResponse.getString("total_cnt"));
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
    }

    private void searchadd(String suggestion_title) {
        final Map<String, String> params = new HashMap<>();
        String para1="?user_id="+ BSession.getInstance().getUser_id(getApplicationContext());
        String para2="&title="+search_txt.getText().toString().trim();
        String baseUrl = ProductConfig.user_search+para1+para2;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("message") && jsonResponse.getString("message").equals("Successfully Registered")) {
                        search_txt.setText(suggestion_title);
                        loadProductList();

                    } else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                //progressDialog.dismiss();
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


    public void loadProductList() {
        productwiselist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        progressDialog.show();
        rv_menuList.setVisibility(View.VISIBLE);
        String para_str1 = "?keywords=" + key;
        String para_str2 = "&user_id=" + BSession.getInstance().getUser_id(getApplicationContext());
        String para_str3 = "&priceorder=" + first;
        String para_str4 = "&neworder=" + second;
        String para_str5 = "&pricefrom=" + pricefrom;
        String para_str6 = "&priceto=" + priceto;
        String baseUrl   = ProductConfig.allproducts + para_str1+para_str2+para_str3+para_str4+para_str5+para_str6;
        System.out.println("baseUrl=="+baseUrl);
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        productwiselist = new ArrayList<>();
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
                            model.setShort_description(array.getJSONObject(i).getString("short_description"));
                            model.setRatting(array.getJSONObject(i).getString("ratting"));
                            model.setPcode(array.getJSONObject(i).getString("pcode"));
                            model.setPrice(array.getJSONObject(i).getString("price"));
                            model.setStock(array.getJSONObject(i).getString("stock"));
                            model.setExclusive_product(array.getJSONObject(i).getString("exclusive_product"));
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            System.out.println("totalamount=="+array.getJSONObject(i).getString("discount_price"));
                            JSONArray array1 = product.getJSONArray("image");
                            model.setImage(array1.getJSONObject(0).getString("image"));
                            String stocks =  model.setStock(array.getJSONObject(i).getString("stock"));
                            //productwiselist.add(model);

                            if (!stocks.equalsIgnoreCase("")) {
                                productwiselist.add(model);
                            }
                               //productwiselist.clear();
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchableProductActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_menuList.setLayoutManager(layoutManager);
                        SearchableProductAdapter productListAdapter = new SearchableProductAdapter(SearchableProductActivity.this, productwiselist);
                        rv_menuList.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchableProductActivity.this, 2);
                        rv_menuList.setLayoutManager(gridLayoutManager);
                        rv_menuList.setHasFixedSize(true);
                        progressDialog.dismiss();
                        not_found.setVisibility(View.GONE);
                    } else {
                        rv_menuList.setVisibility(View.GONE);
                        not_found.setVisibility(View.VISIBLE);
                        Toast.makeText(SearchableProductActivity.this, "No Product Result Found", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
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
                //progressDialog.dismiss();
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
        RequestQueue requestQueue = Volley.newRequestQueue(SearchableProductActivity.this);
        requestQueue.add(jsObjRequest);
    }



  /* public void getSubcategoryList() {

        final Map<String, String> params = new HashMap<>();
       // String para_str = "?cid=" + cid;
        //  progressDialog.show();
       String para_str1 = "?keywords=" + key;
       String para_str2 = "&user_id=" + BSession.getInstance().getUser_id(getApplicationContext());
     //  String baseUrl   = ProductConfig.allproducts + para_str1+para_str2;

        String baseUrl = ProductConfig.subcategorylist  +para_str1+para_str2;

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    //  progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {

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
                            model.setShort_description(array.getJSONObject(i).getString("short_description"));
                            model.setRatting(array.getJSONObject(i).getString("ratting"));
                            model.setPcode(array.getJSONObject(i).getString("pcode"));
                            model.setPrice(array.getJSONObject(i).getString("price"));
                            model.setStock(array.getJSONObject(i).getString("stock"));
                            model.setSubcategoryname(array.getJSONObject(i).getString("subcategoryname"));
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            JSONArray array1 = product.getJSONArray("image");
                            model.setImage(array1.getJSONObject(0).getString("image"));
                            mFragmentTitleList.add(array.getJSONObject(i).getString("subcategoryname"));
                            mFragmentscidList.add(array.getJSONObject(i).getString("scid"));
                            mFragmentcidList.add(array.getJSONObject(i).getString("cid"));

                            Log.d("FragmentTitle", String.valueOf(mFragmentTitleList));
                        }
                        for (int i = 0; i < mFragmentTitleList.size(); i++) {
                            mFragmentList.add(new ProductsDynamicFragment());
                        }
                        setupViewPager(viewPager);
                        tabLayout.setupWithViewPager(viewPager);
                        // Tab ViewPager setting
                        viewPager.setOffscreenPageLimit(mFragmentList.size());
                        tabLayout.setupWithViewPager(viewPager);
                    } else {
                        Toast.makeText(getApplicationContext(), "No SubCategory Result Found", Toast.LENGTH_LONG).show();
                    }

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                    //  progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("Error", error.toString());
                // progressDialog.dismiss();
                NetworkResponse networkResponse = error.networkResponse;

                String message = null;
                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_LONG).show();

                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_LONG).show();

                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_LONG).show();

                    message = "Connection TimeOut! Please check your internet connection.";
                } else if (networkResponse != null && networkResponse.statusCode == 503) {
                    Toast.makeText(getApplicationContext(), "Service Unavailable server error response", Toast.LENGTH_LONG).show();

                } else if (networkResponse != null && networkResponse.statusCode == 400) {
                    Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();

                } else if (networkResponse != null && networkResponse.statusCode == 401) {
                    Toast.makeText(getApplicationContext(), "401 Unauthorized", Toast.LENGTH_LONG).show();

                } else if (networkResponse != null && networkResponse.statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "404 File not found", Toast.LENGTH_LONG).show();

                } else if (networkResponse != null && networkResponse.statusCode == 202) {
                    Toast.makeText(getApplicationContext(), "Processing has not been completed", Toast.LENGTH_LONG).show();

                } else if (networkResponse != null && networkResponse.statusCode == 204) {
                    Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();

                } else if (networkResponse != null && networkResponse.statusCode == 403) {
                    Toast.makeText(getApplicationContext(), "The request is for something forbidden. Authorization will not help.", Toast.LENGTH_LONG).show();

                } else if (networkResponse != null && networkResponse.statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "The server encountered an unexpected condition which prevented it from fulfilling the request.\n" +
                            "\n", Toast.LENGTH_LONG).show();

                } else if (networkResponse != null && networkResponse.statusCode == 501) {
                    Toast.makeText(getApplicationContext(), "The server does not support the facility required.\n" +
                            "\n", Toast.LENGTH_LONG).show();

                } else if (networkResponse != null && networkResponse.statusCode == 502) {
                    Toast.makeText(getApplicationContext(), "The implication is that this is a temporary condition which maybe alleviated at other times." +
                            "\n", Toast.LENGTH_LONG).show();

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


   private void setupViewPager(ViewPager viewPager) {
        System.out.println("---first=="+first);
        TuitionViewPagerAdapter adapter = new TuitionViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mFragmentTitleList, mFragmentscidList,mFragmentcidList);
        viewPager.setAdapter(adapter);
    }*/


  /*  public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.MailViewHolder> {

        List<ProductsModel> suggestionlist;
        private Context mContext;
        AlertDialog dialog;
        ProgressDialog progressDialog;

        public SuggestionAdapter(Context mContext, List<ProductsModel> suggestionlist) {
            this.mContext = mContext;
            this.suggestionlist = suggestionlist;
        }

        @NonNull
        @Override
        public SuggestionAdapter.MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_items, parent, false);
            return new SuggestionAdapter.MailViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final SuggestionAdapter.MailViewHolder holder, final int position) {
            final ProductsModel model = suggestionlist.get(position);
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Updating.....");
            holder.title.setText(model.getWeb_title());


            holder.lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SearchableProductActivity.class);
                    intent.putExtra("keyword",model.getWeb_title());
                    mContext.startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return suggestionlist.size();
        }


        public class MailViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            LinearLayout lin;

            public MailViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                lin = itemView.findViewById(R.id.lin);


            }
        }
    }*/

    public void loadProductLists() {
        productwiselist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str  = "?cid="+cid;
        String para_str1 = "&scid=" + scid;
        String para_str2 = "&priceorder=" + first;
        String para_str3 = "&neworder=" + second;
        String para_str4 = "&pricefrom=" + pricefrom;
        String para_str5 = "&priceto=" + priceto;
        String para_str6 = "&user_id=" + BSession.getInstance().getUser_id(SearchableProductActivity.this);
        String para_str7 = "&orderlist=" +"1";

        System.out.println("priceorder="+first);
        System.out.println("neworder="+second);
        System.out.println("pricefrom="+pricefrom);
        System.out.println("priceto="+priceto);

        String baseUrl   = ProductConfig.productlist +para_str+ para_str1+para_str2+para_str3+para_str4+para_str5+para_str6+para_str7 ;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        productwiselist = new ArrayList<>();
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
                            model.setShort_description(array.getJSONObject(i).getString("short_description"));
                            model.setRatting(array.getJSONObject(i).getString("ratting"));
                            model.setPcode(array.getJSONObject(i).getString("pcode"));
                            model.setPrice(array.getJSONObject(i).getString("price"));
                            model.setStock(array.getJSONObject(i).getString("stock"));
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            JSONArray array1 = product.getJSONArray("image");
                         // model.setImage(array1.getJSONObject(0).getString("image"));
                            productwiselist.add(model);

                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchableProductActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_menuList.setLayoutManager(layoutManager);
                        ProductsAdapter     productListAdapter = new ProductsAdapter(SearchableProductActivity.this, productwiselist);
                        rv_menuList.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchableProductActivity.this, 2);
                        rv_menuList.setLayoutManager(gridLayoutManager);
                        rv_menuList.setHasFixedSize(true);

                    } else {
                        rv_menuList.setAdapter(null);
                        productwiselist.clear();
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
        RequestQueue requestQueue = Volley.newRequestQueue(SearchableProductActivity.this);
        requestQueue.add(jsObjRequest);
    }

}