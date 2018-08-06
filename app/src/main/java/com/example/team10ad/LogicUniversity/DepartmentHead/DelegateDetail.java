package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.Delegation;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.DelegationService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//Author: Chit Su Shine, Khaing Myat Kyaw Soe
public class DelegateDetail extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
    Calendar c = Calendar.getInstance();
    private int smYear = c.get(Calendar.YEAR);
    private int smMonth = c.get(Calendar.MONTH);
    private int smDay = c.get(Calendar.DAY_OF_MONTH);
    private int emYear = c.get(Calendar.YEAR);
    private int emMonth = c.get(Calendar.MONTH);
    private int emDay = c.get(Calendar.DAY_OF_MONTH);

    public DelegateDetail() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DelegateDetail newInstance(String param1, String param2) {
        DelegateDetail fragment = new DelegateDetail();
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
        Bundle b = this.getArguments();
        String delegatedUser = b.getString(Constants.DELEGATED_USER);
        Gson gson = new Gson();
        final User user = gson.fromJson(delegatedUser, User.class);
        final View view = inflater.inflate(R.layout.fragment_delegate_detail, container, false);

        TextView userName = view.findViewById(R.id.delegatedUserName);
        userName.setText(user.getFullName());
        // Selecting Start Date
        final TextView startDate = (TextView) view.findViewById(R.id.startDate);
        startDate.setText(c.get(Calendar.YEAR) + "-"
                + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
        final ImageButton startDateButton = view.findViewById(R.id.btn_startDate);
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDatePicker(startDate);
            }
        });

        // Selecting End Date
        final TextView endDate = (TextView) view.findViewById(R.id.endDate);
        endDate.setText(c.get(Calendar.YEAR) + "-"
                + (c.get(Calendar.MONTH) + 1) + "-" + (c.get(Calendar.DAY_OF_MONTH)));
        final ImageButton endDateButton = view.findViewById(R.id.btn_endDate);
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePicker(endDate);
            }
        });

        // Clicking Delegate Button
        Button delegateButton = view.findViewById(R.id.btn_delegate);
        delegateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickView) {
                long startTime = getTimeMills(smYear, smMonth, smDay);
                long endTime = getTimeMills(emYear, emMonth, emDay);
                if (endTime >= startTime) {
                    TextView startDate = view.findViewById(R.id.startDate);
                    TextView endDate = view.findViewById(R.id.endDate);
                    Gson gson = new Gson();
                    String json = MyApp.getInstance().getPreferenceManager().getString(Constants.USER_GSON);
                    final User hod = gson.fromJson(json, User.class);
                    Delegation createdDelegation = new Delegation();
                    createdDelegation.setStartDate(startDate.getText().toString());
                    createdDelegation.setEndDate(endDate.getText().toString());
                    createdDelegation.setUserId(user.getUserId());
                    createdDelegation.setAssignedBy(hod.getUserId());

                    DelegationService delegationService = ServiceGenerator.createService(DelegationService.class, token);
                    Call<Delegation> dCall = delegationService.createDelegation(createdDelegation);
                    dCall.enqueue(new Callback<Delegation>() {
                        @Override
                        public void onResponse(Call<Delegation> call, Response<Delegation> response) {
                            if (response.isSuccessful()) {
                                DelegateAuthority authorityFragment = new DelegateAuthority();
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, authorityFragment).commit();
                            } else {
                                Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Delegation> call, Throwable t) {
                            Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    new android.support.v7.app.AlertDialog.Builder(getContext())
                            .setTitle(Constants.DELEGATE_AUTHORITY)
                            .setMessage(Constants.DELEGATION_END_DATE)
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


    // Method for showing StartDatePicker Dialog
    public void showStartDatePicker(final TextView date) {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        smYear = year;
                        smMonth = monthOfYear;
                        smDay = dayOfMonth;
                        date.setText(smYear + "-"
                                + (smMonth + 1) + "-" + smDay);
                    }
                }, smYear, smMonth, smDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.setTitle("");
        datePickerDialog.show();
    }

    // Method for showing EndDatePicker Dialog
    public void showEndDatePicker(final TextView date) {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        emYear = year;
                        emMonth = monthOfYear;
                        emDay = dayOfMonth;
                        // set day of month , month and year value in the edit text
                        date.setText(emYear + "-"
                                + (emMonth + 1) + "-" + emDay);
                    }
                }, emYear, emMonth, emDay);
        datePickerDialog.getDatePicker().setMinDate(getTimeMills(smYear, smMonth, smDay));
        datePickerDialog.setTitle("");
        datePickerDialog.show();
    }

    public long getTimeMills(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }
}
