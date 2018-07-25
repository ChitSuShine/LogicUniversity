package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.OrderHistory;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.OrderHistoryService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.LogicUniversity.Util.OrderHistoryAdapter;
import com.example.team10ad.team10ad.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HodDashboardFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    List<OrderHistory> result;
    ListView recentorderhistory;

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
        final View view= inflater.inflate(R.layout.fragment_hod_dashboard, container, false);
        String userInfo = MyApp.getPreferenceManager().getString(Constants.USER_GSON);
        User user = new Gson().fromJson(userInfo, User.class);
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        OrderHistoryService inventoryService= ServiceGenerator.createService(OrderHistoryService.class,token);
        Call<List<OrderHistory>> call=inventoryService.getAllOrderHistory(user.getDepId());

        call.enqueue(new Callback<List<OrderHistory>>() {
            @Override
            public void onResponse(Call<List<OrderHistory>> call, Response<List<OrderHistory>> response) {
                if(response.isSuccessful()){
                    result=response.body();
                    final OrderHistoryAdapter adapter = new OrderHistoryAdapter(getContext(),R.layout.row_orderhistory,result);
                    recentorderhistory = (ListView) view.findViewById(R.id.recentorderhistory);
                    recentorderhistory.setAdapter(adapter);
                }
                else{
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderHistory>> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();

            }
        });
        //approve
        CardView cardapp=(CardView)view.findViewById(R.id.cardapprove);
        cardapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HodRequisitionListFragment hodapprej=new HodRequisitionListFragment();
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
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, hoddelegate);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        //Collection Point
        CardView cardcollect=(CardView)view.findViewById(R.id.cardcollect);
        cardcollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeCollectionPoint hodcollect=new ChangeCollectionPoint();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, hodcollect).commit();
            }
        });
        //tracking
        CardView cardtracking=(CardView)view.findViewById(R.id.cardtracking);
        cardtracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReqListForTrackingOrder hodtracking=new ReqListForTrackingOrder();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, hodtracking);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
