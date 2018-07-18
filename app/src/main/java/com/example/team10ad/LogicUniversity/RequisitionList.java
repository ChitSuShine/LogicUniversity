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

import com.example.team10ad.team10ad.R;

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
        String s = getArguments().getString("test");
        View view=inflater.inflate(R.layout.fragment_requisition_list, container, false);
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
        filterText.setText(s);
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
