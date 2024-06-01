package com.taprocycle.service.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;


import com.taprocycle.service.R;
import com.taprocycle.service.test.model.WeightModel;

import java.util.List;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.Holder> {
    /* access modifiers changed from: private */
    public Context context;
    private List<WeightModel> papularModelList;
    int selectedPosition = -1;

    public class Holder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        TextView txtTitle;

        Holder(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.tv_wgt);
        }
    }

    public WeightAdapter(Context context, List<WeightModel> papularModelList) {
        this.context = context;
        this.papularModelList = papularModelList;
    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Holder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        viewHolder = getViewHolder(viewGroup, inflater);
        return viewHolder;
    }

    private Holder getViewHolder(ViewGroup viewGroup, LayoutInflater inflater) {
        Holder viewHolder;
        View v1 = inflater.inflate(R.layout.layout_weight_list, viewGroup, false);
        viewHolder = new Holder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, @SuppressLint("RecyclerView") final int position) {
        WeightModel model = papularModelList.get(position);

        holder.txtTitle.setText(model.getColor());

        if (selectedPosition == position)
            holder.itemView.setBackgroundColor(Color.parseColor("#0081D6"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getLayoutPosition();
                if (selectedPosition != currentPosition) {
                    // Temporarily save the last selected position
                    int lastSelectedPosition = selectedPosition;
                    // Save the new selected position
                    selectedPosition = currentPosition;
                    // Update the previous selected row
                    notifyItemChanged(lastSelectedPosition);
                    // Select the clicked row by changing its background color
                    holder.itemView.setBackgroundColor(Color.parseColor("#0081D6"));
                }
            }
        });
    }

    public int getItemCount() {
        return papularModelList == null ? 0 : papularModelList.size();
    }
}
