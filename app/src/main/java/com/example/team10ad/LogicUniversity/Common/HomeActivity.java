package com.example.team10ad.LogicUniversity.Common;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Clerk.ApproveCollectionPoint;
import com.example.team10ad.LogicUniversity.Clerk.ClerkDashboard;
import com.example.team10ad.LogicUniversity.Clerk.Inventory;
import com.example.team10ad.LogicUniversity.DepartmentHead.AssignDepRep;
import com.example.team10ad.LogicUniversity.DepartmentHead.OrderHistory;
import com.example.team10ad.LogicUniversity.DepartmentHead.HodDashboard;
//import com.example.team10ad.LogicUniversity.DepartmentHead.HodReportFragment;
import com.example.team10ad.LogicUniversity.DepartmentHead.HodRequisitionList;
import com.example.team10ad.LogicUniversity.DepartmentHead.ReqListForTrackingOrder;
import com.example.team10ad.LogicUniversity.DepartmentRep.RepScanQRFragment;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Service.UserService;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Author: Chit Su Shine, Wint Yadanat Htet, Khaing Myat, Htet Wai Yan
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
                    TextView profileName = findViewById(R.id.profileName);
                    profileName.setText(user.getFullName());
                    TextView userRole = findViewById(R.id.userRole);
                    String role = getRole(user.getRole());
                    userRole.setText(role);
                    // Storing current login user's info
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    MyApp.getInstance().getPreferenceManager().putString(Constants.USER_GSON, json);
                    // Navigating related menus
                    if (user.getRole() == Constants.CLERK_ROLE) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ClerkDashboard()).commit();
                        nvDrawer.inflateMenu(R.menu.activity_home_clerk);
                        nvDrawer.setCheckedItem(R.id.dashboard);
                    } else if (user.getRole() == Constants.HOD_ROLE) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HodDashboard()).commit();
                        nvDrawer.inflateMenu(R.menu.activity_home_hod);
                        nvDrawer.setCheckedItem(R.id.dashboardHod);
                    } else if (user.getRole() == Constants.DEP_REP_ROLE) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ReqListForTrackingOrder()).commit();
                        nvDrawer.inflateMenu(R.menu.activity_home_rep);
                        nvDrawer.setCheckedItem(R.id.trackRep);
                    } else if (user.getRole() == Constants.EMP_ROLE) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ReqListForTrackingOrder()).commit();
                        nvDrawer.inflateMenu(R.menu.activity_home_emp);
                        nvDrawer.setCheckedItem(R.id.trackRep);
                    } else if (user.getRole() == Constants.TEMP_HOD_ROLE) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HodRequisitionList()).commit();
                        nvDrawer.inflateMenu(R.menu.activity_home_temphod);
                        nvDrawer.setCheckedItem(R.id.temp_approve);
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
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                finishAffinity();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                AboutFragment aboutFragment = new AboutFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, aboutFragment).addToBackStack(null).commit();
                break;
            case R.id.website:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(Constants.WEB_VER_URL));
                startActivity(intent);
                break;
            case R.id.noti:
                Notification notification = new Notification();
                FragmentManager fragmentMg = getSupportFragmentManager();
                fragmentMg.beginTransaction().replace(R.id.content_frame, notification).addToBackStack(null).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItemDrawer(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        int id = menuItem.getItemId();

        // Logout
        if (id == R.id.logout) {
            MyApp.getInstance().getPreferenceManager().clearLoginData();
            finishAffinity();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else {
            switch (id) {
                // Temporary HOD
                case R.id.temp_approve:
                    fragmentClass = HodRequisitionList.class;
                    break;

                // Clerk
                case R.id.dashboard:
                    fragmentClass = ClerkDashboard.class;
                    break;
                case R.id.inventory:
                    fragmentClass = Inventory.class;
                    break;
                case R.id.cpreq:
                    fragmentClass = ApproveCollectionPoint.class;
                    break;

                // Dep Rep & Employee
                case R.id.scanRep:
                    fragmentClass = RepScanQRFragment.class;
                    break;
                case R.id.trackRep:
                    fragmentClass = ReqListForTrackingOrder.class;
                    break;
                case R.id.orderHisRep:
                    fragmentClass = OrderHistory.class;
                    break;

                // HOD
                case R.id.dashboardHod:
                    fragmentClass = HodDashboard.class;
                    break;
                case R.id.assignDeptRep:
                    fragmentClass = AssignDepRep.class;
                    break;
                case R.id.orderhishod:
                    fragmentClass = OrderHistory.class;
                    break;
                default:
                    fragmentClass = ClerkDashboard.class;
            }
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.content_frame, fragment, menuItem.getItemId() + "");
            ft.addToBackStack(null).commit();
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
        }
    }

    // Processing QR results
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            // If QR code has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(MyApp.getInstance(), Constants.REP_RES_NOT_FOUND, Toast.LENGTH_LONG).show();
            } else {
                // If QR contains data
                try {
                    String qrCode = result.getContents().toString();
                    RepScanQRFragment.showData(qrCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MyApp.getInstance(), Constants.REP_SCAN_ERROR_MSG + result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Getting the role in text
    public String getRole(int role) {
        switch (role) {
            case Constants.CLERK_ROLE:
                return Constants.CLERK;
            case Constants.HOD_ROLE:
                return Constants.HOD;
            case Constants.EMP_ROLE:
                return Constants.EMPLOYEE;
            case Constants.DEP_REP_ROLE:
                return Constants.DEP_REP;
            case Constants.TEMP_HOD_ROLE:
                return Constants.TEMP_HOD;
            default:
                return "";
        }
    }
}
