package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.team10ad.LogicUniversity.RequisitionList;
import com.example.team10ad.team10ad.R;




public class RequisitionListFragment extends Fragment {
    public RequisitionListFragment() {
        // Required empty public constructor

    }


    public void onStart() {
        super.onStart();
        // ---Button view---
        Button btnGetText = (Button) getActivity()
                .findViewById(R.id.checkbtn);
        btnGetText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ApproveRejectRequisitionFragment approveRejectRequisitionFragment = new ApproveRejectRequisitionFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, approveRejectRequisitionFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requisition_list2, container, false);
    }


    public FragmentManager getSupportFragmentManager() {
        return null;
    }
}
