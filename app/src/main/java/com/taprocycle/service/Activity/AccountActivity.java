package com.taprocycle.service.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taprocycle.service.MainActivity;
import com.taprocycle.service.ProfileActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.SplashScreenActivity;
import com.taprocycle.service.test.model.BSession;

public class AccountActivity extends AppCompatActivity {
    LinearLayout cv_address;
    LinearLayout cv_setting;
    LinearLayout cv_terms;
    LinearLayout cv_about;
    LinearLayout cv_orders;
    LinearLayout cv_logout;
    LinearLayout cv_help,cv_policy;
    TextView txt_name,txt_phn;
    RelativeLayout tb_notify;
    ImageView cart;
    LinearLayout arrow_lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        //toolbar();
        cv_address = findViewById(R.id.cv_address);
        cv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, DeliveryAddressActivity.class);
                startActivity(intent);
            }
        });

        cv_setting = findViewById(R.id.cv_setting);
        cv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        cv_terms = findViewById(R.id.cv_terms);
        cv_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, TermsActivity.class);
                startActivity(intent);
            }
        });

        cv_help = findViewById(R.id.cv_help);
        cv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, HelpSupportActivity.class);
                startActivity(intent);
            }
        });

        cv_policy = findViewById(R.id.cv_policy);
        cv_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, PrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });
        cv_about = findViewById(R.id.cv_about);
        cv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });
        cv_orders = findViewById(R.id.cv_orders);
        cv_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, MyOrdersActivity.class);
                startActivity(intent);
            }
        });
        cv_logout = findViewById(R.id.cv_logout);
        cv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutAlert();
            }
        });
        arrow_lin=findViewById(R.id.arrow_lin);
        cart=findViewById(R.id.cart);
        arrow_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });*/
        txt_phn = findViewById(R.id.txt_phn);
        txt_name = findViewById(R.id.txt_name);

        String mobileobji = BSession.getInstance().getUser_mobile(AccountActivity.this);
        final String nameobjs = BSession.getInstance().getUser_name(AccountActivity.this);

        txt_phn.setText(mobileobji);
        txt_name.setText(nameobjs);

        System.out.println("namess=="+mobileobji);
        System.out.println("namess=="+nameobjs);
    }

    private void logoutAlert() {


        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setMessage(getApplicationContext().getResources().getString(R.string.alert_want_logout))
                .setCancelable(false)
                .setPositiveButton(getApplicationContext().getResources().getString(R.string.alert_yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                                logout();
                            }
                        })
                .setNegativeButton(getApplicationContext().getResources().getString(R.string.alert_no),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {


                                dialog.cancel();
                            }
                        });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private void logout() {

        BSession.getInstance().destroy(AccountActivity.this);
        Toast.makeText(AccountActivity.this, getResources().getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AccountActivity.this, SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }



    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AccountActivity.this,MainActivity.class);
        startActivity(intent);
    }
}