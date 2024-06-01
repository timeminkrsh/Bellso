package com.taprocycle.service;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class DescriptionFragment extends Fragment {
    TextView text_view2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View view =inflater.inflate(R.layout.fragment_description, container, false);

    // Assign variable
        text_view2=view.findViewById(R.id.text_view2);
    Bundle args = getArguments();
        if (args != null) {
        String value = args.getString("long");
            text_view2.setText(value);
        // Now you have the value passed from the activity
        // You can use it as needed
    }

        return view;
}

}