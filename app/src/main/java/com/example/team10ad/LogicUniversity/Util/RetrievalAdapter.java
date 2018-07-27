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

import com.example.team10ad.LogicUniversity.Model.StationaryRetrieval;
import com.example.team10ad.team10ad.R;

import java.util.List;

public class RetrievalAdapter extends ArrayAdapter<StationaryRetrieval>  {

    int resource;
    private List<StationaryRetrieval> items;

    public RetrievalAdapter(@NonNull Context context, int resource, @NonNull List<StationaryRetrieval> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);

        TextView retrieve1 = (TextView) v.findViewById(R.id.retrieve1);
        TextView retrieve2 = (TextView) v.findViewById(R.id.retrieve2);
        TextView retrieve3 = (TextView) v.findViewById(R.id.retrieve3);
        TextView retrieve4 = (TextView) v.findViewById(R.id.retrieve4);
        TextView retrieve5 = (TextView) v.findViewById(R.id.retrieve5);


        StationaryRetrieval retrieval=items.get(position);

        retrieve1.setText(retrieval.getShelfLocaition()+retrieval.getShelfLevel());
        retrieve2.setText(retrieval.getDescription());
        retrieve3.setText(retrieval.getCatName());
        retrieve4.setText(retrieval.getUom());
        retrieve5.setText(retrieval.getTotal());
        return v;

    }
}
