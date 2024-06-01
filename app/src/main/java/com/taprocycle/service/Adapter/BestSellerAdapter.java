package com.taprocycle.service.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.taprocycle.service.Activity.NewProductViewActivity;
import com.taprocycle.service.Activity.SubcategoryActivity;

import com.taprocycle.service.R;
import com.taprocycle.service.test.model.OfferModel;

import java.util.List;


public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.MailViewHolder> {

    List<OfferModel> bannerlist;
    private Context mContext;
    AlertDialog dialog;


    public BestSellerAdapter(Context mContext, List<OfferModel> bannerlist) {
        this.mContext = mContext;
        this.bannerlist = bannerlist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bestseler_items, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final OfferModel model = bannerlist.get(position);

        int whats =bannerlist.get(position).getImage();
        Glide.with(mContext)
                .load(whats)
                .placeholder(R.drawable.logo)
                .into(holder.bannerimg);
        holder.title.setText(model.getWeb_title());
        String cid=model.getCategory_id();
        String scid=model.getSubcategory_id();
        String pid=model.getProduct_id();

        holder.bannerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cid.equalsIgnoreCase("")&&!(pid.equalsIgnoreCase(""))){
                    Intent intent=new Intent(mContext, NewProductViewActivity.class);
                    intent.putExtra("pid",pid);
                    intent.putExtra("qty","0");
                    mContext.startActivity(intent);
                }else if(cid.equalsIgnoreCase("")&&(scid.equalsIgnoreCase("")&&(pid.equalsIgnoreCase("")))){
                    Toast.makeText(mContext, "Items records not found", Toast.LENGTH_SHORT).show();

                }
                else{
                    Intent intent=new Intent(mContext, SubcategoryActivity.class);
                    intent.putExtra("cid",cid);
                    intent.putExtra("scid",scid);
                    mContext.startActivity(intent);
                }

            }
        });


        if(position==0){
            holder.title.setBackgroundColor(mContext.getResources().getColor(R.color.c1));

        }else if(position==1){
            holder.title.setBackgroundColor(mContext.getResources().getColor(R.color.c2));

        }else if(position==2){
            holder.title.setBackgroundColor(mContext.getResources().getColor(R.color.c3));

        }else if(position==3){
            holder.title.setBackgroundColor(mContext.getResources().getColor(R.color.c4));

        }else if(position==4){
            holder.title.setBackgroundColor(mContext.getResources().getColor(R.color.c5));

        }else if(position==5){
            holder.title.setBackgroundColor(mContext.getResources().getColor(R.color.c6));

        }
        else if(position==6){
            holder.title.setBackgroundColor(mContext.getResources().getColor(R.color.c7));

        }
        else if(position==7){
            holder.title.setBackgroundColor(mContext.getResources().getColor(R.color.c8));

        }
        else if(position==8){
            holder.title.setBackgroundColor(mContext.getResources().getColor(R.color.c9));

        }
      /*  holder.mrp.setText(model.getMrp());
        holder.price.setText(model.getPrice());
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
*/
     /*   float mRadius=3f;
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
       // drawable.setStroke(3, Color.BLACK);
        drawable.setCornerRadius(8);
        if(position==0){
            drawable.setColor(mContext.getResources().getColor(R.color.c1));

        }else if(position==1){
            drawable.setColor(mContext.getResources().getColor(R.color.c2));

        }else if(position==2){
            drawable.setColor(mContext.getResources().getColor(R.color.c3));

        }else if(position==3){
            drawable.setColor(mContext.getResources().getColor(R.color.c4));

        }else if(position==4){
            drawable.setColor(mContext.getResources().getColor(R.color.c5));

        }else if(position==5){
            drawable.setColor(mContext.getResources().getColor(R.color.c6));

        }
        drawable.setCornerRadii(new float[]{mRadius, mRadius, 0, 0, 0, 0, mRadius, mRadius});
        holder.title.setBackgroundDrawable(drawable);*/
    }

    @Override
    public int getItemCount() {
        return bannerlist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        ImageView bannerimg;
        TextView title,mrp,price;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);


            bannerimg     = itemView.findViewById(R.id.image);
            title         = itemView.findViewById(R.id.title);
           /* mrp         = itemView.findViewById(R.id.mrp);
            price         = itemView.findViewById(R.id.price);
*/
        }
    }
}

