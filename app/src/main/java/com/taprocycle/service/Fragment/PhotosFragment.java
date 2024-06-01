package com.taprocycle.service.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.taprocycle.service.R;

import com.taprocycle.service.test.model.EventModel;
import com.taprocycle.service.test.model.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    RecyclerView rv_photoes;
    private List<EventModel> photoslist = new ArrayList<>();
    GifImageView gif;

    public PhotosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotosFragment newInstance(String param1, String param2) {
        PhotosFragment fragment = new PhotosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_photos, container, false);
        rv_photoes=view.findViewById(R.id.rv_photoes);
        gif=view.findViewById(R.id.gif);
        photos();
        return view;
    }

    public void photos() {
        photoslist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str1 = "?type=" +"1";
        String baseUrl   = ProductConfig.events + para_str1;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        photoslist = new ArrayList<>();
                        JSONArray array = jsonResponse.getJSONArray("storeList");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            EventModel model = new EventModel();
                            model.setTitle(array.getJSONObject(i).getString("title"));
                            model.setImage(array.getJSONObject(i).getString("image"));
                            model.setLocation(array.getJSONObject(i).getString("location"));
                            model.setHall(array.getJSONObject(i).getString("hall"));
                            model.setDate(array.getJSONObject(i).getString("date"));

                            JSONArray array1 = product.getJSONArray("images");
                            model.setImage(array1.getJSONObject(i).getString("images"));

                          //  photoslist.add(model);


                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv_photoes.setLayoutManager(layoutManager);
                    //    PhotosAdapter productListAdapter = new PhotosAdapter(getContext(), photoslist);
                     //   rv_photoes.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                        rv_photoes.setLayoutManager(gridLayoutManager);
                        rv_photoes.setHasFixedSize(true);
                        gif.setVisibility(View.GONE);

                    } else {
                        gif.setVisibility(View.GONE);

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
/*
    public void photos(){

        photoslist = new ArrayList<>();
        ProductModel best = new ProductModel("Bag", "₹ 150", "₹ 1500", "₹ 2500",R.drawable.bag1);photoslist.add(best);
        ProductModel bestt = new ProductModel("Wallet", "₹ 150", "₹ 1500", "₹ 2500",R.drawable.wallet);photoslist.add(bestt);
        ProductModel besttt = new ProductModel("Bag", "₹ 150", "₹ 1500", "₹ 2500",R.drawable.bagc3);photoslist.add(besttt);
        ProductModel bestttt = new ProductModel("Mobile", "₹ 150", "₹ 1500", "₹ 2500",R.drawable.mobile);photoslist.add(bestttt);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);rv_photoes.setLayoutManager(linearLayoutManager1);
        PhotosAdapter bestSaleAdapter = new PhotosAdapter(getActivity(), photoslist);
        rv_photoes.setAdapter(bestSaleAdapter);
        rv_photoes.setLayoutManager(new GridLayoutManager(getContext(), 2));

        final LayoutAnimationController controller1 = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation);rv_photoes.setLayoutAnimation(controller1);

    }
*/
}