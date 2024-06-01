package com.taprocycle.service.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.taprocycle.service.R;
import com.taprocycle.service.test.model.SubwalletModel;

public class SubwalletViewByIdAdapter extends RecyclerView.Adapter<SubwalletViewByIdAdapter.MailViewHolder> {

    List<SubwalletModel> noticeboardlist;
    private Context mContext;
    AlertDialog dialog;


    public SubwalletViewByIdAdapter(Context mContext, List<SubwalletModel> noticeboardlist) {
        this.mContext = mContext;
        this.noticeboardlist = noticeboardlist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subwallet_history, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final SubwalletModel model = noticeboardlist.get(position);

        holder.details.setText(model.getNarration());
        holder.date.setText(model.getDate());
        holder.amount.setText(" ₹ " + model.getAmount());

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


        }
    }
}
