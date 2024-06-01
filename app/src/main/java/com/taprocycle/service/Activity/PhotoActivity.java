package com.taprocycle.service.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taprocycle.service.R;
import com.taprocycle.service.Util.NetworkUtil;
import com.taprocycle.service.test.model.BannerModel;
import com.taprocycle.service.test.model.EventModel;
import com.taprocycle.service.test.model.ProductConfig;

public class PhotoActivity extends AppCompatActivity {
    private List<EventModel> photoslist = new ArrayList<>();
    RecyclerView rv_events;
    ImageView image;
    String resId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        /////////NETWORK CONDITION CHECK///////////

        String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equalsIgnoreCase("No internet is available")) {
            NetworkUtil.Show(PhotoActivity.this);
            Toast.makeText(getApplicationContext(), "state: " + status, Toast.LENGTH_LONG).show();
        }else{

        }
        rv_events = findViewById(R.id.rv_events);
        image = findViewById(R.id.images);
        String imagevalue ="" ;

       photoes();
        toolbar();
        Bundle bundle = getIntent().getExtras();
       // if (bundle != null) {
            // int images = (extras.getInt("images"));
          //  resId = bundle.getString("id");
            if (bundle != null) {
                imagevalue = bundle.getString("image");

               /* Glide.with(PhotoActivity.this)
                        .load(imagevalue)
                        .placeholder(R.drawable.logo)
                        .override(500, 500)
                        .into(image);
*/


            }
            else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();

            }
            System.out.println("images111"+imagevalue);
        }




    private void toolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setTitle("Photo");
        toolbar.setTitleTextColor(Color.WHITE);
    }


    private void photoes() {
        photoslist = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        String para_str1 = "?type=" + "1";
       // String para_str2 = "&event_id=" + resId;
        System.out.println("eventimageid"+resId);

        String baseUrl = ProductConfig.events + para_str1 ;
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
                            model.setMultipleimages(product.getString("images"));

                             JSONArray jsonResarray2 = product.getJSONArray("images");

                       for (int k = 0; k < jsonResarray2.length(); k++) {
                            JSONObject jsonObjects = jsonResarray2.getJSONObject(k);
                            EventModel models = new EventModel();
                            model.setMultipleimages(jsonObjects.getString("images"));
                          //  photoslist.add(models);
                           photoslist.add(model);

                        }


                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(PhotoActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_events.setLayoutManager(layoutManager);
                        PhotosAdapter productListAdapter = new PhotosAdapter(PhotoActivity.this, photoslist);
                        rv_events.setAdapter(productListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(PhotoActivity.this, 3);
                        rv_events.setLayoutManager(gridLayoutManager);
                        rv_events.setHasFixedSize(true);

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
        RequestQueue requestQueue = Volley.newRequestQueue(PhotoActivity.this);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsObjRequest);

    }



    public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MailViewHolder> {

        List<BannerModel> eventModelList;
        List<EventModel> photoslist;

        private Context mContext;
        AlertDialog dialog;
        ProgressDialog progressDialog;

        public PhotosAdapter(Context mContext, List<EventModel> photoslist) {
            this.mContext = mContext;
            //  this.eventModelList = eventModelList;
            this.photoslist = photoslist;

        }

        @NonNull
        @Override
        public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleproductview_items, parent, false);
            return new PhotosAdapter.MailViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
            final EventModel model = photoslist.get(position);

            String img = photoslist.get(position).getMultipleimages();
            Glide.with(mContext)
                    .load(img)
                    .placeholder(R.drawable.logo)
                    .into(holder.views);

            Glide.with(mContext)
                    .load(img)
                    .placeholder(R.drawable.logo)
                    .into(holder.views);
            String url1 = photoslist.get(0).getMultipleimages();
            Glide.with(mContext)
                    .load(url1)
                    .placeholder(R.drawable.logo)
                    .into(image);
            holder.views.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = model.getMultipleimages();
                    Glide.with(mContext)
                            .load(url)
                            .placeholder(R.drawable.logo)
                            .into(image);
                }
            });

        }

        @Override
        public int getItemCount() {
            return photoslist.size();
        }


        public class MailViewHolder extends RecyclerView.ViewHolder {

            ImageView views;
            TextView event_title;

            public MailViewHolder(@NonNull View itemView) {
                super(itemView);
                views = itemView.findViewById(R.id.img);
                // event_title=itemView.findViewById(R.id.event_title);

            }
        }
    }
}