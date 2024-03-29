package com.nusiss.team10ad.LogicUniversity.Clerk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nusiss.team10ad.LogicUniversity.Model.Disbursement;
import com.nusiss.team10ad.LogicUniversity.Model.DisbursementDetail;
import com.nusiss.team10ad.LogicUniversity.Service.DisbursementService;
import com.nusiss.team10ad.LogicUniversity.Service.ServiceGenerator.ServiceGenerator;
import com.nusiss.team10ad.LogicUniversity.Util.Constants;
import com.nusiss.team10ad.LogicUniversity.Util.ExpandableAdapter;
import com.nusiss.team10ad.LogicUniversity.Util.MyApp;
import com.nusiss.team10ad.team10ad.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// Author: Wint Yadanar Htet, Htet Wai Yan
public class DisbursementList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ExpandableListView disblistview;
    List<Disbursement> result = new ArrayList<Disbursement>();

    public DisbursementList() {

    }

    public static DisbursementList newInstance(String param1, String param2) {
        DisbursementList fragment = new DisbursementList();
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
        // getting data which are passed from calling fragment
        final String Cp = getArguments().getString("Cp");
        final int CpId = getArguments().getInt("CpId");
        final View view = inflater.inflate(R.layout.fragment_requisition_list, container, false);
        ((TextView) view.findViewById(R.id.lblCp)).setText(Cp);

        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

        // loading progress bar settings
        final ProgressBar progressBar = (SpinKitView) view.findViewById(R.id.loadingReqList);
        Wave wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);
        progressBar.showContextMenu();

        // getting list of all disbursements
        final DisbursementService disbService = ServiceGenerator.createService(DisbursementService.class, token);
        Call<List<Disbursement>> call = disbService.getAllDisbursements();
        call.enqueue(new Callback<List<Disbursement>>() {
            @Override
            public void onResponse(Call<List<Disbursement>> call, Response<List<Disbursement>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    // filtering data for current collection point
                    for (Disbursement item : response.body()) {
                        if (Integer.parseInt(item.getCpID()) == CpId) {
                            result.add(item);
                        }
                    }
                    // text for empty list
                    if(result.isEmpty()){
                        TextView txt = view.findViewById(R.id.emptyReqList);
                        txt.setText("Nothing to deliver!!");
                    }
                    // creating hash map for expandable list view
                    HashMap<Disbursement, List<DisbursementDetail>> map = new
                            HashMap<>();
                    for (Disbursement d : result) {
                        DisbursementDetail disbD = new DisbursementDetail();
                        disbD.setItemname("Item");
                        disbD.setCategoryName("Category");
                        disbD.setRequestQty("Requested");
                        disbD.setApprovedQty("Approved");
                        d.getDisbursementDetails().add(0, disbD);
                        map.put(d, d.getDisbursementDetails());
                    }
                    // expandable adapter settings
                    disblistview = (ExpandableListView) view.findViewById(R.id.disblistview);
                    ExpandableAdapter adapter = new ExpandableAdapter(getContext(),
                            result, map);
                    disblistview.setAdapter(adapter);
                } else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Disbursement>> call, Throwable t) {
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

