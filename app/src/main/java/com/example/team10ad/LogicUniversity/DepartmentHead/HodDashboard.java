package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.team10ad.R;

import java.util.List;

// Author: Wint Yadanar Htet
public class HodDashboard extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    List<Requisition> result;
    ListView recentOrderHistory;

    public HodDashboard() { }

    public static HodDashboard newInstance(String param1, String param2) {
        HodDashboard fragment = new HodDashboard();
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
        final View view = inflater.inflate(R.layout.fragment_hod_dashboard, container, false);
        // CardView for Approve or Reject requisitions
        CardView appRejCardView = (CardView) view.findViewById(R.id.cardapprove);
        appRejCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HodRequisitionList hodAppRej = new HodRequisitionList();
                getFragmentManager().beginTransaction().replace(R.id.content_frame, hodAppRej).addToBackStack(null).commit();
            }
        });

        // CardView for delegation
        CardView delegateCardView = (CardView) view.findViewById(R.id.carddelegate);
        delegateCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DelegateAuthority hodDelegate = new DelegateAuthority();
                getFragmentManager().beginTransaction().replace(R.id.content_frame, hodDelegate).addToBackStack(null).commit();
            }
        });
        // CardView for Collection Point
        CardView collectCardView = (CardView) view.findViewById(R.id.cardcollect);
        collectCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeCollectionPoint hodCollect = new ChangeCollectionPoint();
                getFragmentManager().beginTransaction().replace(R.id.content_frame, hodCollect).addToBackStack(null).commit();
            }
        });
        // CardView for order tracking
        CardView trackingCardView = (CardView) view.findViewById(R.id.cardtracking);
        trackingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReqListForTrackingOrder hodTracking = new ReqListForTrackingOrder();
                getFragmentManager().beginTransaction().replace(R.id.content_frame, hodTracking).addToBackStack(null).commit();
            }
        });
        // Report
        CardView reportCardView = (CardView) view.findViewById(R.id.cardreport);
        reportCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HodAnalysisReport hodReport= new HodAnalysisReport();
                getFragmentManager().beginTransaction().replace(R.id.content_frame, hodReport).addToBackStack(null).commit();
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
