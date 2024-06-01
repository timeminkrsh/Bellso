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
import com.taprocycle.service.test.model.RentalModel;

import java.util.List;


public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.MailViewHolder> {

    List<RentalModel> OrderModelist;
    private Context mContext;
    AlertDialog dialog;
    ProgressDialog progressDialog;

    public RentalAdapter(Context mContext, List<RentalModel> OrderModelist) {
        this.mContext = mContext;
        this.OrderModelist = OrderModelist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acknowledge, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final RentalModel model = OrderModelist.get(position);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Updating.....");
        holder.title.setText(OrderModelist.get(position).getTitile());
        holder.address.setText(OrderModelist.get(position).getAddress());
        holder.phone.setText(OrderModelist.get(position).getPhone());
        holder.price.setText(OrderModelist.get(position).getPrice());
        String img = OrderModelist.get(position).getImage();
        Glide.with(mContext)
                .load(img)
                .placeholder(R.drawable.logo)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return OrderModelist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        TextView title, address, phone, price;
        ImageView image;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phone);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);


        }
    }
}
