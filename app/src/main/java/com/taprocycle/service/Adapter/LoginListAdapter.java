package com.taprocycle.service.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.taprocycle.service.R;

import com.taprocycle.service.test.model.LoginModel;

import java.util.List;


public class LoginListAdapter extends RecyclerView.Adapter<LoginListAdapter.MailViewHolder> {

    List<LoginModel> LoginModelist;
    private Context mContext;
    AlertDialog dialog;
    ProgressDialog progressDialog;

    public LoginListAdapter(Context mContext, List<LoginModel> LoginModelist) {
        this.mContext = mContext;
        this.LoginModelist = LoginModelist;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.login_ids, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final LoginModel model = LoginModelist.get(position);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Updating.....");
        holder.login_id.setText(model.getLogin_id());
        holder.name.setText(model.getName());
        holder.delete_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* final Map<String, String> params = new HashMap<>();
                String para1="?user_id="+BSession.getInstance().getUser_id(mContext);
                String para2="&address_id="+ LoginModelist.get(position).getId();
                progressDialog.show();

                String baseUrl = ProductConfig.user_address_delete+para1+para2;

                StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonResponse = new JSONObject(response);

                            if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                                ((DeliveryAddressActivity) mContext).deliveryaddress();

                                bottomSheetDialog.dismiss();
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
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
            }
        });




    }

    @Override
    public int getItemCount() {
        return LoginModelist.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        TextView login_id,name;
        ImageView delete_login;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            login_id = itemView.findViewById(R.id.loginid);
            name = itemView.findViewById(R.id.name);
            delete_login=itemView.findViewById(R.id.delete_login);


        }
    }
}