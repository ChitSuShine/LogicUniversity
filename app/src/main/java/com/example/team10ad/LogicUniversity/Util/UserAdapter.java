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
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.team10ad.R;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    int resource;
    private List<User> items;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource, null);

        TextView employeeName = (TextView) view.findViewById(R.id.employeeName);
        User user = items.get(position);
        employeeName.setText(user.getFullName());
        return view;
    }
}
