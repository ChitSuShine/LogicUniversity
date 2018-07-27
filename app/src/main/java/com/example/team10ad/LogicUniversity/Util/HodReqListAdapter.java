package com.example.team10ad.LogicUniversity.Util;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Slide;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.team10ad.LogicUniversity.DepartmentHead.HodReqApproveRejectFragment;
import com.example.team10ad.LogicUniversity.DepartmentHead.HodRequisitionListFragment;
import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.team10ad.R;

import java.util.ArrayList;
import java.util.List;

public class HodReqListAdapter extends ArrayAdapter<Requisition> {

    int resource;
    private List<Requisition> items;
    private static List<Button> btns = new ArrayList<>();

    public HodReqListAdapter(@NonNull Context context, int resource, @NonNull List<Requisition> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);

        TextView hodreqlist1 = (TextView) v.findViewById(R.id.hodreqlist1);
        TextView hodreqlist2 = (TextView) v.findViewById(R.id.hodreqlist2);

        final Requisition hodreq= items.get(position);
        String date = hodreq.getReqDate().substring(0,10);
        hodreqlist1.setText(hodreq.getRasiedByname());
        hodreqlist2.setText(date);
        Button btn = new Button(getContext());
        btn.setText("Check");
        btn.setTextColor(getContext().getResources().getColor( R.color.background));
        btn.setBackground(getContext().getResources().getDrawable(R.drawable.my_button_approve));
        btn.setId(Integer.parseInt(hodreq.getReqID()));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity act = (AppCompatActivity) getContext();
                HodReqApproveRejectFragment hodReqApproveRejectFragment = new HodReqApproveRejectFragment();
                Bundle b = new Bundle();
                b.putString("id", String.valueOf(view.getId()));
                hodReqApproveRejectFragment.setArguments(b);
                FragmentTransaction fragmentTransaction = act.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, hodReqApproveRejectFragment);
                fragmentTransaction.commit();
            }
        });
        LinearLayout lo = v.findViewById(R.id.layoutToFind);
        btns.add(btn);
        lo.addView(btn);
        return v;
    }

}