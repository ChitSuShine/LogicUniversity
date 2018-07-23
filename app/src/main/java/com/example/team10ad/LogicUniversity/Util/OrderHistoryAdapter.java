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

import com.example.team10ad.LogicUniversity.Model.OrderHistory;
import com.example.team10ad.team10ad.R;

import java.util.List;

public class OrderHistoryAdapter extends ArrayAdapter<OrderHistory> {
    int resource;
    private List<OrderHistory> items;

    public OrderHistoryAdapter(@NonNull Context context, int resource, @NonNull List<OrderHistory> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);

        TextView orderhis1=(TextView) v.findViewById(R.id.orderhis1);
        TextView orderhis2=(TextView) v.findViewById(R.id.orderhis2);
        TextView orderhis3=(TextView) v.findViewById(R.id.orderhis3);
        TextView orderhis4=(TextView) v.findViewById(R.id.orderhis4);

        OrderHistory orderhis=items.get(position);
        orderhis1.setText(orderhis.getReqdate());
        orderhis2.setText(orderhis.getRaisename());
        orderhis3.setText(orderhis.getCpname());
        orderhis4.setText(orderhis.getStatus());
        return v;
    }
}