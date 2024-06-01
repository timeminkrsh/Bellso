package com.taprocycle.service.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taprocycle.service.Activity.NewProductViewActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.OrderModel;

import java.util.List;

public class MyOrderDetailsAdapter extends RecyclerView.Adapter<MyOrderDetailsAdapter.MailViewHolder>{


    private Context mContext;
    List<OrderModel> myListData;
    String pid = "", customer_id = "", editqut, type;
    Integer i;
    String name, price, qty = "", mrp, wid,stackcoit;
    ArrayAdapter<String> arrayAdapter;
    String[] SPINNERLIST = {"250 g", "500 g", "1 kg", "5 kg"};
    private BottomSheetDialog mBottomSheetDialog;
    String[] courses = { "500 g", "1 kg",
            "3 kg", "5 kg"
    };

    public MyOrderDetailsAdapter(Context mContext, List<OrderModel> myListData) {
        this.mContext = mContext;
        this.myListData = (myListData);

    }




    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem_list, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final OrderModel model = myListData.get(position);


        String img = myListData.get(position).getImage();
        Glide.with(mContext)
                .load(img)
                .placeholder(R.drawable.logo)
                .into(holder.ivMenu_image);
        holder.tv_pTitle.setText(model.getProduct_name());
        holder.tv_mrp.setText("MRP ₹"+model.getProduct_price());
        holder.tv_pPrice1.setText("₹"+model.getDiscount_price());
        holder.tv_size.setText("Size : "+model.getColor());
        holder.tv_colour.setText("Colour : "+model.getColor_name());
        if (model.getColor_name().isEmpty()){
            holder.tv_colour.setVisibility(View.GONE);
        }

        holder.tv_mrp.setPaintFlags(holder.tv_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_pTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getProduct_id().isEmpty()){
                    Toast.makeText(mContext, "No Product found", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(mContext, NewProductViewActivity.class);
                    intent.putExtra("pid",myListData.get(position).getProduct_id());
                    intent.putExtra("exclusive",myListData.get(position).getExclusive_product());
                    System.out.println("pid=="+myListData.get(position).getProduct_id());
                    mContext.startActivity(intent);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return myListData.size();
    }

    public class MailViewHolder extends RecyclerView.ViewHolder {

        TextView tv_pTitle ,tv_pPrice1,tv_mrp,tv_size,tv_colour;
        ImageView ivMenu_image;
        CardView od_products;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMenu_image=itemView.findViewById(R.id.ivMenu_image);
            tv_pTitle = itemView.findViewById(R.id.tv_pTitle);
            tv_pPrice1 =itemView.findViewById(R.id.tv_pPrice1);
            tv_mrp =itemView.findViewById(R.id.tv_mrp);
            tv_size =itemView.findViewById(R.id.tv_size);
            tv_colour =itemView.findViewById(R.id.tv_colour);
            od_products =itemView.findViewById(R.id.od_products);
        }
    }
}
