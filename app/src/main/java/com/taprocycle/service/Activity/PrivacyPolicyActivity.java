package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.taprocycle.service.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        toolbar();
    }
    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrivacyPolicyActivity.this.startActivity(new Intent(PrivacyPolicyActivity.this.getApplicationContext(), AccountActivity.class));
                PrivacyPolicyActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Privacy Policy");
        toolbar.setTitleTextColor(-1);
    }
}