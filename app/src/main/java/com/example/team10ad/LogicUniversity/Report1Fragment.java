package com.example.team10ad.LogicUniversity;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.internal.TextScale;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.ItemTrend;
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

import org.w3c.dom.Text;

import java.text.DateFormatSymbols;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Report1Fragment extends Fragment implements OnChartValueSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private BarChart mChart;
    ArrayList<BarEntry> barValues1;
    ArrayList<BarEntry> barValues2;
    ArrayList<BarEntry> barValues3;

    private OnFragmentInteractionListener mListener;

    float groupSpace = 0.18f;
    float barSpace = 0.04f;
    float barWidth = 0.30f;

    public Report1Fragment() { }

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report1, container, false);

        mChart = view.findViewById(R.id.barChart);

        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        ReportService service = ServiceGenerator.createService(ReportService.class, token);
        Call<List<ItemTrend>> call = service.getItemTrend();
        call.enqueue(new Callback<List<ItemTrend>>() {
            @Override
            public void onResponse(Call<List<ItemTrend>> call, Response<List<ItemTrend>> response) {
                if (response.isSuccessful()) {
                    int i = 1;
                    for (ItemTrend item : response.body()) {
                        barValues1.add(new BarEntry(i, i+1));
                        barValues2.add(new BarEntry(i, i+2));
                        barValues3.add(new BarEntry(i, i+3));
                        i++;
                    }
                }
                setUpChart();
            }

            @Override
            public void onFailure(Call<List<ItemTrend>> call, Throwable t) {
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
    public void onValueSelected(Entry e, Highlight h) { }

    @Override
    public void onNothingSelected() { }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void setUpChart() {

        mChart.setOnChartValueSelectedListener(this);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        setBarData();

        mChart.animateY(1000);

        setLegend(mChart);
        xAndYAxis(mChart);

        mChart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        mChart.getXAxis().setAxisMinimum(0);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        mChart.getXAxis().setAxisMaximum(0 + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * 3);
        mChart.groupBars(0, groupSpace, barSpace);
        mChart.invalidate();
    }

    private void setBarData(){
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        String[] months = new DateFormatSymbols().getMonths();
        BarDataSet set1, set2, set3;
        set1 = new BarDataSet(barValues1, months[month-2]);
        set1.setColor(Color.argb(85,128,201,190));
        set1.setBarBorderColor(Color.rgb(128,201,190));
        set1.setBarBorderWidth(1.5f);
        set2 = new BarDataSet(barValues2, months[month-1]);
        set2.setColor(Color.argb(85,242,226,205));
        set2.setBarBorderColor(Color.rgb(242,226,205));
        set2.setBarBorderWidth(1.5f);
        set3 = new BarDataSet(barValues3, months[month]);
        set3.setColor(Color.argb(85,233,151,144));
        set3.setBarBorderColor(Color.rgb(233,151,144));
        set3.setBarBorderWidth(1.5f);
        set1.setValueTextSize(13);
        set2.setValueTextSize(13);
        set3.setValueTextSize(13);

        BarData data = new BarData(set1, set2, set3);
        data.setValueFormatter(new LargeValueFormatter());

        mChart.setData(data);
    }

    private void setLegend(BarChart chart){
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(16f);
    }

    private void xAndYAxis(BarChart chart){
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setTextSize(13f);

        XAxis xAxis = chart.getXAxis();
        chart.getAxisLeft().setAxisMinimum(0);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"ONE", "TWO", "THREE"}));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1.18f);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(14f);

        chart.getAxisRight().setEnabled(false);
    }
}
