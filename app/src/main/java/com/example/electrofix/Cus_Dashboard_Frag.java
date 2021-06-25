package com.example.electrofix;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Cus_Dashboard_Frag extends Fragment {

    private Activity thiscontext;
//    private List<DashboardData> data_list;
//    private Customer cust;

    private View view;

    public Cus_Dashboard_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cus__dashboard_, container, false);
        thiscontext = getActivity();
        //int getID = getArguments().getInt("id");
        //rep = (Repairer) getArguments().getSerializable("repairer");

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Pending", 74));
        data.add(new ValueDataEntry("InProgress", 30));
        data.add(new ValueDataEntry("Completed", 40));

        pie.data(data);


        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);


//        View view = inflater.inflate(R.layout.fragment_cus__track__req_, container, false);
//        thiscontext = getActivity();
//
//        //cust = (Customer) getArguments().getSerializable("custom");
//
//        final AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
//
//
//
//        Call<List<DashboardData>> call = RetrofitClient.getInstance().getApi().getCusDashBoardData(cust.getId());
//        call.enqueue(new Callback<List<DashboardData>>() {
//            @Override
//            public void onResponse(Call<List<DashboardData>> call, Response<List<DashboardData>> response) {
//                data_list = response.body();
//
//                Pie pie = AnyChart.pie();
//                List<DataEntry> data = new ArrayList<>();
//                data.add(new ValueDataEntry("Pending", data_list.get(0).getPending()));
//                data.add(new ValueDataEntry("InProgress", data_list.get(0).getIn_progress()));
//                data.add(new ValueDataEntry("Completed", data_list.get(0).getCompleted()));
//                pie.data(data);
//
//                anyChartView.setChart(pie);
//            }
//
//            @Override
//            public void onFailure(Call<List<DashboardData>> call, Throwable t) {
//                Toast.makeText(thiscontext, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//
//        return view;

        return view;

    }

}
