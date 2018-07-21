package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.Delegation;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.DelegationService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Service.UserService;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.LogicUniversity.Util.UserAdapter;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DelegateAuthorityFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    List<User> resultedUsers;
    ListView employeeDetailView;

    private Delegation delegation;
    private String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

    public DelegateAuthorityFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DelegateAuthorityFragment newInstance(String param1, String param2) {
        DelegateAuthorityFragment fragment = new DelegateAuthorityFragment();
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
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_delegate_authority, container, false);

        final TextView selectedEndDate = view.findViewById(R.id.selectedEndDate);
        ImageButton endDateButton=view.findViewById(R.id.btn_endDate);
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // startDate picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                selectedEndDate.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        // Getting current user's info & store in shared preferences
        Gson gson = new Gson();
        String json = MyApp.getInstance().getPreferenceManager().getString(Constants.USER_GSON);
        final User user = gson.fromJson(json, User.class);

        getPreviousAuthority(user,view);
        // Calling Web API for services
        UserService userService = ServiceGenerator.createService(UserService.class, token);
        Call<List<User>> call = userService.getUsersByDeptId(user.getDepId());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    resultedUsers = response.body();
                    final UserAdapter adapter = new UserAdapter(getContext(), R.layout.row_usersforhod, resultedUsers);
                    employeeDetailView = (ListView) view.findViewById(R.id.employeeListView);
                    employeeDetailView.setAdapter(adapter);
                    employeeDetailView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            DelegateDetailFragment detailFragment = new DelegateDetailFragment();
                            User delegatedUser = resultedUsers.get(i);
                            Gson gson = new Gson();
                            String json = gson.toJson(delegatedUser);
                            Bundle b = new Bundle();
                            b.putString(Constants.DELEGATED_USER, json);
                            detailFragment.setArguments(b);
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.content_frame, detailFragment).commit();
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

        // Update Delegation
        Button updateDelegation = view.findViewById(R.id.btn_delegateUpdate);
        updateDelegation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewClick) {
                DelegationService delegationService = ServiceGenerator.createService(DelegationService.class, token);
                delegation.setEndDate(selectedEndDate.getText().toString());
                Call<Delegation> dCall = delegationService.updateDelegation(delegation);
                dCall.enqueue(new Callback<Delegation>() {
                    @Override
                    public void onResponse(Call<Delegation> call, Response<Delegation> response) {
                        if (response.isSuccessful()) {
                            getPreviousAuthority(user, view);
                        } else {
                            Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Delegation> call, Throwable t) {
                        Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Delete Delegation
        Button deleteDelegation = view.findViewById(R.id.btn_delegateCancel);
        deleteDelegation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewClick) {
                if(delegation.getActive()!=0)
                {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(Constants.DELEGATE_AUTHORITY)
                            .setMessage(Constants.DELEGATE_CONFIRM_MSG)
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    DelegationService delegationService = ServiceGenerator.createService(DelegationService.class, token);
                                    Call<Delegation> dCall = delegationService.cancelDelegation(delegation);
                                        dCall.enqueue(new Callback<Delegation>() {
                                            @Override
                                            public void onResponse(Call<Delegation> call, Response<Delegation> response) {
                                                if (response.isSuccessful()) {
                                                    getPreviousAuthority(user, view);
                                                } else {
                                                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Delegation> call, Throwable t) {
                                                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else
                {
                    new android.support.v7.app.AlertDialog.Builder(getContext())
                            .setTitle(Constants.DELEGATE_AUTHORITY)
                            .setMessage(Constants.DELEGATE_WARNING_MSG)
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

        return view;
    }

    public void getPreviousAuthority(User user, final View view)
    {
        final DelegationService delegationService = ServiceGenerator.createService(DelegationService.class, token);
        Call<Delegation> dCall = delegationService.getAuthorityUser(user.getDepId());
        dCall.enqueue(new Callback<Delegation>() {
            @Override
            public void onResponse(Call<Delegation> call, Response<Delegation> response) {
                if (response.isSuccessful()) {
                    delegation = response.body();
                    TextView currentEmployeeName = view.findViewById(R.id.currentEmployeeName);
                    TextView startDate = view.findViewById(R.id.delegateStartDate);
                    TextView selectedEndDate = view.findViewById(R.id.selectedEndDate);
                    if (delegation.getActive()==0) {
                        currentEmployeeName.setText(Constants.NO_DELEGATION);
                        startDate.setVisibility(View.INVISIBLE);
                        selectedEndDate.setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.delegateEndDate).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.dateLayout).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.label_delegateStartDate).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.label_currentEmployeeName).setVisibility(View.INVISIBLE);
                    } else {
                        currentEmployeeName.setText(delegation.getUsername());
                        startDate.setText(delegation.getStartDate());
                        selectedEndDate.setText(delegation.getEndDate());
                    }
                } else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Delegation> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

