package com.taprocycle.service.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.NoticeBoard;

import java.util.List;


public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.MailViewHolder> {

    List<NoticeBoard> noticeboardlist;
    private Context mContext;
    AlertDialog dialog;


    public NoticeBoardAdapter(Context mContext, List<NoticeBoard> noticeboardlist) {
        this.mContext = mContext;
        this.noticeboardlist = noticeboardlist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alloffers_items, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final NoticeBoard model = noticeboardlist.get(position);

        holder.offer.setText(model.getShortt());
        holder.text.setText(model.getDescription());
        String img = noticeboardlist.get(position).getBanner_image();
        Glide.with(mContext)
                .load(img)
                .placeholder(R.drawable.logo)
                .into(holder.ivMenu);
      /*  holder.ivMenu.setImageResource(model.getImgId());
        holder.tv_pTitle.setText(model.getName());
        holder.mrp.setText(model.getMrp());
        holder.price.setText(model.getPrice());
        holder.saveprice.setText("₹ 1000");
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
*/

    }

    @Override
    public int getItemCount() {
        return noticeboardlist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMenu;
        TextView tv_pTitle,mrp,price,variation,saveprice,text,offer;
        Button view,acknowldege;
        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            text         = itemView.findViewById(R.id.text);
            offer         = itemView.findViewById(R.id.offer);
            ivMenu     = itemView.findViewById(R.id.ivMenu);
         /*   ivMenu     = itemView.findViewById(R.id.ivMenu);
            tv_pTitle         = itemView.findViewById(R.id.tv_pTitle);
            mrp         = itemView.findViewById(R.id.mrp);
            price         = itemView.findViewById(R.id.price);
            variation         = itemView.findViewById(R.id.variation);
            saveprice=itemView.findViewById(R.id.saveprice);
*/
        }
    }
}
