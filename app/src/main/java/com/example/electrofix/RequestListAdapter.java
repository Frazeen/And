package com.example.electrofix;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.MyViewHolder> {


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cus_n, cus_ph, req_amount, req_status;
        TextView pos, neg, con, comp;
        String rec_amt;

        public MyViewHolder(View itemView) {
            super(itemView);
            cus_n = itemView.findViewById(R.id.cus_uname);
            cus_ph = itemView.findViewById(R.id.cus_phone);
            req_amount = itemView.findViewById(R.id.amount);
            req_status = itemView.findViewById(R.id.status);
            pos = itemView.findViewById(R.id.positive);
            neg = itemView.findViewById(R.id.negative);
            con = itemView.findViewById(R.id.contact);
            comp = itemView.findViewById(R.id.complete);
        }
    }

    private List<RequestList> request_data_list = null;
    //private List<RequestList> arraylist;
    private Context context;
    private String button_status;

    public RequestListAdapter(List<RequestList> request_data_list) {
        this.request_data_list = request_data_list;
        //this.arraylist = new ArrayList<>(request_data_list);
    }

    @NonNull
    @Override
    public RequestListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.req_list_row, parent, false);
        context = parent.getContext();
        return new RequestListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestListAdapter.MyViewHolder viewHolder, int position) {
        //Repairer repairer = (Repairer) repairer_data_list.get(position);
        viewHolder.cus_n.setText(request_data_list.get(position).getCus_name());
        viewHolder.cus_ph.setText(request_data_list.get(position).getCus_phone());
        viewHolder.req_amount.setText(request_data_list.get(position).getAmount());
        viewHolder.req_status.setText(request_data_list.get(position).getStatus());

        viewHolder.neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("pending")) {
                    Call<ResponseBody> call = RetrofitClient.getInstance().getApi().cancelRequest(request_data_list.get(viewHolder.getAdapterPosition()).getReq_id());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(context, "Request canceled Successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("in progress")) {
                    Toast.makeText(context, "request is in progress cannot cancel" + request_data_list.get(viewHolder.getAdapterPosition()).getReq_id(), Toast.LENGTH_SHORT).show();
                }
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("completed")) {
                    Toast.makeText(context, "request is completed cannot cancel" + request_data_list.get(viewHolder.getAdapterPosition()).getReq_id(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("pending")
                        || request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("in progress")) {
                    //call repairer
                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                    context.startActivity(phoneIntent);
                }
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("completed")) {
                    Toast.makeText(context, "request is completed so you cannot call repairer" , Toast.LENGTH_SHORT).show();
                }

            }
        });


        viewHolder.pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("completed")) {
                    Toast.makeText(context, "request is already completed" + request_data_list.get(viewHolder.getAdapterPosition()).getReq_id(), Toast.LENGTH_SHORT).show();
                }
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("in progress")) {
                    Toast.makeText(context, "request is already in progress" + request_data_list.get(viewHolder.getAdapterPosition()).getReq_id(), Toast.LENGTH_SHORT).show();
                }
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("pending")) {

                    Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateRequest(request_data_list.get(viewHolder.getAdapterPosition()).getReq_id());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(context, "Request accepted Successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }
        });


        viewHolder.comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("completed")) {
                    Toast.makeText(context, "request is already completed" + request_data_list.get(viewHolder.getAdapterPosition()).getReq_id(), Toast.LENGTH_SHORT).show();
                }
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("in progress")) {

                    final EditText taskEditText = new EditText(context);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Add a recieved amount")
                            .setView(taskEditText)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String task = String.valueOf(taskEditText.getText());
                                    if(task.trim().isEmpty()) {
                                        Toast.makeText(context, "Enter a valid value" , Toast.LENGTH_SHORT).show();
                                    } else {
                                        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().completeRequest(request_data_list.get(viewHolder.getAdapterPosition()).getReq_id(), task);
                                        call.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                Toast.makeText(context, "Request completed Successfully", Toast.LENGTH_LONG).show();
                                            }
                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }

                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();
                }
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("pending")) {
                    Toast.makeText(context, "first place it in progress" + request_data_list.get(viewHolder.getAdapterPosition()).getReq_id(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return request_data_list.size();
    }

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Add a recieved amount")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        Toast.makeText(context, task , Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

}
