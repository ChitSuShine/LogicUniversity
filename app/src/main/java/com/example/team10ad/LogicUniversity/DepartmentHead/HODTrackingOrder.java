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

import com.example.team10ad.LogicUniversity.Model.RequisitionDetail;
import com.example.team10ad.LogicUniversity.Service.ReqDetailService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.HodTrackingAdapter;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HODTrackingOrder extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    List<RequisitionDetail> result;
    ListView reqDetaillistview;

    String[] descriptionData = {"Pending", "Preparing", "RtC", "Completed"};

    public HODTrackingOrder() {
    }

    public static HODTrackingOrder newInstance(String param1, String param2) {
        HODTrackingOrder fragment = new HODTrackingOrder();
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
        Bundle b = this.getArguments();
        String id = b.getString("id");
        final View view= inflater.inflate(R.layout.fragment_hodtracking_order, container, false);
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        ReqDetailService requisitionService = ServiceGenerator.createService(ReqDetailService.class, token);
        Call<List<RequisitionDetail>> call = requisitionService.getAllReqDetail();
        call.enqueue(new Callback<List<RequisitionDetail>>() {
            @Override
            public void onResponse(Call<List<RequisitionDetail>> call, Response<List<RequisitionDetail>> response) {
                if(response.isSuccessful()){
                    result = response.body();
                    final HodTrackingAdapter adapter = new HodTrackingAdapter(getContext(),R.layout.row_hodtracking,result);
                    reqDetaillistview = (ListView) view.findViewById(R.id.hodtrackinglistview);
                    reqDetaillistview.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<RequisitionDetail>> call, Throwable t) {

            }
        });
        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);
        TextView v = (TextView) view.findViewById(R.id.reqdetailid);
        v.setText(id);
        return  view;
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
