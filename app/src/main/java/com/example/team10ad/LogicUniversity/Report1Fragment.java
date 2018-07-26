package com.example.team10ad.LogicUniversity;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.ItemUsageClerk;
import com.example.team10ad.LogicUniversity.Service.ReportService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Report1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Report1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Report1Fragment extends Fragment implements OnChartValueSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BarChart mChart;
    ArrayList<BarEntry> barValues1;
    ArrayList<BarEntry> barValues2;
    ArrayList<BarEntry> barValues3;

    ArrayList<Integer> sup1;
    ArrayList<Integer> sup2;
    ArrayList<Integer> sup3;


    private OnFragmentInteractionListener mListener;

    float groupSpace = 0.1f;
    float barSpace = 0.05f; // x4 DataSet
    float barWidth = 0.25f; // x4 DataSet

    public Report1Fragment() {
        // Required empty public constructor
    }

    public static Report1Fragment newInstance(String param1, String param2) {
        Report1Fragment fragment = new Report1Fragment();
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
        barValues1 = new ArrayList<BarEntry>();
        barValues2 = new ArrayList<BarEntry>();
        barValues3 = new ArrayList<BarEntry>();
        Calendar today = Calendar.getInstance();
        final int month = today.get(Calendar.MONTH);
        final int year = today.get(Calendar.YEAR);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report1, container, false);

        mChart = view.findViewById(R.id.barChart);

        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        ReportService service = ServiceGenerator.createService(ReportService.class, token);
        Call<List<ItemUsageClerk>> call = service.getItemUsageClerk();
        call.enqueue(new Callback<List<ItemUsageClerk>>() {
            @Override
            public void onResponse(Call<List<ItemUsageClerk>> call, Response<List<ItemUsageClerk>> response) {
                if (response.isSuccessful()) {
                    sup1 = new ArrayList<>();
                    sup2 = new ArrayList<>();
                    sup3 = new ArrayList<>();
                    for (ItemUsageClerk item : response.body()) {
                        if (item.getSupId() == 11)
                            sup1.add(item.getQty());
                        else if (item.getSupId() == 12)
                            sup2.add(item.getQty());
                        else if (item.getSupId() == 13)
                            sup3.add(item.getQty());
                    }
                }
                for (int i = 0; i < 3; i++) {
                    sup1.add(0);
                    sup2.add(0);
                    sup3.add(0);
                }
                for (int i = 0; i < 3; i++) {
                    barValues1.add(new BarEntry(1, sup1.get(i)));
                    barValues2.add(new BarEntry(2, sup2.get(i)));
                    barValues3.add(new BarEntry(3, sup3.get(i)));
                }
                setUpChart();
            }

            @Override
            public void onFailure(Call<List<ItemUsageClerk>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void setUpChart() {

        mChart.setOnChartValueSelectedListener(this);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        BarDataSet set1, set2, set3;

        set1 = new BarDataSet(barValues1, "Sup 1");
        set1.setColor(Color.argb(85,104,241,175));
        set1.setBarBorderColor(Color.rgb(104,241,175));
        set1.setBarBorderWidth(1.5f);
        set2 = new BarDataSet(barValues2, "Sup 2");
        set2.setColor(Color.argb(85,164, 228, 251));
        set2.setBarBorderColor(Color.rgb(164, 228, 251));
        set2.setBarBorderWidth(1.5f);
        set3 = new BarDataSet(barValues3, "Sup 3");
        set3.setColor(Color.argb(85,242, 247, 158));
        set3.setBarBorderColor(Color.rgb(242, 247, 158));
        set3.setBarBorderWidth(1.5f);

        BarData data = new BarData(set1, set2, set3);
        data.setValueFormatter(new LargeValueFormatter());

        mChart.setData(data);
        mChart.animateY(1000);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(12f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = mChart.getXAxis();
        mChart.getAxisLeft().setAxisMinimum(0);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"ONE", "TWO", "THREE"}));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);

        mChart.getAxisRight().setEnabled(false);

        mChart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        mChart.getXAxis().setAxisMinimum(0);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        mChart.getXAxis().setAxisMaximum(0 + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * 3);
        mChart.groupBars(0, groupSpace, barSpace);
        mChart.invalidate();
    }
}
