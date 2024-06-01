package com.taprocycle.service.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com.taprocycle.service.Activity.PhotoActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.BannerModel;
import com.taprocycle.service.test.model.EventModel;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MailViewHolder>  {

    List<EventModel> bannerlist;
    private Context mContext;
    AlertDialog dialog;
    private List<EventModel> photoslist = new ArrayList<>();
    private List<EventModel> videoslist = new ArrayList<>();
    private List<BannerModel> photoslists = new ArrayList<>();

  //  EventAdapter powerAdapter = new EventAdapter();

    public EventsAdapter(Context mContext, List<EventModel> bannerlist) {
        this.mContext = mContext;
        this.bannerlist = bannerlist;
       // this.photoslists = photoslists;

    }

    @NonNull
    @Override
    public EventsAdapter.MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventslist, parent, false);
        return new MailViewHolder(view);


    }
    @Override
    public void onBindViewHolder(@NonNull final EventsAdapter.MailViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final EventModel model = bannerlist.get(position);
        String img = bannerlist.get(position).getImage();
        Glide.with(mContext)
                .load(img)
                .placeholder(R.drawable.logo)
                .into(holder.bannerimage);
        holder.event_name.setText(model.getTitle());
        holder.event_location.setText(model.getLocation());
        holder.As_mahal.setText(model.getHall());
        holder.event_date.setText(model.getDate());

        String imgs = bannerlist.get(position).getMultipleimages();
        Glide.with(mContext)
                .load(imgs)
                .placeholder(R.drawable.logo)
                .into(holder.image);

       holder.photos.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(mContext, PhotoActivity.class);
               intent.putExtra("image",imgs);
               mContext.startActivity(intent);
           }
       });

        holder.videos.setOnClickListener(new View.OnClickListener() {
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

        ImageView bannerimage,image;
        TextView photos,videos,event_name,event_location,As_mahal,event_date,saveprice,description,rattingcount;
        RatingBar ratingBar;
        RelativeLayout rel_offer;
        TabLayout tabLayout;
        ViewPager viewPager;
        RecyclerView recycleview_photo,recycle_videos;
        public MailViewHolder(@NonNull View itemView) {
            super(itemView);


            bannerimage   = itemView.findViewById(R.id.bannerimage);
            event_name         = itemView.findViewById(R.id.event_name);
            event_location           = itemView.findViewById(R.id.event_location);
            As_mahal         = itemView.findViewById(R.id.As_mahal);
            event_date     = itemView.findViewById(R.id.event_date);
            photos     = itemView.findViewById(R.id.photos);
            videos     = itemView.findViewById(R.id.videos);
            recycleview_photo     = itemView.findViewById(R.id.recycleview_photo);
            recycle_videos     = itemView.findViewById(R.id.recycle_videos);
            image =itemView.findViewById(R.id.imag);




        }
    }
}