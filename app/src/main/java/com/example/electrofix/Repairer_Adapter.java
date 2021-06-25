package com.example.electrofix;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repairer_Adapter extends RecyclerView.Adapter<Repairer_Adapter.MyViewHolder> implements Filterable {


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user_name, area, phone, skill, fare, rat;
        TextView send;
        public MyViewHolder(View itemView) {
            super(itemView);
            user_name=itemView.findViewById(R.id.user_name);
            area=itemView.findViewById(R.id.area);
            phone=itemView.findViewById(R.id.phone);
            skill=itemView.findViewById(R.id.skill);
            fare=itemView.findViewById(R.id.base_fare);
            rat=itemView.findViewById(R.id.rating);
            send=itemView.findViewById(R.id.send_req);
        }
    }

    private List<RepairerList> repairer_data_list=null;
    private List<RepairerList> arraylist;
    private Context context;
    private int cus_id;

    public Repairer_Adapter(List<RepairerList> repairer_data_list, int id) {
        this.repairer_data_list=repairer_data_list;
        this.arraylist = new ArrayList<>(repairer_data_list);
        this.cus_id = id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.repairer_list_row, parent, false);
        context=parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, int position) {
        //Repairer repairer = (Repairer) repairer_data_list.get(position);
        viewHolder.user_name.setText(repairer_data_list.get(position).getUser_name());
        viewHolder.area.setText(repairer_data_list.get(position).getArea());
        viewHolder.phone.setText(repairer_data_list.get(position).getPhone());
        viewHolder.skill.setText(repairer_data_list.get(position).getSkill());
        viewHolder.fare.setText(repairer_data_list.get(position).getBase_fare());
        viewHolder.rat.setText(repairer_data_list.get(position).getRating());

        viewHolder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().sendRequest(cus_id, repairer_data_list.get(viewHolder.getAdapterPosition()).getId());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context, "Request sent Successfully", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(context, Cus_Main_Activity.class);
//                        context.startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


                Toast.makeText(context,"request sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return repairer_data_list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RepairerList> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(arraylist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (RepairerList rep : arraylist) {
                    if(rep.getSkill().toLowerCase().contains(filterPattern)) {
                        filteredList.add(rep);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            repairer_data_list.clear();
            repairer_data_list.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
