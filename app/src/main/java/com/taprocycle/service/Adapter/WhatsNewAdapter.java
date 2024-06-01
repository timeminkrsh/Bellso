package com.taprocycle.service.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.taprocycle.service.Activity.NewProductViewActivity;
import com.taprocycle.service.Activity.SubcategoryActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.OfferModel;

import java.util.List;


public class WhatsNewAdapter extends RecyclerView.Adapter<WhatsNewAdapter.MailViewHolder> {

    List<OfferModel> bannerlist;
    private Context mContext;
    AlertDialog dialog;


    public WhatsNewAdapter(Context mContext, List<OfferModel> bannerlist) {
        this.mContext = mContext;
        this.bannerlist = bannerlist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.whatsnew_items, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final OfferModel model = bannerlist.get(position);

        holder.title.setText(model.getWeb_title());
        int whats =bannerlist.get(position).getImage();
        Glide.with(mContext)
                .load(whats)
                .placeholder(R.drawable.logo)
                .into(holder.bannerimg);
        String cid=model.getCategory_id();
        String scid=model.getSubcategory_id();
        String pid=model.getProduct_id();

        holder.bannerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cid.equalsIgnoreCase("")){
                    Intent intent=new Intent(mContext, NewProductViewActivity.class);
                    intent.putExtra("pid",pid);
                    intent.putExtra("qty","0");
                    mContext.startActivity(intent);
                }else{
                    Intent intent=new Intent(mContext, SubcategoryActivity.class);
                    intent.putExtra("cid",cid);
                    intent.putExtra("scid",scid);

                    mContext.startActivity(intent);
                }

            }
        });
      /*  holder.mrp.setText(model.getMrp());
        holder.price.setText(model.getPrice());
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
*/

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

