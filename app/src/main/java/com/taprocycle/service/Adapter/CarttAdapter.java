package com.taprocycle.service.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.taprocycle.service.Activity.CartActivity;

import com.taprocycle.service.Activity.NewProductViewActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ProductsModel;
import com.taprocycle.service.test.model.SubWalletAddCartSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CarttAdapter extends RecyclerView.Adapter<CarttAdapter.MailViewHolder> {

    List<ProductsModel> cartModelList;
    private Context mContext;
    AlertDialog dialog;
    Integer i;
    private boolean isCartReduceInProgress = false;
    private boolean isCartReduceInProgress1 = false;
    private long lastClickTime = 0;
    private static final long CLICK_DELAY = 1000;

    public CarttAdapter(Context mContext, List<ProductsModel> cartModelList) {
        this.mContext = mContext;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_design, parent, false);
        return new MailViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final ProductsModel model = cartModelList.get(position);
        String img = cartModelList.get(position).getImage();
        Glide.with(mContext)
                .load(img)
                .placeholder(R.drawable.logo)
                .into(holder.ivMenu);
        String type=BSession.getInstance().getType(mContext);
            holder.saveprice.setVisibility(View.GONE);
            double a= Double.parseDouble(model.getPrice());
            double b= Double.parseDouble(model.getDiscount_price());
            double c=a-b;
            int resul =(int) Math.abs(c);
        holder.tv_pTitle.setText(model.getProductname());
        System.out.println("reposne=="+model.getProductname());
        holder.saveprice.setText("You Save ₹"+String.valueOf(resul));
        holder.mrp.setText("MRP "+model.getPrice());
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.price.setText("₹ "+model.getDiscount_price());
        System.out.println("colr=="+model.getDiscount_price());
        if(model.getColor().isEmpty()){
            holder.variation.setVisibility(View.GONE);
        }
        if(model.getProduct_color().isEmpty()){
            holder.colur.setVisibility(View.GONE);
        }
        holder.variation.setText("Size :"+model.getColor());
        holder.colur.setText("Colour :"+model.getProduct_color());
        System.out.println("colr=="+model.getColor());
        holder.tv_qty.setText(model.getQty());

        holder.ll_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getProduct_id().isEmpty()){
                    Toast.makeText(mContext, "No Product found", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(mContext, NewProductViewActivity.class);
                    intent.putExtra("pid",cartModelList.get(position).getProduct_id());
                    intent.putExtra("stock",cartModelList.get(position).getStock());
                    intent.putExtra("exclusive",cartModelList.get(position).getExclusive_product());
                    System.out.println("pid=="+cartModelList.get(position).getProduct_id());
                    mContext.startActivity(intent);
                }

            }
        });

        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime < CLICK_DELAY) {
                    // Ignore the click if it's too soon after the last one
                    return;
                }
                lastClickTime = currentTime;
                cartadd();
            }

            private void cartadd() {
                if (isCartReduceInProgress1) {
                    // If cartReduce is already in progress, return and ignore the click
                    return;
                }
                isCartReduceInProgress1 = true;
                try {
                    final String _stringVal;
                    Log.d("src", "Increasing value...");
                    i = Integer.valueOf(holder.tv_qty.getText().toString());
                    i = i + 1;
                    _stringVal = String.valueOf(i);
                    String  customer_id = BSession.getInstance().getUser_id(mContext);
                    final Map<String, String> params = new HashMap<>();
                    String para_str1 = "?product_id=" + cartModelList.get(position).getProduct_id();
                    String para_str2 = "&quantity=" + "1";
                    String para_str4 = "&cart_type=" + "add";
                    String para_str5 = "&user_id=" + customer_id;
                    String para_str6 = "&price=" + cartModelList.get(position).getPrice();
                    String para_str7 = "&discount_price=" + cartModelList.get(position).getDiscount_price();
                    String para_str8 = "&subcategory=" + cartModelList.get(position).getScid();
                    String para_str9 = "&vid=" +cartModelList.get(position).getVid();
                    String baseUrl = ProductConfig.addcart + para_str1 + para_str2  + para_str4 + para_str5+para_str6+para_str7+para_str8+para_str9;
                    System.out.println("bllll=="+baseUrl);
                    StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                if (jsonResponse.has("success") && jsonResponse.getString("success").equals("1")) {
                                    if (jsonResponse.getString("message").equalsIgnoreCase("Successfully Updated")) {
                                        //((CartActivity) mContext).getCartCount1();
                                        ((CartActivity) mContext).getCartCount();
                                        //cartModelList.clear();
                                    } else {
                                        holder.tv_qty.setText(jsonResponse.getString("quantity"));
                                        //((CartActivity) mContext).getCartCount1();
                                        ((CartActivity) mContext).getCartCount();
                                        //cartModelList.clear();

                                    }

                                } else if(jsonResponse.has("success") && jsonResponse.getString("success").equals("fail")){
                                    if (jsonResponse.getString("message").equalsIgnoreCase("Out of stock")) {
                                        holder.tv_qty.setText(jsonResponse.getString("quantity"));
                                        Toast.makeText(mContext, "Sorry this item was out of stock", Toast.LENGTH_LONG).show();

                                    }
                                }
                                else {
                                    Toast.makeText(mContext, "Something went wrong your item not added .. Try it again", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.e("Error", error.toString());

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                    requestQueue.add(jsObjRequest);
                }finally {
                    isCartReduceInProgress1 = false;

                }

            }
        });
        holder.tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime < CLICK_DELAY) {
                    // Ignore the click if it's too soon after the last one
                    return;
                }
                lastClickTime = currentTime;
                cartReduce();
            }

            private void cartReduce() {
                if (isCartReduceInProgress) {
                    // If cartReduce is already in progress, return and ignore the click
                    return;
                }
                isCartReduceInProgress = true;

                try {
                    final String _stringVal;
                    Log.d("src", "Decreasing value...");
                    i = Integer.valueOf(holder.tv_qty.getText().toString());
                    if (i > 0) {
                        i = i - 1;
                        _stringVal = String.valueOf(i);
                        String customer_id = BSession.getInstance().getUser_id(mContext);
                        final Map<String, String> params = new HashMap<>();
                        String para_str1 = "?product_id=" + cartModelList.get(position).getProduct_id();
                        String para_str2 = "&quantity=" + "1";
                        String para_str4 = "&cart_type=" + "sub";
                        String para_str5 = "&user_id=" + customer_id;
                        String para_str6 = "&price=" + cartModelList.get(position).getPrice();
                        String para_str7 = "&discount_price=" + cartModelList.get(position).getDiscount_price();
                        String para_str8 = "&subcategory=" + cartModelList.get(position).getScid();
                        String para_str9 = "&vid=" + cartModelList.get(position).getVid();
                        String baseUrl = ProductConfig.addcart + para_str1 + para_str2 + para_str4 + para_str5 + para_str6 + para_str7 + para_str8 + para_str9;
                        System.out.println("bllll=="+baseUrl);
                        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("Response", response.toString());
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);

                                    if (jsonResponse.has("success") && jsonResponse.getString("success").equals("1")) {


                                        if (jsonResponse.getString("message").equalsIgnoreCase("Successfully Updated")) {


                                            //  ((CartActivity) mContext).getCartCount1();
                                            ((CartActivity) mContext).getCartCount();
                                            //cartModelList.clear();


                                        } else {

                                            ((CartActivity) mContext).getCartCount();
                                            //cartModelList.clear();

                                        }

                                    } else if (jsonResponse.has("success") && jsonResponse.getString("success").equals("1")) {
                                        if (jsonResponse.getString("message").equalsIgnoreCase("cart_id deleted")) {

                                            ((CartActivity) mContext).getCartCount();
                                            //Toast.makeText(mContext, "Sorry this item was out of stock", Toast.LENGTH_LONG).show();
                                        }else {
                                            holder.tv_qty.setText(jsonResponse.getString("quantity"));
                                        }
                                    } else if (jsonResponse.has("success") && jsonResponse.getString("success").equals("fail")) {
                                        if (jsonResponse.getString("message").equalsIgnoreCase("Out of stock")) {
                                            holder.tv_qty.setText(jsonResponse.getString("quantity"));
                                            Toast.makeText(mContext, "Sorry this item was out of stock", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        // Handle other cases
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.e("Error", error.toString());

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                        requestQueue.add(jsObjRequest);
                    } else {
                        Log.d("src", "Value can't be less than 0");
                    }
                }finally {
                    // Reset the flag after processing, whether it's successful or not
                    isCartReduceInProgress = false;
                }

            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you Delete your Product");
                builder.setCancelable(true);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delitemethod();

                    }

                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            private void delitemethod() {

                    String customer_id = BSession.getInstance().getUser_id(mContext);
                    final Map<String, String> params = new HashMap<>();
                    String para_str1 = "?cart_id=" + cartModelList.get(position).getCart_id();
                    String baseUrl = ProductConfig.cartdelete + para_str1;

                    StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);


                                if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    Toast.makeText(CarttAdapter.this.mContext, "unable to remove the item", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                CarttAdapter.this.cartModelList = new ArrayList();
                                ((CartActivity) CarttAdapter.this.mContext).getCartCount();
                                /*((CartActivity) CarttAdapter.this.mContext).setRecyclerview();*/
                                CarttAdapter.this.cartModelList.clear();
                                Toast.makeText(CarttAdapter.this.mContext, "Product removed from your cart", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.e("Error", error.toString());

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                    requestQueue.add(jsObjRequest);


            }
        });

    }


    @Override
    public int getItemCount() {
        return cartModelList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MailViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_delete;
        ImageView ivMenu;
        TextView tv_pTitle, colur,mrp, price, variation, saveprice,tv_qty,tv_minus,tv_plus,totalprice;
        LinearLayout iv_delete_lin,ll_parent;
        View view;
        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMenu = itemView.findViewById(R.id.ivMenu);
            view = itemView.findViewById(R.id.view);
            tv_pTitle = itemView.findViewById(R.id.tv_pTitle);
            mrp = itemView.findViewById(R.id.mrp);
            price = itemView.findViewById(R.id.price);
            variation = itemView.findViewById(R.id.variation);
            saveprice = itemView.findViewById(R.id.saveprice);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            tv_qty = itemView.findViewById(R.id.tv_qty);
            ll_parent = itemView.findViewById(R.id.ll_parent);
            tv_minus = itemView.findViewById(R.id.tv_minus);
            tv_plus = itemView.findViewById(R.id.tv_plus);
            totalprice=itemView.findViewById(R.id.totalprice);
            iv_delete_lin=itemView.findViewById(R.id.iv_delete_lin);
            colur=itemView.findViewById(R.id.colur);

        }
    }
}

