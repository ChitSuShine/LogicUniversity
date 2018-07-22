package com.example.team10ad.LogicUniversity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.team10ad.LogicUniversity.DepartmentHead.ChangeCollectionPoint;
import com.example.team10ad.team10ad.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // Data for pie chart
    int qty[]={89,50,45,30};
    String name[]={"Pen","Pencil","Stapler","Clip"};

    private View currentView;

    public DashboardFragment() {
    }
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        currentView = view;

        setupPieChart();

        // Link to inventory screen
        LinearLayout inv=(LinearLayout)view.findViewById(R.id.inventoryID);
        inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InventoryFragment inventoryFragement=new InventoryFragment();
                FragmentTransaction invfm = getActivity().getSupportFragmentManager().beginTransaction();
                invfm.replace(R.id.content_frame,inventoryFragement);
                invfm.addToBackStack(null);
                invfm.commit();
            }
        });
        // Link to requisition list screen
        LinearLayout req=(LinearLayout)view.findViewById(R.id.requisitionID);
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequisitionList requisitionList=new RequisitionList();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.content_frame,requisitionList);
                fm.addToBackStack(null);
                fm.commit();
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

    // Set pie chart in dashboard
    private void setupPieChart() {
        List<PieEntry> pieEntries=new ArrayList<>();
        for(int i=0;i<qty.length;i++){
            pieEntries.add(new PieEntry(qty[i],name[i]));
        }
        PieDataSet pieDataSet=new PieDataSet(pieEntries,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData=new PieData();
        pieData.setDataSet(pieDataSet);

        PieChart pieChart=(PieChart)currentView.findViewById(R.id.piechart);
        pieChart.setData(pieData);
        pieChart.getDescription().setText("Top 5 frequent ordered items in 2018");
        pieChart.animateX(1000);
        pieChart.invalidate();
    }
}
