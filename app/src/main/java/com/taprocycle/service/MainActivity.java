package com.taprocycle.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.smarteist.autoimageslider.SliderView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ViewListener;
import com.taprocycle.service.Activity.AboutUsActivity;
import com.taprocycle.service.Activity.AccountActivity;
import com.taprocycle.service.Activity.BikeActivity;
import com.taprocycle.service.Activity.CartActivity;
import com.taprocycle.service.Activity.DeliveryAddressActivity;
import com.taprocycle.service.Activity.EventActivity;
import com.taprocycle.service.Activity.HelpSupportActivity;
import com.taprocycle.service.Activity.LoginActivity;
import com.taprocycle.service.Activity.MyOrdersActivity;
import com.taprocycle.service.Activity.NewProductViewActivity;
import com.taprocycle.service.Activity.ShopbyCategoryActivity;
import com.taprocycle.service.Activity.SubcategoryActivity;
import com.taprocycle.service.Adapter.RecentlyAdapter;
import com.taprocycle.service.Adapter.CategoryAdapter;

import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.CategoryModel;
import com.taprocycle.service.test.model.OfferModel;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductModel;
import com.taprocycle.service.test.model.ProductsModel;
import com.taprocycle.service.test.model.StaticImages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,NavigationView.OnNavigationItemSelectedListener {
    RecyclerView rv_category, rv_bestseller, rv_sale, rv_products, rv_whatsnew;
    private List<CategoryModel> categoryModelList = new ArrayList<>();
    private static ViewPager mPager, mPager1;
    private static int currentPage = 0, currentPage1 = 0;
    private static final Integer[] XMEN = {R.drawable.locate};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    private static final Integer[] XMEN1 = {R.drawable.logo};
    private ArrayList<Integer> XMENArray1 = new ArrayList<Integer>();
    private List<OfferModel> bestsalelist = new ArrayList<>();
    private List<ProductsModel> productsalelist = new ArrayList<>();
    RelativeLayout relative_layout;
    Toolbar toolbar;
    Button btn_viewall;
    DrawerLayout drawer, drawer1;
    LinearLayout cart;
    NavigationView nav_view, nav_view1;
    ImageView menu,  whatsnew_image, bestseller_image, recently_image;
    BottomNavigationView customBottomBar;
    private List<ProductModel> productwhatsnewlist = new ArrayList<>();
    private List<ProductModel> productbestsellerlist = new ArrayList<>();
    FrameLayout rel;
    SwipeRefreshLayout swipeRefreshLayout;
    String addresss ="";
    FrameLayout frame_sub;
    RelativeLayout coordinatorlayout,offer_cat,badge_layout;
    LinearLayout home_lin,accessories,bikes,rental,more;
    ImageView person, notify,empty_product;
    ProgressDialog progressDialog;
    LinearLayout search_txt;
    String pid="";
    TextView  badge_notification,heart_notification,badge_notify_count;
    CarouselView corouselview;
    SliderView slider_image;
    List<StaticImages> apiSliderList = new ArrayList<>();
    StaticImages sliderModel = new StaticImages();
    private List<OfferModel> rvsalelist = new ArrayList<>();
    private List<OfferModel> whatnewlist = new ArrayList<>();
    private List<OfferModel> bestsellerlist = new ArrayList<>();
    private List<ProductsModel> recentlyaddedwiselist = new ArrayList<>();
    private List<ProductsModel> mainaddedwiselist = new ArrayList<>();
    int[] sampleImages = {R.drawable.bann,  R.drawable.banners,R.drawable.bann, R.drawable.banners};

    CarouselView banner_carousal;
    List<ProductsModel> sliderlist = new ArrayList<>();
    ProgressBar ProgressBar;
    GifImageView gif;
    int page = 0, limit = 2;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;
    RecentlyAdapter productListAdapter ;
    CategoryAdapter categoryAdapter;
    int count = 0;
    int stock = 0;
    RelativeLayout rel_heart;
    String cat_id = "12";
    TextView pincode;
    Button txt_viewall;
    Button txt_viewless;
    String pincodes="" ;
    LinearLayout closed_ll,cart1,idNestedSV;
    CardView cv_card;
    String picodes,payment_advance,discount_bonus;
    ArrayList<String> subwalletidlist = new ArrayList<String>();
    ArrayList<String> subwalletamountlist = new ArrayList<String>();
    int sublist = 0, thouf;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    private static final int FLEXIBLE_APP_UPDATE_REQ_CODE = 123;
    private Object Flexible;
    public List<CategoryModel> papularModelList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        /////////NETWORK CONDITION CHECK///////////
        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        installStateUpdatedListener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            } else if (state.installStatus() == InstallStatus.INSTALLED) {
                removeInstallStateUpdateListener();
            } else {
                Toast.makeText(getApplicationContext(), "InstallStateUpdatedListener: state: " + state.installStatus(), Toast.LENGTH_LONG).show();
            }
        };
        //appUpdateManager.registerListener(installStateUpdatedListener);
        checkUpdate();
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);

        String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(MainActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }

        /////////NETWORK CONDITION CHECK///////////
        ////////////////////////////////////////////////////////bottom navication///////////////////////////////

        init();

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
        home_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, ShopbyCategoryActivity.class);
                startActivity(intent);
            }
        });
        bikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, BikeActivity.class);
                startActivity(intent);
            }
        });
        rental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,MoreActivity.class);
                startActivity(intent);
            }
        });
        String customer_id = BSession.getInstance().getUser_id(MainActivity.this);
        if (customer_id.equalsIgnoreCase("")) {

        } else {
            getCartCount();
        }
       // bestsale();
        //products();
        appstatus();
        setCategoryList();
        accessories();
        loadslider();
        recently();
        getCartCount();
        getWishCount();
    }

    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Refreshed!", Toast.LENGTH_SHORT).show();
                setCategoryList();
                accessories();
                loadslider();
                recently();
                getCartCount();
                appstatus();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
    private void checkUpdate() {

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                startUpdateFlow(appUpdateInfo);
            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            }
        });
    }

    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, 100);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLEXIBLE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " , Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(),"Update success! Result Code: ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed! Result Code: ", Toast.LENGTH_LONG).show();
                checkUpdate();
            }
        }
    }

    private void popupSnackBarForCompleteUpdate() {
        Snackbar.make(findViewById(android.R.id.content).getRootView(), "New app is ready!", Snackbar.LENGTH_INDEFINITE)

                .setAction("Install", view -> {
                    if (appUpdateManager != null) {
                        appUpdateManager.completeUpdate();
                    }
                })
                .show();
    }

    private void removeInstallStateUpdateListener() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeInstallStateUpdateListener();
    }

    public void init() {
        empty_product=findViewById(R.id.empty_product);
        cart=findViewById(R.id.cart);
        cart1=findViewById(R.id.cart1);
        closed_ll=findViewById(R.id.closed_ll);
        idNestedSV=findViewById(R.id.idNestedSV);
        slider_image=findViewById(R.id.slider);
        rv_category = findViewById(R.id.rv_category);
        rv_sale = findViewById(R.id.rv_sale);
        rv_products = findViewById(R.id.rv_products);
        relative_layout = findViewById(R.id.relative_layout);
        frame_sub = findViewById(R.id.frame_subbbbb);
        coordinatorlayout = findViewById(R.id.coordinatorlayout);
        search_txt = findViewById(R.id.search_txt);
        btn_viewall = findViewById(R.id.btn_viewall);
        customBottomBar = findViewById(R.id.customBottomBar);
        accessories = findViewById(R.id.accessories);
        home_lin = findViewById(R.id.home_lin);
        bikes = findViewById(R.id.bikes);
        rental = findViewById(R.id.rental);
        more = findViewById(R.id.more);
        txt_viewall = findViewById(R.id.txt_viewall);
        txt_viewless = findViewById(R.id.txt_viewless);
        badge_notification = findViewById(R.id.badge_notification);
        badge_layout = findViewById(R.id.badge_layout);
        heart_notification = findViewById(R.id.heart_notification);
        rel_heart = findViewById(R.id.rel_heart);
        ProgressBar = findViewById(R.id.ProgressBar);
        gif = findViewById(R.id.gif);
        offer_cat = findViewById(R.id.offer_cat);

        loadingPB = findViewById(R.id.idPBLoading);
        pincode=findViewById(R.id.pincode);
        cv_card=findViewById(R.id.cv_card);
        banner_carousal=findViewById(R.id.banner_carousal);
        rel_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WishlistActivity.class);
                startActivity(intent);
            }
        });
        search_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.taprocycle.service.Activity.SearchActivity.class);
                startActivity(intent);
            }
        });
        txt_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*txt_viewall.setVisibility(View.GONE);
                txt_viewless.setVisibility(View.VISIBLE);*/
                Intent intent = new Intent(MainActivity.this, AccessoriesActivity.class);
                startActivity(intent);
            }
        });
        btn_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*txt_viewall.setVisibility(View.GONE);
                txt_viewless.setVisibility(View.VISIBLE);*/
                Intent intent = new Intent(MainActivity.this, AccessoriesActivity.class);
                startActivity(intent);
            }
        });

        offer_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OfferActivity.class);
                startActivity(intent);
            }
        });

        txt_viewless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_viewless.setVisibility(View.GONE);
                txt_viewall.setVisibility(View.VISIBLE);
                accessories();
            }
        });

        System.out.println("user_id=" + BSession.getInstance().getUser_id(MainActivity.this));

        nav_view = findViewById(R.id.nav_view);
        //menu = findViewById(R.id.menu);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle1);
        toggle1.syncState();
        //nav_view.setNavigationItemSelectedListener(this);
        relative_layout = findViewById(R.id.relative_layout);
       /*rental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });*/
    }

    private void appstatus() {
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        StringRequest jsObjRequest = new StringRequest(0, ProductConfig.appstatus, new Response.Listener<String>() {
            @SuppressLint("WrongConstant")
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    MainActivity.this.progressDialog.dismiss();
                    if (jsonResponse.has("status")&& jsonResponse.getString("status").equals("0")) {

                        cart1.setVisibility(View.GONE);
                        idNestedSV.setVisibility(View.GONE);
                        cv_card.setVisibility(View.GONE);
                        closed_ll.setVisibility(View.VISIBLE);
                        return;
                    } else if (jsonResponse.has("status")&& jsonResponse.getString("status").equals("1")){
                        cart1.setVisibility(View.VISIBLE);
                        idNestedSV.setVisibility(View.VISIBLE);
                        cv_card.setVisibility(View.VISIBLE);
                        closed_ll.setVisibility(View.GONE);
                        return;
                    }
                    /*cart1.setVisibility(View.GONE);
                    drawer.setDrawerLockMode(drawer.LOCK_MODE_LOCKED_CLOSED);
                    closed_ll.setVisibility(View.VISIBLE);
                    System.out.println("reposnse=="+response);*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        };
        Volley.newRequestQueue(this).add(jsObjRequest);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
    }

    private void setUpSlider() {

        banner_carousal.setViewListener(viewListener);
        banner_carousal.setPageCount(sliderlist.size());
        banner_carousal.setSlideInterval(3000);
    }

    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {

            View customView = getLayoutInflater().inflate(R.layout.slide, null);

            ImageView bannerimage = (ImageView) customView.findViewById(R.id.image);

            banner_carousal.setImageClickListener(new ImageClickListener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(MainActivity.this, OfferActivity.class);
                    startActivity(intent);
                   /* String cid = sliderlist.get(position).getCart_id();
                    String pid = sliderlist.get(position).getPid();
                    String scid = sliderlist.get(position).getScid();

                    if (cid.equalsIgnoreCase("")) {
                        Intent intent = new Intent(MainActivity.this, NewProductViewActivity.class);
                        intent.putExtra("pid", pid);
                        intent.putExtra("qty", "0");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, SubcategoryActivity.class);
                        intent.putExtra("cid", cid);
                        intent.putExtra("scid", scid);
                        startActivity(intent);
                    }*/

                }
            });

            String url = sliderlist.get(position).getImage();
            Glide.with(MainActivity.this)
                    .load(sliderlist.get(position).getImage())
                    .placeholder(R.drawable.logo)
                    .into(bannerimage);

            return customView;

        }

    };

