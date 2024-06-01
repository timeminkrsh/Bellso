package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.taprocycle.service.R;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        toolbar();
    }
    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TermsActivity.this.startActivity(new Intent(TermsActivity.this.getApplicationContext(), AccountActivity.class));
                TermsActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Terms And Conditions");
        toolbar.setTitleTextColor(-1);
    }
}