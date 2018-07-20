package com.example.team10ad.LogicUniversity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.Department;
import com.example.team10ad.LogicUniversity.Service.DepartmentService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReqFilter extends DialogFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public ReqFilter() {

    }

    public static ReqFilter newInstance(String param1, String param2) {
        ReqFilter fragment = new ReqFilter();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_req_filter, container, false);
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        DepartmentService requisitionService = ServiceGenerator.createService(DepartmentService.class, token);
        Call<List<Department>> call = requisitionService.getAllDepartments();

        call.enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                if(response.isSuccessful()){
                    LinearLayout ll = (LinearLayout) view.findViewById(R.id.testlayout);
                    final CheckBox[] cbs = new CheckBox[6];

                    final CheckBox selectall = new CheckBox(getContext());
                    ll.addView(selectall);

                    for(int i = 0; i < cbs.length; i++) {

                        CheckBox cb = new CheckBox(getContext());
                        cb.setText("");
                        cb.setTag(i);
                        cbs[i] = cb;
                        ll.addView(cb);
                    }

                    selectall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            boolean all = true;
                            for(CheckBox x : cbs){
                                if(x.isChecked() == false){
                                    all = false;
                                    break;
                                }
                            }
                            if(!selectall.isChecked()){
                                for(CheckBox z : cbs)
                                    z.setChecked(false);
                            } else {
                                if (!all) {
                                    for (CheckBox y : cbs)
                                        y.setChecked(true);
                                }
                            }
                        }
                    });


                    Button btn = view.findViewById(R.id.filterBtn);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ArrayList<String> data = new ArrayList<>();

                            for(CheckBox x : cbs){
                                if(x.isChecked())
                                    data.add(x.getTag().toString());
                            }

                            Intent i = new Intent();
                            i.putExtra("param", data);
                            getTargetFragment().onActivityResult
                                    (1, 1,i);
                            dismiss();
                        }
                    });
                }
                else{
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {

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
