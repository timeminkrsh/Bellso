package com.taprocycle.service.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.taprocycle.service.R;
import com.taprocycle.service.test.model.NewPayout;

import java.util.List;


public class PayoutSummaryAdapter extends RecyclerView.Adapter<PayoutSummaryAdapter.MailViewHolder> {

    List<NewPayout> noticeboardlist;
    private Context mContext;
    AlertDialog dialog;


    public PayoutSummaryAdapter(Context mContext, List<NewPayout> noticeboardlist) {
        this.mContext = mContext;
        this.noticeboardlist = noticeboardlist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payout_history, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final NewPayout model = noticeboardlist.get(position);

        holder.details.setText("Commission");
        holder.date.setText(model.getTdate());
        holder.amount.setText(" ₹ " + model.getAmount());
        holder.netamount.setText(" ₹ " + model.getNet_amount());
        // String amount =noticeboardlist.get(0).getBalance();
        //  System.out.println("amountamout"+amount);
        holder.lesstds.setText(" ₹ " + model.getTds_amount());

    }
    @Override
    public int getItemCount() {
        return noticeboardlist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        TextView details,date,amount,lesstds,netamount;
        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            details         = itemView.findViewById(R.id.details);
            date         = itemView.findViewById(R.id.date);
            amount     = itemView.findViewById(R.id.amount);
            lesstds     = itemView.findViewById(R.id.lesstds);
            netamount     = itemView.findViewById(R.id.netamount);

        }
    }
}
