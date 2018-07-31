package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.FreqentlyItem;
import com.example.team10ad.LogicUniversity.Model.FrequentItemHod;
import com.example.team10ad.LogicUniversity.Model.User;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HODReport extends Fragment implements OnChartValueSelectedListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Bar Colors
    int[] reds = {255,54,255,75,153};
    int[] greens = {99,162,206,192,102};
    int[] blues = {132,235,86,192,255};

    // Bar data
    private BarChart mChart;
    private List<FrequentItemHod> result;
    private ArrayList<BarEntry> barEntries;
    private BarDataSet dataSet;
    private BarData barData;
    private ArrayList<String> xVals;

    private OnFragmentInteractionListener mListener;

    public HODReport() { }

    public static HODReport newInstance(String param1, String param2) {
        HODReport fragment = new HODReport();
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
        barData = new BarData();
        xVals = new ArrayList<>();
        final View view= inflater.inflate(R.layout.fragment_hodreport, container, false);
        final String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        ReportService service = ServiceGenerator.createService(ReportService.class, token);
        String userInfo = MyApp.getPreferenceManager().getString(Constants.USER_GSON);
        User user = new Gson().fromJson(userInfo, User.class);

        Call<List<FrequentItemHod>> call = service.getFrequentItemListHod(user.getDepId());
        call.enqueue(new Callback<List<FrequentItemHod>>() {
            @Override
            public void onResponse(Call<List<FrequentItemHod>> call, Response<List<FrequentItemHod>> response) {
                if(response.isSuccessful()){
                    result = response.body();
                    int i = 0;
                    for(FrequentItemHod item: result){
                        barEntries = new ArrayList<>();
                        barEntries.add(new BarEntry(i, item.getQty()));
                        xVals.add(item.getDescription());
                        dataSet = new BarDataSet(barEntries, item.getDescription());
                        dataSet.setColor(Color.argb( 85, reds[i], greens[i], blues[i]));
                        dataSet.setBarBorderColor(Color.rgb( reds[i], greens[i], blues[i]));
                        dataSet.setBarBorderWidth(1.5f);
                        barData.addDataSet(dataSet);
                        i++;
                    }
                    mChart = view.findViewById(R.id.frequentlyItemChart);
                    // chart settings with predefined methods
                    mChart = generalSetting(mChart);
                    mChart = xAxisSetting(mChart, xVals);
                    mChart = yAxisSetting(mChart);
                    mChart = legendSetting(mChart);
                    mChart.setData(barData);
                    mChart.invalidate();
                }
            }

            @Override
            public void onFailure(Call<List<FrequentItemHod>> call, Throwable t) {

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

    @Override
    public void onValueSelected(Entry e, Highlight h) { }

    @Override
    public void onNothingSelected() { }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    // bar chart styling
    private BarChart generalSetting(BarChart chart){
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.getAxisRight().setEnabled(false);
        chart.animateY(1000);
        return chart;
    }

    // bar chart styling x axis
    private BarChart xAxisSetting(BarChart chart, ArrayList<String> lbls){
        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMinimum(-1f);
        xAxis.setAxisMaximum(5f);
        xAxis.setDrawAxisLine(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setDrawLabels(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(lbls));
        xAxis.setLabelRotationAngle(360-90);
        return chart;
    }

    // bar chart styling y axis
    private BarChart yAxisSetting(BarChart chart){
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        return chart;
    }

    // bar chart legend settings
    private BarChart legendSetting(BarChart chart){
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setTextSize(12f);
        return chart;
    }
}
