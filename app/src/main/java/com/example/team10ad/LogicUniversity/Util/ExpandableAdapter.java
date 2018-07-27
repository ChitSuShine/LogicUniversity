package com.example.team10ad.LogicUniversity.Util;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.DepartmentHead.HodDashboardFragment;
import com.example.team10ad.LogicUniversity.Model.Disbursement;
import com.example.team10ad.LogicUniversity.Model.DisbursementDetail;
import com.example.team10ad.team10ad.R;

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Disbursement> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Disbursement, List<DisbursementDetail>> _listDataChild;

    public ExpandableAdapter(Context context, List<Disbursement> listDataHeader,
                                 HashMap<Disbursement, List<DisbursementDetail>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    public List<Disbursement> get_listDataHeader() {
        return _listDataHeader;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
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
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dis_list_item, null);
        }

        TextView disbdeteail1 = (TextView) convertView
                .findViewById(R.id.disbdetail1);
        TextView disbdeteail2 = (TextView) convertView
                .findViewById(R.id.disbdetail2);
        TextView disbdeteail3 = (TextView) convertView
                .findViewById(R.id.disbdetail3);
        TextView disbdeteail4 = (TextView) convertView
                .findViewById(R.id.disbdetail4);

        disbdeteail1.setText(childText.getItemname());
        disbdeteail2.setText(childText.getCategoryName());
        disbdeteail3.setText(childText.getRequestQty());
        disbdeteail4.setText(childText.getApprovedQty());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
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
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dis_list_group, null);
        }

        TextView disb1 = (TextView) convertView
                .findViewById(R.id.disb1);
        TextView disb2 = (TextView) convertView
                .findViewById(R.id.disb2);
        TextView disb3 = (TextView) convertView
                .findViewById(R.id.disb3);

        convertView.setTag(headerTitle);
        disb1.setText(headerTitle.getDepName());
        disb2.setText(headerTitle.getReqDate());
        disb3.setText(Constants.STATUS[headerTitle.getStatus()]);

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
}