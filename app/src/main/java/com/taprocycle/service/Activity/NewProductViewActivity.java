package com.taprocycle.service.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.taprocycle.service.Adapter.WeightAdapter;
import com.taprocycle.service.DescriptionFragment;
import com.taprocycle.service.Fragment.AboutItemFragment;
import com.taprocycle.service.WishlistActivity;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.ColorModel;
import com.taprocycle.service.test.model.RecyclerItemClickListener;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.synnapps.carouselview.CarouselView;
import com.taprocycle.service.Adapter.RelatedAdapter;
import com.taprocycle.service.Adapter.SuggestionAdapter;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.test.model.DeliveryAddressSession;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductModel;
import com.taprocycle.service.test.model.ProductsModel;
import com.taprocycle.service.test.model.SubWalletAddCartSession;
import com.taprocycle.service.test.model.WeightModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;
import pl.droidsonroids.gif.GifImageView;

public class NewProductViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener{
    TextView rel_count_add,badge_notification,tv_plus,tv_qty,tv_minus,tv_wid,product_description,product_price,product_mrp,product_saveprice,outstock,p_stock_count,product_longdiscription;
    RatingBar ratingBars, ratting;
    ImageView share,arrow,cart,search,home;
    ViewPager pager1;
    EditText product_title;
    CircleIndicator indicator1;
    RecyclerView rv_color_list,rv_products;
    RelativeLayout rel_add,rel_review;
    LinearLayout llay_cart;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<ProductModel> productsalelist = new ArrayList<>();
    private static ViewPager mPager,mPager1;
    private static int currentPage = 0,currentPage1 = 0;
    private static final Integer[] XMEN = {R.drawable.locate};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    RelativeLayout rel_carosal;
    Fragment fragment;
    String rtv,ratings,varient_id="",colour_id="",username="first",pid="",scid="",exclusive="",price="",colorName="",product_color="",pprice,pmrp, baseUrl,subwallet_id="";
    FrameLayout frame,framee,frame_product_frag;
    NestedScrollView scroll;
    AutoCompleteTextView search_txt;
    RelativeLayout rel_count;
    String customer_id;
    String[] country = { "S", "L", "M", "X", "XL"};
    ProgressDialog progressDialog;
    CarouselView productcarousel;
    List<ProductsModel> apiSliderList = new ArrayList<>();
    RecyclerView rv_wgtList;
    TextView view_cart,exclusive_products;
    private List<WeightModel> weightModelList;
    private List<ColorModel> colorModelList;
    ArrayList<String> weightarray = new ArrayList<String>();
    private List<ProductsModel> productwiselist = new ArrayList<>();
    private List<WeightModel> lists = new ArrayList<>();
    public static ProductsModel subcourcemodel = new ProductsModel();
    Integer i;
    BottomSheetDialog bottomSheetDialog;
    RelativeLayout rel_offer;
    TextView offer_text,product_id,txt_titile,size_chart;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    public final List<ProductsModel> image = new ArrayList<>();
    int stock=0,qty=0;
    public final List<Fragment> mFragmentList = new ArrayList();
    public final List<String> mFragmentSHOList = new ArrayList();
    public final List<String> mFragmentLONList = new ArrayList();

