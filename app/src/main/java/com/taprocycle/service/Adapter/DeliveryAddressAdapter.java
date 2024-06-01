package com.taprocycle.service.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taprocycle.service.Activity.CartActivity;
import com.taprocycle.service.Activity.DeliveryAddressActivity;
import com.taprocycle.service.AddAddressActivity;
import com.taprocycle.service.EditAddressActivity;
import com.taprocycle.service.MainActivity;
import com.taprocycle.service.R;
import com.taprocycle.service.test.model.BSession;
import com.taprocycle.service.test.model.DeliveryAddressModel;
import com.taprocycle.service.test.model.GlobalVariable;
import com.taprocycle.service.test.model.DeliveryAddressSession;
import com.taprocycle.service.test.model.ProductConfig;
import com.taprocycle.service.test.model.ZipcodeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.MailViewHolder> {

    List<DeliveryAddressModel> deliveryAddressModelList;
    private Context mContext;
    AlertDialog dialog;
    BottomSheetDialog bottomSheetDialog;
    ProgressDialog progressDialog;
    Dialog dialogg;
    List<ZipcodeModel> apiZipcodeList = new ArrayList<>();
    ZipcodeModel zipcodeModel = new ZipcodeModel();
    ArrayList<String> pincodelist = new ArrayList<String>();
    EditText et_pincode;
    GlobalVariable globalVariable;
    AlertDialog.Builder builder;
    AlertDialog AAlertDialog;
    String pincodes="";


    public DeliveryAddressAdapter(Context mContext, List<DeliveryAddressModel> deliveryAddressModelList) {
        this.mContext = mContext;
        this.deliveryAddressModelList = deliveryAddressModelList;

    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deliveryaddress_items, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final DeliveryAddressModel model = deliveryAddressModelList.get(position);
        holder.name.setText(deliveryAddressModelList.get(position).getName());
        holder.address.setText(deliveryAddressModelList.get(position).getLine1()+",\n"+ deliveryAddressModelList.get(position).getLine2()+",\n"+ deliveryAddressModelList.get(position).getLine3()+
                ",\n"+ deliveryAddressModelList.get(position).getLine5()+""+"Pincode - "+deliveryAddressModelList.get(position).getPincode()+",\n"+"Mobile - "+deliveryAddressModelList.get(position).getPhone()+".");
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading.....");
        holder.radioGroup.setVisibility(View.GONE);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeliveryAddressSession.getInstance().initialize(mContext,
                        deliveryAddressModelList.get(position).getLine1(),deliveryAddressModelList.get(position).getLine2(),deliveryAddressModelList.get(position).getLine3(),deliveryAddressModelList.get(position).getPincode(),deliveryAddressModelList.get(position).getLandmark(),deliveryAddressModelList.get(position).getPhone(),deliveryAddressModelList.get(position).getName(),"");

                DeliveryAddressModel model = deliveryAddressModelList.get(position);
                Intent intent = new Intent(mContext, CartActivity.class);
                CartActivity.subcourcemodel = model;
                mContext.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you  delete your Address");
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

                        DeliveryAddressSession.getInstance().initialize(mContext,
                                "" , "", "","","","","","");

                    }

                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            private void delitemethod() {
                final Map<String, String> params = new HashMap<>();
                String para1 = "?user_id=" + BSession.getInstance().getUser_id(mContext);
                String para2 = "&address_id=" + deliveryAddressModelList.get(position).getId();
                progressDialog.show();
                String baseUrl = ProductConfig.user_address_delete + para1 + para2;
                StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonResponse = new JSONObject(response);

                            if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                                ((DeliveryAddressActivity) mContext).deliveryaddress();

                                Toast.makeText(mContext, "Your adddress has been deleted", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(mContext, "deleted failed", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("Error", error.toString());
                        progressDialog.dismiss();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                requestQueue.add(jsObjRequest);
                jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                        220000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            }

        });

        holder.location_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mContext, EditAddressActivity.class);
                intent.putExtra("id",deliveryAddressModelList.get(position).getId());
                intent.putExtra("etname",deliveryAddressModelList.get(position).getName());
                intent.putExtra("etphone",deliveryAddressModelList.get(position).getPhone());
                intent.putExtra("etline1",deliveryAddressModelList.get(position).getLine1());
                intent.putExtra("etline2",deliveryAddressModelList.get(position).getLine2());
                intent.putExtra("etline3",deliveryAddressModelList.get(position).getLine3());
                intent.putExtra("pincode",deliveryAddressModelList.get(position).getPincode());
                intent.putExtra("landmark",deliveryAddressModelList.get(position).getLandmark());
                intent.putExtra("deliveryaddress",true);
                mContext.startActivity(intent);
            }
                /* bottomSheetDialog = new BottomSheetDialog(mContext);
                bottomSheetDialog.setContentView(R.layout.deliveryaddress_add);
                EditText et_name = bottomSheetDialog.findViewById(R.id.et_name);
                EditText et_phone = bottomSheetDialog.findViewById(R.id.et_phone);
                EditText et_line1 = bottomSheetDialog.findViewById(R.id.et_line1);
                EditText et_line2 = bottomSheetDialog.findViewById(R.id.et_line2);
                EditText et_line3 = bottomSheetDialog.findViewById(R.id.et_line3);
                EditText et_line4 = bottomSheetDialog.findViewById(R.id.et_line4);
                AutoCompleteTextView atv_pincode = bottomSheetDialog.findViewById(R.id.atv_pincode);
                EditText et_landmark = bottomSheetDialog.findViewById(R.id.et_landmark);
                Button sumbit = bottomSheetDialog.findViewById(R.id.sumbit);
                et_name.setText(deliveryAddressModelList.get(position).getName());
                et_phone.setText(deliveryAddressModelList.get(position).getPhone());
                et_line1.setText(deliveryAddressModelList.get(position).getLine1());
                et_line2.setText(deliveryAddressModelList.get(position).getLine2());
                et_line3.setText(deliveryAddressModelList.get(position).getLine3());
                et_line4.setText(deliveryAddressModelList.get(position).getLine4());
                atv_pincode.setText(deliveryAddressModelList.get(position).getPincode());
                et_landmark.setText(deliveryAddressModelList.get(position).getLandmark());

                final Map<String, String> params = new HashMap<>();

                String baseUrl = ProductConfig.pincode;

                final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonResponse = new JSONObject(response);

                            if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                                apiZipcodeList = new ArrayList<>();
                                JSONArray jsonlist = jsonResponse.getJSONArray("pincodeList");
                                for (int j = 0; j < jsonlist.length(); j++) {

                                    JSONObject jsonlistObject = jsonlist.getJSONObject(j);
                                    ZipcodeModel zipcodeModel = new ZipcodeModel();

                                    zipcodeModel.setId(jsonlistObject.getString("id").toString());
                                    zipcodeModel.setZipcode(jsonlistObject.getString("pincode").toString());

                                    pincodelist.add(jsonlistObject.getString("pincode"));

                                    apiZipcodeList.add(zipcodeModel);

                                    //rv_mega.setLayoutManager(layoutManager);

                                    ArrayAdapter adapter=new ArrayAdapter(mContext,R.layout.pincode_list,R.id.pincode,pincodelist);
                                    atv_pincode.setAdapter(adapter);
                                    atv_pincode.setThreshold(1);
                                    atv_pincode.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //atv_pincode.showDropDown();
                                        }
                                    });
                                    atv_pincode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            //////////////////////////
                                            for (ZipcodeModel model:apiZipcodeList) {
                                                if(atv_pincode.getText().toString().equals(model.getZipcode())){
                                                    Toast.makeText(mContext, "model.getZipcode() = "+model.getZipcode()+"\n model.getId() == "+model.getId(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        }
                                    });



                                }
                            } else {
                                Toast.makeText(mContext, "Pincode records not found", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Error", error.toString());
                                progressDialog.dismiss();
                                String message = null;
                                if (error instanceof NetworkError) {
                                    Toast.makeText(mContext, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                                    message = "Cannot connect to Internet...Please check your connection!";
                                } else if (error instanceof ServerError) {
                                    Toast.makeText(mContext, "The server could not be found. Please try again after some time!!", Toast.LENGTH_LONG).show();

                                    message = "The server could not be found. Please try again after some time!!";
                                } else if (error instanceof AuthFailureError) {
                                    Toast.makeText(mContext,"Cannot connect to Internet...Please check your connection!" , Toast.LENGTH_LONG).show();

                                    message = "Cannot connect to Internet...Please check your connection!";
                                } else if (error instanceof ParseError) {
                                    Toast.makeText(mContext,"Parsing error! Please try again after some time!!" , Toast.LENGTH_LONG).show();

                                    message = "Parsing error! Please try again after some time!!";
                                } else if (error instanceof NoConnectionError) {
                                    Toast.makeText(mContext,"Cannot connect to Internet...Please check your connection!" , Toast.LENGTH_LONG).show();

                                    message = "Cannot connect to Internet...Please check your connection!";
                                } else if (error instanceof TimeoutError) {
                                    Toast.makeText(mContext,"Connection TimeOut! Please check your internet connection." , Toast.LENGTH_LONG).show();

                                    message = "Connection TimeOut! Please check your internet connection.";
                                }
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                requestQueue.add(jsObjRequest);

                sumbit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String   name = et_name.getText().toString().trim();
                        String   phone = et_phone.getText().toString().trim();
                        String   line1 = et_line1.getText().toString().trim();
                        String   line2 = et_line2.getText().toString().trim();
                        String   line3 = et_line3.getText().toString().trim();
                        String   line4 = et_line4.getText().toString().trim();
                        String   pincode = atv_pincode.getText().toString().trim();
                        String   landmark = et_landmark.getText().toString().trim();

                        if (name.isEmpty()) {
                            et_name.setError("*Enter your name");
                            et_name.requestFocus();
                            return;
                        }
                        if (phone.isEmpty()) {
                            et_phone.setError("*Enter your mobile number");
                            et_phone.requestFocus();
                            return;
                        }
                        if (line1.isEmpty()) {
                            et_line1.setError("*Enter your address line1");
                            et_line1.requestFocus();
                            return;
                        }
                        if (line2.isEmpty()) {
                            et_line2.setError("*Enter your address line2");
                            et_line2.requestFocus();
                            return;
                        }
                        if (pincode.isEmpty()) {
                            atv_pincode.setError("*Enter your pincode");
                            atv_pincode.requestFocus();
                            return;
                        }
                        if (landmark.isEmpty()) {
                            et_landmark.setError("*Enter your landmark");
                            et_landmark.requestFocus();
                            return;
                        }
                        if (name != null && name != "" && phone != null && phone != "" & line1 != null && line1 != ""& line2 != null && line2 != ""& pincode != null &&pincode != ""& landmark != null &&landmark != "") {
                            final Map<String, String> params = new HashMap<>();

                            String para1="?method="+"3";
                            String para2="&id="+deliveryAddressModelList.get(position).getId();
                            String para3="&user_id="+ BSession.getInstance().getUser_id(mContext);
                            String para4="&name="+et_name.getText().toString().trim();
                            String para5="&phone="+et_phone.getText().toString().trim();
                            String para6="&email="+" - ";
                            String para7="&line1="+et_line1.getText().toString().trim();
                            String para8="&line2="+et_line2.getText().toString().trim();
                            String para9="&line3="+et_line3.getText().toString().trim();
                            String para10="&line4="+et_line4.getText().toString().trim();
                            String para11="&pincode="+atv_pincode.getText().toString().trim();
                            String para12="&lanmark="+et_landmark.getText().toString().trim();
                            String para13="&type="+"Home";

                            progressDialog.show();

                            String baseUrl = ProductConfig.user_address+para1+para2+para3+para4+para5+para6+para7+para8+para9+para10+para11+para12+para13;

                            StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("Response", response.toString());
                                    try {
                                        progressDialog.dismiss();
                                        JSONObject jsonResponse = new JSONObject(response);

                                        if (jsonResponse.has("message") && jsonResponse.getString("message").equals("Successfully Registered")) {
                                            ((DeliveryAddressActivity) mContext).deliveryaddress();

                                            bottomSheetDialog.dismiss();
                                            Toast.makeText(mContext, "Your adddress has been updated", Toast.LENGTH_LONG).show();

                                        } else {
                                            Toast.makeText(mContext, "Update failed", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        progressDialog.dismiss();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub
                                    Log.e("Error", error.toString());
                                    progressDialog.dismiss();

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                            requestQueue.add(jsObjRequest);
                            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                                    220000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        } else {
                            Toast.makeText(mContext, "kindly enter all details", Toast.LENGTH_LONG).show();

                        }
                    }
                });
                bottomSheetDialog.show();
            }*/
        });

    }

    @Override
    public int getItemCount() {
        return deliveryAddressModelList.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        ImageView location_edit,delete;
        TextView name,address;
        CardView card;
        RadioGroup radioGroup;
        String alertmessage;
        AlertDialog AAlertDialog;
        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            location_edit=itemView.findViewById(R.id.location_edit);
            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.address);
            card=itemView.findViewById(R.id.card);
            delete=itemView.findViewById(R.id.delete);
            radioGroup=itemView.findViewById(R.id.radiogroup);

        }
    }
}

