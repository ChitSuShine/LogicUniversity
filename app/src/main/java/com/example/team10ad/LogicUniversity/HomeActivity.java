package com.example.team10ad.LogicUniversity;

import android.content.Intent;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.DepartmentHead.AssignDepRepFragment;
import com.example.team10ad.LogicUniversity.DepartmentHead.ChangeCollectionPoint;
import com.example.team10ad.LogicUniversity.DepartmentHead.DelegateAuthorityFragment;
import com.example.team10ad.LogicUniversity.DepartmentHead.HODTrackingOrder;
import com.example.team10ad.LogicUniversity.DepartmentHead.HodDashboardFragment;
//import com.example.team10ad.LogicUniversity.DepartmentHead.HodReportFragment;
import com.example.team10ad.LogicUniversity.DepartmentHead.HodRequisitionListFragment;
import com.example.team10ad.LogicUniversity.DepartmentHead.ReqListForTrackingOrder;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.RequisitionService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Service.UserService;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final NavigationView nvDrawer = findViewById(R.id.nav_view);
        nvDrawer.getMenu().clear();

        // To check user role and set related menu & dashboard
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        UserService userService = ServiceGenerator.createService(UserService.class, token);
        Call<User> call = userService.getLoginUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user.getRole() == Constants.CLERK_ROLE) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new DashboardFragment()).commit();
                        nvDrawer.inflateMenu(R.menu.activity_home_drawer);
                    } else if (user.getRole() == Constants.HOD_ROLE) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HodDashboardFragment()).commit();
                        nvDrawer.inflateMenu(R.menu.activity_home_hod);
                    }
                } else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
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

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItemDrawer(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        int id = menuItem.getItemId();

        //logout
        if (id == R.id.logout) {
            MyApp.getInstance().getPreferenceManager().clearLoginData();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
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
                fragmentClass = ClerkMapDeliveryPoint.class;
                break;
            case R.id.report:
                fragmentClass = ClerkReportFragment.class;
                break;

            // HOD
            case R.id.dashboardHod:
                fragmentClass = HodDashboardFragment.class;
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
                fragmentClass = DelegateAuthorityFragment.class;
                break;
            case R.id.trackinghod:
                fragmentClass = ReqListForTrackingOrder.class;
                break;
            case R.id.reportHod:
                //fragmentClass = HodReportFragment.class;
                //break;
            default:
                fragmentClass = DashboardFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();

    }



}
