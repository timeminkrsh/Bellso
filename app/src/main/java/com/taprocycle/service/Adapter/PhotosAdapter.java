package com.taprocycle.service.Adapter;

import android.app.ProgressDialog;
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

import com.taprocycle.service.test.model.BannerModel;
import com.taprocycle.service.test.model.EventModel;

import java.util.List;


public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MailViewHolder> {

    List<BannerModel> eventModelList;
    List<EventModel> photoslist;

    private Context mContext;
    AlertDialog dialog;
    ProgressDialog progressDialog;

    public PhotosAdapter(Context mContext, List<EventModel> photoslist) {
        this.mContext = mContext;
      //  this.eventModelList = eventModelList;
        this.photoslist = photoslist;

    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleproductview_items, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
       final EventModel model = photoslist.get(position);

        String img = photoslist.get(position).getMultipleimages();
        Glide.with(mContext)
                .load(img)
                .placeholder(R.drawable.logo)
                .into(holder.view);





    }

    @Override
    public int getItemCount() {
        return photoslist.size();
    }


    public static class MailViewHolder extends RecyclerView.ViewHolder {

        ImageView view;
        TextView event_title;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.img);
           // event_title=itemView.findViewById(R.id.event_title);

        }
    }
}

