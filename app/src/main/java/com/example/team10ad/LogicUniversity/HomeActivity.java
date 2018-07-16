package com.example.team10ad.LogicUniversity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.team10ad.team10ad.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Report2Fragment.OnFragmentInteractionListener ,RequisitionList.OnFragmentInteractionListener,RequisitionDetail.OnFragmentInteractionListener
        ,ReqFilter.OnFragmentInteractionListener{
    //data for pie chart
    int qty[]={89,50,45,30};
    String name[]={"Pen","Pencil","Stapler","Clip"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupPieChart();
        //link to requisition list screen
        LinearLayout inv=(LinearLayout)findViewById(R.id.inventoryID);
        inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InventoryFragement inventoryFragement=new InventoryFragement();
                FragmentManager invfm=getSupportFragmentManager();
                invfm.beginTransaction().replace(R.id.content_frame,inventoryFragement).commit();
            }
        });
        LinearLayout req=(LinearLayout)findViewById(R.id.requisitionID);
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequisitionList requisitionList=new RequisitionList();
                FragmentManager fm=getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame,requisitionList).commit();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    //Set pie chart in dashboard
    private void setupPieChart() {
        List<PieEntry> pieEntries=new ArrayList<>();
        for(int i=0;i<qty.length;i++){
            pieEntries.add(new PieEntry(qty[i],name[i]));
        }
        PieDataSet pieDataSet=new PieDataSet(pieEntries,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData=new PieData();
        pieData.setDataSet(pieDataSet);

        PieChart pieChart=(PieChart)findViewById(R.id.piechart);
        pieChart.setData(pieData);
        pieChart.getDescription().setText("Top 5 frequent ordered items in 2018");
        pieChart.animateX(1000);
        pieChart.invalidate();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dashboard) {
            finish();
            Intent i=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(i);
        } else if (id == R.id.inventory) {
            setTitle("Inventory");
            InventoryFragement inventoryFragement=new InventoryFragement();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,inventoryFragement).commit();

        } else if (id == R.id.requisition) {
            setTitle("Requistion list");
            RequisitionList requisitionList = new RequisitionList();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,requisitionList).commit();

        } else if (id == R.id.tracking) {

        } else if (id == R.id.report) {
            setTitle("Report");
            ReportFragment reportFragment = new ReportFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,reportFragment).commit();

        } else if (id == R.id.nav_share) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
