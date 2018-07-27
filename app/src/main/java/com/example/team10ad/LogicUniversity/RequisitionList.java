package com.example.team10ad.LogicUniversity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.CollectionPoint;
import com.example.team10ad.LogicUniversity.Model.Disbursement;
import com.example.team10ad.LogicUniversity.Model.DisbursementDetail;
import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.LogicUniversity.Service.DisbursementService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.DisbAdapter;
import com.example.team10ad.LogicUniversity.Util.ExpandableAdapter;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequisitionList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ExpandableListView disblistview;
    List<Disbursement> result = new ArrayList<Disbursement>();

    public RequisitionList() {

    }

    public static RequisitionList newInstance(String param1, String param2) {
        RequisitionList fragment = new RequisitionList();
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
        final String Cp = getArguments().getString("Cp");
        final int CpId = getArguments().getInt("CpId");
        final View view = inflater.inflate(R.layout.fragment_requisition_list, container, false);
        ((TextView) view.findViewById(R.id.lblCp)).setText(Cp);

        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

        final ProgressBar progressBar = (SpinKitView) view.findViewById(R.id.loadingReqList);
        Wave wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);
        progressBar.showContextMenu();

        final DisbursementService disbService = ServiceGenerator.createService(DisbursementService.class, token);
        Call<List<Disbursement>> call = disbService.getAllDisbursements();
        call.enqueue(new Callback<List<Disbursement>>() {
            @Override
            public void onResponse(Call<List<Disbursement>> call, Response<List<Disbursement>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    for (Disbursement item : response.body()) {
                        if (Integer.parseInt(item.getCpID()) == CpId) {
                            result.add(item);
                        }
                    }
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
                    disblistview = (ExpandableListView) view.findViewById(R.id.disblistview);
                    ExpandableAdapter adapter = new ExpandableAdapter(getContext(),
                            result, map);
                    disblistview.setAdapter(adapter);
                    disblistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
                            AlertDialog.Builder deliverDiag = new AlertDialog.Builder(getContext())
                                    .setTitle("Finish Delivering Item(s)?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deliver(disbService, (Disbursement) view.getTag());
                                        }
                                    })
                                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                            deliverDiag.show();
                            return false;
                        }
                    });
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

    private void deliver(DisbursementService disbursementService, final Disbursement disb){
        Requisition req = new Requisition();
        req.setDepID(disb.getDepID());
        req.setReqID(disb.getReqID());
        req.setCpID(disb.getCpID());
        req.setApprovedBy(disb.getApprovedBy());
        req.setRaisedBy(disb.getApprovedBy());
        req.setStatus(String.valueOf(4));
        Call<Requisition> call = disbursementService.UpdateRequisition(req);
        call.enqueue(new Callback<Requisition>() {
            @Override
            public void onResponse(Call<Requisition> call, Response<Requisition> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getContext(), "Delivered!", Toast.LENGTH_SHORT).show();
                    redirect(disb);
                }
            }
            @Override
            public void onFailure(Call<Requisition> call, Throwable t) {
                Toast.makeText(getContext(),
                        "CONNECTION ERROR!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirect(Disbursement disbursement){
        RequisitionList reqList = new RequisitionList();
        Bundle b = new Bundle();
        b.putInt("CpId", Integer.parseInt(disbursement.getCpID()));
        b.putString("Cp", disbursement.getCpName());
        reqList.setArguments(b);
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, reqList)
                .commit();
    }
}

