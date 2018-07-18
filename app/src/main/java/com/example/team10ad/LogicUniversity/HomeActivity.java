package com.example.team10ad.LogicUniversity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.team10ad.LogicUniversity.DepartmentHead.AssignDepRepFragment;
import com.example.team10ad.LogicUniversity.DepartmentHead.ChangeCollectionPoint;
import com.example.team10ad.LogicUniversity.DepartmentHead.HODTrackingOrder;
import com.example.team10ad.LogicUniversity.DepartmentHead.HodRequisitionListFragment;
import com.example.team10ad.LogicUniversity.DepartmentHead.ReqListForTrackingOrder;
import com.example.team10ad.team10ad.R;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new DashboardFragment(),"dashboardFrag").commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        NavigationView nvDrawer = findViewById(R.id.nav_view);

        // Clerk menu
        nvDrawer.getMenu().clear();
        nvDrawer.inflateMenu(R.menu.activity_home_drawer);

        // HOD menu
        /*nvDrawer.getMenu().clear();
        nvDrawer.inflateMenu(R.menu.activity_home_hod);*/

        toggle.syncState();
        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectItemDrawer(menuItem);
                return true;
            }
        });
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

    public void selectItemDrawer(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        int id = menuItem.getItemId();
        switch (id) {
            // Clerk
            case R.id.dashboard:
                fragmentClass = DashboardFragment.class;
                break;
            case R.id.inventory:
                fragmentClass = InventoryFragment.class;
                break;
            case R.id.requisition:
                fragmentClass = RequisitionList.class;
                break;
            case R.id.tracking:
                fragmentClass = DashboardFragment.class;
                break;
            case R.id.report:
                fragmentClass = ClerkReportFragment.class;
                break;
            // HOD
            case R.id.dashboardHod:
                fragmentClass = DashboardFragment.class;
                break;
            case R.id.apprejreq:
                fragmentClass = HodRequisitionListFragment.class;
                break;
            case R.id.assignDeptRep:
                fragmentClass = AssignDepRepFragment.class;
                break;
            case R.id.changeCP:
                fragmentClass = ChangeCollectionPoint.class;
                break;
            case R.id.delegateAuthority:
                //HOD tracking order
            case R.id.trackinghod:
                fragmentClass= ReqListForTrackingOrder.class;
                //fragmentClass = HODTrackingOrder.class;
                break;

            case R.id.reportHod:

            case R.id.logout:
                fragmentClass = DashboardFragment.class;
                break;
            default:
                fragmentClass = DashboardFragment.class;
        }
        try
        {
            fragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }
}
