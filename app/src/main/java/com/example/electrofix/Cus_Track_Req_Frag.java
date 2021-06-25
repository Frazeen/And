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
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Cus_Track_Req_Frag extends Fragment {

    private Activity thiscontext;
    private RecyclerView recyclerView;
    private List<RequestList> req_list;
    private Request_Adapter request_adapter;
    SearchView searchView;
    private Customer cus;

    public Cus_Track_Req_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cus__track__req_, container, false);
        thiscontext = getActivity();

        recyclerView = view.findViewById(R.id.track_req_rcv);
        cus = (Customer) getArguments().getSerializable("customer");

        Call<List<RequestList>> call = RetrofitClient.getInstance().getApi().getRequestsForCus(cus.getId());
        call.enqueue(new Callback<List<RequestList>>() {
            @Override
            public void onResponse(Call<List<RequestList>> call, Response<List<RequestList>> response) {
                req_list = response.body();
                request_adapter = new Request_Adapter(req_list);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(thiscontext);
                recyclerView.setLayoutManager(manager);
                recyclerView.addItemDecoration(new DividerItemDecoration(thiscontext, LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(request_adapter);
            }

            @Override
            public void onFailure(Call<List<RequestList>> call, Throwable t) {
                Toast.makeText(thiscontext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return view;

//        RequestDataPrepare();
//        recyclerView = view.findViewById(R.id.track_req_rcv);
//        request_adapter = new Request_Adapter(request_list);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(thiscontext);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(thiscontext, LinearLayoutManager.VERTICAL));
//        recyclerView.setAdapter(request_adapter);
//
//        searchView = view.findViewById(R.id.search_req);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                request_adapter.getFilter().filter(newText);
//                return false;
//            }
//        });


//        return view;
    }

//    public void RequestDataPrepare() {
//
//        Request repairer = new Request("hassan.munir", "johar town", "03059328783", "mobile repairer", "pending");
//        request_list.add(repairer);
//
//        repairer = new Request("umer", "multan road", "03059328777", "mobile repairer", "completed");
//        request_list.add(repairer);
//
//        repairer = new Request("momin.shahid", "thokar", "03051234567", "computer repairer", "pending");
//        request_list.add(repairer);
//
//        repairer = new Request("atif.mumtaz", "gulberg", "03159328703", "computer repairer", "completed");
//        request_list.add(repairer);
//
//        repairer = new Request("umair", "anarkali", "03059345783", "mobile repairer", "pending");
//        request_list.add(repairer);
//
//        repairer = new Request("hamza.taseer", "bahria town", "03129328783", "all electronics", "completed");
//        request_list.add(repairer);
//
//        repairer = new Request("hassan.rasheed", "hussain town", "03079328000", "all electronics", "pending");
//        request_list.add(repairer);
//
//        repairer = new Request("zubair.ahmed", "gulshan ravi", "03059300000", "all electronics", "completed");
//        request_list.add(repairer);
//
//    }

}
