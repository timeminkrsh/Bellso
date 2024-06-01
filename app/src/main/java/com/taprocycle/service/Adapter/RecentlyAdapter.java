package com.taprocycle.service.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
//import com.google.android.material.timepicker.ChipTextInputComboView;
import com.taprocycle.service.Activity.NewProductViewActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecentlyAdapter extends RecyclerView.Adapter<RecentlyAdapter.MailViewHolder> {

    List<ProductsModel> bannerlist;
    private Context mContext;
    AlertDialog dialog;
    String pid;

    public RecentlyAdapter(Context mContext, List<ProductsModel> bannerlist) {
        this.mContext = mContext;
        this.bannerlist = bannerlist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlist, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final ProductsModel model = bannerlist.get(position);
        String img = bannerlist.get(position).getImage();
        Glide.with(mContext)
                .load(img)
                .placeholder(R.drawable.logo)
                .into(holder.product_img);
        holder.title.setText(model.getProductname());
        pid=bannerlist.get(position).getPid();
            holder.saveprice.setVisibility(View.GONE);
            holder.rel_offer.setVisibility(View.VISIBLE);
            System.out.println("moedl=="+model.getPrice());
            double a= Double.parseDouble(model.getPrice());
            double b= Double.parseDouble(model.getDiscount_price());
            double c=a-b;
            int resul =(int) Math.abs(c);
            holder.saveprice.setText("You Save ₹"+String.valueOf(resul));
            System.out.println("offer=="+resul);
            double offer=c*100/a;
            int result = (int) Math.ceil(offer);
            holder.offer_text.setText(String.valueOf(result)+" % OFF");
            holder.mrp.setText("MRP ₹ "+model.getPrice());
            holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.price.setText(" ₹ "+model.getDiscount_price());

        if(model.getStock().equalsIgnoreCase("0")||model.getStock().equalsIgnoreCase("")){
            holder.outstock.setVisibility(View.VISIBLE);
        }
       // holder.description.setText(Jsoup.parse(model.getShort_description()).text());
        if(model.getRatting().equalsIgnoreCase("")||model.getRatting().equalsIgnoreCase(null)){

        }else {
            //holder.ratingBar.setRating(Float.parseFloat(model.getRatting()));
           // holder.rattingcount.setText(model.getRatting() + ".0");
        }

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    //Case 1
                    String customer_id = BSession.getInstance().getUser_id(mContext);
                    final Map<String, String> params = new HashMap<>();
                    String para1="?user_id="+customer_id;
                    String para2="&product_id="+bannerlist.get(position).getPid();
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
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                    requestQueue.add(jsObjRequest);
                    jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                            10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));                    ((CheckBox) v).setButtonDrawable(R.drawable.baseline_favorite_24);
                    Toast.makeText(mContext, "Item added to wishlist", Toast.LENGTH_SHORT).show();
                }
                else{
                    ((CheckBox) v).setButtonDrawable(R.drawable.baseline_favorite_border_24);
                    Toast.makeText(mContext, "Item removed from wishlist", Toast.LENGTH_SHORT).show();
                }
            }

        });

        holder.product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, NewProductViewActivity.class);
                intent.putExtra("pid",bannerlist.get(position).getPid());
                intent.putExtra("scid",bannerlist.get(position).getScid());
                intent.putExtra("price",bannerlist.get(position).getPrice());
                intent.putExtra("discount",bannerlist.get(position).getDiscount_price());
                intent.putExtra("qty",bannerlist.get(position).getQty());
                intent.putExtra("stock",bannerlist.get(position).getStock());
                intent.putExtra("exclusive",bannerlist.get(position).getExclusive_product());
                System.out.println("exclusive=="+bannerlist.get(position).getExclusive_product());
                mContext.startActivity(intent);

            }
        });

    }





    @Override
    public int getItemCount() {
        return bannerlist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkbox;
        ImageView product_img;
        TextView offer_text,title,mrp,price,saveprice,description,rattingcount;
        RatingBar ratingBar;
        TextView outstock;
        RelativeLayout rel_offer;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);


            product_img   = itemView.findViewById(R.id.product_img);
            title         = itemView.findViewById(R.id.product_name);
            checkbox         = itemView.findViewById(R.id.checkbox);
            mrp           = itemView.findViewById(R.id.mrp);
            price         = itemView.findViewById(R.id.price);
            saveprice     = itemView.findViewById(R.id.saveprice);
            description   = itemView.findViewById(R.id.description);
            ratingBar     = itemView.findViewById(R.id.ratting);
            rattingcount  = itemView.findViewById(R.id.rattingcount);
            rel_offer     = itemView.findViewById(R.id.rel_offer);
            offer_text    = itemView.findViewById(R.id.offer_text);
            outstock    = itemView.findViewById(R.id.outstock);
        }
    }
}