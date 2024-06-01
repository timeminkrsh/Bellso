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

import com.bumptech.glide.Glide;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.OrderModel;

import java.util.List;


public class OrderdetailAdapter extends RecyclerView.Adapter<OrderdetailAdapter.MailViewHolder> {

    List<OrderModel> orderdetaillist;
    private Context mContext;
    AlertDialog dialog;


    public OrderdetailAdapter(Context mContext, List<OrderModel> orderdetaillist) {
        this.mContext = mContext;
        this.orderdetaillist = orderdetaillist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem_list, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final OrderModel model = orderdetaillist.get(position);

        String img = orderdetaillist.get(position).getImage();
        Glide.with(mContext)
                .load(img)
                .placeholder(R.drawable.logo)
                .into(holder.ivMenu_image);
        holder.tv_pTitle.setText(model.getProduct_name());
        holder.tv_mrp.setText("MRP ₹"+model.getProduct_price());
        holder.tv_pPrice1.setText("₹"+model.getDiscount_price());

        holder.tv_mrp.setPaintFlags(holder.tv_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


    }

    @Override
    public int getItemCount() {
        return orderdetaillist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        TextView tv_pTitle ,tv_pPrice1,tv_mrp;
        ImageView ivMenu_image;
        TextView p_name,varient,mrp,price,pcp,quantity;
        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMenu_image=itemView.findViewById(R.id.ivMenu_image);
            tv_pTitle = itemView.findViewById(R.id.tv_pTitle);
            tv_pPrice1 =itemView.findViewById(R.id.tv_pPrice1);
            tv_mrp =itemView.findViewById(R.id.tv_mrp);


        }
    }
}

