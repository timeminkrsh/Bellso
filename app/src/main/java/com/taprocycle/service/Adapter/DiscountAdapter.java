package com.taprocycle.service.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.taprocycle.service.R;
import com.taprocycle.service.test.model.Discountpoints;


public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.MailViewHolder> {

    List<Discountpoints> noticeboardlist;
    private Context mContext;
    AlertDialog dialog;


    public DiscountAdapter(Context mContext, List<Discountpoints> noticeboardlist) {
        this.mContext = mContext;
        this.noticeboardlist = noticeboardlist;
    }



    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactionlist, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final Discountpoints  model = noticeboardlist.get(position);

        //  holder.date.setText(model.getDate());
        holder.title.setText(model.getTitle());


        holder.date.setText(model.getDate());
        holder.amount.setText(" ₹ "+model.getAmount());
        holder.balance.setText(" ₹ "+model.getBalance());
        String amount =noticeboardlist.get(0).getBalance();
        System.out.println("amountamout"+amount);
        //  holder.balance.setText(" ₹ "+model.getBalance());

        // String amount =noticeboardlist.get(0).getBalance();
        //  System.out.println("amountamout"+amount);

       /* WalletSession.getInstance().initialize(mContext,
                amount,
                "");
    }*/
    }

    @Override
    public int getItemCount() {
        return noticeboardlist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        TextView title,details,date,amount,balance,note;
        LinearLayout layout;
        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            details         = itemView.findViewById(R.id.details);
            date         = itemView.findViewById(R.id.date);
            amount     = itemView.findViewById(R.id.amount);
            note=itemView.findViewById(R.id.note);
            balance     = itemView.findViewById(R.id.balance);
            title         = itemView.findViewById(R.id.title);
            layout = itemView.findViewById(R.id.layout);

        }
    }
}
