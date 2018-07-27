package com.example.team10ad.LogicUniversity.Util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.ClerkApproveCollectionPoint;
import com.example.team10ad.LogicUniversity.Model.DepartmentCollectionPoint;
import com.example.team10ad.LogicUniversity.Service.CollectionPointService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.team10ad.R;
import com.google.android.gms.common.internal.DialogRedirect;


import java.util.ArrayList;
import java.util.List;

import de.mrapp.android.dialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClerkApproveCPAdapter extends ArrayAdapter<DepartmentCollectionPoint> {

    int resource;
    DepartmentCollectionPoint dcp = new DepartmentCollectionPoint();
    private List<DepartmentCollectionPoint> items;
    private static List<Button> btns = new ArrayList<>();

    String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

    public ClerkApproveCPAdapter(@NonNull Context context, int resource, @NonNull List<DepartmentCollectionPoint> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);

        TextView CPdeptname=(TextView)v.findViewById(R.id.CPdeptname);
        TextView CPname=(TextView)v.findViewById(R.id.CPname);
        TextView CPlocation=(TextView)v.findViewById(R.id.CPlocation);

        DepartmentCollectionPoint deptCP=items.get(position);

        CPdeptname.setText(deptCP.getDeptName());
        CPname.setText(deptCP.getCpName());
        CPlocation.setText(deptCP.getCpLocation());

        Button app_btn = new Button(getContext());
        app_btn.setText("Approve");
        app_btn.setTextColor(getContext().getResources().getColor( R.color.background));
        app_btn.setBackground(getContext().getResources().getDrawable( R.drawable.my_button_cancel));

        Button cancel_btn = new Button(getContext());
        cancel_btn.setText("Reject");
        cancel_btn.setTextColor(getContext().getResources().getColor( R.color.background));
        cancel_btn.setBackground(getContext().getResources().getDrawable( R.drawable.my_button_cancel));

        app_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(getContext());
                dialogBuilder.setTitle(R.string.approve_title);
                dialogBuilder.setMessage(R.string.approve_message);
                dialogBuilder.setIcon(R.drawable.ic_check);
                dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FragmentTransaction ft =
                                ((FragmentActivity)getContext())
                                .getSupportFragmentManager()
                                .beginTransaction();
                        ft.replace(R.id.content_frame, new ClerkApproveCollectionPoint())
                                .commit();
                    }
                });
                MaterialDialog dialog = dialogBuilder.create();
                dialog.setTitleColor(Color.BLACK);
                dialog.setMessageColor(Color.BLACK);
                dialog.setButtonTextColor(Color.BLACK);
                dialog.show();

                dcp= getItem(position);
                CollectionPointService cpService = ServiceGenerator.createService(CollectionPointService.class, token);
                Call<DepartmentCollectionPoint> call = cpService.approveCollectionPoint(dcp);
                call.enqueue(new Callback<DepartmentCollectionPoint>() {
                    @Override
                    public void onResponse(Call<DepartmentCollectionPoint> call, Response<DepartmentCollectionPoint> response) {
                        if(response.isSuccessful()){ }
                        else {
                            Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<DepartmentCollectionPoint> call, Throwable t) {
                        Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(getContext());
                dialogBuilder.setTitle(R.string.reject_title);
                dialogBuilder.setMessage(R.string.reject_message);
                dialogBuilder.setIcon(R.drawable.ic_reject);
                dialogBuilder.setPositiveButton(android.R.string.ok,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FragmentTransaction ft =
                                ((FragmentActivity)getContext())
                                        .getSupportFragmentManager()
                                        .beginTransaction();
                        ft.replace(R.id.content_frame, new ClerkApproveCollectionPoint())
                                .commit();
                    }
                });
                MaterialDialog dialog = dialogBuilder.create();
                dialog.setTitleColor(Color.BLACK);
                dialog.setMessageColor(Color.BLACK);
                dialog.setButtonTextColor(Color.BLACK);
                dialog.show();

                dcp= getItem(position);

                CollectionPointService cpService = ServiceGenerator.createService(CollectionPointService.class, token);
                Call<DepartmentCollectionPoint> call = cpService.rejectCollectionPoint(dcp);
                call.enqueue(new Callback<DepartmentCollectionPoint>() {
                    @Override
                    public void onResponse(Call<DepartmentCollectionPoint> call, Response<DepartmentCollectionPoint> response) {
                        if(response.isSuccessful()){ }
                        else {
                            Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<DepartmentCollectionPoint> call, Throwable t) {
                        Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        LinearLayout layoutapp = v.findViewById(R.id.approve);
        btns.add(app_btn);
        layoutapp.addView(app_btn);

        LinearLayout layoutcancel = v.findViewById(R.id.cancel);
        btns.add(cancel_btn);
        layoutcancel.addView(cancel_btn);

        return v;
    }
}
