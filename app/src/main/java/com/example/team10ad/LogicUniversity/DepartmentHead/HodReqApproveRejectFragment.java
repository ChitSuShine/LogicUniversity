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
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.LogicUniversity.Model.RequisitionDetail;
import com.example.team10ad.LogicUniversity.Service.RequisitionService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.HodReqListAdapter;
import com.example.team10ad.LogicUniversity.Util.HodTrackingAdapter;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.team10ad.LogicUniversity.Util.Constants.REJECT_GSON;

public class HodReqApproveRejectFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Requisition result;
    ListView reqDetaillistview;
    private String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);


    public HodReqApproveRejectFragment() {
    }

    public static HodReqApproveRejectFragment newInstance(String param1, String param2) {
        HodReqApproveRejectFragment fragment = new HodReqApproveRejectFragment();
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
        Bundle b = getArguments();
        String id = b.getString("id");
        final View view= inflater.inflate(R.layout.fragment_hod_req_approve_reject, container, false);
        Gson gson = new Gson();
        String json=MyApp.getInstance().getPreferenceManager().getString(REJECT_GSON);
        final Requisition requisition=gson.fromJson(json,Requisition.class);

        //String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        final RequisitionService requisitionService = ServiceGenerator.createService(RequisitionService.class, token);
        Call<Requisition> call = requisitionService.getReqById(id);
        call.enqueue(new Callback<Requisition>() {
            @Override
            public void onResponse(Call<Requisition> call, Response<Requisition> response) {
                if(response.isSuccessful()){
                    result = response.body();
                    TextView tv1 = (TextView) view.findViewById(R.id.hodrasiedby);
                    TextView tv2 = (TextView) view.findViewById(R.id.hodraisedate);
                    tv1.setText(result.getRasiedByname());
                    tv2.setText(result.getReqDate());
                    TextView status=view.findViewById(R.id.hodstatus);
                    status.setText(result.getStatus());
                    List<RequisitionDetail> details = result.getRequisitionDetails();
                    final HodTrackingAdapter adapter = new HodTrackingAdapter(getContext(),R.layout.row_hodtracking,details);
                    reqDetaillistview = (ListView) view.findViewById(R.id.hodreqlistdetaillist);
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
        //approve
        Button appovebtn=view.findViewById(R.id.approvebtn);
        appovebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                result.setStatus("1");
                RequisitionService requisitionService = ServiceGenerator.createService(RequisitionService.class, token);
                Call<Requisition> rejcall = requisitionService.updateRequisition(result);
                rejcall.enqueue(new Callback<Requisition>() {
                    @Override
                    public void onResponse(Call<Requisition> call, Response<Requisition> response) {
                        if (response.isSuccessful()) {
                            result=response.body();
                            HodRequisitionListFragment hodRequisitionListFragment = new HodRequisitionListFragment();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(HodReqApproveRejectFragment.this);
                            ft.add(R.id.content_frame, hodRequisitionListFragment).commit();
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

            }
        });
        //reject
        Button rejectreq=view.findViewById(R.id.rejectbtn);
        rejectreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                result.setStatus("7");
                RequisitionService requisitionService = ServiceGenerator.createService(RequisitionService.class, token);
                Call<Requisition> rejcall = requisitionService.updateRequisition(result);
                rejcall.enqueue(new Callback<Requisition>() {
                    @Override
                    public void onResponse(Call<Requisition> call, Response<Requisition> response) {
                        if (response.isSuccessful()) {
                            result=response.body();
                            HodRequisitionListFragment hodRequisitionListFragment = new HodRequisitionListFragment();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(HodReqApproveRejectFragment.this);
                            ft.add(R.id.content_frame, new HodRequisitionListFragment());
                            ft.commit();
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
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
