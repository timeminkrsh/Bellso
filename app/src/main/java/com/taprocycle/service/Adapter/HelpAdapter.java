package com.taprocycle.service.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Arrays;
import java.util.List;

import com.taprocycle.service.R;
import com.taprocycle.service.test.model.HelpModel;

public class HelpAdapter extends  RecyclerView.Adapter<HelpAdapter.MailViewHolder> {

        private Context mContext;
        List<HelpModel> myListData;
        String pid = "", customer_id = "", editqut, type;
        Integer i;
        String name, price, qty = "", mrp, wid,stackcoit;
        ArrayAdapter<String> arrayAdapter;
        String[] SPINNERLIST = {"250 g", "500 g", "1 kg", "5 kg"};
        private BottomSheetDialog mBottomSheetDialog;
        String[] courses = { "500 g", "1 kg",
                "3 kg", "5 kg"
        };

        public HelpAdapter(Context mContext, HelpModel[] myListData) {
            this.mContext = mContext;
            this.myListData = Arrays.asList(myListData);

        }



        @NonNull
        @Override
        public HelpAdapter.MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.helpandsupport, parent, false);
            return new MailViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final HelpAdapter.MailViewHolder holder, final int position) {

            final HelpModel model = myListData.get(position);
            holder.aboutticket.setText(model.getDetails());
            holder.issues.setText(model.getNaturecomplaint());
            holder.proof.setText(model.getStatus());


        }


        @Override
        public int getItemCount() {
            return myListData.size();
        }

        public static class MailViewHolder extends RecyclerView.ViewHolder {

            TextView aboutticket ,issues,proof;

            public MailViewHolder(@NonNull View itemView) {
                super(itemView);
             //   Status = itemView.findViewById(R.id.Status);
                aboutticket =itemView.findViewById(R.id.aboutticket);
                issues =itemView.findViewById(R.id.issues);
                proof =itemView.findViewById(R.id.proof);

            }
        }

}
