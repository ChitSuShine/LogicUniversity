package com.example.team10ad.LogicUniversity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.AccessToken;
import com.example.team10ad.LogicUniversity.Model.Department;
import com.example.team10ad.LogicUniversity.Service.DepartmentService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequisitionList extends Fragment implements RequisitionDetail.OnFragmentInteractionListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RequisitionList() {

    }

    public static RequisitionList newInstance(String param1, String param2) {
        RequisitionList fragment = new RequisitionList();
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

        View view=inflater.inflate(R.layout.fragment_requisition_list, container, false);


        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        DepartmentService departmentService = ServiceGenerator.createService(DepartmentService.class, token);

        Call<Department> call = departmentService.getDepartmentById(1);
        call.enqueue(new Callback<Department>() {
            @Override
            public void onResponse(Call<Department> call, Response<Department> response) {
                if (response.isSuccessful()) {
                    Department d = response.body();
                    Toast.makeText(MyApp.getInstance(), "Yay..."+response.body().getDeptName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Department> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });

        /*Call<Department> call = departmentService.getDepartmentById(1);
        call.enqueue(new Callback<Department>() {
            @Override
            public void onResponse(Call<Department> call, Response<Department> response) {
                if (response.isSuccessful()) {
                    Department d = response.body();
                    Toast.makeText(MyApp.getInstance(), "Yay..."+response.body().getDeptName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Department> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });*/

        /*Call<List<Department>> call = departmentService.getAllDepartments();
        call.enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MyApp.getInstance(), "Yay..."+response.body().size(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });*/

        /*TextView deptName = view.findViewById(R.id.cardDeptName);
        TextView deptDate = view.findViewById(R.id.cardDeptDate);
        deptName.setText("Tested Name");
        deptDate.setText("Tested Date");*/


        LinearLayout filter=(LinearLayout)view.findViewById(R.id.filterID);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReqFilter reqFilter=new ReqFilter();
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.reqFrame,reqFilter).commit();
            }
        });
        final LinearLayout test=(LinearLayout)view.findViewById(R.id.testlayout);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequisitionDetail requisitionDetail=new RequisitionDetail();
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.reqFrame,requisitionDetail).commit();


            }
        });
        TextView filterText=(TextView)view.findViewById(R.id.filterText);
        filterText.setTypeface(filterText.getTypeface(), Typeface.BOLD);
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
