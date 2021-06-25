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

public class Rep_Main_Activity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Repairer rep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rep_nav_draw_layout);

        rep = (Repairer) getIntent().getSerializableExtra("repairer");

        Toolbar toolbar = findViewById(R.id.rep_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.rep_draw_layout);
        NavigationView navigationView = findViewById(R.id.rep_nav_view);

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
                    case R.id.rep_dashboard:
                        Rep_Dashboard_Frag rep_dashboard_frag = new Rep_Dashboard_Frag();
                        Bundle repData4 = new Bundle();
                        repData4.putInt("id", rep.getId());
                        rep_dashboard_frag.setArguments(repData4);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.rep_frag_container, rep_dashboard_frag)
                                .commit();
                        break;
                    case R.id.rep_update_profile:
                        Rep_Update_Profile_Frag rep_update_profile_frag = new Rep_Update_Profile_Frag();
                        Bundle repData = new Bundle();
                        repData.putSerializable("repairer", rep);
                        rep_update_profile_frag.setArguments(repData);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.rep_frag_container, rep_update_profile_frag)
                                .commit();
                        break;
                    case R.id.rep_track_req:
                        Rep_Track_Request_Frag rep_track_request_frag = new Rep_Track_Request_Frag();
                        Bundle repData1 = new Bundle();
                        repData1.putSerializable("repairer", rep);
                        rep_track_request_frag.setArguments(repData1);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.rep_frag_container, rep_track_request_frag)
                                .commit();
                        break;
                    case R.id.payment:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.rep_frag_container, new Payment())
                                .commit();
                        break;
                    case R.id.help_center_rep:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.rep_frag_container, new Help_Center_Frag())
                                .commit();
                        break;

                    case R.id.logout:
                        Intent intent = new Intent(Rep_Main_Activity.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Rep_Main_Activity.this.finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rep_frag_container, new Rep_Dashboard_Frag())
                    .commit();
            navigationView.setCheckedItem(R.id.rep_dashboard);
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
