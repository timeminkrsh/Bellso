package com.taprocycle.service.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.taprocycle.service.R;

import com.taprocycle.service.test.model.ProductsModel;

import java.util.ArrayList;
import java.util.List;

public class SuggestionAdapter extends ArrayAdapter<ProductsModel> {
    private Context context;
    private int resourceId;
    private List<ProductsModel> items, tempItems, suggestions;

    public SuggestionAdapter(@NonNull Context context, int resourceId, ArrayList<ProductsModel> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            final ProductsModel fruit = getItem(position);
            TextView pname = (TextView) view.findViewById(R.id.title);
            pname.setText(fruit.getWeb_title());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public ProductsModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProductsModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(tempItems);
            } else {
                if( constraint.length() >1) {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (ProductsModel item : tempItems) {
                        if (item.getWeb_title().toLowerCase().startsWith(filterPattern)) {
                            System.out.println("item.getWeb_title().toLowerCase().contains(filterPattern)"+item.getWeb_title().toLowerCase().contains(filterPattern));
                            filteredList.add(item);
                        }
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

