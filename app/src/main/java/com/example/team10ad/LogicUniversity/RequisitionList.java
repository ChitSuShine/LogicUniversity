package com.example.team10ad.LogicUniversity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.team10ad.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
        LinearLayout filter=(LinearLayout)view.findViewById(R.id.filterID);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment reqFil = new ReqFilter();
                reqFil.setTargetFragment(RequisitionList.this, 1);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                reqFil.show(ft, "Filter");
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

    public void update(String text){
        TextView ftext = (TextView) getView().findViewById(R.id.filterText);
        ftext.setText(text);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            ArrayList<String> ss = data.getStringArrayListExtra("param");
            TextView ft = (TextView) getView().findViewById(R.id.filterText);
            StringBuilder str = new StringBuilder();
            for(String sss : ss) {
                str.append(sss);
                str.append(" ; ");
            }
            ft.setText(str);
    }
}
