package com.taprocycle.service.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.taprocycle.service.R;
import com.taprocycle.service.test.model.ProductModel;

import java.util.List;


public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.MailViewHolder> {

    List<ProductModel> bannerlist;
    private Context mContext;
    AlertDialog dialog;


    public GroceryAdapter(Context mContext, List<ProductModel> bannerlist) {
        this.mContext = mContext;
        this.bannerlist = bannerlist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_design, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final ProductModel model = bannerlist.get(position);

        holder.ivMenu.setImageResource(model.getImgId());
        holder.tv_pTitle.setText(model.getName());
        holder.mrp.setText(model.getMrp());
        holder.price.setText(model.getPrice());
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


    }

    @Override
    public int getItemCount() {
        return bannerlist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMenu;
        TextView tv_pTitle,mrp,price,saveprice;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);


            ivMenu     = itemView.findViewById(R.id.ivMenu);
            tv_pTitle         = itemView.findViewById(R.id.tv_pTitle);
            mrp         = itemView.findViewById(R.id.mrp);
            price         = itemView.findViewById(R.id.price);
            saveprice=itemView.findViewById(R.id.saveprice);

        }
    }
}

