package com.taprocycle.service.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.taprocycle.service.Activity.NewProductViewActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.ProductsModel;

import java.util.List;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MailViewHolder> {

    List<ProductsModel> bannerlist;
    private Context mContext;
    AlertDialog dialog;


    public ProductsAdapter(Context mContext, List<ProductsModel> bannerlist) {
        this.mContext = mContext;
        this.bannerlist = bannerlist;
    }

    @NonNull
    @Override
    public ProductsAdapter.MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_products, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductsAdapter.MailViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final ProductsModel model = bannerlist.get(position);
        String img = bannerlist.get(position).getImage();
        Glide.with(mContext)
                .load(img)
                .placeholder(R.drawable.logo)
                .into(holder.product_img);
        holder.title.setText(model.getWeb_title());
        holder.mrp.setText(model.getTotal());
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
        holder.product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, NewProductViewActivity.class);
                intent.putExtra("pid",bannerlist.get(position).getPid());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return bannerlist.size();
    }

    public class MailViewHolder extends RecyclerView.ViewHolder {

        ImageView product_img;
        TextView title,mrp,price,offer_text,saveprice,description,rattingcount;
        RatingBar ratingBar;
        RelativeLayout rel_offer;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);

            product_img   = itemView.findViewById(R.id.product_img);
            title         = itemView.findViewById(R.id.product_name);
            mrp           = itemView.findViewById(R.id.mrp);
            price         = itemView.findViewById(R.id.price);
            saveprice     = itemView.findViewById(R.id.saveprice);
            description   = itemView.findViewById(R.id.description);
            ratingBar     = itemView.findViewById(R.id.ratting);
            rattingcount  = itemView.findViewById(R.id.rattingcount);
            rel_offer     = itemView.findViewById(R.id.rel_offer);
            offer_text    = itemView.findViewById(R.id.offer_text);
        }
    }
}