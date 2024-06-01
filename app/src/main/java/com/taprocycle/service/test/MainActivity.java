package com.taprocycle.service.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.taprocycle.service.Adapter.RecentlyAdapter;
import com.taprocycle.service.R;

import com.taprocycle.service.test.model.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }
}*/
public class MainActivity extends AppCompatActivity {

    // creating a variable for our array list, adapter class,
    // recycler view, progressbar, nested scroll view
    private ArrayList<UserModal> userModalArrayList;
    private UserRVAdapter userRVAdapter;
    private RecyclerView userRV;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;
    private List<ProductsModel> recentlyaddedwiselist = new ArrayList<>();

    // creating a variable for our page and limit as 2
    // as our api is having highest limit as 2 so
    // we are setting a limit = 2
    int page1 = 0, limit = 2,count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // creating a new array list.
        userModalArrayList = new ArrayList<>();

        // initializing our views.
        userRV = findViewById(R.id.idRVUsers);
        loadingPB = findViewById(R.id.idPBLoading);
        nestedSV = findViewById(R.id.idNestedSV);

        // calling a method to load our api.
       // getDataFromAPI(page, limit);
        getDataFromAPI1(page1,limit);
        System.out.println("url+page--"+page1);

        // adding on scroll change listener method for our nested scroll view.
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    page1++;

                    loadingPB.setVisibility(View.VISIBLE);
                   // getDataFromAPI(page, limit);
                    getDataFromAPI1(page1, limit);
                    System.out.println("url+page++"+page1);

                }
            }
        });
    }

    private void getDataFromAPI(int page, int limit) {
        if (page > limit) {
            // checking if the page number is greater than limit.
            // displaying toast message in this case when page>limit.
            Toast.makeText(this, "That's all the data..", Toast.LENGTH_SHORT).show();

            // hiding our progress bar.
            loadingPB.setVisibility(View.GONE);
            return;
        }
        // creating a string variable for url .
        String url = "https://reqres.in/api/users?page=" + page;
        System.out.println("url+page"+page);


        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // creating a variable for our json object request and passing our url to it.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // on below line we are extracting data from our json array.
                    JSONArray dataArray = response.getJSONArray("data");

                    // passing data from our json array in our array list.
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject = dataArray.getJSONObject(i);

                        // on below line we are extracting data from our json object.
                        userModalArrayList.add(new UserModal(jsonObject.getString("first_name"), jsonObject.getString("last_name"), jsonObject.getString("email"), jsonObject.getString("avatar")));

                        // passing array list to our adapter class.
                        userRVAdapter = new UserRVAdapter(userModalArrayList, MainActivity.this);

                        // setting layout manager to our recycler view.
                        userRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                        // setting adapter to our recycler view.
                        userRV.setAdapter(userRVAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling on error listener method.
                Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        // calling a request queue method
        // and passing our json object
        queue.add(jsonObjectRequest);
    }

    private void getDataFromAPI1(int page, int limit) {
        if (page > limit) {
            // checking if the page number is greater than limit.
            // displaying toast message in this case when page>limit.
            Toast.makeText(this, "That's all the data..", Toast.LENGTH_SHORT).show();

            // hiding our progress bar.
            loadingPB.setVisibility(View.GONE);
            page1=page;
            return;
        }
        page1=page;
        if(page==1){
            page=10;
        }else if(page==2){
            page=20;
        }
        // creating a string variable for url .
        String url = "https://manufacturing360.in/beta1/mobile/api/productlist.php?neworder=desc&limit=" + page;
        System.out.println("url+page"+page);


        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // creating a variable for our json object request and passing our url to it.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject jsonResponse = new JSONObject(response);

                    if (response.has("result") && response.getString("result").equals("Success")) {
                        recentlyaddedwiselist = new ArrayList<>();
                        JSONArray array = response.getJSONArray("storeList");

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
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            JSONArray array1 = product.getJSONArray("image");
                            //model.setImage(array1.getJSONObject(0).getString("image"));
                            recentlyaddedwiselist.add(model);
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                        userRV.setLayoutManager(layoutManager);
                        RecentlyAdapter productListAdapter = new RecentlyAdapter(MainActivity.this, recentlyaddedwiselist);
                        userRV.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                        userRV.setLayoutManager(gridLayoutManager);
                        userRV.setHasFixedSize(true);
                       // gif.setVisibility(View.GONE);


                    } else {
                        // Toast.makeText(getContext(), "No Product Result Found", Toast.LENGTH_SHORT).show();
                    }
             /*       if (response.has("result") && response.getString("result").equals("Success")) {
                        JSONArray dataArray = response.getJSONArray("storeList");
                        // on below line we are extracting data from our json array.
                       // JSONArray dataArray = response.getJSONArray("data");

                        // passing data from our json array in our array list.
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject jsonObject = dataArray.getJSONObject(i);

                            // on below line we are extracting data from our json object.
                            userModalArrayList.add(new UserModal(jsonObject.getString("productname"), jsonObject.getString("productname"), jsonObject.getString("productname"), jsonObject.getString("image")));

                            // passing array list to our adapter class.
                            userRVAdapter = new UserRVAdapter(userModalArrayList, MainActivity.this);

                            // setting layout manager to our recycler view.
                            userRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                            // setting adapter to our recycler view.
                            userRV.setAdapter(userRVAdapter);
                        }
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling on error listener method.
                Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        // calling a request queue method
        // and passing our json object
        queue.add(jsonObjectRequest);
    }
/*
    public void recently() {

        recentlyaddedwiselist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str3 = "?neworder=" + "desc";
        String para_str5 = "&limit=" + "100";

        String baseUrl   = ProductConfig.productlist + para_str3+para_str5;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        recentlyaddedwiselist = new ArrayList<>();
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
                            model.setDiscount_price(array.getJSONObject(i).getString("discount_price"));
                            JSONArray array1 = product.getJSONArray("image");
                            model.setImage(array1.getJSONObject(0).getString("image"));
                            recentlyaddedwiselist.add(model);
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(com.timeminsolutions.accsysindia.MainActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_products.setLayoutManager(layoutManager);
                        RecentlyAdapter productListAdapter = new RecentlyAdapter(com.timeminsolutions.accsysindia.MainActivity.this, recentlyaddedwiselist);
                        rv_products.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(com.timeminsolutions.accsysindia.MainActivity.this, 2);
                        rv_products.setLayoutManager(gridLayoutManager);
                        rv_products.setHasFixedSize(true);
                        gif.setVisibility(View.GONE);


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
        RequestQueue requestQueue = Volley.newRequestQueue(com.timeminsolutions.accsysindia.MainActivity.this);
        requestQueue.add(jsObjRequest);
    }
*/
}