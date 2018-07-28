package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.OrderHistory;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.OrderHistoryService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.LogicUniversity.Util.OrderHistoryAdapter;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HODOrderHistory extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    List<OrderHistory> result;
    ListView orderhistorylistview;
    private OrderHistory orderHistory;

    public HODOrderHistory() { }

    public static HODOrderHistory newInstance(String param1, String param2) {
        HODOrderHistory fragment = new HODOrderHistory();
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
        //converting json format to user model
        String userInfo = MyApp.getPreferenceManager().getString(Constants.USER_GSON);
        User user = new Gson().fromJson(userInfo, User.class);
        final View view= inflater.inflate(R.layout.fragment_hodorder_history, container, false);
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        OrderHistoryService inventoryService= ServiceGenerator.createService(OrderHistoryService.class,token);
        Call<List<OrderHistory>> call=inventoryService.getAllOrderHistory(user.getDepId());
        call.enqueue(new Callback<List<OrderHistory>>() {
            @Override
            public void onResponse(Call<List<OrderHistory>> call, Response<List<OrderHistory>> response) {
                if(response.isSuccessful()){
                    result=response.body();
                    final OrderHistoryAdapter adapter = new OrderHistoryAdapter(getContext(),R.layout.row_orderhistory,result);
                    orderhistorylistview = (ListView) view.findViewById(R.id.orderhistorylistview);
                    orderhistorylistview.setAdapter(adapter);
                    if(orderhistorylistview.getAdapter().getCount()==0) {
                        TextView emptyText = view.findViewById(android.R.id.empty);
                        orderhistorylistview.setEmptyView(emptyText);
                    }

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
