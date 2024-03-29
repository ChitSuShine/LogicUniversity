package com.nusiss.team10ad.LogicUniversity.DepartmentHead;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nusiss.team10ad.LogicUniversity.Model.User;
import com.nusiss.team10ad.LogicUniversity.Service.ServiceGenerator.ServiceGenerator;
import com.nusiss.team10ad.LogicUniversity.Service.UserService;
import com.nusiss.team10ad.LogicUniversity.Util.Constants;
import com.nusiss.team10ad.LogicUniversity.Util.MyApp;
import com.nusiss.team10ad.LogicUniversity.Util.UserAdapter;
import com.nusiss.team10ad.team10ad.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// Author: Chit Su Shine
public class AssignDepRep extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<User> resultedUsers;
    ListView employeeDetailView;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

    public AssignDepRep() {
    }

    public static AssignDepRep newInstance(String param1, String param2) {
        AssignDepRep fragment = new AssignDepRep();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_assign_dep_rep, container, false);

        // Getting current user's info & store in shared preferences
        Gson gson = new Gson();
        String json = MyApp.getInstance().getPreferenceManager().getString(Constants.USER_GSON);
        final User user = gson.fromJson(json, User.class);

        final UserService userService = ServiceGenerator.createService(UserService.class, token);
        // Showing current Dep Rep
        Call<List<User>> repCall = userService.getRepByDeptId(Constants.DEP_REP_ROLE, user.getDepId());
        repCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    TextView currentRepName = view.findViewById(R.id.currentRepName);
                    currentRepName.setText(response.body().get(0).getFullName());
                } else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });


        Call<List<User>> call = userService.getUsersByDeptId(user.getDepId());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    resultedUsers = response.body();
                    final UserAdapter adapter = new UserAdapter(getContext(), R.layout.row_usersforhod, resultedUsers);
                    employeeDetailView = (ListView) view.findViewById(R.id.repListView);
                    employeeDetailView.setAdapter(adapter);
                    employeeDetailView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            final User delegatedUser = resultedUsers.get(i);
                            if (delegatedUser.getRole() != Constants.DEP_REP_ROLE) {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle(Constants.ASSIGN_DEP_REP)
                                        .setMessage(Constants.ASSIGN_CONFIRM_MSG)
                                        .setCancelable(false)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Call<User> call = userService.assignDepRep(delegatedUser.getUserId());
                                                call.enqueue(new Callback<User>() {
                                                    @Override
                                                    public void onResponse(Call<User> call, Response<User> response) {
                                                        if (response.isSuccessful()) {
                                                            Toast.makeText(MyApp.getInstance(), Constants.ASSIGN_SUCCESS_MSG, Toast.LENGTH_SHORT).show();
                                                            // Reload the fragment
                                                            Fragment fragment = getFragmentManager().findFragmentByTag(R.id.assignDeptRep + "");
                                                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                            ft.detach(fragment);
                                                            ft.attach(fragment);
                                                            ft.commit();
                                                        } else {
                                                            Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<User> call, Throwable t) {
                                                        Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .setIcon(R.drawable.alert)
                                        .show();
                            } else {
                                new android.support.v7.app.AlertDialog.Builder(getContext())
                                        .setTitle(Constants.ASSIGN_DEP_REP)
                                        .setMessage(Constants.ASSIGN_WARNING_MSG)
                                        .setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}