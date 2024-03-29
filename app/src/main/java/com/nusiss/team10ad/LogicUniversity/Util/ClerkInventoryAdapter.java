package com.nusiss.team10ad.LogicUniversity.Util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nusiss.team10ad.LogicUniversity.Model.InventoryDetail;
import com.nusiss.team10ad.team10ad.R;

import java.util.List;

public class ClerkInventoryAdapter extends ArrayAdapter<InventoryDetail>{

    int resource;
    private List<InventoryDetail> items;

    public ClerkInventoryAdapter(@NonNull Context context, int resource, @NonNull List<InventoryDetail> items) {
        super(context, resource,items);
        this.resource = resource;
        this.items = items;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);
        TextView inv1 = (TextView) v.findViewById(R.id.inv1);
        TextView inv2 = (TextView) v.findViewById(R.id.inv2);
        TextView inv3 = (TextView) v.findViewById(R.id.inv3);
        TextView inv4 = (TextView) v.findViewById(R.id.inv4);
        TextView inv5 = (TextView) v.findViewById(R.id.inv5);
        TextView isPending = (TextView) v.findViewById(R.id.isPending);
        InventoryDetail inventoryDetail = items.get(position);
        inv1.setText(inventoryDetail.getItemDescription());
        inv2.setText(inventoryDetail.getCatName());
        inv3.setText(inventoryDetail.getStock());
        inv4.setText(inventoryDetail.getUom());
        inv5.setText(inventoryDetail.getShelfLocation()+inventoryDetail.getShelfLevel());

        if (inventoryDetail.getIsPending())
        {
            isPending.setVisibility(View.VISIBLE);
            LinearLayout linearLayout = v.findViewById(R.id.cardbg);
            linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.lightgray));
        }

        TextView currentStock = v.findViewById(R.id.currentStock);
        TextView reason = v.findViewById(R.id.reason);
        if(inventoryDetail.getCurrentStock()!=null)
        {
            currentStock.setText(inventoryDetail.getCurrentStock());
        }
        if(inventoryDetail.getReason()!=null)
        {
            reason.setText(inventoryDetail.getReason());
        }
        return v;
    }
}