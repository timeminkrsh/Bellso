package com.taprocycle.service.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.taprocycle.service.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> ParentItem;
    private HashMap<String, List<String>> ChildItem;


    public ExpandableListAdapter(Context context, List<String> ParentItem,
                                 HashMap<String, List<String>> ChildItem) {
        this.context = context;
        this.ParentItem = ParentItem;
        this.ChildItem = ChildItem;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.ChildItem.get(this.ParentItem.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_item, null);
        }
        TextView text1 = (TextView) convertView.findViewById(R.id.item1);
        TextView text2 = (TextView) convertView.findViewById(R.id.item2);
        text1.setText(""+expandedListPosition);
        text2.setText(""+expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.ChildItem.get(this.ParentItem.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.ParentItem.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.ParentItem.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.parent_item, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> ParentItem = new HashMap<String, List<String>>();

        List<String> Colors = new ArrayList<String>();
        Colors.add("GROCERY");
        Colors.add("PERSONAL CARE");
        Colors.add("HOME CARE");


        List<String> Animals = new ArrayList<String>();
        Animals.add("M JEANS PANT");
        Animals.add("M FORMAT PANT");
        Animals.add("M TRACK PANT");
        Animals.add("M T-SHIRT");
        Animals.add("M DHOTIES");
        Animals.add("M SHIRTS");
        Animals.add("M FOOTWEAR");
        Animals.add("LINGI");
        Animals.add("BAGS");
        Animals.add("WATCHES");
        Animals.add("SOCKS");
        Animals.add("M INNERWEARS");


        List<String> Sports = new ArrayList<String>();
        Sports.add("W JEANS PANT");
        Sports.add("W LEGGINGS");
        Sports.add("SAREES");
        Sports.add("CHUDICHAR");
        Sports.add("READY MADE CHUDICHAR");
        Sports.add("TOPS");
        Sports.add("WALLET");
        Sports.add("W FOOTWEAR");
        Sports.add("NIGHTIES");
        Sports.add("W INNERWEAR");
        Sports.add("HANDBAGS");



        ParentItem.put("PROVISION", Colors);
        ParentItem.put("W FASHION", Sports);
        ParentItem.put("MY FASHION", Animals);
        return ParentItem;



    }
}
