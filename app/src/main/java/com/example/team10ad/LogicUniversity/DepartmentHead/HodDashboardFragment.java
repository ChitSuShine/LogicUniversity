package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.example.team10ad.team10ad.R;

public class HodDashboardFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HodDashboardFragment() {
    }

    public static HodDashboardFragment newInstance(String param1, String param2) {
        HodDashboardFragment fragment = new HodDashboardFragment();
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
        View view= inflater.inflate(R.layout.fragment_hod_dashboard, container, false);
        //approve
        CardView cardapp=(CardView)view.findViewById(R.id.cardapprove);
        cardapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HodReqApproveRejectFragment hodapprej=new HodReqApproveRejectFragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, hodapprej).commit();
            }
        });
        //delegate
        CardView carddelegate=(CardView)view.findViewById(R.id.carddelegate);
        carddelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DelegateAuthorityFragment hoddelegate=new DelegateAuthorityFragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, hoddelegate).commit();
            }
        });
        //assign
        CardView cardassign=(CardView)view.findViewById(R.id.cardassign);
        cardassign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AssignDepRepFragment hodassign=new AssignDepRepFragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, hodassign).commit();
            }
        });
        //tracking
        CardView cardtracking=(CardView)view.findViewById(R.id.cardtracking);
        cardtracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReqListForTrackingOrder hodtracking=new ReqListForTrackingOrder();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, hodtracking).commit();
            }
        });
        //change collection point
        CardView cardcollect=(CardView)view.findViewById(R.id.cardcollect);
        cardcollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeCollectionPoint hodcollect=new ChangeCollectionPoint();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, hodcollect).commit();
            }
        });
        //report
        CardView cardreport=(CardView)view.findViewById(R.id.cardreport);
        cardreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HODReport hodreport=new HODReport();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, hodreport).commit();
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
