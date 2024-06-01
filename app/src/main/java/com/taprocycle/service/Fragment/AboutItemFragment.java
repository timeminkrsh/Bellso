package com.taprocycle.service.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taprocycle.service.R;

public class AboutItemFragment extends Fragment {
    TextView text_view;
    Context mcontext;
    String pid="";
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize view
        View view =inflater.inflate(R.layout.fragment_about_item, container, false);

        // Assign variable
        text_view=view.findViewById(R.id.text_view);
        Bundle args = getArguments();
        if (args != null) {
            String value = args.getString("key");

            text_view.setText(value);
            // Now you have the value passed from the activity
            // You can use it as needed
        }

        return view;
    }

}