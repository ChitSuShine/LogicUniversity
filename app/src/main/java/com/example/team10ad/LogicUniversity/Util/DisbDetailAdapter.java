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

import com.example.team10ad.LogicUniversity.Model.DisbursementDetail;
import com.example.team10ad.team10ad.R;

import java.util.List;

public class DisbDetailAdapter extends ArrayAdapter<DisbursementDetail> {
    int resource;
    private List<DisbursementDetail> items;

    public DisbDetailAdapter(@NonNull Context context, int resource, @NonNull List<DisbursementDetail> items) {
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

        TextView disbdetail1 = (TextView) v.findViewById(R.id.disbdetail1);
        TextView disbdetail2 = (TextView) v.findViewById(R.id.disbdetail2);
        TextView disbdetail3 = (TextView) v.findViewById(R.id.disbdetail3);
        TextView disbdetail4 = (TextView) v.findViewById(R.id.disbdetail4);

        DisbursementDetail disbDetail = items.get(position);

        disbdetail1.setText(disbDetail.getItemname());
        disbdetail2.setText(disbDetail.getCategoryName());
        disbdetail3.setText(disbDetail.getApprovedQty());
        disbdetail4.setText(disbDetail.getUOM());


        return v;
    }
}
