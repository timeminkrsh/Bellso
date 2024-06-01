package com.taprocycle.service;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SampleFragment extends Fragment {
    // Initialize variable
    TextView text_view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view =inflater.inflate(R.layout.fragment_sample, container, false);

        // Assign variable
        text_view=view.findViewById(R.id.text_view);

        // Get Title
        String sTitle=getArguments().getString("title");

        // Set title on text view
        text_view.setText(sTitle);

        // return view
        return view;
    }

    String pid="";
    ProgressDialog progressDialog;

    private void loadslider() {
        final Map<String, String> params = new HashMap<>();
        String para="?pid="+pid;
        String baseUrl = ProductConfig.productlist+para;

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
                            /*String plains = Html.fromHtml(jsonObject.getString("short_description")).toString();
                            text_view2.setText(plains);*/
                            model.setDescription(jsonResarray.getJSONObject(i).getString("description"));
                            String plain = Html.fromHtml(jsonObject.getString("description")).toString();
                            text_view.setText(plain+" ");

                            //System.out.println("txt1=="+plains);
                            System.out.println("txt1=="+plain);
                            System.out.println("txt2=="+model.getDescription());
                            if(jsonObject.getString("ratting").equalsIgnoreCase("")||jsonObject.getString("ratting").equalsIgnoreCase(null)){

                            }
                            String type= BSession.getInstance().getType(getContext());
                            System.out.println("type!!!!"+type);


                        }

                    } else {
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

