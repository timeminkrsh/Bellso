package com.taprocycle.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.taprocycle.service.Activity.CartActivity;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.CategoryModel;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SportsViewActivity extends AppCompatActivity {
    public static CategoryModel subcourcemodel = new CategoryModel();
    TuitionViewPagerAdapter adapter;
    TextView badge_notification;
    String badge_notification1 = "";
    String baseUrl;
    String customer_id = null;
    String deviceid;
    String ipaddress;
    /* access modifiers changed from: private */
    public final List<Fragment> mFragmentList = new ArrayList();
    /* access modifiers changed from: private */
    public final List<String> mFragmentTitleList = new ArrayList();
    /* access modifiers changed from: private */
    public ArrayList<String> mFragmentscidList = new ArrayList<>();
    private ArrayList<String> mFragmentcidList = new ArrayList<>();
    String para_str;
    ProgressDialog progressDialog;
    /* access modifiers changed from: private */
    public TabLayout tabLayout;
    /* access modifiers changed from: private */
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_view);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        this.deviceid = Settings.Secure.getString(getContentResolver(), "android_id");
        toolbar();
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager2 = (ViewPager) findViewById(R.id.viewpager);
        this.viewPager = viewPager2;
        this.tabLayout.setupWithViewPager(viewPager2);
        for (int i = 0; i < this.tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) this.tabLayout.getChildAt(0)).getChildAt(i);
            ((ViewGroup.MarginLayoutParams) tab.getLayoutParams()).setMargins(0, 0, 50, 0);
            tab.requestLayout();
        }
        getSubcategoryList();
    }


    /* access modifiers changed from: package-private */
    public String GetDeviceipMobileData() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = en.nextElement().getInetAddresses();
                while (true) {
                    if (enumIpAddr.hasMoreElements()) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            }
            return null;
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
            return null;
        }
    }

    public String GetDeviceipWiFiData() {
        return Formatter.formatIpAddress(((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getIpAddress());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(menuItem);
        this.badge_notification = (TextView) actionView.findViewById(R.id.badge_notification);
        getCartCount();
        actionView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SportsViewActivity.this.onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.action_cart) {
            return super.onOptionsItemSelected(item);
        }
        startActivity(new Intent(this, CartActivity.class));
        return true;
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SportsViewActivity.this.startActivity(new Intent(SportsViewActivity.this, MainActivity.class));
            }
        });
        getSupportActionBar().setTitle((CharSequence) subcourcemodel.getCategory_name());
        toolbar.setTitleTextColor(-1);
    }

    private void getSubcategoryList() {
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.subcategorylist + ("?cid=" + subcourcemodel.getCategory_id()), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    SportsViewActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(SportsViewActivity.this.getApplicationContext(), "No SubCategory Result Found", Toast.LENGTH_LONG).show();
                        return;
                    }
                    JSONArray array = jsonResponse.getJSONArray("storeList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jSONObject = array.getJSONObject(i);
                        ProductsModel model = new ProductsModel();
                        model.setCid(array.getJSONObject(i).getString("cid"));
                        model.setScid(array.getJSONObject(i).getString("scid"));
                        model.setSubcategoryname(array.getJSONObject(i).getString("subcategoryname"));
                        SportsViewActivity.this.mFragmentTitleList.add(array.getJSONObject(i).getString("subcategoryname"));
                        SportsViewActivity.this.mFragmentscidList.add(array.getJSONObject(i).getString("scid"));
                        Log.d("FragmentTitle", String.valueOf(SportsViewActivity.this.mFragmentTitleList));
                    }
                    for (int i2 = 0; i2 < SportsViewActivity.this.mFragmentTitleList.size(); i2++) {
                        SportsViewActivity.this.mFragmentList.add(new Fragment());
                    }
                    SportsViewActivity.this.setupViewPager(SportsViewActivity.this.viewPager);
                    SportsViewActivity.this.tabLayout.setupWithViewPager(SportsViewActivity.this.viewPager);
                    SportsViewActivity.this.viewPager.setOffscreenPageLimit(SportsViewActivity.this.mFragmentList.size());
                    SportsViewActivity.this.tabLayout.setupWithViewPager(SportsViewActivity.this.viewPager);
                } catch (JSONException e) {
                    e.printStackTrace();
                    SportsViewActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                SportsViewActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    /* access modifiers changed from: private */
    public void setupViewPager(ViewPager viewPager2) {
        TuitionViewPagerAdapter tuitionViewPagerAdapter = new TuitionViewPagerAdapter(getSupportFragmentManager(), this.mFragmentList, this.mFragmentTitleList, this.mFragmentscidList, mFragmentcidList);
        this.adapter = tuitionViewPagerAdapter;
        viewPager2.setAdapter(tuitionViewPagerAdapter);
    }

    public void getCartCount() {
        final Map<String, String> params = new HashMap<>();
        String user_id = BSession.getInstance().getUser_id(this);
        this.customer_id = user_id;
        if (user_id.equalsIgnoreCase("")) {
            this.para_str = "?ip_address=" + this.deviceid;
            this.baseUrl = ProductConfig.cartlist + this.para_str;
        } else {
            this.para_str = "?user_id=" + this.customer_id;
            this.baseUrl = ProductConfig.cartlist + this.para_str;
        }
        Volley.newRequestQueue(this).add(new StringRequest(0, this.baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        SportsViewActivity.this.badge_notification.setText("0");
                    } else {
                        SportsViewActivity.this.badge_notification.setText(jsonResponse.getString("count"));
                    }
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
        });
    }
}