package com.taprocycle.service.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.taprocycle.service.Activity.OrdersDetailActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.OrderModel;

import java.util.List;


public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MailViewHolder> {

    List<OrderModel> OrderModelist;
    private Context mContext;
    AlertDialog dialog;
    ProgressDialog progressDialog;
    String yousaveamount;

    public MyOrderAdapter(Context mContext, List<OrderModel> OrderModelist) {
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
    public void onBindViewHolder(@NonNull final MailViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final OrderModel model = OrderModelist.get(position);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Updating.....");
        holder.order_date.setText(model.getOrder_date());
       // holder.you_save.setText(model.getPayment_from());

        String a = model.getMrp_total();
        String b = model.getOrder_netamount();
        String c = model.getShipping_fee();

// Check if strings are not empty or null before parsing
        if (!a.isEmpty() && !b.isEmpty() && !c.isEmpty()) {
            try {
                int av = Integer.parseInt(a);
                int bv = Integer.parseInt(b);
                int cv = Integer.parseInt(c);
                int finalamount = av - bv + cv;
                holder.you_save.setText("₹" + finalamount);
                yousaveamount = String.valueOf(finalamount);
                System.out.println("saveamount==" + yousaveamount);
                holder.cv_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(mContext, OrdersDetailActivity.class);
                        intent.putExtra("order_no",OrderModelist.get(position).getOrder_no());
                        intent.putExtra("order_date",model.getOrder_date());
                        intent.putExtra("order_netamount",model.getOrder_netamount());
                        intent.putExtra("order_status",model.getOrder_status());
                        intent.putExtra("name",model.getName());
                        intent.putExtra("yousave",String.valueOf(finalamount));
                        intent.putExtra("address",model.getAddress());
                        intent.putExtra("landmark",model.getLandmark());
                        intent.putExtra("address1",model.getAddress1());
                        intent.putExtra("address2",model.getAddress2());
                        intent.putExtra("address3",model.getAddress3());
                        intent.putExtra("address4",model.getAddress4());
                        intent.putExtra("pincode",model.getPincode());
                        intent.putExtra("payment",model.getPayment_from());
                        mContext.startActivity(intent);
                    }
                });
            } catch (NumberFormatException e) {
                // Handle the exception, such as logging or showing an error message
                e.printStackTrace();
            }
        } else {
            // Handle the case where either 'a' or 'b' is empty or null
            // Display an error message or provide a default value as needed
            holder.you_save.setText("₹" + "0");
        }

        holder.mrp_amount.setText( "₹"+model.getMrp_total());
        holder.order_netamount.setText("₹"+model.getOrder_netamount());
        holder.order_no.setText(model.getOrder_no());
        holder.order_status.setText(model.getOrder_status());

    }

    @Override
    public int getItemCount() {
        return OrderModelist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        TextView order_date, mrp_amount,you_save,order_no, order_netamount, order_status;
        Button view, acknowldege;
        CardView cv_card;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            order_date = itemView.findViewById(R.id.order_date);
            cv_card = itemView.findViewById(R.id.cv_card);
            order_no = itemView.findViewById(R.id.order_no);
            order_netamount = itemView.findViewById(R.id.order_netamount);
            order_status = itemView.findViewById(R.id.order_status);
            mrp_amount =itemView.findViewById(R.id.mrp_amount);
            you_save =itemView.findViewById(R.id.you_save);


        }
    }
}

