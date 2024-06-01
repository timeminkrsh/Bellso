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

import com.taprocycle.service.R;
import com.taprocycle.service.test.model.NoticeBoard;

import java.util.List;

public class SubWalletAdapter extends RecyclerView.Adapter<SubWalletAdapter.MailViewHolder> {

    List<NoticeBoard> noticeboardlist;
    private Context mContext;
    AlertDialog dialog;


    public SubWalletAdapter(Context mContext, List<NoticeBoard> noticeboardlist) {
        this.mContext = mContext;
        this.noticeboardlist = noticeboardlist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subwallet_list, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final NoticeBoard model = noticeboardlist.get(position);
        TextView id,name,cfl,cfr,cwl,cwr,mpv,pp,tl,tr;
        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return noticeboardlist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        TextView id,name,cfl,cfr,cwl,cwr,mpv,pp,tl,tr;
        LinearLayout lin;
        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            lin=itemView.findViewById(R.id.lin);


        }
    }
}
