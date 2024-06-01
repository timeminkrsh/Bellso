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
import com.taprocycle.service.test.model.AddModel;

public class AddpvAdapter extends  RecyclerView.Adapter<AddpvAdapter.MailViewHolder>{
    List<AddModel> noticeboardlist;
    private Context mContext;
    AlertDialog dialog;


    public AddpvAdapter(Context mContext, List<AddModel> noticeboardlist) {
        this.mContext = mContext;
        this.noticeboardlist = noticeboardlist;
    }

    @NonNull
    @Override
    public AddpvAdapter.MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pvreport_list, parent, false);
        return new AddpvAdapter.MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddpvAdapter.MailViewHolder holder, final int position) {
        final AddModel model = noticeboardlist.get(position);
        TextView id,name,cfl,cfr,cwl,cwr,mpv,pp,tl,tr;
       // holder.id.setText(model.getPayid());
        //   holder.name.setText(model.getName());
        holder.cfl.setText(model.getCp_left());
        holder.cwl.setText(model.getRp_left());
        holder.cwr.setText(model.getRp_right());
        holder.mpv.setText(model.getMatch_point());
        holder.pp.setText(model.getPayout_amount());
        holder.tl.setText(model.getTotal_left());
        holder.tr.setText(model.getTotal_right());
        holder.cfr.setText(model.getCp_right());


    }

    @Override
    public int getItemCount() {
        return noticeboardlist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        TextView id,name,cfl,cfr,cwl,cwr,mpv,pp,tl,tr;
        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            id         = itemView.findViewById(R.id.id);
            //  name         = itemView.findViewById(R.id.name);
            cfl     = itemView.findViewById(R.id.cfl);
            cfr     = itemView.findViewById(R.id.cfr);
            cwl     = itemView.findViewById(R.id.cwl);
            cwr     = itemView.findViewById(R.id.cwr);
            mpv     = itemView.findViewById(R.id.mpv);
            pp     = itemView.findViewById(R.id.pp);
            tl     = itemView.findViewById(R.id.tl);
            tr     = itemView.findViewById(R.id.tr);

        }
    }
}
