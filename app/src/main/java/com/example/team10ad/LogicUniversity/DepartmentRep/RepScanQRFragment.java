package com.example.team10ad.LogicUniversity.DepartmentRep;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.HomeActivity;
import com.example.team10ad.LogicUniversity.Model.Disbursement;
import com.example.team10ad.LogicUniversity.Model.DisbursementDetail;
import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.DisbursementService;
import com.example.team10ad.LogicUniversity.Service.RequisitionService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.DisbAdapter;
import com.example.team10ad.LogicUniversity.Util.DisbDetailAdapter;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepScanQRFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static TextView repMsg;
    private static TextView repRaisedBy;
    private static TextView requestedDate;
    private static TextView collection;
    private static TextView lockerName;
    private static ListView itemsList;
    private static CardView repCardView;

    public RepScanQRFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RepScanQRFragment newInstance(String param1, String param2) {
        RepScanQRFragment fragment = new RepScanQRFragment();
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
        View view = inflater.inflate(R.layout.fragment_rep_scan_qr, container, false);

        repRaisedBy = view.findViewById(R.id.rep_rasiedBy);
        requestedDate = view.findViewById(R.id.rep_requestedDate);
        collection = view.findViewById(R.id.rep_collection);
        lockerName = view.findViewById(R.id.rep_lockerName);
        itemsList = view.findViewById(R.id.rep_itemListView);
        repCardView = view.findViewById(R.id.rep_cardView);
        repMsg = view.findViewById(R.id.rep_warnMsg);
        // Initiating the qr code scan
        new ZxingOrient(getActivity()).initiateScan();
        Button scanBtn = view.findViewById(R.id.btn_scan);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initiating the qr code scan
                new ZxingOrient(getActivity()).initiateScan();
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static void showData(String qrCode) {
        final String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        DisbursementService disbursementService = ServiceGenerator.createService(DisbursementService.class, token);
        Call<Disbursement> call = disbursementService.getScannedReqId(qrCode);
        call.enqueue(new Callback<Disbursement>() {
            @Override
            public void onResponse(Call<Disbursement> call, Response<Disbursement> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String json = MyApp.getInstance().getPreferenceManager().getString(Constants.USER_GSON);
                    User user = gson.fromJson(json, User.class);
                    Disbursement disbursement = response.body();
                    int delivered = disbursement.getStatus();
                    if (delivered == Constants.REP_DELIVER && disbursement.getDepID().equals(Integer.toString(user.getDepId()))) {
                        repCardView.setVisibility(View.VISIBLE);
                        repRaisedBy.setText(disbursement.getRasiedByname());
                        requestedDate.setText(disbursement.getReqDate());
                        collection.setText(disbursement.getCpName());
                        lockerName.setText(disbursement.getLockerName());
                        List<DisbursementDetail> result = disbursement.getDisbursementDetails();
                        DisbDetailAdapter disbAdapter = new DisbDetailAdapter(MyApp.getInstance().getApplicationContext(), R.layout.row_disbdetail, result);
                        itemsList.setAdapter(disbAdapter);

                        Requisition completedReq = new Requisition();
                        completedReq.setReqID(disbursement.getReqID());
                        RequisitionService requisitionService = ServiceGenerator.createService(RequisitionService.class, token);
                        Call<Requisition> requisitionCall = requisitionService.changeRequisitionStatus(completedReq);
                        requisitionCall.enqueue(new Callback<Requisition>() {
                            @Override
                            public void onResponse(Call<Requisition> call, Response<Requisition> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(MyApp.getInstance(), Constants.REP_COLLECTED_MSG, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Requisition> call, Throwable t) {
                                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (delivered == Constants.REP_OUTSTANDING && disbursement.getDepID().equals(Integer.toString(user.getDepId()))) {
                        repMsg.setText(Constants.REP_OUTSTANDING_MSG);
                    } else if (delivered == Constants.REP_COMPLETE) {
                        repMsg.setText(Constants.REP_COMPLETE_MSG);
                    }
                } else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Disbursement> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }
}