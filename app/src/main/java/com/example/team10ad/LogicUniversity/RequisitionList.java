package com.example.team10ad.LogicUniversity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.DepartmentHead.HodReqApproveRejectFragment;
import com.example.team10ad.LogicUniversity.Model.Disbursement;
import com.example.team10ad.LogicUniversity.Service.DisbursementService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.DisbAdapter;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.LogicUniversity.Util.RetrievalFormFragment;
import com.example.team10ad.team10ad.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequisitionList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView disblistview;
    List<Disbursement> result=new ArrayList<Disbursement>();

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
        final View view=inflater.inflate(R.layout.fragment_requisition_list, container, false);
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

        LinearLayout filter=(LinearLayout)view.findViewById(R.id.filterID);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment reqFil = new ReqFilter();
                reqFil.setTargetFragment(RequisitionList.this, 1);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                reqFil.show(ft, "Filter");
            }
        });

        /*final LinearLayout test=(LinearLayout)view.findViewById(R.id.testlayout);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequisitionDetail requisitionDetail=new RequisitionDetail();
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.reqFrame,requisitionDetail).commit();


            }
        });*/
       /* TextView filterText=(TextView)view.findViewById(R.id.filterText);
        filterText.setTypeface(filterText.getTypeface(), Typeface.BOLD);*/

        DisbursementService disbService= ServiceGenerator.createService(DisbursementService.class,token);
        Call<List<Disbursement>> call=disbService.getAllDisbursements();
        call.enqueue(new Callback<List<Disbursement>>() {
            @Override
            public void onResponse(Call<List<Disbursement>> call, Response<List<Disbursement>> response) {
                if(response.isSuccessful()){
                    result=response.body();
                    final DisbAdapter disbAdapter=new DisbAdapter(getContext(),R.layout.row_disblist,result);
                    disblistview=(ListView) view.findViewById(R.id.disblistview);
                    disblistview.setAdapter(disbAdapter);
                }
                else{
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<String> ss = data.getStringArrayListExtra("param");
        TextView ft = (TextView) getView().findViewById(R.id.filterText);
        StringBuilder str = new StringBuilder();
        for(String sss : ss) {
            str.append(sss);
            str.append(" ; ");
        }
        ft.setText(str);
    }
}

