package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
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
import com.taprocycle.service.Adapter.SuggestionAdapter;
import com.taprocycle.service.Fragment.ProductsDynamicFragment;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.TuitionViewPagerAdapter;
import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.CategoryModel;
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

import pl.droidsonroids.gif.GifImageView;

public class SubcategoryActivity extends AppCompatActivity {
    RecyclerView rv_category, rv_products;
    private List<CategoryModel> subcategorylist = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private ArrayList<String> mFragmentscidList = new ArrayList<>();
    private ArrayList<String> mFragmentcidList = new ArrayList<>();
    private List<ProductsModel> productwiselist = new ArrayList<>();
    private static ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    private List<ProductModel> productsalelist = new ArrayList<>();
    RelativeLayout relative_layout;
    ProgressDialog progressDialog;
    LinearLayout sort, lin_filter;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FrameLayout frame_sub, fram_sub_frag;
    LinearLayout lin;
    ImageView arrow, cart, search,home,search_remove;
    TextView badge_notification;
    String sub = "", scid = "", cid = "",first,second;
    AutoCompleteTextView search_txt;
    GifImageView gif;
    LinearLayout arrow_lin;
    public ArrayList<ProductsModel> spinnerList = new ArrayList<>();
    SuggestionAdapter suggestionAdapter;
    int subid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory2);
        /////////NETWORK CONDITION CHECK///////////

        String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(SubcategoryActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }
      /*  progressDialog = new ProgressDialog(SubcategoryActivity.this);
        progressDialog.setMessage("Loading.....");*/
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cid = (extras.getString("cid"));
            scid = (extras.getString("scid"));

        } else {
            // Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }


        init();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubcategoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        search_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchadd();
                 /*   Intent intent=new Intent(SubcategoryActivity.this,SearchableProductActivity.class);
                    intent.putExtra("keyword",search_txt.getText().toString().trim());
                    startActivity(intent);*/
                    return true;
                }
                return false;
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubcategoryActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubcategoryActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        arrow_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /*lin_filter.setOnClickListener(new View.OnClickListener() {
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
        });*/
        getSubcategoryList();
        String customer_id = BSession.getInstance().getUser_id(SubcategoryActivity.this);
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

        search_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_txt.getText().clear();
                search_remove.setVisibility(View.GONE);
            }
        });

    }


    public void init() {
        lin = findViewById(R.id.lin);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        View root = tabLayout.getChildAt(0);
        LinearLayout linearLayout = (LinearLayout) root;
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.TRANSPARENT);
        drawable.setSize(20, 0);
        linearLayout.setDividerDrawable(drawable);
        frame_sub = findViewById(R.id.frame_subbbbb);
        arrow = findViewById(R.id.arrow);
        search = findViewById(R.id.search);
        search_txt = findViewById(R.id.search_txt);
        rv_category = findViewById(R.id.rv_subcategory);
        rv_products = findViewById(R.id.rv_products);
        sort = findViewById(R.id.sort);
        search_remove = findViewById(R.id.search_remove);
        search_remove.setVisibility(View.GONE);
        lin_filter = findViewById(R.id.lin_filter);
        fram_sub_frag = findViewById(R.id.fram_sub_frag);
        badge_notification=findViewById(R.id.badge_notification);
        cart = findViewById(R.id.cart);
        gif=findViewById(R.id.gif);
        arrow_lin=findViewById(R.id.arrow_lin);
        home=findViewById(R.id.home);
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

                            suggestionAdapter = new SuggestionAdapter(SubcategoryActivity.this, R.layout.suggestion_items, spinnerList);
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

                            gif.setVisibility(View.GONE);


                        }
                    }else{
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
                        gif.setVisibility(View.GONE);

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

    private void searchadd() {

        final Map<String, String> params = new HashMap<>();
        String para1="?user_id="+BSession.getInstance().getUser_id(getApplicationContext());
        String para2="&title="+search_txt.getText().toString().trim();
        //  progressDialog.show();
        String baseUrl = ProductConfig.user_search+para1+para2;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    // progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("message") && jsonResponse.getString("message").equals("Successfully Registered")) {
                        Intent intent=new Intent(SubcategoryActivity.this,SearchableProductActivity.class);
                        intent.putExtra("keyword",search_txt.getText().toString().trim());
                        startActivity(intent);

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
        String customer_id = BSession.getInstance().getUser_id(SubcategoryActivity.this);
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
                                Intent intent=new Intent(SubcategoryActivity.this,CartActivity.class);
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
    public void getSubcategoryList() {

        final Map<String, String> params = new HashMap<>();
        String para_str = "?cat_id=" + cid ;
        // String para_str1 ="&scid=" + scid;
        //  progressDialog.show();

        String baseUrl = ProductConfig.subcategorylist + para_str;

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

                            model.setCid(array.getJSONObject(i).getString("cid"));
                            model.setScid(array.getJSONObject(i).getString("scid"));
                            model.setSubcategoryname(array.getJSONObject(i).getString("subcategoryname"));
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
                        tabLayout.selectTab(tabLayout.getTabAt(subid));
                        System.out.println("Respnse=="+response);
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
    }


    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
        RadioGroup radioGroup = bottomSheetDialog.findViewById(R.id.radiogroup);
        LinearLayout lin = bottomSheetDialog.findViewById(R.id.lin);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                RadioButton defaultt=radioGroup.findViewById(R.id.defaultt);
                RadioButton price_l_h=radioGroup.findViewById(R.id.price_l_h);
                RadioButton price_h_l=radioGroup.findViewById(R.id.price_h_l);
                RadioButton newest=radioGroup.findViewById(R.id.newest);
                boolean isChecked = defaultt.isChecked();
                boolean isChecked1 = price_l_h.isChecked();
                boolean isChecked2 = price_h_l.isChecked();
                boolean isChecked3 = newest.isChecked();

                if (isChecked) {
                    first = "";
                    getSubcategoryList();
                    bottomSheetDialog.dismiss();
                } else if (isChecked1) {
                    first = "asc";
                    System.out.println("---first=="+first);
                    getSubcategoryList();
                    bottomSheetDialog.dismiss();
                }else if (isChecked2) {
                    first = "asc";
                    getSubcategoryList();
                    bottomSheetDialog.dismiss();
                }else if (isChecked3) {
                    first = "ascnew";
                    getSubcategoryList();
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
    }

    private void showBottomSheetDialog1() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_filter);
        RadioGroup radioGroup = bottomSheetDialog.findViewById(R.id.radiogroup1);
        LinearLayout lin = bottomSheetDialog.findViewById(R.id.lin);

        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        String customer_id = BSession.getInstance().getUser_id(SubcategoryActivity.this);
        if (customer_id.equalsIgnoreCase("")) {

        } else {
            getCartCount();
        }
    }
}