//////////////////////////////////////////////menu/////////////////////////////////////

    public void setCategoryList() {

        final Map<String, String> params = new HashMap<>();

        String baseUrl = ProductConfig.categorylist;
//        progressDialog.show();

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        categoryModelList = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonlistObject = array.getJSONObject(i);
                            CategoryModel model = new CategoryModel();
                            model.setCat_id(jsonlistObject.getString("cat_id"));
                            model.setSubcategoryname(jsonlistObject.getString("web_title"));
                            model.setWeb_image(jsonlistObject.getString("web_image"));
                            categoryModelList.add(model);
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        rv_category.setLayoutManager(layoutManager);
                        CategoryAdapter recentlyAdapter = new CategoryAdapter(MainActivity.this, categoryModelList);
                        rv_category.setAdapter(recentlyAdapter);
                        rv_category.setHasFixedSize(true);

                        //progressDialog.dismiss();
                        System.out.println("Respnse=="+response);
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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsObjRequest);

    }

    private void setCategoryLists() {
        this.categoryModelList = new ArrayList();
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.categorylist, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    MainActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        JSONArray jsonlist = jsonResponse.getJSONArray("storeList");
                        for (int j = 0; j < jsonlist.length(); j++) {
                            JSONObject jsonlistObject = jsonlist.getJSONObject(j);
                            CategoryModel model = new CategoryModel();
                            model.setCat_id(jsonlistObject.getString("cat_id"));
                            model.setSubcategoryname(jsonlistObject.getString("web_title"));
                            model.setWeb_image(jsonlistObject.getString("web_image"));
                            MainActivity.this.categoryModelList.add(model);
                            Toast.makeText(MainActivity.this, " list", Toast.LENGTH_SHORT).show();

                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_category.setLayoutManager(layoutManager);
                        CategoryAdapter productListAdapter = new CategoryAdapter(MainActivity.this, papularModelList);
                        rv_category.setAdapter(productListAdapter);
                        rv_category.setHasFixedSize(true);
                    }
                     Toast.makeText(MainActivity.this, "No category list", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    MainActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                MainActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        Intent intent = null;

        if (id == R.id.action_profile) {

                intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);

        } else if (id == R.id.action_orders) {
            String cusid = BSession.getInstance().getUser_id(getApplicationContext());

                intent = new Intent(getApplicationContext(), MyOrdersActivity.class);
                startActivity(intent);

        } else if (id == R.id.action_address) {
            String cusid = BSession.getInstance().getUser_id(getApplicationContext());
            if (cusid.equalsIgnoreCase("")) {
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            } else {
                intent = new Intent(getApplicationContext(), DeliveryAddressActivity.class);
                startActivity(intent);
            }

        }  else if (id == R.id.action_events) {

            intent = new Intent(getApplicationContext(), EventActivity.class);
            startActivity(intent);

        } else if (id == R.id.action_help) {

            intent = new Intent(getApplicationContext(), HelpSupportActivity.class);
            startActivity(intent);

        } else if (id == R.id.action_invitee) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "APP LINK : " + "\n" + "com.timeminsolutions.accsysindia";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (id == R.id.action_about) {

            intent = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.action_log) {
            logoutAlert();

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutAlert() {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, R.style.AlertDialogTheme);
            builder.setMessage(getApplicationContext().getResources().getString(R.string.alert_want_logout))
                    .setCancelable(false)
                    .setPositiveButton(getApplicationContext().getResources().getString(R.string.alert_yes),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();

                                    logout();
                                }
                            })
                    .setNegativeButton(getApplicationContext().getResources().getString(R.string.alert_no),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {


                                    dialog.cancel();
                                }
                            });
            final android.app.AlertDialog alert = builder.create();
            alert.show();
        } /*else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            // builder.setMessage("Insufficient Discount Points. Please Topup ");
            builder.setMessage("Please Remove Your Cart");

            builder.setCancelable(true);

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.cancel();
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent=new Intent(MainActivity.this,CartActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }*/


    private void loadslider() {
        final Map<String, String> params = new HashMap<>();
        String para = "?type=" + "2";
        String baseUrl = ProductConfig.offer + para;
        sliderlist = new ArrayList<>();

        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {

                        JSONArray jsonResarray = jsonResponse.getJSONArray("storeList");

                        for (int i = 0; i < jsonResarray.length(); i++) {

                            JSONObject jsonObject1 = jsonResarray.getJSONObject(i);
                            ProductsModel model1 = new ProductsModel();
                            model1.setCart_id(jsonObject1.getString("category_id"));
                            model1.setScid(jsonObject1.getString("subcategory_id"));
                            model1.setPid(jsonObject1.getString("product_id"));
                            model1.setImage(jsonObject1.getString("image"));
                            sliderlist.add(model1);
                            setUpSlider();
                        }


                    } else {

                        Toast.makeText(MainActivity.this, "Items records not found", Toast.LENGTH_SHORT).show();

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

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsObjRequest);

    }


    public void accessories() {
        empty_product.setVisibility(View.GONE);
        rv_products.setVisibility(View.VISIBLE);
        recentlyaddedwiselist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str3 = "?cid=" ;
        String para_str7 = "&user_id=" + BSession.getInstance().getUser_id(getApplicationContext());

        String baseUrl = ProductConfig.productlist + para_str3  +para_str7;
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
                        for (int i = 0; i <=3; i++) {
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
                            model.setSize_chart(array.getJSONObject(i).getString("size_chart"));
                            model.setExclusive_product(array.getJSONObject(i).getString("exclusive_product"));
                            model.setOffer_status(array.getJSONObject(i).getString("offer_status"));
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            JSONArray array1 = product.getJSONArray("image");
                            model.setImage(array1.getJSONObject(0).getString("image"));

                            String stocks =  model.setStock(array.getJSONObject(i).getString("stock"));

                            if (!stocks.equalsIgnoreCase("")) {
                                // Toast.makeText(MainActivity.this, " out of stock!!!!!!!", Toast.LENGTH_LONG).show();
                                recentlyaddedwiselist.add(model);
                            }

                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_products.setLayoutManager(layoutManager);
                        productListAdapter = new RecentlyAdapter(MainActivity.this, recentlyaddedwiselist);
                        rv_products.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsObjRequest);
    }
    public void recently() {
        empty_product.setVisibility(View.GONE);
        rv_products.setVisibility(View.VISIBLE);
        recentlyaddedwiselist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str3 = "?neworder=" + "desc";
        String para_str7 = "&user_id=" + BSession.getInstance().getUser_id(getApplicationContext());

        String baseUrl = ProductConfig.recendlylist + para_str3  +para_str7;
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
                        for (int i = 0; i <4; i++) {
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
                            model.setSize_chart(array.getJSONObject(i).getString("size_chart"));
                            model.setExclusive_product(array.getJSONObject(i).getString("exclusive_product"));
                            model.setOffer_status(array.getJSONObject(i).getString("offer_status"));
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            JSONArray array1 = product.getJSONArray("image");
                            model.setImage(array1.getJSONObject(0).getString("image"));

                            String stocks =  model.setStock(array.getJSONObject(i).getString("stock"));
                            //recentlyaddedwiselist.add(model);
                            if (!stocks.equalsIgnoreCase("")) {
                                // Toast.makeText(MainActivity.this, " out of stock!!!!!!!", Toast.LENGTH_LONG).show();
                                recentlyaddedwiselist.add(model);
                            }

                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_sale.setLayoutManager(layoutManager);
                        productListAdapter = new RecentlyAdapter(MainActivity.this, recentlyaddedwiselist);
                        rv_sale.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                        rv_sale.setLayoutManager(gridLayoutManager);
                        rv_sale.setHasFixedSize(true);

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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsObjRequest);
    }

    public void getCartCount() {

        final Map<String, String> params = new HashMap<>();
        String customer_id = BSession.getInstance().getUser_id(MainActivity.this);
        String para_str = "?user_id=" + customer_id;
        String para_str1 = "&user_type=" + BSession.getInstance().getType(getApplicationContext());

        String baseUrl = ProductConfig.cartlist + para_str + para_str1;

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
    public void getWishCount() {

        final Map<String, String> params = new HashMap<>();
        String customer_id = BSession.getInstance().getUser_id(MainActivity.this);
        String para_str = "?user_id=" + customer_id;
        String para_str1 = "&product_id=" + pid;

        String baseUrl = ProductConfig.wishlist + para_str + para_str1;

        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {

                        heart_notification.setText(jsonResponse.getString("total_cnt"));

                    } else {
                        heart_notification.setText("0");
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


    private void logout() {

        BSession.getInstance().destroy(MainActivity.this);
        Toast.makeText(MainActivity.this, getResources().getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }

    public void onBackPressed()   {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen((int) GravityCompat.START)) {
            drawer.closeDrawer((int) GravityCompat.START);
            return;
        }
        AlertDialog.Builder BackAlertDialog = new AlertDialog.Builder(this);
        BackAlertDialog.setTitle((CharSequence) "Activity Exit Alert");
        BackAlertDialog.setMessage((CharSequence) "Are you sure want to exit ?");
        BackAlertDialog.setIcon((int) R.drawable.logo);
        BackAlertDialog.setPositiveButton((CharSequence) "NO", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        BackAlertDialog.setNegativeButton((CharSequence) "YES", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                MainActivity.this.finish();
            }

        });
        BackAlertDialog.show();
    }


}
