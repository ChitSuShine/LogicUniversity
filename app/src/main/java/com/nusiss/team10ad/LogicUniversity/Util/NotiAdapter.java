package com.nusiss.team10ad.LogicUniversity.Util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nusiss.team10ad.LogicUniversity.Model.Noti;
import com.nusiss.team10ad.team10ad.R;

import java.util.ArrayList;
import java.util.List;

public class NotiAdapter extends ArrayAdapter<Noti>{

    int resource;
    private List<Noti> items;
    private static List<ImageView> imgs = new ArrayList<>();

    public NotiAdapter(@NonNull Context context, int resource, @NonNull List<Noti> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);

        TextView notiTilte=(TextView) v.findViewById(R.id.notiTitle);
        TextView notiRemark=(TextView)v.findViewById(R.id.notiRemark);
        TextView notiTime=(TextView)v.findViewById(R.id.notitime);

        Noti noti= items.get(position);

        notiTilte.setText(noti.getTitle());
        notiRemark.setText(noti.getRemark());
        notiTime.setText(noti.getDatetime());

        ImageView img=new ImageView(getContext());
        img.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.msg));
        LinearLayout lo = v.findViewById(R.id.msg);
        imgs.add(img);
        lo.addView(img);
        return v;
    }

    public List<Noti> getItems() {
        return items;
    }
}
