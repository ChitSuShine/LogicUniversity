package com.example.team10ad.LogicUniversity.Util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.team10ad.LogicUniversity.Model.Disbursement;
import com.example.team10ad.LogicUniversity.Model.RequisitionDetail;
import com.example.team10ad.team10ad.R;

import java.util.List;

public class DisbAdapter extends ArrayAdapter<Disbursement> {

    int resource;
    private List<Disbursement> items;

    public DisbAdapter(@NonNull Context context, int resource, @NonNull List<Disbursement> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);

        TextView disb1 = (TextView) v.findViewById(R.id.disb1);
        TextView disb2 = (TextView) v.findViewById(R.id.disb2);
        TextView disb3 = (TextView) v.findViewById(R.id.disb3);

        Disbursement disb=items.get(position);

        disb1.setText(disb.getDepName());//Department Name

        String date = disb.getReqDate();//Requisition Date
        disb2.setText(date);

        disb3.setText(disb.getCpName());//Collection Point Name

        return v;
    }
}
