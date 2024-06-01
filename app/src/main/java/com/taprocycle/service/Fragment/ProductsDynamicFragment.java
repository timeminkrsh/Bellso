package com.taprocycle.service.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taprocycle.service.Activity.SubcategoryActivity;
import com.taprocycle.service.Adapter.ProductsAdapter;
import com.taprocycle.service.Adapter.RelatedAdapter;
import com.taprocycle.service.R;
import com.taprocycle.service.Util.NetworkUtil;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsDynamicFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView rv_menuList;
    SwipeRefreshLayout swipeRefreshLayout;
    List<ProductsModel> productsModelList = new ArrayList<>();
    ProgressDialog progressDialog;
    String user_id = "", scid = "",cid="",first="",second="",pricefrom="",priceto="";
    GridView gv_menuList;
    LinearLayout sort,lin_filter;
    private List<ProductsModel> productwiselist = new ArrayList<>();
    RelatedAdapter productListAdapter;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;
    int page = 0, limit = 2;

    public ProductsDynamicFragment() {
    }

    int position;
    private TextView textView;


    public static Fragment getInstance(int position, String scid,String cid) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        bundle.putString("scid", scid);
        bundle.putString("cid", cid);
        ProductsDynamicFragment tabFragment = new ProductsDynamicFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
        scid = getArguments().getString("scid");
        cid = getArguments().getString("cid");

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tution_myclass_dynamic, container, false);

        /////////NETWORK CONDITION CHECK///////////

        String status = NetworkUtil.getConnectivityStatusString(getContext());
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(getContext());
            Toast.makeText(getContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        rv_menuList = view.findViewById(R.id.rv_menuList);
        sort=view.findViewById(R.id.sort);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        lin_filter=view.findViewById(R.id.lin_filter);



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
        return view;
    }

    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "Refreshed!", Toast.LENGTH_SHORT).show();
                loadProductList();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
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
                    second="";
                    loadProductList();
                    bottomSheetDialog.dismiss();
                } else if (isChecked1) {
                    first = "asc";
                    System.out.println("---first=="+first);
                    loadProductList();
                    bottomSheetDialog.dismiss();
                }else if (isChecked2) {
                    first = "dsc";
                    loadProductList();
                    bottomSheetDialog.dismiss();
                }else if (isChecked3) {
                    first = "asc";
                    second="asc";
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
    }


    private void showBottomSheetDialog1() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
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
                    pricefrom = "";
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
                RadioButton two_two=radioGroup4.findViewById(R.id.two_two);
                RadioButton two_above=radioGroup4.findViewById(R.id.two_above);

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
    }


    public void loadProductList() {

        productwiselist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str  = "?cid="+cid;
        String para_str1 = "&scid=" + scid;
        //String para_str2 = "&priceorder=" + first;
       // String para_str3 = "&neworder=" + second;
        //String para_str4 = "&pricefrom=" + pricefrom;
        //String para_str5 = "&priceto=" + priceto;
        String para_str6 = "&user_id=" + BSession.getInstance().getUser_id(getContext());
       // String para_str7 = "&orderlist=" +"1";

        System.out.println("priceorder="+first);
        System.out.println("neworder="+second);
        System.out.println("pricefrom="+pricefrom);
        System.out.println("priceto="+priceto);

        String baseUrl   = ProductConfig.productlist +para_str+ para_str1+para_str6;
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
                            JSONArray array1 = product.getJSONArray("image");
                            model.setImage(array1.getJSONObject(0).getString("image"));
                            productwiselist.add(model);


                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv_menuList.setLayoutManager(layoutManager);
                        productListAdapter = new RelatedAdapter(getContext(), productwiselist);
                        rv_menuList.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsObjRequest);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        loadProductList();
    }
}