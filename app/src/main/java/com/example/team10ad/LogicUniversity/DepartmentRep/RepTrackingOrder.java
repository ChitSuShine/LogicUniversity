package com.example.team10ad.LogicUniversity.DepartmentRep;

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

import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.LogicUniversity.Model.RequisitionDetail;
import com.example.team10ad.LogicUniversity.Service.RequisitionService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.HodTrackingAdapter;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// Author: Wint Yadanar Htet
public class RepTrackingOrder extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String[] STATE = {"ONE", "ONE", "ONE", "TWO", "THREE", "FOUR", "FOUR", "ONE"};

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Requisition result;
    ListView reqDetaillistview;

    String[] descriptionData = {"Pending", "Preparing", "RtC", "Completed"};

    public RepTrackingOrder() { }

    public static RepTrackingOrder newInstance(String param1, String param2) {
        RepTrackingOrder fragment = new RepTrackingOrder();
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
        final View view= inflater.inflate(R.layout.fragment_rep_tracking_order, container, false);

        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        RequisitionService requisitionService = ServiceGenerator.createService(RequisitionService.class, token);
        Call<Requisition> call = requisitionService.getReqById(id);

        call.enqueue(new Callback<Requisition>() {
            @Override
            public void onResponse(Call<Requisition> call, Response<Requisition> response) {
                if(response.isSuccessful()){
                    result = response.body();
                    TextView tv1 = (TextView) view.findViewById(R.id.raisedBy);
                    TextView tv2 = (TextView) view.findViewById(R.id.raisedbydate);
                    TextView tv3 = (TextView) view.findViewById(R.id.collectionpoint);
                    TextView tv4 = (TextView) view.findViewById(R.id.apprBy);
                    tv1.setText(result.getRasiedByname());
                    tv2.setText(result.getReqDate());
                    tv3.setText(result.getCpName());
                    tv4.setText(result.getApprovedByname());

                    StateProgressBar stateProgressBar =
                            (StateProgressBar) view.findViewById(R.id.your_state_progress_bar_id);
                    stateProgressBar.setStateDescriptionData(descriptionData);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar
                            .StateNumber.valueOf(STATE[Integer.parseInt(result.getStatus())]));

                    List<RequisitionDetail> details = result.getRequisitionDetails();
                    final HodTrackingAdapter adapter = new HodTrackingAdapter(getContext(),R.layout.row_hodtracking,details);
                    reqDetaillistview = (ListView) view.findViewById(R.id.hodtrackinglistview);
                    reqDetaillistview.setAdapter(adapter);
                }
                else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Requisition> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.valueOf(STATE[6-3]));
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
