package com.example.electrofix;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
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

public class Request_Adapter extends RecyclerView.Adapter<Request_Adapter.MyViewHolder> implements Filterable {

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rep_n, rep_ph, req_amount, req_status;
        TextView con, rat, neg;

        public MyViewHolder(View itemView) {
            super(itemView);
            rep_n = itemView.findViewById(R.id.rep_uname);
            rep_ph = itemView.findViewById(R.id.rep_phone);
            req_amount = itemView.findViewById(R.id.rep_amount);
            req_status = itemView.findViewById(R.id.rq_status);
            con = itemView.findViewById(R.id.contact);
            rat = itemView.findViewById(R.id.rate);
            neg = itemView.findViewById(R.id.negative);
        }
    }

    private List<RequestList> request_data_list = null;
    private List<Request> arraylist;
    private Context context;
    private String button_status;
    private int req_id;

    public Request_Adapter(List<RequestList> request_data_list) {
        this.request_data_list = request_data_list;
        //this.arraylist = new ArrayList<>(request_data_list);
    }

    @NonNull
    @Override
    public Request_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_list_row, parent, false);
        context = parent.getContext();
        return new Request_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Request_Adapter.MyViewHolder viewHolder, int position) {
        //Repairer repairer = (Repairer) repairer_data_list.get(position);
        viewHolder.rep_n.setText(request_data_list.get(position).getRep_name());
        viewHolder.rep_ph.setText(request_data_list.get(position).getRep_phone());
        viewHolder.req_amount.setText(request_data_list.get(position).getAmount());
        viewHolder.req_status.setText(request_data_list.get(position).getStatus());
        //req_id=request_data_list.get(position).getReq_id();

        //button_status = request_data_list.get(position).getStatus();

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
                    Toast.makeText(context, "request is in progress please wait..." + request_data_list.get(viewHolder.getAdapterPosition()).getReq_id(), Toast.LENGTH_SHORT).show();
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


        viewHolder.rat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("completed")) {

                    final EditText taskEditText = new EditText(context);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Rate Repairer")
                            .setView(taskEditText)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String task = String.valueOf(taskEditText.getText());
                                    if(task.trim().isEmpty()) {
                                        Toast.makeText(context, "Enter a valid value" , Toast.LENGTH_SHORT).show();
                                    } else {
                                        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().rateRepairer(request_data_list.get(viewHolder.getAdapterPosition()).getRep_id(), task);
                                        call.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                Toast.makeText(context, "rateRepairer Successfully", Toast.LENGTH_LONG).show();
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
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("in progress")) {
                    Toast.makeText(context, "request is in progress, rate later" + request_data_list.get(viewHolder.getAdapterPosition()).getReq_id(), Toast.LENGTH_SHORT).show();
                }
                if (request_data_list.get(viewHolder.getAdapterPosition()).getStatus().equals("pending")) {
                    Toast.makeText(context, "request is pending, rate later" + request_data_list.get(viewHolder.getAdapterPosition()).getReq_id(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return request_data_list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Request> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arraylist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Request req : arraylist) {
                    if (req.getStatus().toLowerCase().contains(filterPattern)) {
                        filteredList.add(req);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            request_data_list.clear();
            request_data_list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
