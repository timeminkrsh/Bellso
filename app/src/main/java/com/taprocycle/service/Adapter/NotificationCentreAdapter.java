package com.taprocycle.service.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.taprocycle.service.R;

import com.taprocycle.service.test.model.ProductsModel;

import java.util.List;


public class NotificationCentreAdapter extends RecyclerView.Adapter<NotificationCentreAdapter.MailViewHolder> {

    List<ProductsModel> bannerlist;
    private Context mContext;
    AlertDialog dialog;


    public NotificationCentreAdapter(Context mContext, List<ProductsModel> bannerlist) {
        this.mContext = mContext;
        this.bannerlist = bannerlist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.norification_list, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final ProductsModel model = bannerlist.get(position);


        holder.tv_pTitle.setText(model.getTitle());
        holder.des.setText(model.getDescription());



    }

    @Override
    public int getItemCount() {
        return bannerlist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        TextView tv_pTitle, des, price, variation, saveprice;
        Button view, acknowldege;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_pTitle     = itemView.findViewById(R.id.title);
            des         = itemView.findViewById(R.id.description);

        }
    }
}