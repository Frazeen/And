package com.example.electrofix;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Rep_Dashboard_Frag extends Fragment {

    private Activity thiscontext;
    //private Repairer rep;
    private View view;

    public Rep_Dashboard_Frag() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rep__dashboard_, container, false);
        thiscontext = getActivity();
        //int getID = getArguments().getInt("id");
        //rep = (Repairer) getArguments().getSerializable("repairer");

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Pending", 74));
        data.add(new ValueDataEntry("InProgress", 30));
        data.add(new ValueDataEntry("Completed", 40));

        pie.data(data);



        AnyChartView anyChartView = view.findViewById(R.id.rep_any_chart_view);
        anyChartView.setChart(pie);

        return view;
    }

}
