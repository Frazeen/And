package com.example.electrofix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class Cus_Main_Activity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Customer cus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cus_nav_draw_layout);

        cus = (Customer) getIntent().getSerializableExtra("customer");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.cus_draw_layout);
        NavigationView navigationView = findViewById(R.id.cus_nav_view);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open_nav_drawer,
                R.string.close_nav_drawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        Cus_Dashboard_Frag cus_dashboard_frag = new Cus_Dashboard_Frag();
                        Bundle d = new Bundle();
                        d.putSerializable("custom", cus);
                        cus_dashboard_frag.setArguments(d);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.cus_frag_container, cus_dashboard_frag)
                                .commit();
                        break;
                    case R.id.update_profile:
                        Cus_Update_Profile_Frag cus_update_profile_frag = new Cus_Update_Profile_Frag();
                        Bundle cusData = new Bundle();
                        cusData.putSerializable("customer", cus);
                        cus_update_profile_frag.setArguments(cusData);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.cus_frag_container, cus_update_profile_frag)
                                .commit();
                        break;
                    case R.id.req_rep:
                        Cus_Req_Rep_Frag cus_req_rep_frag = new Cus_Req_Rep_Frag();
                        Bundle cusData1 = new Bundle();
                        cusData1.putSerializable("customer", cus);
                        cus_req_rep_frag.setArguments(cusData1);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.cus_frag_container, cus_req_rep_frag)
                                .commit();
                        break;
                    case R.id.help_center:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.cus_frag_container, new Help_Center_Frag())
                                .commit();
                        break;

                    case R.id.track_req:
                        Cus_Track_Req_Frag cus_track_req_frag = new Cus_Track_Req_Frag();
                        Bundle cusData2 = new Bundle();
                        cusData2.putSerializable("customer", cus);
                        cus_track_req_frag.setArguments(cusData2);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.cus_frag_container, cus_track_req_frag)
                                .commit();
                        break;

                    case R.id.logout_cus:
                        Intent intent = new Intent(Cus_Main_Activity.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Cus_Main_Activity.this.finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.cus_frag_container, new Cus_Dashboard_Frag())
                    .commit();
            navigationView.setCheckedItem(R.id.dashboard);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
