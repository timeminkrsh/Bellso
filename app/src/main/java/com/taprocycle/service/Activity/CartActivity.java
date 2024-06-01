package com.taprocycle.service.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AutomaticZenRule;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.taprocycle.service.Adapter.CarttAdapter;
import com.taprocycle.service.EditAddressActivity;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.OrderConfirm;
import com.taprocycle.service.R;
import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.WishlistActivity;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.DeliveryAddressModel;
import com.taprocycle.service.test.model.DeliveryAddressSession;
import com.taprocycle.service.test.model.PickStoreModel;
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
import java.util.StringTokenizer;

import com.taprocycle.service.test.model.StoredetailsSession;
import com.taprocycle.service.test.model.ZipcodeModel;
import pl.droidsonroids.gif.GifImageView;

public class CartActivity extends AppCompatActivity implements PaymentResultListener {
    private static final Object CP_MERCHANT_ACCESS_KEY = 100;
    RecyclerView rv_delivery,rv_cartproduct;
    public static DeliveryAddressModel subcourcemodel = new DeliveryAddressModel();
    static TextView DeliveryAddress;
    TextView storedetails;
    TextView before1;
    TextView before2;
    TextView before4;
    TextView beforephone;
    TextView pin;
    TextView mrp_amount;
    TextView product_discount_amount;
    TextView total_amount,free;
    TextView save_price;
    TextView payable_amount;
    TextView wallet_amounts,names;
    Button placeorder_button;
    private List<ProductModel> cartlist = new ArrayList<>();
    FrameLayout frame_cat_frag;
    ImageView arrow, search, cart, home;
    TextView total_mrp, badge_notification, txt_titile, totalpcpp;
    private static List<DeliveryAddressModel> deliveryaddress = new ArrayList<>();
    private List<ProductsModel> productwiselist = new ArrayList<>();
    LinearLayout deliveryaddress_lin;
    static LinearLayout delivery_address;
    LinearLayout store_address;
    String overtotal;
    String wallet_amount = "0";
    String wamount = "0";
    String total = "";
    String selectedAdress="";
    String address = "";
    String name = "";
    static String namee = "";
    static String pincodee;
    String emaill;
    static String linee1;
    static String linee2;
    static String linee3;
    String linee4;
    static String landmarkk;
    static String mobilee = "";
    String storeLine1="",storeLine2="",storeLine3="",storeLine4="",storeLine5="";
    String deliveryname="";
    static String deliverylinee1="";
    String deliverylinee2="";
    String deliverylinee3="";
    String deliverypincodee="",delivery_fee;
    String deliverylandmarkk="",deliverymobilee="";
    TextView total_amountcondition,cgst,sgst;
    ProgressDialog progressDialog;
    LinearLayout wallet_lin, save_lin, totalmrp_lin;
    double orderTotal = 0;
    CardView cartaddress,cartadress;
    ImageView wislist;
    double payableAmount = 0;
    GifImageView gif;
    LinearLayout arrow_lin;
    AutoCompleteTextView search_txt,atv_pincode;
    public ArrayList<ProductsModel> spinnerList = new ArrayList<>();
    List<ZipcodeModel> apiZipcodeList = new ArrayList<>();
    ArrayList<String> pincodelist = new ArrayList<String>();
    int totalpcp= 0;
    int totalmrp= 0;
    public  String selectedPaymentMethod = "";
    EditText etSurl, etFurl, etMerchantName, etPhone, etAmount, etUserCredential, etSurePayCount, etKey, etSalt;
    RadioButton radioBtnProduction;
    CoordinatorLayout clMain;
    RelativeLayout rlReviewOrder,searh;
    RadioGroup radioGrpEnv,radiogroup;
    int sublist = 0, thouf;
    LinearLayout  CartEmptyLinear,llay_cart_empty;
    FrameLayout linearl;
    String phone, email;
    Button btn_continue;
    TabLayout tabLayout;
    String yousave;
    static String defaultAddressText;
    ViewPager2 viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        /////////NETWORK CONDITION CHECK///////////
        name = BSession.getInstance().getUser_name(CartActivity.this);
        phone = BSession.getInstance().getUser_mobile(CartActivity.this);
        email = BSession.getInstance().getUser_email(CartActivity.this);

