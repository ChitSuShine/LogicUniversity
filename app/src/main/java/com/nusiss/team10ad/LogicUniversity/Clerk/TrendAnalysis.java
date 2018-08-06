package com.nusiss.team10ad.LogicUniversity.Clerk;

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

import com.nusiss.team10ad.LogicUniversity.Model.Category;
import com.nusiss.team10ad.LogicUniversity.Model.Department;
import com.nusiss.team10ad.LogicUniversity.Model.ItemTrend;
import com.nusiss.team10ad.LogicUniversity.Service.DepartmentService;
import com.nusiss.team10ad.LogicUniversity.Service.InventoryService;
import com.nusiss.team10ad.LogicUniversity.Service.ReportService;
import com.nusiss.team10ad.LogicUniversity.Service.ServiceGenerator.ServiceGenerator;
import com.nusiss.team10ad.LogicUniversity.Util.Constants;
import com.nusiss.team10ad.LogicUniversity.Util.MyApp;
import com.nusiss.team10ad.team10ad.R;
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
// Author: Htet Wai Yan
public class TrendAnalysis extends Fragment implements OnChartValueSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // declaring required variables
    String token;
    ArrayList<Integer> deptId = new ArrayList<>();
    ArrayList<Integer> catId = new ArrayList<>();
    private BarChart mChart;
    List<ItemTrend> result;
    ArrayList<BarEntry> barValues1;
    ArrayList<BarEntry> barValues2;
    ArrayList<BarEntry> barValues3;

    private OnFragmentInteractionListener mListener;

    // values for chart styling
    float groupSpace = 0.18f;
    float barSpace = 0.04f;
    float barWidth = 0.30f;

    public TrendAnalysis() { }

    public static TrendAnalysis newInstance(String param1, String param2) {
        TrendAnalysis fragment = new TrendAnalysis();
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

        // specifying the spinners
        mChart = view.findViewById(R.id.barChart);
        mChart.setVisibility(View.INVISIBLE);
        final Spinner catSpin = view.findViewById(R.id.catSpin);
        final Spinner dept1Spin = view.findViewById(R.id.dept1);
        final Spinner dept2Spin = view.findViewById(R.id.dept2);
        final Spinner dept3Spin = view.findViewById(R.id.dept3);

        token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

        // setting up chart with predefined methods
        setUpChart();
        mChart.getXAxis().setDrawLabels(false);
        setCatSpinner(catSpin);
        setDeptSpinners(dept1Spin, dept2Spin, dept3Spin);
        // button action
        Button btnGen = view.findViewById(R.id.reportBtn);
        btnGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getting selected data and making chart visible
                mChart.setVisibility(View.VISIBLE);
                int d1 = deptId.get(dept1Spin.getSelectedItemPosition());
                int d2 = deptId.get(dept2Spin.getSelectedItemPosition());
                int d3 = deptId.get(dept3Spin.getSelectedItemPosition());
                int cat = catId.get(catSpin.getSelectedItemPosition());
                mChart.getXAxis().setDrawLabels(true);
                mChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(
                        new String[]{
                                dept1Spin.getSelectedItem().toString(),
                                dept2Spin.getSelectedItem().toString(),
                                dept3Spin.getSelectedItem().toString()
                        }
                ));
                mChart.getXAxis().setLabelRotationAngle(360 - 15);
                requestData(d1, d2, d3, cat);
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

    // general settings
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
    // bar data binding
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
    // setting chart legend
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
    // setting up X axis and Y axis
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
    // updating chart
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
    // populating categories in spinner
    private void setCatSpinner(final Spinner spin) {
        InventoryService service = ServiceGenerator.createService(InventoryService.class, token);
        Call<List<Category>> call = service.getAllCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    ArrayList<String> temp = new ArrayList<>();
                    for (Category c : response.body()) {
                        catId.add(c.getCatId());
                        temp.add(c.getName());
                    }
                    android.widget.ArrayAdapter<String> dataAdapter =
                            new android.widget.ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item, temp);
                    spin.setAdapter(dataAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // populating departments in spinners
    private void setDeptSpinners(final Spinner spin1, final Spinner spin2, final Spinner spin3) {
        DepartmentService service = ServiceGenerator.createService(DepartmentService.class, token);
        Call<List<Department>> call = service.getAllDepartments();
        call.enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                if (response.isSuccessful()) {
                    ArrayList<String> temp = new ArrayList<>();
                    for (Department d : response.body()) {
                        deptId.add(d.getDeptId());
                        temp.add(d.getDeptName());
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
            public void onFailure(Call<List<Department>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // sending request to API for chart data
    private void requestData(int d1, int d2, int d3, int cat) {
        ReportService service = ServiceGenerator.createService(ReportService.class, token);
        Call<List<ItemTrend>> call = service.getItemTrend(d1, d2, d3, cat);
        call.enqueue(new Callback<List<ItemTrend>>() {
            @Override
            public void onResponse(Call<List<ItemTrend>> call, Response<List<ItemTrend>> response) {
                if (response.isSuccessful()) {
                    result = response.body();
                    barValues1 = new ArrayList<>();
                    barValues2 = new ArrayList<>();
                    barValues3 = new ArrayList<>();
                    barValues1.add(new BarEntry(1, result.get(2).getDept1()));
                    barValues2.add(new BarEntry(1, result.get(1).getDept1()));
                    barValues3.add(new BarEntry(1, result.get(0).getDept1()));
                    barValues1.add(new BarEntry(2, result.get(2).getDept2()));
                    barValues2.add(new BarEntry(2, result.get(1).getDept2()));
                    barValues3.add(new BarEntry(2, result.get(0).getDept2()));
                    barValues1.add(new BarEntry(3, result.get(2).getDept3()));
                    barValues2.add(new BarEntry(3, result.get(1).getDept3()));
                    barValues3.add(new BarEntry(3, result.get(0).getDept3()));
                }
                setUpChart();
            }

            @Override
            public void onFailure(Call<List<ItemTrend>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
