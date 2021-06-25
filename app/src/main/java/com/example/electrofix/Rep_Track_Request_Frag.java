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
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Rep_Track_Request_Frag extends Fragment {

    private Activity thiscontext;
    private RecyclerView recyclerView;
    private List<RequestList> req_list;
    private List repairer_list = new ArrayList<>();
    private RequestListAdapter requestListAdapter;
    //    private Spinner area, skill, fare, sort;
//    String area_name, skill_name, fare_name, sort_name;
//    long area_id = 0, skill_id = 0, fare_id = 0, sort_id = 0;
//    private Button find_rep;
    private Repairer rep;
    private View view;
//    SearchView searchView;


    public Rep_Track_Request_Frag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rep__track__request, container, false);
        thiscontext = getActivity();

        rep = (Repairer) getArguments().getSerializable("repairer");

        Call<List<RequestList>> call = RetrofitClient.getInstance().getApi().getRequestsForRep(rep.getId());
        call.enqueue(new Callback<List<RequestList>>() {
            @Override
            public void onResponse(Call<List<RequestList>> call, Response<List<RequestList>> response) {
                req_list = response.body();
                recyclerView = view.findViewById(R.id.req_rcv);
                requestListAdapter = new RequestListAdapter(req_list);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(thiscontext);
                recyclerView.setLayoutManager(manager);
                recyclerView.addItemDecoration(new DividerItemDecoration(thiscontext, LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(requestListAdapter);
            }

            @Override
            public void onFailure(Call<List<RequestList>> call, Throwable t) {
                Toast.makeText(thiscontext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
