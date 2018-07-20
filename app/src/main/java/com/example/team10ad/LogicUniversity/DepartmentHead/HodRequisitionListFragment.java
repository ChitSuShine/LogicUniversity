package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.LogicUniversity.Service.RequisitionService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.HodReqListAdapter;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HodRequisitionListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ListView listView;
    List<Requisition> result=new ArrayList<Requisition>();

    public HodRequisitionListFragment() {
    }

    public static HodRequisitionListFragment newInstance(String param1, String param2) {
        HodRequisitionListFragment fragment = new HodRequisitionListFragment();
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

   /* public void onStart() {
        super.onStart();
        // ---Button view---
        Button btnGetText = (Button) getActivity().findViewById(R.id.hodreqcheck);
        btnGetText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HodReqApproveRejectFragment hodReqApproveRejectFragment = new HodReqApproveRejectFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, hodReqApproveRejectFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_hod_requisition_list, container, false);
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        RequisitionService requisitionService = ServiceGenerator.createService(RequisitionService.class, token);
        Call<List<Requisition>> call = requisitionService.getAllRequisitions();
        call.enqueue(new Callback<List<Requisition>>() {
            @Override
            public void onResponse(Call<List<Requisition>> call, Response<List<Requisition>> response) {
                if(response.isSuccessful()){
                    result=response.body();
                    Toast.makeText(getActivity(),"It's working",Toast.LENGTH_LONG).show();
                    final HodReqListAdapter adapter = new HodReqListAdapter(getContext(),R.layout.row_hodreqlist,result);
                    listView = (ListView) view.findViewById(R.id.hodtrackinglistview);
                    listView.setAdapter(adapter);
                    /*Button btnGetText = (Button) view.findViewById(R.id.hodreqcheck);
                    btnGetText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HodReqApproveRejectFragment hodReqApproveRejectFragment = new HodReqApproveRejectFragment();
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, hodReqApproveRejectFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });*/
                }
                else {
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
