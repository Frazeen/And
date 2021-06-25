package com.example.electrofix;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Cus_Req_Rep_Frag extends Fragment {

    private Activity thiscontext;
    private RecyclerView recyclerView;
    private List<RepairerList> rep_list;
    private List repairer_list = new ArrayList<>();
    private Repairer_Adapter repairer_adapter;
    private Spinner area, skill, fare, sort;
    String area_name, skill_name, fare_name, sort_name;
    long area_id = 0, skill_id = 0, fare_id = 0, sort_id = 0;
    private Button find_rep;
    SearchView searchView;
    private Customer cus;


    public Cus_Req_Rep_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cus__req__rep_, container, false);
        thiscontext = getActivity();

        cus = (Customer) getArguments().getSerializable("customer");

        recyclerView = view.findViewById(R.id.req_rep_rcv);
        find_rep = view.findViewById(R.id.find_repairer);
        area = view.findViewById(R.id.area_sp);
        skill = view.findViewById(R.id.skill_sp);
        fare = view.findViewById(R.id.base_fare_sp);
        sort = view.findViewById(R.id.sort_by);

        addItemsOnAreaSpinner();
        addItemsOnSkillSpinner();
        addItemsOnFareSpinner();
        addItemsOnSortBySpinner();

        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area_name = parent.getItemAtPosition(position).toString().toLowerCase();
                area_id = id;
                if (area_id != 0) {
                    Toast.makeText(parent.getContext(), area_name, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(parent.getContext(), area_name, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        skill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                skill_name = parent.getItemAtPosition(position).toString().toLowerCase();
                skill_id = id;
                if (skill_id != 0) {
                    Toast.makeText(parent.getContext(), skill_name, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(parent.getContext(), skill_name, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        fare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fare_name = parent.getItemAtPosition(position).toString().toLowerCase();
                fare_id = id;
                if (fare_id != 0) {
                    Toast.makeText(parent.getContext(), fare_name, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(parent.getContext(), fare_name, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sort_name = parent.getItemAtPosition(position).toString().toLowerCase();
                sort_id = id;
                if (sort_id != 0) {
                    Toast.makeText(parent.getContext(), sort_name, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(parent.getContext(), sort_name, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        find_rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RepairerDataPrepare();
            }
        });

//        recyclerView = view.findViewById(R.id.req_rep_rcv);
//        repairer_adapter = new Repairer_Adapter(repairer_list);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(thiscontext);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(thiscontext, LinearLayoutManager.VERTICAL));
//        recyclerView.setAdapter(repairer_adapter);

//        searchView = view.findViewById(R.id.search_rep);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                repairer_adapter.getFilter().filter(newText);
//                return false;
//            }
//        });


        return view;
    }

    public void RepairerDataPrepare() {

        TextView error1 = (TextView) area.getSelectedView();
        if (area_id == 0) {
            error1.setError("");
            error1.setTextColor(Color.RED);
            error1.setText("Select Area");
            return;
        }

        TextView error2 = (TextView) skill.getSelectedView();
        if (skill_id == 0) {
            error2.setError("");
            error2.setTextColor(Color.RED);
            error2.setText("Select Skill");
            return;
        }

        TextView error3 = (TextView) fare.getSelectedView();
        if (fare_id == 0) {
            error3.setError("");
            error3.setTextColor(Color.RED);
            error3.setText("Select Base Fare");
            return;
        }

        TextView error4 = (TextView) sort.getSelectedView();
        if (sort_id == 0) {
            error4.setError("");
            error4.setTextColor(Color.RED);
            error4.setText("Select Sort By");
            return;
        }

        Call<List<RepairerList>> call = RetrofitClient.getInstance().getApi().getFilteredRepairers(area_name, skill_name, fare_name, sort_name);
        call.enqueue(new Callback<List<RepairerList>>() {
            @Override
            public void onResponse(Call<List<RepairerList>> call, Response<List<RepairerList>> response) {
                rep_list = response.body();
                repairer_adapter = new Repairer_Adapter(rep_list, cus.getId() );
                RecyclerView.LayoutManager manager = new LinearLayoutManager(thiscontext);
                recyclerView.setLayoutManager(manager);
                recyclerView.addItemDecoration(new DividerItemDecoration(thiscontext, LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(repairer_adapter);
            }

            @Override
            public void onFailure(Call<List<RepairerList>> call, Throwable t) {
                Toast.makeText(thiscontext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });






//        Repairer repairer = new Repairer("hassannnnnnnnnnn", "johar town", "03059328783", "mobile repairer");
//        repairer_list.add(repairer);
//
//        repairer = new Repairer("umer", "multan road", "03059328777", "mobile repairer");
//        repairer_list.add(repairer);
//
//        repairer = new Repairer("momin.shahid", "thokar", "03051234567", "computer repairer");
//        repairer_list.add(repairer);
//
//        repairer = new Repairer("atif.mumtaz", "gulberg", "03159328703", "computer repairer");
//        repairer_list.add(repairer);
//
//        repairer = new Repairer("umair", "anarkali", "03059345783", "mobile repairer");
//        repairer_list.add(repairer);
//
//        repairer = new Repairer("hamza.taseer", "bahria town", "03129328783", "all electronics");
//        repairer_list.add(repairer);
//
//        repairer = new Repairer("hassan.rasheed", "hussain town", "03079328000", "all electronics");
//        repairer_list.add(repairer);
//
//        repairer = new Repairer("zubair.ahmed", "gulshan ravi", "0305930000", "all electronics");
//        repairer_list.add(repairer);



//        recyclerView = view.findViewById(R.id.req_rep_rcv);
//        repairer_adapter = new Repairer_Adapter(repairer_list);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(thiscontext);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(thiscontext, LinearLayoutManager.VERTICAL));
//        recyclerView.setAdapter(repairer_adapter);

    }

    public void addItemsOnAreaSpinner() {
        List<String> skill_list = new ArrayList<String>();
        skill_list.add("Select Area");
        skill_list.add("Hafeez Center");
        skill_list.add("Hall Road");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(thiscontext,
                R.layout.area_spinner_item, skill_list);
        dataAdapter.setDropDownViewResource(R.layout.area_spinner_item);
        area.setAdapter(dataAdapter);
    }

    public void addItemsOnSkillSpinner() {
        List<String> skill_list = new ArrayList<String>();
        skill_list.add("Select Skill");
        skill_list.add("Mobile Repairer");
        skill_list.add("Computer Repairer");
        skill_list.add("Other Electronics");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(thiscontext,
                R.layout.skill_spinner_item, skill_list);
        dataAdapter.setDropDownViewResource(R.layout.skill_spinner_item);
        skill.setAdapter(dataAdapter);
    }


    public void addItemsOnFareSpinner() {
        List<String> skill_list = new ArrayList<String>();
        skill_list.add("Select Base Fare");
        skill_list.add("500");
        skill_list.add("600");
        skill_list.add("700");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(thiscontext,
                R.layout.base_fare_spinner_item, skill_list);
        dataAdapter.setDropDownViewResource(R.layout.base_fare_spinner_item);
        fare.setAdapter(dataAdapter);
    }

    public void addItemsOnSortBySpinner() {
        List<String> skill_list = new ArrayList<String>();
        skill_list.add("Sort By");
        skill_list.add("Area");
        skill_list.add("Skill");
        skill_list.add("Base_Fare");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(thiscontext,
                R.layout.sort_by_spinner, skill_list);
        dataAdapter.setDropDownViewResource(R.layout.sort_by_spinner);
        sort.setAdapter(dataAdapter);
    }


}