        String status = NetworkUtil.getConnectivityStatusString(this);
        if (status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(CartActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        } else {

        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");

        Checkout.preload(getApplicationContext());

        placeorder_button = findViewById(R.id.placeorder_button);
        txt_titile = findViewById(R.id.txt_titile);
        txt_titile.setText("My Cart");
        deliveryaddress_lin = findViewById(R.id.deliveryaddress_lin);
        wallet_lin = findViewById(R.id.wallet_lin);
        rv_delivery = findViewById(R.id.rv_delivery);
        wislist = findViewById(R.id.wislist);
        save_lin = findViewById(R.id.save_lin);
        totalmrp_lin = findViewById(R.id.totalmrp_lin);
        DeliveryAddress = findViewById(R.id.DeliveryAddress);
        storedetails = findViewById(R.id.storedetails);
        before1 = findViewById(R.id.before1);
        before2 = findViewById(R.id.before2);
        linearl = findViewById(R.id.linearl);
        btn_continue = findViewById(R.id.btn_continue);
        before4 = findViewById(R.id.before4);
        names = findViewById(R.id.name);
        beforephone = findViewById(R.id.beforephone);
        pin = findViewById(R.id.pin);
        total_amountcondition = findViewById(R.id.total_amountcondition);

        total_mrp = findViewById(R.id.total_mrp);
        rv_cartproduct = findViewById(R.id.rv_cartproduct);
        llay_cart_empty = findViewById(R.id.llay_cart_empty);
        mrp_amount = findViewById(R.id.mrp_amount);
        product_discount_amount = findViewById(R.id.product_discount_amount);
        total_amount = findViewById(R.id.total_amount);
        cgst = findViewById(R.id.cgst);
        sgst = findViewById(R.id.sgst);
        free = findViewById(R.id.free);
        save_price = findViewById(R.id.save_price);
        payable_amount = findViewById(R.id.payable_amount);
        frame_cat_frag = findViewById(R.id.frame_cat_frag);
        arrow = findViewById(R.id.arrow);
        arrow_lin = findViewById(R.id.arrow_lin);
        search_txt = findViewById(R.id.search_txt);
        searh = findViewById(R.id.searh);
        search_txt.setVisibility(View.GONE);
        searh.setVisibility(View.GONE);
        search = findViewById(R.id.search);
        cart = findViewById(R.id.cart);
        badge_notification = findViewById(R.id.badge_notification);
        gif = findViewById(R.id.gif);
        home = findViewById(R.id.home);
        wallet_amounts = findViewById(R.id.wallet_amounts);
        totalpcpp = findViewById(R.id.totalpcpp);
        cartaddress = findViewById(R.id.cartaddress);
        cartadress = findViewById(R.id.cartadress);
        tabLayout =  findViewById(R.id.tab_layout);
        delivery_address =  findViewById(R.id.delivery_address);
        store_address =  findViewById(R.id.store_address);
        viewPager =  findViewById(R.id.viewpager);

        /////////payumoney declare
        etSurl = findViewById(R.id.etSurl);
        etFurl = findViewById(R.id.etFurl);
        etMerchantName = findViewById(R.id.etMerchantName);
        etPhone = findViewById(R.id.etPhone);
        etAmount = findViewById(R.id.etAmount);
        etUserCredential = findViewById(R.id.etUserCredential);
        etSurePayCount = findViewById(R.id.etSurePayCount);
        etKey = findViewById(R.id.etKey);
        etSalt = findViewById(R.id.etSalt);
        radioBtnProduction = findViewById(R.id.radioBtnProduction);
        clMain = findViewById(R.id.clMain);
        rlReviewOrder = findViewById(R.id.rlReviewOrder);
        radioGrpEnv = findViewById(R.id.radioGrpEnv);
        radiogroup = findViewById(R.id.radiogroup);
        CartEmptyLinear = findViewById(R.id.CartEmptyLinear);

        getCartCount();
        //getCart();
        method();

        RadioButton codRadioButton2 = findViewById(R.id.online);
        codRadioButton2.setChecked(true);
        selectedPaymentMethod = "Online";

        //////////payumoney declare
        /*namee = DeliveryAddressSession.getInstance().getName(getApplicationContext());
        linee1 = DeliveryAddressSession.getInstance().getLine1(getApplicationContext());
        System.out.println("selectedAdress~~~~"+linee1);
        linee2 = DeliveryAddressSession.getInstance().getLine2(getApplicationContext());
        linee3 = DeliveryAddressSession.getInstance().getLine3(getApplicationContext());
        pincodee = DeliveryAddressSession.getInstance().getPincode(getApplicationContext());
        landmarkk = DeliveryAddressSession.getInstance().getLanmark(getApplicationContext());
        mobilee = DeliveryAddressSession.getInstance().getMobile(getApplicationContext());*/

        deliveryname = DeliveryAddressSession.getInstance().getName(getApplicationContext());
        deliverylinee1 = DeliveryAddressSession.getInstance().getLine1(getApplicationContext());
        System.out.println("selectedAdress~~~~"+linee1);
        deliverylinee2 = DeliveryAddressSession.getInstance().getLine2(getApplicationContext());
        deliverylinee3 = DeliveryAddressSession.getInstance().getLine3(getApplicationContext());
        deliverypincodee = DeliveryAddressSession.getInstance().getPincode(getApplicationContext());
        deliverylandmarkk = DeliveryAddressSession.getInstance().getLanmark(getApplicationContext());
        deliverymobilee = DeliveryAddressSession.getInstance().getMobile(getApplicationContext());

        storeLine1 = StoredetailsSession.getInstance().getLine1(getApplicationContext());
        System.out.println("linee1"+storeLine1);
        storeLine2 = StoredetailsSession.getInstance().getLine2(getApplicationContext());
        System.out.println("linee1"+storeLine2);
        storeLine3 = StoredetailsSession.getInstance().getLine3(getApplicationContext());
        System.out.println("linee1"+storeLine3);
        storeLine4 = StoredetailsSession.getInstance().getLine4(getApplicationContext());
        System.out.println("linee1"+storeLine4);
        storeLine5 = StoredetailsSession.getInstance().getPincode(getApplicationContext());
        System.out.println("linee1"+storeLine5);
        String storeId = getIntent().getStringExtra("storeid");
        System.out.println("storeid==" + storeId);

        /*boolean hideCartAddress = getIntent().getBooleanExtra("", false);
        if (hideCartAddress) {
            delivery_address.setVisibility(View.GONE);
            store_address.setVisibility(View.VISIBLE);
            System.out.println("storeid=="+selectedAddress);
            DeliveryAddressSession.getInstance().initialize(CartActivity.this,
                    "","","",
                    "","","","","");
        }

        boolean hideStoreAddress = getIntent().getBooleanExtra("", false);
        if (hideStoreAddress) {

            delivery_address.setVisibility(View.VISIBLE);
            store_address.setVisibility(View.GONE);
            RadioButton codRadioButton1 = findViewById(R.id.cod);
            codRadioButton1.setChecked(false);
            radiogroup.findViewById(R.id.cod).setEnabled(false);

            RadioButton codRadioButton2 = findViewById(R.id.online);
            codRadioButton2.setChecked(true);
            selectedPaymentMethod = "Online";
            System.out.println("codRadioButton"+selectedPaymentMethod);
            StoredetailsSession.getInstance().initialize(CartActivity.this,
                    "","","",
                    "","");

        }

        DeliveryAddress.setText(namee + "\n" + linee1 + "\n" + linee2 + "\n" + linee3 + "\n" + linee4 + "\n" + landmarkk + "\n" + "Pin - " + pincodee + "\n" + "Mob - " + mobilee + ".");

        storedetails.setText(storeLine1 + "\n" + storeLine2 + "\n" + storeLine3 + "\n" + storeLine4 + "\n" + "Pin - " + storeLine5);

        selectedAddress = namee + " "+ "\n" + linee1 + " "+ "\n" + linee2 + " "+ "\n" + linee3 + " "+ "\n" + linee4 + " "+ "\n" + landmarkk + " "+ "\n" + "Pincode - " + pincodee +" "+ "\n" + "Mobile - " + mobilee + ".";

        selectedAddress = storeLine1 + " "+"\n" + storeLine2 +" "+ "\n" + storeLine3 +" "+ "\n" + storeLine4 +" "+ "\n" + "Pincode - " + storeLine5;*/

        DeliveryAddress.setText(namee + "\n" + linee1 + "\n" + linee2 + "\n" + linee3 + "\n" + linee4 + "\n" + landmarkk + "\n" + "Pin - " + pincodee + "\n" + "Mob - " + mobilee + ".");

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        arrow_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        wislist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CartActivity.this, WishlistActivity.class);
                startActivity(intent);
            }
        });

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (checkedId == R.id.cod) {
                   selectedPaymentMethod="COD";
                    System.out.println("codRadioButton"+selectedPaymentMethod);
                } else if (checkedId == R.id.online) {
                    selectedPaymentMethod="Online";
                    System.out.println("codRadioButton"+selectedPaymentMethod);
                    //Toast.makeText(CartActivity.this, "Online", Toast.LENGTH_SHORT).show();
                }
            }
        });

        placeorder_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deliverylinee1.isEmpty() && storeLine1.isEmpty()){
                   Toast.makeText(CartActivity.this, "Please add Delivery address", Toast.LENGTH_SHORT).show();
                   return;
                }
                System.out.println("linee1"+selectedPaymentMethod);
                if(selectedPaymentMethod.isEmpty()){
                    Toast.makeText(CartActivity.this, "Choose Your payment method", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedPaymentMethod=="COD"){
                    placeorder();
                    return;
                }if (selectedPaymentMethod=="Online"){
                    payUsingUpi();
                }

            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void address_validatemethod(DeliveryAddressModel selectedAddress)  {
        delivery_address.setVisibility(View.VISIBLE);
        store_address.setVisibility(View.GONE);
        deliverylinee1 = selectedAddress.getLine1();

        DeliveryAddress.setText(selectedAddress.getName() + "\n" +
                selectedAddress.getLine1() + "\n" +
                selectedAddress.getLine2() + "\n" +
                selectedAddress.getLine3() + "\n" +
                selectedAddress.getLandmark() + "\n" +
                "Pincode - " + selectedAddress.getPincode() + "\n" +
                "Mobile - " + selectedAddress.getPhone() + ".");

        namee = selectedAddress.getName();
        linee1 = selectedAddress.getLine1();
        linee2 = selectedAddress.getLine2();
        linee3 = selectedAddress.getLine3();
        pincodee = selectedAddress.getPincode();
        landmarkk = selectedAddress.getLandmark();
        mobilee = selectedAddress.getPhone();

        RadioButton codRadioButton1 = findViewById(R.id.cod);
        codRadioButton1.setChecked(false);
        radiogroup.findViewById(R.id.cod).setEnabled(false);

        RadioButton codRadioButton2 = findViewById(R.id.online);
        codRadioButton2.setChecked(true);
        selectedPaymentMethod = "Online";
        System.out.println("codRadioButton"+selectedPaymentMethod);
        StoredetailsSession.getInstance().initialize(CartActivity.this,
                "","","",
                "","");
        selectedAdress = (selectedAddress.getName() + "\n" +
                selectedAddress.getLine1() + "\n" +
                selectedAddress.getLine2() + "\n" +
                selectedAddress.getLine3() + "\n" +
                selectedAddress.getLandmark() + "\n" +
                "Pincode - " + selectedAddress.getPincode() + "\n" +
                "Mobile - " + selectedAddress.getPhone() + ".");
        getCart();
        System.out.println("selectedAdress~~~~"+selectedAdress);

    }

    private void address_validatmethod(PickStoreModel selectedPickupAddress)  {
        delivery_address.setVisibility(View.GONE);
        store_address.setVisibility(View.VISIBLE);
        storeLine1 =selectedPickupAddress.getShop_name();

        storedetails.setText(selectedPickupAddress.getShop_name() + "\n" +
                selectedPickupAddress.getMobile() + "\n" +
                selectedPickupAddress.getEmail() + "\n" +
                        selectedPickupAddress.getAddress() + "\n" +
                        "Pincode - " + selectedPickupAddress.getPincode());

        linee1 = selectedPickupAddress.getShop_name();
        mobilee = selectedPickupAddress.getMobile();
        linee2 = selectedPickupAddress.getEmail();
        linee3 = selectedPickupAddress.getAddress();
        pincodee = selectedPickupAddress.getPincode();
        landmarkk = "";

        RadioButton codRadioButton1 = findViewById(R.id.cod);
        codRadioButton1.setChecked(true);
        radiogroup.findViewById(R.id.cod).setEnabled(true);

        RadioButton codRadioButton2 = findViewById(R.id.online);
        codRadioButton2.setChecked(true);
        selectedPaymentMethod = "Online";
        System.out.println("codRadioButton"+selectedPaymentMethod);
        DeliveryAddressSession.getInstance().initialize(CartActivity.this,
                "","","",
                "","","","","");

        selectedAdress = (selectedPickupAddress.getShop_name() + "\n" +
                selectedPickupAddress.getMobile() + "\n" +
                selectedPickupAddress.getEmail() + "\n" +
                selectedPickupAddress.getAddress() + "\n" +
                "Pin - " + selectedPickupAddress.getPincode());
        getCart();
        System.out.println("selectedAdress~~~~"+selectedAdress);

        /*RadioButton codRadioButton = findViewById(R.id.cod);
        codRadioButton.setChecked(true);
        selectedPaymentMethod = "COD";
        System.out.println("codRadioButton"+selectedPaymentMethod);

        String storeId = getIntent().getStringExtra("storeid");
        System.out.println("storeid==" + storeId);
        boolean hideCartAddress = getIntent().getBooleanExtra("", false);
        if (hideCartAddress) {
            delivery_address.setVisibility(View.GONE);
            store_address.setVisibility(View.VISIBLE);
            System.out.println("storeid=="+selectedAddress);
            DeliveryAddressSession.getInstance().initialize(CartActivity.this,
                    "","","",
                    "","","","","");
        }

        boolean hideStoreAddress = getIntent().getBooleanExtra("", false);
        if (hideStoreAddress) {

            delivery_address.setVisibility(View.VISIBLE);
            store_address.setVisibility(View.GONE);
            RadioButton codRadioButton1 = findViewById(R.id.cod);
            codRadioButton1.setChecked(false);
            radiogroup.findViewById(R.id.cod).setEnabled(false);

            RadioButton codRadioButton2 = findViewById(R.id.online);
            codRadioButton2.setChecked(true);
            selectedPaymentMethod = "Online";
            System.out.println("codRadioButton"+selectedPaymentMethod);
            StoredetailsSession.getInstance().initialize(CartActivity.this,
                    "","","",
                    "","");

        }

        DeliveryAddress.setText(namee + "\n" + linee1 + "\n" + linee2 + "\n" + linee3 + "\n" + linee4 + "\n" + landmarkk + "\n" + "Pin - " + pincodee + "\n" + "Mob - " + mobilee + ".");

        storedetails.setText(storeLine1 + "\n" + storeLine2 + "\n" + storeLine3 + "\n" + storeLine4 + "\n" + "Pin - " + storeLine5);

        selectedAddress = namee + " "+ "\n" + linee1 + " "+ "\n" + linee2 + " "+ "\n" + linee3 + " "+ "\n" + linee4 + " "+ "\n" + landmarkk + " "+ "\n" + "Pincode - " + pincodee +" "+ "\n" + "Mobile - " + mobilee + ".";

        selectedAddress = storeLine1 + " "+"\n" + storeLine2 +" "+ "\n" + storeLine3 +" "+ "\n" + storeLine4 +" "+ "\n" + "Pincode - " + storeLine5;*/

    }


    private void payUsingUpi() {
        // Create a Checkout object
        Checkout checkout = new Checkout();
        int a=Integer.parseInt(total);
        // Set your API key
        //checkout.setKeyID("rzp_test_HrMtihPItXcmhE");
        checkout.setKeyID("rzp_live_Z7DmRw6BGNcNoV");

        // Set payment options
        JSONObject options = new JSONObject();
        try {
            options.put("name", "Bellso Sports");
            options.put("description", "Payment for your product/service");
            options.put("currency", "INR"); // Use your preferred currency code
            options.put("amount", a*100); // Amount in paise
            options.put("prefill", new JSONObject().put("email", BSession.getInstance().getUser_email(CartActivity.this)));

            checkout.open(this, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Checkout.RZP_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Payment successful
                String paymentId = data.getStringExtra("payment_id");
                Toast.makeText(this, "Payment Successful: " + paymentId, Toast.LENGTH_SHORT).show();
                placeorder();
            } else if (resultCode == Checkout.TLS_ERROR) {
                // Payment failed
                String error = data.getStringExtra("error_description");
                Toast.makeText(this, "Payment Failed: " + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void method(){
        Bundle dataBundle = new Bundle();

        // Create a FragmentStateAdapter to manage fragments
        FragmentStateAdapter adapter = new FragmentStateAdapter (this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        AddressFragment fragmentA = new AddressFragment();

                        return fragmentA;
                    case 1:
                        StoreFragment fragmentB = new StoreFragment();

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
        /*viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    AddressFragment fragmentA = (AddressFragment) adapter.createFragment(position);
                    fragmentA.text_radi.setEnabled(true); // Enable radio buttons in AddressFragment
                    StoreFragment fragmentB = (StoreFragment) adapter.createFragment(position + 1);
                    fragmentB.text_radio.setEnabled(false); // Disable radio buttons in StoreFragment
                } else if (position == 1) {
                    StoreFragment fragmentB = (StoreFragment) adapter.createFragment(position);
                    fragmentB.text_radio.setEnabled(true); // Enable radio buttons in StoreFragment
                    AddressFragment fragmentA = (AddressFragment) adapter.createFragment(position - 1);
                    fragmentA.text_radi.setEnabled(false); // Disable radio buttons in AddressFragment
                }
            }

            // ... other overriden methods (onPageScrolled, onPageScrollStateChanged)
        });*/

        // Connect TabLayout to ViewPager
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // Set tab titles here
                    if (position == 0) {
                        tab.setText("Home Delivery");
                    } else if (position == 1) {
                        tab.setText("Pickup From Store");
                    }
                }
        ).attach();
    }

    public void getCartCount() {
        rv_cartproduct.setVisibility(View.VISIBLE);
        cartlist = new ArrayList<>();
        productwiselist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String customer_id = BSession.getInstance().getUser_id(CartActivity.this);
        String para_str = "?user_id=" + customer_id;
        progressDialog.show();
        String baseUrl = ProductConfig.cartlist + para_str ;

        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {

                        linearl.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            ProductsModel model = new ProductsModel();
                            model.setCart_id(array.getJSONObject(i).getString("cart_id"));
                            model.setProduct_id(array.getJSONObject(i).getString("product_id"));
                            model.setScid(array.getJSONObject(i).getString("subcategory"));
                            model.setProductname(array.getJSONObject(i).getString("product_name"));
                            model.setQty(array.getJSONObject(i).getString("qty"));
                            model.setPrice(array.getJSONObject(i).getString("price"));
                            model.setColor(array.getJSONObject(i).getString("color"));
                            model.setSize(array.getJSONObject(i).getString("size"));
                            model.setTotal(array.getJSONObject(i).getString("total"));
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            model.setExclusive_product(array.getJSONObject(i).getString("exclusive_product"));
                            model.setVid(array.getJSONObject(i).getString("vid"));
                            model.setProduct_color(array.getJSONObject(i).getString("product_color"));
                            model.setImage(array.getJSONObject(i).getString("web_image"));
                            productwiselist.add(model);

                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_cartproduct.setLayoutManager(layoutManager);
                        CarttAdapter productListAdapter = new CarttAdapter(CartActivity.this, productwiselist);
                        rv_cartproduct.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(CartActivity.this, 1);
                        rv_cartproduct.setLayoutManager(gridLayoutManager);
                        rv_cartproduct.setHasFixedSize(true);
                        productListAdapter.notifyDataSetChanged();
                        System.out.println("Response=="+response);

                        badge_notification.setText(jsonResponse.getString("total_cnt"));
                        payable_amount.setText("₹ " + jsonResponse.getString("total_pcp_with_sfee"));
                        total_amount.setText("₹ " + jsonResponse.getString("total_pcp_with_sfee"));
                        cgst.setText("₹ " + jsonResponse.getString("total_intra_state_tax"));
                        free.setText("₹ " + jsonResponse.getString("only_shipping_charge"));
                        delivery_fee = jsonResponse.getString("only_shipping_charge");
                        sgst.setText("₹ " + jsonResponse.getString("total_inter_state_tax"));
                        total = jsonResponse.getString("total_pcp");
                        orderTotal = Double.parseDouble(jsonResponse.getString("total"));

                        save_lin.setVisibility(View.VISIBLE);
                        totalmrp_lin.setVisibility(View.VISIBLE);
                        total_mrp.setText("₹ " + jsonResponse.getString("total"));
                        totalmrp = Integer.parseInt(jsonResponse.getString("total_pcp"));

                        int total1 = Integer.parseInt(jsonResponse.getString("total_pcp"));
                        totalpcp = Integer.parseInt(jsonResponse.getString("total"));
                        int c = totalpcp - total1;
                        yousave = String.valueOf(totalpcp - total1);
                        System.out.println("totalamount=="+yousave);
                        save_price.setText( ""+c);
                        save_price.setPaintFlags(save_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    } else {
                        /*if (CartActivity.this.cartlist.size() > 0) {
                            CartActivity.this.llay_cart_empty.setVisibility(View.GONE);
                            CartActivity.this.linearl.setVisibility(View.VISIBLE);
                        } else {
                            CartActivity.this.llay_cart_empty.setVisibility(View.VISIBLE);
                            CartActivity.this.linearl.setVisibility(View.GONE);
                        }*/
                        llay_cart_empty.setVisibility(View.VISIBLE);
                        linearl.setVisibility(View.GONE);
                        payable_amount.setText("₹ " +"0");
                        total_amount.setText("₹ " + "0");
                        save_price.setText("₹ " + "0");
                        total_mrp.setText("₹ " + "0");
                        badge_notification.setText("0");
                        rv_cartproduct.setVisibility(View.GONE);
                        wallet_lin.setVisibility(View.GONE);
                        placeorder_button.setVisibility(View.GONE);
                        orderTotal =0.00;
                        progressDialog.dismiss();

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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
    }

    public void getCart() {
        rv_cartproduct.setVisibility(View.VISIBLE);
        cartlist = new ArrayList<>();
        productwiselist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String customer_id = BSession.getInstance().getUser_id(CartActivity.this);
        String para_str = "?user_id=" + customer_id;
        String baseUrl = ProductConfig.cartlist + para_str ;

        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {

                        linearl.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        System.out.println("Response=="+response);

                        if(landmarkk.isEmpty()){
                            badge_notification.setText(jsonResponse.getString("total_cnt"));
                            payable_amount.setText("₹ " + jsonResponse.getString("total_pcp"));
                            total_amount.setText("₹ " + jsonResponse.getString("total_pcp"));
                            cgst.setText("₹ " + jsonResponse.getString("total_intra_state_tax"));
                            free.setText("Free");
                            delivery_fee = "";
                            sgst.setText("₹ " + jsonResponse.getString("total_inter_state_tax"));
                            total = jsonResponse.getString("total_pcp");
                            orderTotal = Double.parseDouble(jsonResponse.getString("total"));
                        }else{
                            badge_notification.setText(jsonResponse.getString("total_cnt"));
                            payable_amount.setText("₹ " + jsonResponse.getString("total_pcp_with_sfee"));
                            total_amount.setText("₹ " + jsonResponse.getString("total_pcp_with_sfee"));
                            cgst.setText("₹ " + jsonResponse.getString("total_intra_state_tax"));
                            free.setText("₹ " + jsonResponse.getString("only_shipping_charge"));
                            delivery_fee = jsonResponse.getString("only_shipping_charge");
                            sgst.setText("₹ " + jsonResponse.getString("total_inter_state_tax"));
                            total = jsonResponse.getString("total_pcp");
                            orderTotal = Double.parseDouble(jsonResponse.getString("total"));
                        }

                        save_lin.setVisibility(View.VISIBLE);
                        totalmrp_lin.setVisibility(View.VISIBLE);
                        total_mrp.setText("₹ " + jsonResponse.getString("total"));
                        totalmrp = Integer.parseInt(jsonResponse.getString("total_pcp"));

                        int total1 = Integer.parseInt(jsonResponse.getString("total_pcp"));
                        totalpcp = Integer.parseInt(jsonResponse.getString("total"));
                        int c = totalpcp - total1;
                        yousave = String.valueOf(totalpcp - total1);
                        System.out.println("totalamount=="+yousave);
                        save_price.setText( ""+c);
                        save_price.setPaintFlags(save_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    } else {
                        llay_cart_empty.setVisibility(View.VISIBLE);
                        linearl.setVisibility(View.GONE);
                        payable_amount.setText("₹ " +"0");
                        total_amount.setText("₹ " + "0");
                        save_price.setText("₹ " + "0");
                        total_mrp.setText("₹ " + "0");
                        badge_notification.setText("0");
                        rv_cartproduct.setVisibility(View.GONE);
                        wallet_lin.setVisibility(View.GONE);
                        placeorder_button.setVisibility(View.GONE);
                        orderTotal =0.00;
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
    }

    public void placeorder() {

        String ttamount = total_amount.getText().toString().trim();
        StringTokenizer amount = new StringTokenizer(ttamount, "₹ ");
        String ppamount = String.valueOf(payableAmount);
        Double ttamounst = Double.valueOf(ppamount).doubleValue();
        System.out.println("ttamount" + ttamounst);
        StringTokenizer pmount = new StringTokenizer(ppamount, "₹ ");
        System.out.println("343343434~~~~" + overtotal);
        final Map<String, String> params = new HashMap<>();
        String para3 = "?user_id=" + BSession.getInstance().getUser_id(CartActivity.this);
        String para4 = "&name=" + name;
        String para5 = "&phone=" + phone;
        String para6 = "&email=" + BSession.getInstance().getUser_email(CartActivity.this);
        String para7 = "&address1=" + linee1;
        String para8 = "&address2=" + linee2;
        String para9 = "&address3=" + linee3;
        String para10 = "&address4=" + mobilee;
        String para12 = "&pincode=" + pincodee;
        String para13 = "&lanmark=" + landmarkk;
        String para15 = "&order_total=" + totalmrp;
        String para16 = "&order_mrp=" + totalpcp;
        String para17 = "&shipping_fee=" + delivery_fee;

        String baseUrl = ProductConfig.placeorder +para3+para4+para5+para6+para7+para8+para9+para10+para12+para13+para15+para16+para17;

        progressDialog.show();
        System.out.println("selectedAdress~~~~"+baseUrl);
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        Intent intent = new Intent(CartActivity.this, OrderConfirm.class);
                        intent.putExtra("order_id",jsonResponse.getString("order_id"));
                        System.out.println("3435343434~~~~"+jsonResponse.getString("order_id"));
                        intent.putExtra("totalamount",jsonResponse.getString("total"));
                        System.out.println("3435343434~~~~"+jsonResponse.getString("total"));
                        intent.putExtra("yousave",yousave);
                        intent.putExtra("payment",selectedPaymentMethod);
                        System.out.println("ttamount"+selectedPaymentMethod);
                        BSession.getInstance().initialize(CartActivity.this,
                                BSession.getInstance().getUser_id(CartActivity.this),
                                BSession.getInstance().getUser_name(CartActivity.this),
                                BSession.getInstance().getUser_mobile(CartActivity.this),
                                BSession.getInstance().getUser_address(CartActivity.this),
                                BSession.getInstance().getUser_email(CartActivity.this),
                                BSession.getInstance().getProfileimage(CartActivity.this),
                                BSession.getInstance().getPincode(CartActivity.this),
                                BSession.getInstance().getSessionId(CartActivity.this),
                                BSession.getInstance().getType(CartActivity.this),selectedPaymentMethod);
                        System.out.println("ttamount"+jsonResponse.getString("order_id"));
                        System.out.println("3435343434~~~~"+jsonResponse.getString("total"));
                        DeliveryAddressSession.getInstance().initialize(CartActivity.this,
                                "","","",
                                "","","","","");
                        StoredetailsSession.getInstance().initialize(CartActivity.this,
                                "","","",
                                "","");
                        startActivity(intent);

                    } else {
                        Toast.makeText(CartActivity.this, "Update failed", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        requestQueue.add(jsObjRequest);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CartActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            placeorder();
            // Payment was successful, handle it here
        } catch (Exception e) {
            Log.e("PaymentSuccess", "Exception in onPaymentSuccess", e);
        }
    }
    @Override
    public void onPaymentError(int code, String response) {
        try {
            // Payment failed, handle it here
        } catch (Exception e) {
            Log.e("PaymentError", "Exception in onPaymentError", e);
        }
    }



    public static class AddressFragment extends Fragment {
        private List<DeliveryAddressModel> deliveryaddress = new ArrayList<>();
        RecyclerView rv_delivery;
        TextView add_address;

        @SuppressLint("MissingInflatedId")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view =inflater.inflate(R.layout.address_fragment, container, false);
            rv_delivery=view.findViewById(R.id.rv_delivery);
            add_address = view.findViewById(R.id.add_address);

            add_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DeliveryAddressActivity.class);
                    startActivity(intent);

                }
            });

            deliveryaddress();
            return view;
        }

        public void  deliveryaddress() {

            deliveryaddress = new ArrayList<>();
            final Map<String, String> params = new HashMap<>();
            String para_str1 = "?method=" + "2";
            String para_str2 = "&user_id=" + BSession.getInstance().getUser_id(getContext());
            String baseUrl   = ProductConfig.user_address + para_str1+para_str2;

            StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Response", response.toString());
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                            deliveryaddress = new ArrayList<>();
                            JSONArray array = jsonResponse.getJSONArray("storeList");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                DeliveryAddressModel model = new DeliveryAddressModel();
                                model.setId(array.getJSONObject(i).getString("id"));
                                model.setName(array.getJSONObject(i).getString("name"));
                                model.setPhone(array.getJSONObject(i).getString("phone"));
                                model.setLine1(array.getJSONObject(i).getString("line1"));
                                model.setLine2(array.getJSONObject(i).getString("line2"));
                                model.setLine3(array.getJSONObject(i).getString("line3"));
                                model.setLine4(array.getJSONObject(i).getString("line4"));
                                model.setLine5(array.getJSONObject(i).getString("line5"));
                                model.setPincode(array.getJSONObject(i).getString("pincode"));
                                model.setLandmark(array.getJSONObject(i).getString("lanmark"));
                                deliveryaddress.add(model);

                            }
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            rv_delivery.setLayoutManager(layoutManager);
                            AddressAdapter productListAdapter = new AddressAdapter(getContext(), deliveryaddress);
                            rv_delivery.setAdapter(productListAdapter);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            rv_delivery.setLayoutManager(gridLayoutManager);
                            rv_delivery.setHasFixedSize(true);

                            if (!deliveryaddress.isEmpty()) {
                                DeliveryAddressModel defaultAddress = deliveryaddress.get(0);
                                defaultAddressText = defaultAddress.getName() + "\n" +
                                        defaultAddress.getLine1() + "\n" +
                                        defaultAddress.getLine2() + "\n" +
                                        defaultAddress.getLine3() + "\n" +
                                        defaultAddress.getLandmark() + "\n" +
                                        "Pin - " + defaultAddress.getPincode() + "\n" +
                                        "Mob - " + defaultAddress.getPhone() + ".";
                                deliverylinee1 = defaultAddress.getLine1();

                                namee = defaultAddress.getName();
                                linee1 = defaultAddress.getLine1();
                                linee2 = defaultAddress.getLine2();
                                linee3 = defaultAddress.getLine3();
                                pincodee = defaultAddress.getPincode();
                                landmarkk = defaultAddress.getLandmark();
                                mobilee = defaultAddress.getPhone();

                                DeliveryAddress.setText(defaultAddressText);
                                delivery_address.setVisibility(View.VISIBLE);
                            }

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
                    String message = null;
                    if (error instanceof NetworkError) {
                        Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_LONG).show();

                        message = "The server could not be found. Please try again after some time!!";
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(getContext(),"Cannot connect to Internet...Please check your connection!" , Toast.LENGTH_LONG).show();

                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getContext(),"Parsing error! Please try again after some time!!" , Toast.LENGTH_LONG).show();

                        message = "Parsing error! Please try again after some time!!";
                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(getContext(),"Cannot connect to Internet...Please check your connection!" , Toast.LENGTH_LONG).show();

                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(getContext(),"Connection TimeOut! Please check your internet connection." , Toast.LENGTH_LONG).show();

                        message = "Connection TimeOut! Please check your internet connection.";
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsObjRequest);
        }

    }
    public static class StoreFragment extends Fragment {
        private List<PickStoreModel> deliveryaddress = new ArrayList<>();
        RecyclerView rv_storelist;
        List<DeliveryAddressModel> deliveryAddressModelList;
        @SuppressLint("MissingInflatedId")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Initialize view
            View view =inflater.inflate(R.layout.storedetails, container, false);
            rv_storelist=view.findViewById(R.id.rv_storelist);
            pickfromStore();

            return view;
        }

        public void pickfromStore() {
            deliveryaddress = new ArrayList<>();
            final Map<String, String> params = new HashMap<>();
            String baseUrl = ProductConfig.pickstore;
            StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Response", response.toString());
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                            deliveryaddress = new ArrayList<>();
                            JSONArray array = jsonResponse.getJSONArray("pincodeList");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonlistObject = array.getJSONObject(i);
                                PickStoreModel model = new PickStoreModel();
                                model.setId(jsonlistObject.getString("id"));
                                model.setShop_name(jsonlistObject.getString("shop_name"));
                                model.setMobile(jsonlistObject.getString("mobile"));
                                model.setEmail(jsonlistObject.getString("email"));
                                model.setAddress(jsonlistObject.getString("address"));
                                model.setPincode(jsonlistObject.getString("pincode"));
                                deliveryaddress.add(model);
                            }
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            rv_storelist.setLayoutManager(layoutManager);
                            StoredetailsAdapter productListAdapter = new StoredetailsAdapter (getContext(), deliveryaddress);
                            rv_storelist.setAdapter(productListAdapter);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            rv_storelist.setLayoutManager(gridLayoutManager);
                            rv_storelist.setHasFixedSize(true);

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
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsObjRequest);

        }

    }
    public static class StoredetailsAdapter extends RecyclerView.Adapter<StoredetailsAdapter.ViewHolder> {
        List<PickStoreModel> deliveryAddressModelList;
        private Context mContext;
        private int selectedStore = -1;
        String storeLine1,storeLine2,storeLine3,storeLine4,storeLine5,store;

        public StoredetailsAdapter(Context context, List<PickStoreModel> deliveryaddress) {
            this.mContext = context;
            this.deliveryAddressModelList = deliveryaddress;
        }

        @NonNull
        @Override
        public StoredetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storedetails_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StoredetailsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            final PickStoreModel model = deliveryAddressModelList.get(position);
            holder.shopname.setText(model.getShop_name());
            holder.mobile.setText(model.getMobile());
            holder.email.setText(model.getEmail());
            holder.address.setText(model.getAddress());
            holder.pincode.setText(model.getPincode());
            System.out.println("linee1"+model.getPincode());

            storeLine1 = model.getShop_name();
            storeLine2 = model.getMobile();
            storeLine3 = model.getEmail();
            storeLine4 = model.getAddress();
            storeLine5 = model.getPincode();
            System.out.println("linee1"+storeLine5);

            holder.text_radio.setChecked(position == selectedStore);
            holder.text_radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int previousSelectedPosition = selectedStore;
                    selectedStore = holder.getAdapterPosition();
                    notifyItemChanged(previousSelectedPosition);
                    notifyItemChanged(selectedStore);
                    StoredetailsSession.getInstance().initialize(mContext,
                            storeLine1, storeLine2, storeLine3,
                            storeLine4, storeLine5);

                    // Update the other adapter's boolean value
                    PickStoreModel selectedPickupAddress = deliveryAddressModelList.get(position);
                    ((CartActivity) mContext).address_validatmethod(selectedPickupAddress);
                    /*if (position >= 0 && position < deliveryAddressModelList.size()) {
                        // Get the selected delivery address model
                        PickStoreModel selectedAddressModel = deliveryAddressModelList.get(position);

                        // Extract necessary details from the selected address model
                        String storeId = selectedAddressModel.getId();
                        String storeLine1 = selectedAddressModel.getShop_name();
                        String storeLine2 = selectedAddressModel.getMobile();
                        String storeLine3 = selectedAddressModel.getEmail();
                        String storeLine4 = selectedAddressModel.getAddress();
                        String storeLine5 = selectedAddressModel.getPincode();

                        // Store details in StoredetailsSession
                        StoredetailsSession.getInstance().initialize(mContext,
                                storeLine1, storeLine2, storeLine3,
                                storeLine4, storeLine5);

                        int previousSelectedPosition = selectedStore;
                        selectedStore = holder.getAdapterPosition();
                        notifyItemChanged(previousSelectedPosition);
                        notifyItemChanged(selectedStore);
                        System.out.println("delivery=="+storeId);
                        ((CartActivity) mContext).address_validatemethod(selectedAddress);

                       *//* // Start CartActivity with the storeid
                        Intent intent = new Intent(mContext, CartActivity.class);
                        intent.putExtra("storeid", storeId);
                        intent.putExtra("hideCartAddress", true);
                        mContext.startActivity(intent);*//*
                    } else {
                        // Log an error or handle the case where the position is invalid
                        Log.e("YourTag", "Invalid position or empty deliveryAddressModelList");
                    }*/

                }

            });

            /*holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check if position is within bounds
                    if (position >= 0 && position < deliveryAddressModelList.size()) {
                        // Get the selected delivery address model
                        PickStoreModel selectedAddressModel = deliveryAddressModelList.get(position);

                        // Extract necessary details from the selected address model
                        String storeId = selectedAddressModel.getId();
                        String storeLine1 = selectedAddressModel.getShop_name();
                        String storeLine2 = selectedAddressModel.getMobile();
                        String storeLine3 = selectedAddressModel.getEmail();
                        String storeLine4 = selectedAddressModel.getAddress();
                        String storeLine5 = selectedAddressModel.getPincode();

                        // Store details in StoredetailsSession
                        StoredetailsSession.getInstance().initialize(mContext,
                                storeLine1, storeLine2, storeLine3,
                                storeLine4, storeLine5);

                        // Start CartActivity with the storeid
                        Intent intent = new Intent(mContext, CartActivity.class);
                        intent.putExtra("storeid", storeId);
                        intent.putExtra("hideCartAddress", true);
                        mContext.startActivity(intent);
                    } else {
                        // Log an error or handle the case where the position is invalid
                        Log.e("YourTag", "Invalid position or empty deliveryAddressModelList");
                    }
                }
            });*/

        }

        @Override
        public int getItemCount() {
            return deliveryAddressModelList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView shopname;
            TextView mobile;
            TextView email;
            TextView address;
            TextView pincode;
            RadioButton text_radio;
            CardView card;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                shopname = itemView.findViewById(R.id.shopname);
                mobile = itemView.findViewById(R.id.mobile);
                email = itemView.findViewById(R.id.email);
                address = itemView.findViewById(R.id.address);
                pincode = itemView.findViewById(R.id.pincode);
                card = itemView.findViewById(R.id.card);
                text_radio = itemView.findViewById(R.id.radiobtn);

            }
        }
    }

    public static class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MailViewHolder> {

        List<DeliveryAddressModel> deliveryAddressModelList;
        private Context mContext;
        ProgressDialog progressDialog;
        private int selectedPosition = -1;

        public AddressAdapter(Context mContext, List<DeliveryAddressModel> deliveryAddressModelList) {
            this.mContext = mContext;
            this.deliveryAddressModelList = deliveryAddressModelList;
            this.selectedPosition=0;
        }

        @NonNull
        @Override
        public AddressAdapter.MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deliveryaddress_items, parent, false);
            return new AddressAdapter.MailViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final AddressAdapter.MailViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            final DeliveryAddressModel model = deliveryAddressModelList.get(position);
            holder.name.setText(deliveryAddressModelList.get(position).getName());
            holder.address.setText(deliveryAddressModelList.get(position).getLine1()+",\n"+ deliveryAddressModelList.get(position).getLine2()+",\n"+ deliveryAddressModelList.get(position).getLine3()+",\n"+
                    deliveryAddressModelList.get(position).getLandmark()+",\n"+ deliveryAddressModelList.get(position).getLine5()
                    +"Pincode - "+deliveryAddressModelList.get(position).getPincode()+",\n"+"Mobile - "+deliveryAddressModelList.get(position).getPhone()+".");
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading.....");
           /* holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DeliveryAddressSession.getInstance().initialize(mContext,
                            deliveryAddressModelList.get(position).getLine1(),deliveryAddressModelList.get(position).getLine2(),deliveryAddressModelList.get(position).getLine3(),deliveryAddressModelList.get(position).getPincode(),deliveryAddressModelList.get(position).getLandmark(),deliveryAddressModelList.get(position).getPhone(),deliveryAddressModelList.get(position).getName(),"");

                    DeliveryAddressModel model = deliveryAddressModelList.get(position);

                   *//* Intent intent = new Intent(mContext, CartActivity.class);
                    //CartActivity.subcourcemodel = model;
                    intent.putExtra("hideStoreAddress", true);
                    mContext.startActivity(intent);*//*
                }
            });*/

            holder.text_radi.setChecked(position == selectedPosition);
            holder.text_radi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int previousSelectedPosition = selectedPosition;
                    selectedPosition = holder.getAdapterPosition();
                    notifyItemChanged(previousSelectedPosition);
                    notifyItemChanged(selectedPosition);
                    DeliveryAddressSession.getInstance().initialize(mContext,
                            deliveryAddressModelList.get(position).getLine1(),deliveryAddressModelList.get(position).getLine2(),deliveryAddressModelList.get(position).getLine3(),deliveryAddressModelList.get(position).getPincode(),deliveryAddressModelList.get(position).getLandmark(),deliveryAddressModelList.get(position).getPhone(),deliveryAddressModelList.get(position).getName(),"");

                    DeliveryAddressModel selectedAddress = deliveryAddressModelList.get(position);
                    ((CartActivity) mContext).address_validatemethod(selectedAddress);

                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you want to delete your Address");
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
                            delitemethod();

                            DeliveryAddressSession.getInstance().initialize(mContext,
                                    "" ,
                                    "", "","","","","","");
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

                private void delitemethod() {

                    final Map<String, String> params = new HashMap<>();
                    String para1 = "?user_id=" + BSession.getInstance().getUser_id(mContext);
                    String para2 = "&address_id=" + deliveryAddressModelList.get(position).getId();
                    progressDialog.show();

                    String baseUrl = ProductConfig.user_address_delete + para1 + para2;

                    StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                progressDialog.dismiss();
                                JSONObject jsonResponse = new JSONObject(response);

                                if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                                    Intent intent= new Intent(mContext,CartActivity.class);
                                    mContext.startActivity(intent);

                                    Toast.makeText(mContext, "Your adddress has been deleted", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(mContext, "Deleted failed", Toast.LENGTH_LONG).show();
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
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                    requestQueue.add(jsObjRequest);
                    jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                            220000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                }



            });

            holder.location_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditAddressActivity.class);
                    intent.putExtra("id",deliveryAddressModelList.get(position).getId());
                    intent.putExtra("etname",deliveryAddressModelList.get(position).getName());
                    intent.putExtra("etphone",deliveryAddressModelList.get(position).getPhone());
                    intent.putExtra("etline1",deliveryAddressModelList.get(position).getLine1());
                    intent.putExtra("etline2",deliveryAddressModelList.get(position).getLine2());
                    intent.putExtra("etline3",deliveryAddressModelList.get(position).getLine3());
                    intent.putExtra("etline4",deliveryAddressModelList.get(position).getLine4());
                    intent.putExtra("pincode",deliveryAddressModelList.get(position).getPincode());
                    intent.putExtra("landmark",deliveryAddressModelList.get(position).getLandmark());
                    intent.putExtra("cartaddress",true);
                    mContext.startActivity(intent);
                }

            });

        }

        @Override
        public int getItemCount() {
            return deliveryAddressModelList.size();
        }


        public class MailViewHolder extends RecyclerView.ViewHolder {

            ImageView location_edit,delete;
            TextView name,address;
            CardView card;
            RadioButton text_radi;
            String alertmessage;
            AlertDialog AAlertDialog;
            public MailViewHolder(@NonNull View itemView) {
                super(itemView);
                location_edit=itemView.findViewById(R.id.location_edit);
                name=itemView.findViewById(R.id.name);
                address=itemView.findViewById(R.id.address);
                card=itemView.findViewById(R.id.card);
                delete=itemView.findViewById(R.id.delete);
                text_radi=itemView.findViewById(R.id.radiobtn);

            }
        }
    }


}