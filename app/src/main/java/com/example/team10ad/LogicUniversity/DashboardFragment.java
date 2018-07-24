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
import com.example.team10ad.LogicUniversity.Model.FreqentlyItem;
import com.example.team10ad.LogicUniversity.Service.ReportService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.LogicUniversity.Util.RetrievalFormFragment;
import com.example.team10ad.team10ad.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // Data for pie chart
    private List<PieEntry> pieEntries = new ArrayList<>();

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
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        final PieChart pieChart=(PieChart)currentView.findViewById(R.id.piechart);
        ReportService rService = ServiceGenerator.createService(ReportService.class, token);
        Call<List<FreqentlyItem>> call = rService.frequetlyOrderedItemList();
        call.enqueue(new Callback<List<FreqentlyItem>>() {
            @Override
            public void onResponse(Call<List<FreqentlyItem>> call, Response<List<FreqentlyItem>> response) {
                if(response.isSuccessful()){
                    for(FreqentlyItem item : response.body()){
                        pieEntries.add(new PieEntry(item.getQty(), item.getDescription()));
                    }
                    setupPieChart(pieChart, pieEntries);
                }
            }

            @Override
            public void onFailure(Call<List<FreqentlyItem>> call, Throwable t) {

            }
        });

        // Link to Retrieval list screen
        LinearLayout inv=(LinearLayout)view.findViewById(R.id.inventoryID);
        inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrievalFormFragment retrieval=new RetrievalFormFragment();
                FragmentTransaction reqFragment=getActivity().getSupportFragmentManager().beginTransaction();
                reqFragment.replace(R.id.content_frame,retrieval).commit();
            }
        });
        // Link to Delivery Point screen
        LinearLayout req=(LinearLayout)view.findViewById(R.id.requisitionID);
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClerkMapDeliveryPoint deliveryList=new ClerkMapDeliveryPoint();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.content_frame,deliveryList);
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
    private void setupPieChart(PieChart chart, List<PieEntry> entries) {
        PieDataSet pieDataSet=new PieDataSet(entries,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData=new PieData();
        pieData.setDataSet(pieDataSet);
        chart.setData(pieData);
        chart.getDescription().setText("Top 5 frequent ordered items in 2018");
        chart.animateX(1000);
        chart.invalidate();
    }
}
