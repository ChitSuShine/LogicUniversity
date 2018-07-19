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

import com.example.team10ad.LogicUniversity.Model.RequisitionDetail;
import com.example.team10ad.team10ad.R;

import java.util.List;

public class HodTrackingAdapter extends ArrayAdapter<RequisitionDetail>{

    int resource;
    private List<RequisitionDetail> items;

    public HodTrackingAdapter(@NonNull Context context, int resource, @NonNull List<RequisitionDetail> items) {
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

        TextView tracking1 = (TextView) v.findViewById(R.id.tracking1);
        TextView tracking2 = (TextView) v.findViewById(R.id.tracking2);
        TextView tracking3 = (TextView) v.findViewById(R.id.tracking3);
        TextView tracking4 = (TextView) v.findViewById(R.id.tracking4);

        RequisitionDetail requisitionDetail = items.get(position);
        tracking1.setText(requisitionDetail.getItemname());
        tracking2.setText(requisitionDetail.getCategoryName());
        tracking3.setText(requisitionDetail.getQty());
        tracking4.setText(requisitionDetail.getUOM());

        return v;
    }

}