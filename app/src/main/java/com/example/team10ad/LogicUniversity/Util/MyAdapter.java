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

import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.team10ad.R;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Requisition> {

    int resource;
    private List<Requisition> items;

    public MyAdapter(@NonNull Context context, int resource, @NonNull List<Requisition> items) {
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
        TextView req1 = (TextView) v.findViewById(R.id.req1);
        TextView req2 = (TextView) v.findViewById(R.id.req2);
        TextView req3 = (TextView) v.findViewById(R.id.req3);
        Requisition r = items.get(position);

        String date = r.getReqDate().substring(0,10);

        req1.setText(r.getRasiedByname());
        req2.setText(date);
        req3.setText(Constants.STATUS[Integer.parseInt(r.getStatus())]);
        return v;
    }

}