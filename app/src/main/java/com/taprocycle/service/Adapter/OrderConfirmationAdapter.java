package com.taprocycle.service.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.taprocycle.service.OrderConfirm;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.CategoryModel;
import com.taprocycle.service.test.model.ProductsModel;

import java.util.List;

public class OrderConfirmationAdapter extends RecyclerView.Adapter<OrderConfirmationAdapter.ViewHolder>{
    public Context context;
    public List<ProductsModel> papularModelList;
    public OrderConfirmationAdapter(Context context, List<ProductsModel> cartlist) {
        this.context = context;
        this.papularModelList = cartlist;
    }

    @NonNull
    @Override
    public OrderConfirmationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_confirmation, parent, false);
        return new OrderConfirmationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderConfirmationAdapter.ViewHolder holder, int position) {
        final ProductsModel model = papularModelList.get(position);
        String img = papularModelList.get(position).getImage();
        Glide.with(context)
                .load(img)
                .placeholder(R.drawable.logo)
                .into(holder.ivMenu);
        holder.tv_pTitle.setText(model.getProductname());
        holder.tv_pqyt.setText(model.getQty());
        holder.tv_color.setText(model.getColor());
        holder.tv_pwgt.setText(model.getSize());
        holder.tv_pPrice.setText(model.getPrice());

    }

    @Override
    public int getItemCount() {
        List<ProductsModel> list = this.papularModelList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMenu;
        TextView tv_pTitle;
        TextView tv_color;
        TextView tv_pPrice;
        TextView tv_pqyt,tv_pwgt;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            ivMenu   = itemView.findViewById(R.id.ivMenu);
            tv_pTitle         = itemView.findViewById(R.id.tv_pTitle);
            tv_color         = itemView.findViewById(R.id.tv_color);
            tv_pPrice           = itemView.findViewById(R.id.tv_pPrice);
            tv_pqyt         = itemView.findViewById(R.id.tv_pqyt);
            tv_pwgt     = itemView.findViewById(R.id.tv_pwgt);
        }
    }
}
