package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.Noti;
import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.LogicUniversity.Model.RequisitionDetail;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.NotiService;
import com.example.team10ad.LogicUniversity.Service.RequisitionService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.HodTrackingAdapter;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;

import java.util.List;

import de.mrapp.android.dialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HodReqApproveReject extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Requisition result;
    ListView reqDetaillistview;
    private String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
    String json = MyApp.getInstance().getPreferenceManager().getString(Constants.USER_GSON);
    final User user = new Gson().fromJson(json, User.class);
    final int deptId = user.getDepId();

    public HodReqApproveReject() { }

    public static HodReqApproveReject newInstance(String param1, String param2) {
        HodReqApproveReject fragment = new HodReqApproveReject();
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
                MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(getContext());
                dialogBuilder.setTitle(R.string.remark_title);
                final EditText input = new EditText(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                input.setLayoutParams(lp);
                dialogBuilder.setView(input);
                dialogBuilder.setIcon(R.drawable.ic_info);
                dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        result.setApprovedBy(String.valueOf(user.getUserId()));
                        result.setStatus("1");
                        RequisitionService requisitionService = ServiceGenerator.createService(RequisitionService.class, token);
                        Call<Requisition> apprCall = requisitionService.updateRequisition(result);
                        apprCall.enqueue(new Callback<Requisition>() {
                            @Override
                            public void onResponse(Call<Requisition> call, Response<Requisition> response) {
                                if (response.isSuccessful()) {
                                    result=response.body();
                                    HodRequisitionList hodRequisitionList = new HodRequisitionList();
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.detach(HodReqApproveReject.this);
                                    ft.add(R.id.content_frame, hodRequisitionList).commit();
                                   //createNoti(input, response.body(),6);
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
                MaterialDialog dialog = dialogBuilder.create();
                dialog.setTitleColor(Color.BLACK);
                dialog.setMessageColor(Color.BLACK);
                dialog.setButtonTextColor(Color.BLACK);
                dialog.show();
            }
        });
        //reject
        Button rejectreq=view.findViewById(R.id.rejectbtn);
        rejectreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(getContext());
                dialogBuilder.setTitle(R.string.remark_title);
                final EditText input = new EditText(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                input.setLayoutParams(lp);
                dialogBuilder.setView(input);
                dialogBuilder.setIcon(R.drawable.ic_info);
                dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        result.setApprovedBy(String.valueOf(user.getUserId()));
                        result.setStatus("7");
                        RequisitionService requisitionService = ServiceGenerator.createService(RequisitionService.class, token);
                        Call<Requisition> rejcall = requisitionService.updateRequisition(result);
                        rejcall.enqueue(new Callback<Requisition>() {
                            @Override
                            public void onResponse(Call<Requisition> call, Response<Requisition> response) {
                                if (response.isSuccessful()) {
                                    result=response.body();
                                    createNoti(input, response.body(),5, "Requisition rejected!");

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
                MaterialDialog dialog = dialogBuilder.create();
                dialog.setTitleColor(Color.BLACK);
                dialog.setMessageColor(Color.BLACK);
                dialog.setButtonTextColor(Color.BLACK);
                dialog.show();
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

    private void createNoti(EditText input, Requisition res,int notiType, String title){
        final String remark=input.getText().toString();
        Noti notiremark=new Noti();
        notiremark.setNotiType(notiType);
        notiremark.setIsread(false);
        notiremark.setRemark(remark);
        notiremark.setDatetime(String.valueOf(java.util.Calendar.getInstance()));
        notiremark.setRole(Constants.EMP_ROLE);
        notiremark.setTitle(title);
        notiremark.setNotiID(0);
        notiremark.setDeptid(deptId);
        notiremark.setResID(Integer.parseInt(res.getReqID()));
        NotiService notiService=ServiceGenerator.createService(NotiService.class,token);
        Call<Noti> call=notiService.noticreate(notiremark);
        call.enqueue(new Callback<Noti>() {
            @Override
            public void onResponse(Call<Noti> call, Response<Noti> response) {
                if(response.isSuccessful()){
                    HodRequisitionList hodRequisitionList = new HodRequisitionList();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(HodReqApproveReject.this);
                    ft.add(R.id.content_frame, hodRequisitionList).commit();
                }
                else{
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Noti> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
