package com.nusiss.team10ad.LogicUniversity.Util;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nusiss.team10ad.LogicUniversity.Model.Disbursement;
import com.nusiss.team10ad.LogicUniversity.Model.DisbursementDetail;
import com.nusiss.team10ad.LogicUniversity.Model.Requisition;
import com.nusiss.team10ad.LogicUniversity.Clerk.DisbursementList;
import com.nusiss.team10ad.LogicUniversity.Service.DisbursementService;
import com.nusiss.team10ad.LogicUniversity.Service.ServiceGenerator.ServiceGenerator;
import com.nusiss.team10ad.team10ad.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Disbursement> listDisbursement;
    // child data in format of header title, child title
    private HashMap<Disbursement, List<DisbursementDetail>> listDisbItems;

    public ExpandableAdapter(Context context, List<Disbursement> listDataHeader,
                                 HashMap<Disbursement, List<DisbursementDetail>> listChildData) {
        this.context = context;
        this.listDisbursement = listDataHeader;
        this.listDisbItems = listChildData;
    }

    public List<Disbursement> getListDisbursement() {
        return listDisbursement;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDisbItems.get(this.listDisbursement.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final DisbursementDetail childText = (DisbursementDetail) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dis_list_item, null);
            LinearLayout lo = convertView.findViewById(R.id.childList);
        }

        TextView disbDetail1 = (TextView) convertView
                .findViewById(R.id.disbdetail1);
        TextView disbDetail2 = (TextView) convertView
                .findViewById(R.id.disbdetail2);
        TextView disbDetail3 = (TextView) convertView
                .findViewById(R.id.disbdetail3);
        TextView disbDetail4 = (TextView) convertView
                .findViewById(R.id.disbdetail4);

        disbDetail1.setText(childText.getItemname());
        disbDetail2.setText(childText.getCategoryName());
        disbDetail3.setText(childText.getRequestQty());
        disbDetail4.setText(childText.getApprovedQty());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDisbItems.get(this.listDisbursement.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDisbursement.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDisbursement.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final Disbursement headerTitle = (Disbursement) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dis_list_group, null);
        }

        TextView disb1 = (TextView) convertView
                .findViewById(R.id.disb1);
        TextView disb2 = (TextView) convertView
                .findViewById(R.id.disb2);
        TextView disb3 = (TextView) convertView
                .findViewById(R.id.disb3);

        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        final DisbursementService disbService = ServiceGenerator.createService(DisbursementService.class, token);
        Button btn = convertView.findViewById(R.id.btnDeliver);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deliverDiag = new AlertDialog.Builder(context)
                        .setTitle("Finish Delivering Item(s)?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deliver(disbService, headerTitle);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                deliverDiag.show();
            }
        });
        disb1.setText(headerTitle.getDepName());
        disb2.setText(headerTitle.getReqDate());
        disb3.setText("Requisition ID : " + headerTitle.getReqID());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void deliver(DisbursementService disbursementService, final Disbursement disb) {
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
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Delivered!", Toast.LENGTH_SHORT).show();
                    redirect(disb);
                }
            }

            @Override
            public void onFailure(Call<Requisition> call, Throwable t) {
                Toast.makeText(context,
                        "CONNECTION ERROR!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirect(Disbursement disbursement) {
        DisbursementList reqList = new DisbursementList();
        Bundle b = new Bundle();
        b.putInt("CpId", Integer.parseInt(disbursement.getCpID()));
        b.putString("Cp", disbursement.getCpName());
        reqList.setArguments(b);
        FragmentTransaction ft = ((FragmentActivity) context)
                .getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, reqList).commit();
    }
}