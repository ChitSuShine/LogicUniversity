package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.RequisitionService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyAdapter;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReqListForTrackingOrder extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    SearchView searchView;
    ListView listView;
    List<Requisition> result=new ArrayList<Requisition>();

    public ReqListForTrackingOrder() { }

    public static ReqListForTrackingOrder newInstance(String param1, String param2) {
        ReqListForTrackingOrder fragment = new ReqListForTrackingOrder();
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

        final View view= inflater.inflate(R.layout.fragment_req_list_for_tracking_order, container, false);

        String userInfo = MyApp.getPreferenceManager().getString(Constants.USER_GSON);
        final User user = new Gson().fromJson(userInfo, User.class);

        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        RequisitionService requisitionService = ServiceGenerator.createService(RequisitionService.class, token);
        Call<List<Requisition>> call = requisitionService.getAllRequisitions();
        call.enqueue(new Callback<List<Requisition>>() {
            @Override
            public void onResponse(Call<List<Requisition>> call, Response<List<Requisition>> response) {
                if (response.isSuccessful()) {
                    result = response.body();

                    List<Requisition> filtered = new ArrayList<Requisition>();
                    for(Requisition rq: result){
                        if(Integer.parseInt(rq.getDepID())==user.getDepId())
                            filtered.add(rq);
                    }

                    final MyAdapter adapter = new MyAdapter(getContext(),R.layout.row,filtered);
                    listView = (ListView) view.findViewById(R.id.replistview);
                    listView.setAdapter(adapter);
                    if(listView.getAdapter().getCount()==0){
                        TextView emptyText = view.findViewById(android.R.id.empty);
                        listView.setEmptyView(emptyText);
                    }

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            HODTrackingOrder hodTrackingOrder=new HODTrackingOrder();
                            Bundle b = new Bundle();
                            b.putString("id", result.get(i).getReqID());
                            hodTrackingOrder.setArguments(b);
                            FragmentManager fragmentManager=getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.content_frame, hodTrackingOrder).commit();
                        }
                    });
                } else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Requisition>> call, Throwable t) {
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
