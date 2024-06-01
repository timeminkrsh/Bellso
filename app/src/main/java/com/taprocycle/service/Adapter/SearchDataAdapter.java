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

import com.taprocycle.service.Activity.SearchableProductActivity;
import com.taprocycle.service.R;

import com.taprocycle.service.test.model.SearchModel;

import java.util.List;


public class SearchDataAdapter extends RecyclerView.Adapter<SearchDataAdapter.MailViewHolder> {

    List<SearchModel> SearchModellist;
    private Context mContext;
    AlertDialog dialog;


    public SearchDataAdapter(Context mContext, List<SearchModel> SearchModellist) {
        this.mContext = mContext;
        this.SearchModellist = SearchModellist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_data_items, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final SearchModel model = SearchModellist.get(position);
        holder.title.setText(model.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, SearchableProductActivity.class);
                intent.putExtra("keyword",model.getTitle());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return SearchModellist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title,mrp,price;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);


        }
    }
}
