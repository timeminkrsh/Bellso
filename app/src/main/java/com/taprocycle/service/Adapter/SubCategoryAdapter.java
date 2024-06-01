package com.taprocycle.service.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.ProductModel;

import java.util.List;


public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MailViewHolder> {

    List<ProductModel> bannerlist;
    List<ProductModel> categoryModelList;
    private Context mContext;
    AlertDialog dialog;


    public SubCategoryAdapter(Context mContext, List<ProductModel> bannerlist) {
        this.mContext = mContext;
        this.bannerlist = bannerlist;
    }

   /* public SubCategoryAdapter(MainActivity mContext, int layout_categorys_home, List<CategoryModel> categoryModelList) {
        this.mContext = mContext;
        this.bannerlist = categoryModelList;
    }*/

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_access_business, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final ProductModel model = bannerlist.get(position);
    String img=bannerlist.get(position).getImage();
        holder.title.setText(model.getName());
        Glide.with(mContext)
                .load(img)
                .placeholder(R.drawable.logo)
                .into(holder.bannerimg);

        holder.bannerimg.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }

    @Override
    public int getItemCount() {
        return bannerlist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        ImageView bannerimg;
        TextView title;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);


            bannerimg     = itemView.findViewById(R.id.image);
            title         = itemView.findViewById(R.id.txt);
        }
    }
}
