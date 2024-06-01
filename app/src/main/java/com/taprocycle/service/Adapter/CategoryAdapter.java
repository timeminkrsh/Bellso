package com.taprocycle.service.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.taprocycle.service.Activity.SubcategoryActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    public Context context;
    /* access modifiers changed from: private */
    public List<CategoryModel> papularModelList;

    public CategoryAdapter(Context context, List<CategoryModel> papularModelList) {
        this.context= context;
        this.papularModelList = papularModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categorys_home, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       holder.tv_catTitle.setText((this.papularModelList.get(position).getSubcategoryname()));
       System.out.println("title=="+papularModelList.get(position).getSubcategoryname());
        String imag = papularModelList.get(position).getWeb_image();
        Glide.with(context)
                .load(imag)
                .placeholder(R.drawable.dummy)
                .into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SubcategoryActivity.class);
                intent.putExtra("cid",papularModelList.get(position).getCat_id());
                intent.putExtra("scid",papularModelList.get(position).getScid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        List<CategoryModel> list = this.papularModelList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        LinearLayout llParent;
        TextView tv_catTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = (ImageView) itemView.findViewById(R.id.kit1);
            this.tv_catTitle = (TextView) itemView.findViewById(R.id.textcat);
        }
    }
}
