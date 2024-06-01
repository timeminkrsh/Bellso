package com.taprocycle.service.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.taprocycle.service.Activity.ShopSubActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.ShopData;
import com.taprocycle.service.test.model.ShopDataDetail;

import java.util.ArrayList;
import java.util.List;

public class ShopDataAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<ShopData> listDataHeader;

    public ShopDataAdapter(Context context, List<ShopData> listDataHeader) {
        this._context = context;
        this.listDataHeader = listDataHeader;
    }

    @Override
    public ArrayList<ShopDataDetail> getChild(int groupPosition, int childPosititon) {
        return this.listDataHeader.get(groupPosition).getCh_list();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ShopDataDetail childText = listDataHeader.get(groupPosition).getCh_list().get(childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_item, null);
        }

        TextView item_txt_sub_cat_id = convertView.findViewById(R.id.item1);
        item_txt_sub_cat_id.setText(childText.getItem_id());

        TextView item_txt_sub_cat_name = convertView.findViewById(R.id.item2);
        item_txt_sub_cat_name.setText(childText.getItem_name());

        item_txt_sub_cat_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // System.out.println("item_txt_sub_cat_name==```"+childText.getItem_id());
                Intent intent=new Intent(_context, ShopSubActivity.class);
                intent.putExtra("scid",childText.getItem_id());
                System.out.println("item_txt_sub_cat_name==```"+childText.getItem_id());
                intent.putExtra("sid_name",childText.getItem_name());
                System.out.println("item_txt_sub_cat_name==```"+childText.getItem_name());
                _context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataHeader.get(groupPosition).getCh_list().size();
    }

    @Override
    public ShopData getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ShopData headerTitle = getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.parent_item, null);
        }

        ImageView image = convertView.findViewById(R.id.image);
        TextView item_txt_category_name = convertView.findViewById(R.id.listTitle);
        Glide.with(convertView)
                .load(headerTitle.getWeb_image())
                .placeholder(R.drawable.logo)
                .into(image);

         //item_txt_category_id.setText(headerTitle.getCategory_id());
          item_txt_category_name.setText(headerTitle.getWeb_title());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}