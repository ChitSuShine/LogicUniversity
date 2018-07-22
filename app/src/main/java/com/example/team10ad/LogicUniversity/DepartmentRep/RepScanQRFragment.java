package com.example.team10ad.LogicUniversity.DepartmentRep;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.LogicUniversity.Service.RequisitionService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

    private String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
    private TextView scannedData;

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

        Button scanBtn = view.findViewById(R.id.btn_scan);
        scannedData = view.findViewById(R.id.scannedData);
        // Qr code scanner object
        final IntentIntegrator qrScan = new IntentIntegrator(getActivity());
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initiating the qr code scan
                // qrScan.initiateScan();

                RequisitionService requisitionService = ServiceGenerator.createService(RequisitionService.class, token);
                Call<Requisition> call = requisitionService.getScannedReqId("8");
                call.enqueue(new Callback<Requisition>() {
                    @Override
                    public void onResponse(Call<Requisition> call, Response<Requisition> response) {
                        if (response.isSuccessful()) {
                            Requisition resultedRequisition = response.body();
                            scannedData.setText(resultedRequisition.getApprovedByname());
                        } else {
                            Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Requisition> call, Throwable t) {
                        Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }

    // Getting the scan results
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            // If qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(MyApp.getInstance(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                // If qr contains data
                try {

                    String qrCode = result.getContents().toString();
                    scannedData.setText(result.getContents().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MyApp.getInstance(), "Scan Error " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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