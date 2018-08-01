package com.example.team10ad.LogicUniversity;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.Category;
import com.example.team10ad.LogicUniversity.Model.Department;
import com.example.team10ad.LogicUniversity.Model.Item;
import com.example.team10ad.LogicUniversity.Model.ItemUsageClerk;
import com.example.team10ad.LogicUniversity.Model.Supplier;
import com.example.team10ad.LogicUniversity.Service.InventoryService;
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
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Report2Fragment extends Fragment implements OnChartValueSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    String token;
    ArrayList<Integer> supId = new ArrayList<>();
    ArrayList<Integer> itemId = new ArrayList<>();
    private BarChart mChart;
    List<ItemUsageClerk> result;
    ArrayList<BarEntry> barValues1;
    ArrayList<BarEntry> barValues2;
    ArrayList<BarEntry> barValues3;

    float groupSpace = 0.18f;
    float barSpace = 0.04f;
    float barWidth = 0.30f;

    public Report2Fragment() {
    }

    public static Report2Fragment newInstance(String param1, String param2) {
        Report2Fragment fragment = new Report2Fragment();
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
        barValues1 = new ArrayList<BarEntry>();
        barValues2 = new ArrayList<BarEntry>();
        barValues3 = new ArrayList<BarEntry>();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report2, container, false);

        mChart = view.findViewById(R.id.barChartItemUsage);
        mChart.setVisibility(View.INVISIBLE);
        final Spinner itemSpin = view.findViewById(R.id.itemSpin);
        final Spinner sup1Spin = view.findViewById(R.id.sup1);
        final Spinner sup2Spin = view.findViewById(R.id.sup2);
        final Spinner sup3Spin = view.findViewById(R.id.sup3);

        token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

        setUpChart();
        mChart.getXAxis().setDrawLabels(false);
        setItemSpinner(itemSpin);
        setSupSpinners(sup1Spin, sup2Spin, sup3Spin);

        Button btnGen = view.findViewById(R.id.reportBtnSup);
        btnGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChart.setVisibility(View.VISIBLE);
                int s1 = supId.get(sup1Spin.getSelectedItemPosition());
                int s2 = supId.get(sup2Spin.getSelectedItemPosition());
                int s3 = supId.get(sup3Spin.getSelectedItemPosition());
                int item = itemId.get(itemSpin.getSelectedItemPosition());
                mChart.getXAxis().setDrawLabels(true);
                mChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(
                        new String[]{
                                sup1Spin.getSelectedItem().toString(),
                                sup2Spin.getSelectedItem().toString(),
                                sup3Spin.getSelectedItem().toString()
                        }
                ));
                mChart.getXAxis().setLabelRotationAngle(360 - 15);
                requestData(s1, s2, s3, item);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void setUpChart() {

        mChart.setOnChartValueSelectedListener(this);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        setBarData();

        setLegend(mChart);
        xAndYAxis(mChart);

        updateChart();
    }

    private void setBarData() {
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        String[] months = new DateFormatSymbols().getMonths();
        BarDataSet set1, set2, set3;
        set1 = new BarDataSet(barValues1, months[month - 2]);
        set1.setColor(Color.argb(85, 128, 201, 190));
        set1.setBarBorderColor(Color.rgb(128, 201, 190));
        set1.setBarBorderWidth(1.5f);
        set2 = new BarDataSet(barValues2, months[month - 1]);
        set2.setColor(Color.argb(85, 72, 105, 127));
        set2.setBarBorderColor(Color.rgb(72, 105, 127));
        set2.setBarBorderWidth(1.5f);
        set3 = new BarDataSet(barValues3, months[month]);
        set3.setColor(Color.argb(85, 233, 151, 144));
        set3.setBarBorderColor(Color.rgb(233, 151, 144));
        set3.setBarBorderWidth(1.5f);
        set1.setValueTextSize(11);
        set2.setValueTextSize(11);
        set3.setValueTextSize(11);

        BarData data = new BarData(set1, set2, set3);
        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                if(value > 0) {
                    return NumberFormat.getNumberInstance().format(value);
                } else {
                    return "";
                }
            }
        });

        mChart.setData(data);
    }

    private void setLegend(BarChart chart) {
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

    private void xAndYAxis(BarChart chart) {
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setTextSize(13f);

        XAxis xAxis = chart.getXAxis();
        chart.getAxisLeft().setAxisMinimum(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1.18f);
        xAxis.setDrawGridLines(true);
        xAxis.setTextSize(14f);

        chart.getAxisRight().setEnabled(false);
    }

    private void updateChart() {
        mChart.getBarData().setBarWidth(barWidth);
        mChart.animateY(1000);

        // restrict the x-axis range
        mChart.getXAxis().setAxisMinimum(0);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        mChart.getXAxis().setAxisMaximum(0 + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * 3);
        mChart.groupBars(0, groupSpace, barSpace);
        mChart.invalidate();
    }

    private void setItemSpinner(final Spinner spin) {
        InventoryService service = ServiceGenerator.createService(InventoryService.class, token);
        Call<List<Item>> call = service.getAllItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    ArrayList<String> temp = new ArrayList<>();
                    for (Item item : response.body()) {
                        itemId.add(item.getItemid());
                        temp.add(item.getDescription());
                    }
                    android.widget.ArrayAdapter<String> dataAdapter =
                            new android.widget.ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item, temp);
                    spin.setAdapter(dataAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSupSpinners(final Spinner spin1, final Spinner spin2, final Spinner spin3) {
        InventoryService service = ServiceGenerator.createService(InventoryService.class, token);
        Call<List<Supplier>> call = service.getAllSuppliers();
        call.enqueue(new Callback<List<Supplier>>() {
            @Override
            public void onResponse(Call<List<Supplier>> call, Response<List<Supplier>> response) {
                if (response.isSuccessful()) {
                    ArrayList<String> temp = new ArrayList<>();
                    for (Supplier s : response.body()) {
                        supId.add(s.getSupId());
                        temp.add(s.getSupName());
                    }
                    android.widget.ArrayAdapter<String> dataAdapter =
                            new android.widget.ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item, temp);
                    // attaching data adapter to spinner
                    spin1.setAdapter(dataAdapter);
                    spin2.setAdapter(dataAdapter);
                    spin3.setAdapter(dataAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Supplier>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestData(int s1, int s2, int s3, int item) {
        ReportService service = ServiceGenerator.createService(ReportService.class, token);
        Call<List<ItemUsageClerk>> call = service.getItemUsage(s1, s2, s3, item);
        call.enqueue(new Callback<List<ItemUsageClerk>>() {
            @Override
            public void onResponse(Call<List<ItemUsageClerk>> call, Response<List<ItemUsageClerk>> response) {
                if (response.isSuccessful()) {
                    result = response.body();
                    barValues1 = new ArrayList<>();
                    barValues2 = new ArrayList<>();
                    barValues3 = new ArrayList<>();
                    barValues1.add(new BarEntry(1, result.get(2).getSup1()));
                    barValues2.add(new BarEntry(1, result.get(1).getSup1()));
                    barValues3.add(new BarEntry(1, result.get(0).getSup1()));
                    barValues1.add(new BarEntry(2, result.get(2).getSup2()));
                    barValues2.add(new BarEntry(2, result.get(1).getSup2()));
                    barValues3.add(new BarEntry(2, result.get(0).getSup2()));
                    barValues1.add(new BarEntry(3, result.get(2).getSup3()));
                    barValues2.add(new BarEntry(3, result.get(1).getSup3()));
                    barValues3.add(new BarEntry(3, result.get(0).getSup3()));
                }
                setUpChart();
            }

            @Override
            public void onFailure(Call<List<ItemUsageClerk>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
