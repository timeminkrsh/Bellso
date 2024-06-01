package com.taprocycle.service.Adapter;

import android.content.Context;
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
import com.taprocycle.service.test.model.OfferModel;

import java.util.List;


public class AllOffesAdapter extends RecyclerView.Adapter<AllOffesAdapter.MailViewHolder> {

    List<OfferModel> bannerlist;
    private Context mContext;
    AlertDialog dialog;


    public AllOffesAdapter(Context mContext, List<OfferModel> bannerlist) {
        this.mContext = mContext;
        this.bannerlist = bannerlist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alloffers_items, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final OfferModel model = bannerlist.get(position);


        holder.offer.setText(model.getWeb_title());
        holder.text.setText(model.getWeb_title());
        Glide.with(mContext)
                .load(model.getImage())
                .placeholder(R.drawable.logo)
                .into(holder.ivMenu);

    }

    @Override
    public int getItemCount() {
        return bannerlist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMenu;
        TextView offer,text;
        public MailViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMenu     = itemView.findViewById(R.id.ivMenu);
            offer         = itemView.findViewById(R.id.offer);
            text         = itemView.findViewById(R.id.text);


        }
    }
}