    ImageView image1,wislist;
    SliderView sliderView;
    GifImageView gif;
    String name;
    String qtyy,color;
    LinearLayout arrow_lin,Colur;
    RelativeLayout searh;
    public ArrayList<ProductsModel> spinnerList = new ArrayList<>();
    SuggestionAdapter suggestionAdapter;
    String SubWalletSession;
    String shortdes,longdes;
    List<ProductsModel> bannerlist;
    Spinner spinners;
    boolean iscolorListavailable;
    CheckBox whishlist;
    List<ColorModel> color_list = new ArrayList<>();
    AlertDialog  alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_view);

        progressDialog = new ProgressDialog(NewProductViewActivity.this);
        progressDialog.setMessage("Loading.....");
        /////////NETWORK CONDITION CHECK///////////
        String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(NewProductViewActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }
        rel_offer=findViewById(R.id.rel_offer);
        txt_titile=findViewById(R.id.txt_titile);
        offer_text=findViewById(R.id.offer_text);
        rel_carosal=findViewById(R.id.rel_carosal);
        framee=findViewById(R.id.framee);
        frame_product_frag=findViewById(R.id.frame_product_frag);
        arrow=findViewById(R.id.arrow);
        search_txt=findViewById(R.id.search_txt);
        searh=findViewById(R.id.searh);
        search_txt.setVisibility(View.GONE);
        searh.setVisibility(View.GONE);
        search=findViewById(R.id.search);
        cart=findViewById(R.id.cart);
        rel_count=findViewById(R.id.rel_count);
        tv_qty=findViewById(R.id.tv_qty);
        whishlist=findViewById(R.id.whishlist);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        tv_plus=findViewById(R.id.tv_plus);
        tv_minus=findViewById(R.id.tv_minus);
        badge_notification=findViewById(R.id.badge_notification);
        rel_count_add=findViewById(R.id.rel_count_add);
        view_cart=findViewById(R.id.view_cart);
        exclusive_products=findViewById(R.id.exclusive_products);
        rel_review=findViewById(R.id.rel_review);
        wislist=findViewById(R.id.wislist);
        image1=findViewById(R.id.image1);
        sliderView=findViewById(R.id.slider);
        size_chart=findViewById(R.id.size_chart);
        viewPager = findViewById(R.id.viewpager);
        product_id = findViewById(R.id.product_id);
        arrow_lin=findViewById(R.id.arrow_lin);
        Colur=findViewById(R.id.Colur);
        home=findViewById(R.id.home);
        product_title=findViewById(R.id.product_title);
        product_mrp=findViewById(R.id.product_mrp);
        product_price=findViewById(R.id.product_price);
        product_saveprice=findViewById(R.id.product_saveprice);
        p_stock_count=findViewById(R.id.p_stock_count);
        outstock=findViewById(R.id.outstock);
        //product_longdiscription=findViewById(R.id.product_longdiscription);
        ratting=findViewById(R.id.ratting);
        ratingBars=findViewById(R.id.ratingBars);
        pager1=findViewById(R.id.pager1);
        viewPager=findViewById(R.id.viewpager);
        indicator1=findViewById(R.id.indicator1);
        share=findViewById(R.id.share);
        rv_products=findViewById(R.id.rv_products);
        llay_cart=findViewById(R.id.llay_cart);
        rel_add=findViewById(R.id.rel_add);
        frame=findViewById(R.id.frame);
        scroll=findViewById(R.id.scroll);
        product_description=findViewById(R.id.product_description);
        productcarousel=findViewById(R.id.productcarousel);
        rv_wgtList=findViewById(R.id.rv_wgtList);
        customer_id = BSession.getInstance().getUser_id(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pid = (extras.getString("pid"));
            qtyy = (extras.getString("qty"));
            scid = (extras.getString("scid"));
            exclusive = (extras.getString("exclusive"));

            System.out.println("pid=="+pid);
            System.out.println("exclusive=="+exclusive);

            //  price = (extras.getString("price"));
            // discount = (extras.getString("discount"));

        } else {
              Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
        if(exclusive.equals("1")){
            System.out.println("exclusive=="+exclusive);
            rel_add.setVisibility(View.GONE);
            exclusive_products.setVisibility(View.VISIBLE);
        }else {

        }
        rv_color_list=findViewById(R.id.rv_color_list);
        loadslider();
        sizeChart();
      /*  NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.scroll);
        scrollView.setFillViewport (true);*/
        tabLayout =  findViewById(R.id.tab_layout);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewProductViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });wislist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewProductViewActivity.this, WishlistActivity.class);
                startActivity(intent);
            }
        });
        arrow_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewProductViewActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        search_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchadd();

                    return true;
                }
                return false;
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewProductViewActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
        view_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewProductViewActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
        whishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bannerlist = new ArrayList<>();
                if (((CheckBox) v).isChecked()) {
                    //Case 1
                    String customer_id = BSession.getInstance().getUser_id(getApplicationContext());
                    final Map<String, String> params = new HashMap<>();
                    String para1="?user_id="+customer_id;
                    String para2="&product_id="+pid;
                    String baseUrl = ProductConfig.wishlist+para1+para2;
                    StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                if (jsonResponse.has("success") && jsonResponse.getString("success").equals("1")){

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

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(jsObjRequest);
                    jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                            10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));                    ((CheckBox) v).setButtonDrawable(R.drawable.baseline_favorite_24);
                    Toast.makeText(NewProductViewActivity.this, "Item added to wishlist", Toast.LENGTH_SHORT).show();
                }
                else{
                    ((CheckBox) v).setButtonDrawable(R.drawable.baseline_favorite_border_24);
                    Toast.makeText(NewProductViewActivity.this, "Item removed from wishlist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_wid=findViewById(R.id.tv_wid);

        relatedproducts();
        loadserach();
        getWeightList();
        getCartCount();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Bellso APP Link :  https://play.google.com/store/apps/details?id=com.taprocycle.service";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));

            }
        });


        rv_wgtList.addOnItemTouchListener(new RecyclerItemClickListener(NewProductViewActivity.this, rv_wgtList, new RecyclerItemClickListener.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                varient_id = (weightModelList.get(position).getWid());
                System.out.println("varient_id"+weightModelList.get(position).getWid());
                String vid = weightModelList.get(position).getColor();
                colorList(vid);
                /*if (iscolorListavailable == false){
                    addMethod();
                    return;
                }*/
                if(colorName.isEmpty()){
                    Colur.setVisibility(View.GONE);
                    return;
                }

                qtyy=weightModelList.get(position).getQty();

                scid = weightModelList.get(position).getScid();
                System.out.println("weightModelList=="+weightModelList.get(position).getWid());
                product_saveprice.setVisibility(View.VISIBLE);
                double a= Double.parseDouble(weightModelList.get(position).getWeb_price());
                double b= Double.parseDouble(weightModelList.get(position).getMrp());
                double c=a-b;
                int resul =(int) Math.abs(c);
                double offer=c*100/a;
                int result = (int) Math.ceil(offer);
                offer_text.setText(String.valueOf(result)+" %\n OFF");
                product_saveprice.setText(String.valueOf(result)+" % OFF");
                product_price.setText(" ₹"+weightModelList.get(position).getMrp());
                product_mrp.setText("₹"+weightModelList.get(position).getWeb_price());
                pprice=(weightModelList.get(position).getWeb_price());
                pmrp=(weightModelList.get(position).getMrp());
                stock= Integer.parseInt((weightModelList.get(position).getStock()));
                p_stock_count.setText("Left : "+(weightModelList.get(position).getStock()));
                if (stock == 0){
                    p_stock_count.setVisibility(View.GONE);
                    rel_add.setVisibility(View.GONE);
                    outstock.setVisibility(View.VISIBLE);
                }
            }
        }));

        tv_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewProductViewActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(NewProductViewActivity.this).inflate(R.layout.layout2, viewGroup, false);
                progressDialog = new ProgressDialog(NewProductViewActivity.this);
                progressDialog.setMessage("updating.....");
                builder.setView(dialogView);
                alertDialog = builder.create();
                alertDialog.show();
                Button button = dialogView.findViewById(R.id.btn_verify);
                EditText value = dialogView.findViewById(R.id.value);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String val = value.getText().toString().toString().trim();
                        if (!val.isEmpty()) {
                            int a =Integer.parseInt(val);
                            int b =Integer.parseInt(String.valueOf(stock));
                            System.out.println("vvvv"+a);
                            System.out.println("vvvv"+b);

                            if (a<b){
                                final Map<String, String> params = new HashMap<>();
                                customer_id = BSession.getInstance().getUser_id(NewProductViewActivity.this);
                                String para1="?user_id="+customer_id;
                                String para2="&product_id="+pid;
                                String para3="&quantity="+val;
                                progressDialog.show();
                                String baseUrl = ProductConfig.whats+para1+para2+para3;
                                System.out.println("parass"+para1);
                                System.out.println("parass"+para2);
                                System.out.println("parass"+para3);
                                StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e("Response", response.toString());
                                        try {
                                            progressDialog.dismiss();
                                            JSONObject jsonResponse = new JSONObject(response);

                                            if (jsonResponse.has("success") && jsonResponse.getString("success").equals("1")) {

                                                Toast.makeText(NewProductViewActivity.this, "Update your quantity", Toast.LENGTH_LONG).show();
                                                tv_qty.setText(val);

                                            } else {
                                                Toast.makeText(NewProductViewActivity.this, "Update failed", Toast.LENGTH_LONG).show();
                                                //tv_qty.setText(qty);
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
                                        progressDialog.dismiss();

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(NewProductViewActivity.this);
                                requestQueue.add(jsObjRequest);
                                jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                                        10000,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                            }
                            else{
                                //Toast.makeText(NewProductViewActivity.this, "please Apply Below the stock"+b, Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder BackAlertDialog = new AlertDialog.Builder(NewProductViewActivity.this);
                                BackAlertDialog.setTitle((CharSequence) "OUT OF STOCK!!!");
                                BackAlertDialog.setMessage((CharSequence) "Your Selected item is out of stock");
                                BackAlertDialog.setIcon((int) R.drawable.logo);
                                BackAlertDialog.setNegativeButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }

                                });
                                BackAlertDialog.show();
                            }
                            alertDialog.dismiss();
                        } else {
                            value.setError("*Enter your quantity");
                            value.requestFocus();
                            Toast.makeText(NewProductViewActivity.this, "please enter your quantity", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        rel_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("weightModelList==" + varient_id);

                // Check if variant_id is empty
                if (varient_id.isEmpty()) {
                    showAlert("Select size!!!");
                    return;
                }

                // Check if variant_id is not empty and colourList is not available
                if (!varient_id.isEmpty() && colorName.isEmpty()) {
                    // Call addMethod() only when colourList is not available
                    addMethod();
                    return;
                }

                // Check if variant_id is selected but colour_id is empty and colour list is available
                if (!varient_id.isEmpty() && colour_id.isEmpty() && iscolorListavailable) {
                    showAlert("Select colour!!!");
                    return;
                }

                // If none of the above conditions are met, call addMethod()
                addMethod();

            }
        });

        tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println("aastock" + stock);
                System.out.println("aaqty" + qty);
                if (stock == 0) {
                    //Toast.makeText(NewProductViewActivity.this, "Sorry this item was out of stock", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder BackAlertDialog = new AlertDialog.Builder(NewProductViewActivity.this);
                    BackAlertDialog.setTitle((CharSequence) "OUT OF STOCK!!!");
                    BackAlertDialog.setMessage((CharSequence) "Your Selected item is out of stock");
                    BackAlertDialog.setIcon((int) R.drawable.logo);
                    BackAlertDialog.setNegativeButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //moveTaskToBack(true);
                            //finish();
                        }

                    });
                    BackAlertDialog.show();
                } else
                //else if (stock >qty ) {

                {
                    final String _stringVal;
                    Log.d("src", "Increasing value...");
                    i = Integer.valueOf(tv_qty.getText().toString());
                    i = i + 1;
                    _stringVal = String.valueOf(i);

                    customer_id = BSession.getInstance().getUser_id(NewProductViewActivity.this);

                    final Map<String, String> params = new HashMap<>();

                    String para_str1 = "?product_id=" + pid;
                    String para_str2 = "&quantity=" + "1";
                    String para_str4 = "&cart_type=" + "add";
                    String para_str5 = "&user_id=" + customer_id;
                    String para_str6 = "&price=" + pprice;
                    String para_str7 = "&discount_price=" + pmrp;
                    String para_str8 = "&subcategory=" + scid;
                    String para_str9 ="";
                    if (colour_id.isEmpty()) {
                        para_str9 = "&vid=" + varient_id;
                    } else {
                        para_str9 = "&vid=" + colour_id;
                    }
                    String para_str10 = "&product_color=" + product_color;
                    String para_str11 = "&user_type=" + BSession.getInstance().getType(getApplicationContext());

                    System.out.println("price=" + pprice);
                    System.out.println("discount_price=" + pmrp);
                    System.out.println("product_color=" + product_color);
                    String baseUrl = ProductConfig.addcart + para_str1 + para_str2 + para_str4 + para_str5 + para_str6 + para_str7 + para_str8 + para_str9 + para_str10 + para_str11;

                    StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                if (jsonResponse.has("success") && jsonResponse.getString("success").equals("1")) {
                                    if (jsonResponse.getString("message").equalsIgnoreCase("cart_id deleted")) {
                                        rel_add.setVisibility(View.VISIBLE);
                                        llay_cart.setVisibility(View.GONE);
                                        rel_count.setVisibility(View.GONE);
                                        SubWalletAddCartSession.getInstance().initialize(NewProductViewActivity.this,
                                                "",
                                                "");
                                        getCartCount();
                                    } else {
                                        tv_qty.setText(jsonResponse.getString("quantity"));
                                        qty = Integer.parseInt(jsonResponse.getString("quantity"));
                                        getCartCount();
                                    }

                                } else if (jsonResponse.has("success") && jsonResponse.getString("success").equals("fail")) {
                                    if (jsonResponse.getString("message").equalsIgnoreCase("Out of stock")) {
                                        tv_qty.setText(jsonResponse.getString("quantity"));
                                        //Toast.makeText(NewProductViewActivity.this, "Sorry this item was out of stock", Toast.LENGTH_LONG).show();
                                        AlertDialog.Builder BackAlertDialog = new AlertDialog.Builder(NewProductViewActivity.this);
                                        BackAlertDialog.setTitle((CharSequence) "OUT OF STOCK!!!");
                                        BackAlertDialog.setMessage((CharSequence) "Your Selected item is out of stock");
                                        BackAlertDialog.setIcon((int) R.drawable.logo);
                                        BackAlertDialog.setNegativeButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //moveTaskToBack(true);
                                                //finish();
                                            }

                                        });
                                        BackAlertDialog.show();
                                    }
                                } else {
                                    Toast.makeText(NewProductViewActivity.this, "Something went wrong your item not added .. Try it again", Toast.LENGTH_LONG).show();
                                }

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
                            }

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(NewProductViewActivity.this);
                    requestQueue.add(jsObjRequest);


                }
            }


        });

        tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String _stringVal;
                Log.d("src", "Decreasing value...");
                i = Integer.valueOf(tv_qty.getText().toString());
                if (i > 0) {
                    i = i - 1;
                    _stringVal = String.valueOf(i);
                    customer_id = BSession.getInstance().getUser_id(NewProductViewActivity.this);
                    final Map<String, String> params = new HashMap<>();
                    String para_str1 = "?product_id=" + pid;
                    String para_str2 = "&quantity=" + "1";
                    String para_str4 = "&cart_type=" + "sub";
                    String para_str5 = "&user_id=" + customer_id;
                    String para_str6 = "&price=" + pprice;
                    String para_str7 = "&discount_price=" + pmrp;
                    String para_str8 = "&subcategory=" + scid;
                    String para_str9 ="";
                    if (colour_id.isEmpty()) {
                        para_str9 = "&vid=" + varient_id;
                    } else {
                        para_str9 = "&vid=" + colour_id;
                    }
                    String para_str10 = "&product_color=" + product_color;
                    String para_str11 = "&user_type=" + BSession.getInstance().getType(getApplicationContext());

                    System.out.println("@@product_id=" + pid);
                    System.out.println("@@user_id=" + customer_id);
                    System.out.println("@@price=" + pprice);
                    System.out.println("@@discount_price=" + pmrp);
                    System.out.println("@@subcategory=" + scid);
                    System.out.println("@@vid=" + tv_wid.getText().toString().trim());
                    System.out.println("product_color=" + product_color);
                    System.out.println("@@user_type=" + BSession.getInstance().getType(getApplicationContext()));

                    // System.out.println("price=" + pprice);
                    // System.out.println("discount_price=" + pmrp);
                    String baseUrl = ProductConfig.addcart + para_str1 + para_str2 + para_str4 + para_str5 + para_str6 + para_str7 + para_str8 + para_str9 + para_str10 + para_str11;


                    StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                if (jsonResponse.has("success") && jsonResponse.getString("success").equals("1")) {
                                    if (jsonResponse.getString("message").equalsIgnoreCase("cart_id deleted")) {
                                        rel_add.setVisibility(View.VISIBLE);
                                        llay_cart.setVisibility(View.GONE);
                                        rel_count.setVisibility(View.GONE);
                                        SubWalletAddCartSession.getInstance().initialize(NewProductViewActivity.this,
                                                "",
                                                "");
                                        getCartCount();
                                    } else {
                                        tv_qty.setText(jsonResponse.getString("quantity"));
                                        qty = Integer.parseInt(jsonResponse.getString("quantity"));
                                        getCartCount();

                                    }
                                    //  Toast.makeText(mContext, holder.tvPrice.getText().toString() + ", Qty " + productsModelList.get(position).getProduct_quantity(), Toast.LENGTH_LONG).show();

                                } else {
                                    //  Toast.makeText(ProductViewActivity.this, "Cart sub Failed", Toast.LENGTH_LONG).show();
                                }

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
                            }

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(NewProductViewActivity.this);
                    requestQueue.add(jsObjRequest);
                } else {
                    Log.d("src", "Value can't be less than 0");
                }
            }

        });

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

    }

    private void showAlert(String errorMessage) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewProductViewActivity.this);
        alertDialog.setTitle("Alert ");
        alertDialog.setMessage(errorMessage);
        alertDialog.setIcon(R.drawable.logo);
        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Handle button click
            }
        });
        alertDialog.show();
    }

    private void addMethod() {
        rel_add.setVisibility(View.GONE);
        llay_cart.setVisibility(View.VISIBLE);
        rel_count.setVisibility(View.VISIBLE);
        customer_id = BSession.getInstance().getUser_id(NewProductViewActivity.this);
        final Map<String, String> params = new HashMap<>();
        String para_str1 = "?product_id=" + pid;
        String para_str2 = "&quantity=" + "1";
        String para_str4 = "&cart_type=" + "add";
        String para_str5 = "&user_id=" + customer_id;
        String para_str6 = "&price=" + pprice;
        String para_str7 = "&discount_price=" + pmrp;
        String para_str8 = "&subcategory=" + scid;

        // Determine whether to include variant_id or colour_id based on availability
        String para_str9 ="";
        if (colour_id.isEmpty()) {
            para_str9 = "&vid=" + varient_id;
        } else {
            para_str9 = "&vid=" + colour_id;
        }
        String para_str10 = "&product_color=" + product_color;
        String para_str11 = "&user_type=" + BSession.getInstance().getType(getApplicationContext());

        System.out.println("product_id=!!!" + pid);
        System.out.println("price=!!!" + pprice);
        System.out.println("user_id=!!!" + customer_id);
        System.out.println("discount_price=!!!" + pmrp);
        System.out.println("subcategory=!!!" + scid);
        System.out.println("product_color" + product_color);
        System.out.println("product_color" + varient_id);
        System.out.println("product_color" + colour_id);
        System.out.println("vid=!!!" + tv_wid.getText().toString().trim());
        System.out.println("user_type=!!!" + BSession.getInstance().getType(getApplicationContext()));

        String baseUrl = ProductConfig.addcart + para_str1 + para_str2 + para_str4 + para_str5 + para_str6 + para_str7 +para_str11+ para_str8 + para_str9 + para_str10;

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("success") && jsonResponse.getString("success").equals("1")) {
                        if (jsonResponse.getString("message").equalsIgnoreCase("cart_id deleted")) {

                            rel_add.setVisibility(View.VISIBLE);
                            llay_cart.setVisibility(View.GONE);
                            rel_count.setVisibility(View.GONE);

                            getCartCount();

                        } else {
                            tv_qty.setText(jsonResponse.getString("quantity"));
                            qty = Integer.parseInt(jsonResponse.getString("quantity"));
                            rel_add.setVisibility(View.GONE);
                            llay_cart.setVisibility(View.VISIBLE);
                            rel_count.setVisibility(View.VISIBLE);
                            tv_plus.setVisibility(View.GONE);
                            tv_minus.setVisibility(View.VISIBLE);
                            tv_plus.setVisibility(View.VISIBLE);


                            getCartCount();
                        }
                        //  Toast.makeText(mContext, holder.tvPrice.getText().toString() + ", Qty " + productsModelList.get(position).getProduct_quantity(), Toast.LENGTH_LONG).show();

                    } else if (jsonResponse.has("success") && jsonResponse.getString("success").equals("fail")) {
                        if (jsonResponse.getString("message").equalsIgnoreCase("Out of stock")) {
                            tv_qty.setText(jsonResponse.getString("quantity"));

                            //Toast.makeText(NewProductViewActivity.this, "Sorry this item was out of stock", Toast.LENGTH_LONG).show();
                            AlertDialog.Builder BackAlertDialog = new AlertDialog.Builder(NewProductViewActivity.this);
                            BackAlertDialog.setTitle((CharSequence) "OUT OF STOCK!!!");
                            BackAlertDialog.setMessage((CharSequence) "Your Selected item is out of stock");
                            BackAlertDialog.setIcon((int) R.drawable.logo);
                            BackAlertDialog.setNegativeButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //moveTaskToBack(true);
                                    //finish();

                                }

                            });
                            BackAlertDialog.show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong your item not added .. Try it again", Toast.LENGTH_LONG).show();
                    }

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

    public class ColorProductAdapter extends RecyclerView.Adapter<ColorProductAdapter.ViewHolder> {
        public Context context;
        public List<ColorModel> papularModelList;
        int selectedPosition = -1;
        public ColorProductAdapter(Context context, List<ColorModel> color_list) {
            this.context = context;
            this.papularModelList = color_list;
        }

        @NonNull
        @Override
        public ColorProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ColorProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            final ColorModel model = papularModelList.get(position);
            holder.txt_catagory.setText(model.getColour_name());
            holder.txt_stock.setText("Left : "+model.getStock());
            String img = papularModelList.get(position).getColour_image();
            Glide.with(context)
                    .load(img)
                    .placeholder(R.drawable.logo)
                    .into(holder.image_category);

            if (selectedPosition == position)
                holder.linearsd.setBackgroundColor(Color.parseColor("#e6e6e6"));
            else
                holder.linearsd.setBackgroundColor(Color.parseColor("#FFFFFF"));

            holder.linearsd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               /* selectedPosition = position;
                notifyDataSetChanged();*/

                    int currentPosition = holder.getLayoutPosition();
                    if (selectedPosition != currentPosition) {
                        // Temporarily save the last selected position
                        int lastSelectedPosition = selectedPosition;
                        // Save the new selected position
                        selectedPosition = currentPosition;
                        product_color = papularModelList.get(position).getColour_name();
                        //varient_id = papularModelList.get(position).getId();
                        colour_id = papularModelList.get(position).getId();
                        stock= Integer.parseInt((papularModelList.get(position).getStock()));
                        System.out.println("product_color"+papularModelList.get(position).getColour_name());
                        System.out.println("product_color"+papularModelList.get(position).getId());
                        // update the previous selected row
                        notifyItemChanged(lastSelectedPosition);
                        // select the clicked row
                        holder.linearsd.setBackgroundColor(Color.parseColor("#0081D6"));
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return papularModelList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout linearsd;
            ImageView image_category;
            TextView txt_catagory,txt_stock;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_stock = itemView.findViewById(R.id.txt_stock);
                txt_catagory = itemView.findViewById(R.id.txt_catagory);
                linearsd = itemView.findViewById(R.id.linearsd);
                image_category = itemView.findViewById(R.id.image_category);
            }
        }
    }
    private void sizeChart() {
        final Map<String, String> params = new HashMap<>();
        String para="?pid="+pid;
        String baseUrl = ProductConfig.sizechart+para;
        progressDialog.show();
        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String sizeChartImage= jsonResponse.getString("size_chart");

                    if (sizeChartImage.isEmpty()) {
                        size_chart.setVisibility(View.GONE);
                        return;
                    }
                    else{
                        size_chart.setVisibility(View.VISIBLE);
                        size_chart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewProductViewActivity.this);
                                ViewGroup viewGroup = new LinearLayout(NewProductViewActivity.this);  // You can use any layout here
                                View dialogView = LayoutInflater.from(NewProductViewActivity.this).inflate(R.layout.sizechart_view, viewGroup, false);
                                ProgressDialog progressDialog = new ProgressDialog(NewProductViewActivity.this);

                                builder.setView(dialogView);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.setCanceledOnTouchOutside(true);
                                ImageView sizeChartImageView = dialogView.findViewById(R.id.aimage);
                                Button closeButton = dialogView.findViewById(R.id.kycbutton);

                                Glide.with(NewProductViewActivity.this)
                                        .load(sizeChartImage)
                                        .placeholder(R.drawable.logo) // You can set a placeholder image
                                        .into(sizeChartImageView);

                                // Set a click listener for the close button to dismiss the dialog
                                closeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss(); // Dismiss the dialog
                                    }
                                });

                                // Show the dialog
                                alertDialog.show();
                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

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
        RequestQueue requestQueue = Volley.newRequestQueue(NewProductViewActivity.this);
        requestQueue.add(jsObjRequest);
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

                            suggestionAdapter = new SuggestionAdapter(NewProductViewActivity.this, R.layout.suggestion_items, spinnerList);
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

    private void searchadd() {

        final Map<String, String> params = new HashMap<>();
        String para1="?user_id="+BSession.getInstance().getUser_id(getApplicationContext());
        String para2="&title="+search_txt.getText().toString().trim();
        // progressDialog.show();
        String baseUrl = ProductConfig.user_search+para1+para2;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    // progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("message") && jsonResponse.getString("message").equals("Successfully Registered")) {
                        Intent intent=new Intent(NewProductViewActivity.this,SearchableProductActivity.class);
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

    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(NewProductViewActivity.this, "Refreshed!", Toast.LENGTH_SHORT).show();
                relatedproducts();
                getWeightList();
                loadslider();
                sizeChart();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
    private void method(){
        Bundle dataBundle = new Bundle();
        dataBundle.putString("key", shortdes);
        dataBundle.putString("long", longdes);

        // Create a FragmentStateAdapter to manage fragments
        FragmentStateAdapter adapter = new FragmentStateAdapter (this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        AboutItemFragment fragmentA = new AboutItemFragment();
                        fragmentA.setArguments(dataBundle);
                        return fragmentA;
                    case 1:
                        DescriptionFragment fragmentB = new DescriptionFragment();
                        fragmentB.setArguments(dataBundle);
                        return fragmentB;
                    // Add more cases for additional fragments if needed
                    default:
                        return null;
                }
            }

            @Override
            public int getItemCount() {
                return 2; // Number of tabs (fragments)
            }
        };

        viewPager.setAdapter(adapter);

        // Connect TabLayout to ViewPager
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // Set tab titles here
                    if (position == 0) {
                        tab.setText("Specification");
                    } else if (position == 1) {
                        tab.setText("Description");
                    }
                }
        ).attach();
    }
    private void loadslider() {
        final Map<String, String> params = new HashMap<>();
        String para="?pid="+pid;
        String baseUrl = ProductConfig.productlist+para;
        progressDialog.show();
        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {

                        JSONArray jsonResarray = jsonResponse.getJSONArray("storeList");

                        for (int i = 0; i < jsonResarray.length(); i++) {

                            JSONObject jsonObject = jsonResarray.getJSONObject(i);
                            ProductsModel model = new ProductsModel();
                            //subwallet_id= jsonObject.getString("sub_wallet");
                            product_title.setText(jsonObject.getString("productname"));
                            txt_titile.setText(jsonObject.getString("productname"));
                            product_id.setText("ID : "+jsonObject.getString("pcode"));
                            shortdes = Html.fromHtml(jsonObject.getString("short_description")).toString();

                            longdes = Html.fromHtml(jsonObject.getString("description")).toString();
                            method();
                            if(jsonObject.getString("ratting").equalsIgnoreCase("")||jsonObject.getString("ratting").equalsIgnoreCase(null)){

                            }else {
                                ratting.setRating(Float.parseFloat(jsonObject.getString("ratting")));
                            }
                            // model.setBanner_image(jsonObject.getString("pcode"));
                            String type=BSession.getInstance().getType(NewProductViewActivity.this);
                            System.out.println("type!!!!"+type);

                            product_saveprice.setVisibility(View.VISIBLE);
                            rel_offer.setVisibility(View.GONE);
                            double a= Double.parseDouble(jsonObject.getString("price"));
                            double b= Double.parseDouble(jsonObject.getString("discount_price"));
                            double c=a-b;
                            int resul =(int) Math.abs(c);

                            double offer=c*100/a;
                            int result = (int) Math.ceil(offer);
                            product_saveprice.setText(String.valueOf(result)+" % OFF");
                            System.out.println("You Save ₹==="+String.valueOf(result));
                            offer_text.setText(String.valueOf(result)+" %\n OFF");
                            product_price.setText(" ₹"+jsonObject.getString("discount_price"));
                            product_mrp.setText("₹"+jsonObject.getString("price"));
                            product_mrp.setPaintFlags(product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                            pprice=jsonObject.getString("price");
                            pmrp=jsonObject.getString("discount_price");
                            pprice=jsonObject.getString("price");
                            pmrp=jsonObject.getString("discount_price");


                            stock= Integer.parseInt(jsonObject.getString("stock"));
                            p_stock_count.setText("Left : "+jsonObject.getString("stock"));
                            if (stock == 0){
                                p_stock_count.setVisibility(View.GONE);
                                rel_add.setVisibility(View.GONE);
                                outstock.setVisibility(View.VISIBLE);
                            }
                            apiSliderList = new ArrayList<>();
                            JSONArray jsonResarray1 = jsonObject.getJSONArray("image");
                            for (int j = 0; j < jsonResarray1.length(); j++) {

                                JSONObject jsonObject1 = jsonResarray1.getJSONObject(j);
                                ProductsModel model1 = new ProductsModel();
                                model1.setImage(jsonObject1.getString("image"));
                                //System.out.println("image=="+model1.getImage());
                                apiSliderList.add(model1);
                            }
                            SliderAdapter adapter = new SliderAdapter(NewProductViewActivity.this, apiSliderList);
                            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                            sliderView.setSliderAdapter(adapter);
                            sliderView.setScrollTimeInSec(4);
                            sliderView.setAutoCycle(true);
                            sliderView.startAutoCycle();
                            progressDialog.dismiss();

                        }

                    } else {

                        Toast.makeText(NewProductViewActivity.this, "Items records not found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

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
        RequestQueue requestQueue = Volley.newRequestQueue(NewProductViewActivity.this);
        requestQueue.add(jsObjRequest);

    }

    public void getCartCount() {
        final Map<String, String> params = new HashMap<>();
        customer_id = BSession.getInstance().getUser_id(NewProductViewActivity.this);
        String para_str = "?user_id=" + customer_id;
        String baseUrl = ProductConfig.cartlist + para_str;
        progressDialog.show();

        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    progressDialog.dismiss();
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        JSONArray jsonArray=(jsonResponse.getJSONArray("storeList"));
                        for (int i=0;i<jsonArray.length();i++){
                            rel_count_add.setText("₹ " + jsonArray.getJSONObject(i).getString("total"));
                        }
                        badge_notification.setText(jsonResponse.getString("total_cnt"));

                        cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(NewProductViewActivity.this,CartActivity.class);
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

    public void relatedproducts() {

        productwiselist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str1 = "?scid=" ;
        String para_str2 = "&user_id=" + BSession.getInstance().getUser_id(getApplicationContext());
        System.out.println("subcategory=!!!232" + scid);

        String baseUrl   = ProductConfig.productlist + para_str1+para_str2;
        //progressDialog.show();

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
                            if(!array.getJSONObject(i).getString("pid").equalsIgnoreCase(pid)) {
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
                                JSONArray array1 = product.getJSONArray("image");
                                System.out.println("price=="+model.getPrice());
                                model.setImage(array1.getJSONObject(0).getString("image"));
                                productwiselist.add(model);
                                //progressDialog.dismiss();

                            }

                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(NewProductViewActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        rv_products.setLayoutManager(layoutManager);
                        RelatedAdapter productListAdapter = new RelatedAdapter(NewProductViewActivity.this, productwiselist);
                        rv_products.setAdapter(productListAdapter);
                        rv_products.setHasFixedSize(true);
                        progressDialog.dismiss();

                    } else {

                        Toast.makeText(NewProductViewActivity.this , "No Product Result Found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(NewProductViewActivity.this);
        requestQueue.add(jsObjRequest);
    }

    private void getWeightList() {
        final Map<String, String> params = new HashMap<>();
        String para_str = "?pid=" + pid;
        String para_str1 = "&user_id=" + customer_id;
        System.out.println("user_id=="+para_str);
        System.out.println("user_id=="+para_str1);
        //progressDialog.show();

        String baseUrl = ProductConfig.productdetails + para_str+para_str1;

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    //progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        weightModelList = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            WeightModel weightModel = new WeightModel();
                            weightModel.setQty(array.getJSONObject(i).getString("qty"));
                            weightModel.setScid(array.getJSONObject(i).getString("scid"));
                            weightModel.setWid(array.getJSONObject(i).getString("vid"));
                            weightModel.setColor(array.getJSONObject(i).getString("color"));
                            weightModel.setColor_name(array.getJSONObject(i).getString("color_name"));
                            weightModel.setColor_image(array.getJSONObject(i).getString("color_image"));
                            weightModel.setSize(array.getJSONObject(i).getString("size"));
                            weightModel.setStock(array.getJSONObject(i).getString("stock"));
                            colorName = array.getJSONObject(i).getString("color_name");
                            weightModel.setWeb_price(array.getJSONObject(i).getString("price"));
                            weightModel.setMrp(array.getJSONObject(i).getString("discount_price"));
                            weightModelList.add(weightModel);
                            qtyy= array.getJSONObject(0).getString("qty");
                            color = array.getJSONObject(0).getString("color");
                            System.out.println("sssssssss"+qtyy);
                            System.out.println("sssssssss"+color);
                            tv_qty.setText(qtyy);

                        }
                        for (WeightModel weightModel : weightModelList) {
                            weightarray.add(weightModel.getColor());
                            System.out.println("wwww"+weightarray);
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(NewProductViewActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        rv_wgtList.setLayoutManager(layoutManager);
                        rv_wgtList.setHasFixedSize(true);
                        WeightAdapter weightAdapter = new WeightAdapter(getApplicationContext(), weightModelList);
                        rv_wgtList.setAdapter(weightAdapter);

                        product_saveprice.setVisibility(View.VISIBLE);
                        rel_offer.setVisibility(View.GONE);
                        double a= Double.parseDouble(weightModelList.get(0).getWeb_price());
                        double b= Double.parseDouble(weightModelList.get(0).getMrp());
                        double c= a-b;
                        int resul =(int) Math.abs(c);

                        System.out.println("You Save ₹"+String.valueOf(resul));
                        System.out.println("You Save ₹"+String.valueOf(rel_offer));
                        double offer=c*100/a;
                        int result = (int) Math.ceil(offer);
                        product_saveprice.setText(String.valueOf(result)+" % OFF");
                        offer_text.setText(String.valueOf(result)+" %\n OFF");
                        product_price.setText(" ₹"+weightModelList.get(0).getMrp());
                        product_mrp.setText("₹"+weightModelList.get(0).getWeb_price());
                        product_mrp.setPaintFlags(product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        pprice=(weightModelList.get(0).getWeb_price());
                        pmrp=(weightModelList.get(0).getMrp());

                        tv_wid.setText(weightModelList.get(0).getWid());
                        scid=weightModelList.get(0).getScid();
                        relatedproducts();

                    } else {
                        Toast.makeText(getApplicationContext(), "Items records not found", Toast.LENGTH_LONG).show();
                    }

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                    //progressDialog.dismiss();
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

    }

    private void colorList(String vid) {
        final Map<String, String> params = new HashMap<>();
        String para1 = "?varient_id=" + vid;
        String para2 = "&p_id=" + pid;
        String baseUrl = ProductConfig.color_list + para1 + para2;
        color_list = new ArrayList<>();
        Colur.setVisibility(View.VISIBLE);
        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        // KycList
                        JSONArray jsonKycList = jsonResponse.getJSONArray("storeList");
                        for (int j = 0; j < jsonKycList.length(); j++) {
                            JSONObject jsonObject = jsonKycList.getJSONObject(j);
                            ColorModel model = new ColorModel();
                            model.setColour_image(jsonObject.getString("color_image"));
                            model.setColour_name(jsonObject.getString("color_name"));
                            colorName = jsonObject.getString("color_name");
                            model.setStock(jsonObject.getString("stock"));
                            model.setId(jsonObject.getString("id"));
                            color_list.add(model);
                        }

                        // Set up RecyclerView for KycList
                        LinearLayoutManager layouManager = new LinearLayoutManager(NewProductViewActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        rv_color_list.setLayoutManager(layouManager);
                        rv_color_list.setHasFixedSize(true);
                        ColorProductAdapter weihtAdapter = new ColorProductAdapter(getApplicationContext(), color_list);
                        rv_color_list.setAdapter(weihtAdapter);
                        iscolorListavailable = true;

                    } else {
                        Colur.setVisibility(View.GONE);
                        iscolorListavailable = false;
                        Toast.makeText(NewProductViewActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
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
                        // Handle error, for example, show a toast or display an error message
                        Toast.makeText(NewProductViewActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(NewProductViewActivity.this);
        requestQueue.add(jsObjRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //  Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        //View views = getLayoutInflater().inflate(R.layout.my_calendar_view, null);
        BottomSheetDialog dialog = new BottomSheetDialog(NewProductViewActivity.this);
      //  dialog.setContentView(views);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        if (item==""){
            dialog.dismiss();
        }else{
            dialog.show();
        }

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onResume() {
        super.onResume();
        SubWalletSession=SubWalletAddCartSession.getInstance().getSubwallet_id(NewProductViewActivity.this);
        customer_id = BSession.getInstance().getUser_id(NewProductViewActivity.this);
        if (customer_id.equalsIgnoreCase("")) {

        } else {
            getWeightList();
        }

    }

    public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

        // list for storing urls of images.
        private final List<ProductsModel> mSliderItems;
        Context context;
        // Constructor
        public SliderAdapter(Context context, List<ProductsModel>  sliderDataArrayList) {
            this.context = context;
            this.mSliderItems = sliderDataArrayList;
        }

        // We are inflating the slider_layout
        // inside on Create View Holder method.
        @Override
        public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_slite, null);
            return new SliderAdapterViewHolder(inflate);
        }

        // Inside on bind view holder we will
        // set data to item of Slider View.
        @Override
        public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

            final ProductsModel sliderItem = mSliderItems.get(position);

            Glide.with(viewHolder.itemView)
                    .load(sliderItem.getImage())
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);

            viewHolder.imageViewBackground1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("position="+position);

                    BitmapDrawable bitmapDrawable = (BitmapDrawable) viewHolder.imageViewBackground.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    shareImageandText(bitmap);

                }
            });
        }

        @Override
        public int getCount() {
            return mSliderItems.size();
        }

        public class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {

            View itemView;
            ImageView imageViewBackground;
            ImageView imageViewBackground1;

            public SliderAdapterViewHolder(View itemView) {
                super(itemView);
                imageViewBackground = itemView.findViewById(R.id.image);
                imageViewBackground1 = itemView.findViewById(R.id.image1);

                this.itemView = itemView;
            }
        }
    }
    public void shareImageandText(Bitmap bitmap) {
        Uri uri = getmageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        String fuull = "Bellso APP Link :  https://play.google.com/store/apps/details?id=com.taprocycle.service";
        intent.putExtra(Intent.EXTRA_TEXT, fuull);
        intent.putExtra(Intent.EXTRA_SUBJECT, "ARMall Shoping !");
        intent.setType("image/png");
        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        Uri screenshotUri = Uri.parse(MediaStore.Images.class + "/" );
        intent.putExtra(Intent.EXTRA_STREAM, imageUris);
        Intent chooser = Intent.createChooser(intent, "Share via");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(chooser);

    }
    private Uri getmageToShare(Bitmap bitmap) {
        File imagefolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(this, "com.timeminsolutions.accsysindia.fileprovider", file);
        } catch (Exception e) {
/*
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
*/
        }
        return uri;
    }

}