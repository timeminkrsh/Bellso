package com.taprocycle.service.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.taprocycle.service.R;
import com.taprocycle.service.test.model.OrderModel;

import java.util.List;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MailViewHolder> {

    List<OrderModel> OrderModelist;
    private Context mContext;
    AlertDialog dialog;
    ProgressDialog progressDialog;

    public OrdersAdapter(Context mContext, List<OrderModel> OrderModelist) {
        this.mContext = mContext;
        this.OrderModelist = OrderModelist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorders_list, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final OrderModel model = OrderModelist.get(position);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Updating.....");
        holder.order_date.setText(model.getOrder_date());

        if(model.getOrder_netamount().equalsIgnoreCase("0")){
            holder.order_netamount.setText("Wallet Used");

        }else{
            holder.order_netamount.setText("₹ "+model.getOrder_netamount());

        }
        holder.order_no.setText(model.getOrder_no());
        holder.order_status.setText(model.getOrder_status());



    }

    @Override
    public int getItemCount() {
        return OrderModelist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        TextView order_date, order_no, order_netamount, order_status;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            order_date = itemView.findViewById(R.id.order_date);
            order_no = itemView.findViewById(R.id.order_no);
            order_netamount = itemView.findViewById(R.id.order_netamount);
            order_status = itemView.findViewById(R.id.order_status);


        }
    }
}
