package com.example.team10ad.LogicUniversity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.Department;
import com.example.team10ad.LogicUniversity.Model.ItemUsageHod;
import com.example.team10ad.LogicUniversity.Service.DepartmentService;
import com.example.team10ad.LogicUniversity.Service.ReportService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Report2Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private PieChart halfPie;
    private ArrayList<String> data;
    private ArrayList<PieEntry> pieEntries;
    private HashMap<String, Integer> deptMap;
    private Spinner spin;

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
        data = new ArrayList<>();
        deptMap = new HashMap<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report2, container, false);
        halfPie = view.findViewById(R.id.halfPieChart);
        spin = view.findViewById(R.id.spinner2);

        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        DepartmentService deptService = ServiceGenerator.createService(DepartmentService.class, token);
        ReportService service = ServiceGenerator.createService(ReportService.class, token);

        addSpinner(deptService, service);

        return view;
    }

    private void setupChart() {

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        halfPie.setBackgroundColor(Color.WHITE);

        halfPie.setUsePercentValues(true);
        halfPie.getDescription().setEnabled(false);

        halfPie.setDrawHoleEnabled(true);
        halfPie.setHoleColor(Color.WHITE);

        halfPie.setTransparentCircleColor(Color.WHITE);
        halfPie.setTransparentCircleAlpha(110);

        halfPie.setHoleRadius(58f);
        halfPie.setTransparentCircleRadius(61f);

        halfPie.setDrawCenterText(true);

        halfPie.setRotationEnabled(false);
        halfPie.setHighlightPerTapEnabled(true);

        halfPie.setMaxAngle(180f); // HALF CHART
        halfPie.setRotationAngle(180f);
        halfPie.setCenterTextOffset(0, -20);

        halfPie.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = halfPie.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextSize(16f);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        halfPie.setEntryLabelColor(Color.WHITE);
        halfPie.setEntryLabelTextSize(12f);
    }

    private void addSpinner(DepartmentService departmentService, final ReportService service) {
        Call<List<Department>> callDept = departmentService.getAllDepartments();
        callDept.enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                if (response.isSuccessful()) {
                    List<String> temp = new ArrayList<>();
                    for (Department d : response.body()) {
                        deptMap.put(d.getDeptName(), d.getDeptId());
                        temp.add(d.getDeptName());
                    }
                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter =
                            new ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_spinner_item, temp);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // attaching data adapter to spinner
                    spin.setAdapter(dataAdapter);
                    spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            int deptId = deptMap.get(adapterView.getSelectedItem().toString());
                            pieEntries = new ArrayList<>();
                            setupChart();
                            populateChart(service, deptId, adapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateChart(final ReportService s, final int id, final String name) {

        Call<List<ItemUsageHod>> call = s.getItemUsageHod();
        call.enqueue(new Callback<List<ItemUsageHod>>() {
            @Override
            public void onResponse(Call<List<ItemUsageHod>> call, Response<List<ItemUsageHod>> response) {
                if (response.isSuccessful()) {
                    int total = 0;
                    for (ItemUsageHod item : response.body()) {
                        if (item.getDeptId() == id) {
                            pieEntries.add(new PieEntry(item.getQty(), item.getDescription()));
                            total += item.getQty();
                        }
                    }
                    setData("", name, total);
                }
            }

            @Override
            public void onFailure(Call<List<ItemUsageHod>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(String label, String centerTxt, int total) {
        PieDataSet dataSet = new PieDataSet(pieEntries, label);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);


        halfPie.setCenterText(generateCenterSpannableText("Total "+String.valueOf(total)));
        halfPie.setCenterTextSize(17f);
        halfPie.setCenterTextTypeface(Typeface.DEFAULT_BOLD);

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        halfPie.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);
        halfPie.setData(data);

        halfPie.invalidate();
    }

    private SpannableString generateCenterSpannableText(String string) {
        SpannableString s = new SpannableString(string);
        return s;
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


}
