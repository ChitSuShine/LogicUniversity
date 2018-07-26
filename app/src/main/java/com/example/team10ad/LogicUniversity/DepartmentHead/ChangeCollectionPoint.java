package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.CollectionPoint;
import com.example.team10ad.LogicUniversity.Model.DepartmentCollectionPoint;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.CollectionPointService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeCollectionPoint extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CollectionPoint current = new CollectionPoint();
    private TextView currentcp;

    private OnFragmentInteractionListener mListener;

    public ChangeCollectionPoint() {
        // Required empty public constructor
    }

    public static ChangeCollectionPoint newInstance(String param1, String param2) {
        ChangeCollectionPoint fragment = new ChangeCollectionPoint();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_change_collection_point, container, false);
        currentcp=(TextView)view.findViewById(R.id.currentcp);
        final RadioGroup radioGroup1 = (RadioGroup)view.findViewById(R.id.changeRadioGroup);
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

        String json = MyApp.getInstance().getPreferenceManager().getString(Constants.USER_GSON);
        final User user = new Gson().fromJson(json, User.class);
        final int deptId = user.getDepId();

        final CollectionPointService cpService = ServiceGenerator.createService(CollectionPointService.class, token);
        Call<List<DepartmentCollectionPoint>> call = cpService.getPendingCollectionPoints();
        call.enqueue(new Callback<List<DepartmentCollectionPoint>>() {
            @Override
            public void onResponse(Call<List<DepartmentCollectionPoint>> call, Response<List<DepartmentCollectionPoint>> response) {
                if(response.isSuccessful()){
                    List<DepartmentCollectionPoint> dcpList = response.body();
                    boolean isPending = false;
                    for (DepartmentCollectionPoint dcp : dcpList) {
                        if(dcp.getDeptId() == deptId){
                            isPending = true;
                            break;
                        }
                    }
                    if(isPending){
                        currentcp.setText("Your previous request is pending.");
                        ((Button) view.findViewById(R.id.cpchange))
                                .setEnabled(false);
                    } else {
                        loadData(cpService, radioGroup1, deptId);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DepartmentCollectionPoint>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn = view.findViewById(R.id.cpchange);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DepartmentCollectionPoint dcp = new DepartmentCollectionPoint();
                dcp.setCpId(radioGroup1.getCheckedRadioButtonId());
                dcp.setDeptId(deptId);
                Call<DepartmentCollectionPoint> call = cpService.changeCollectionPoint(dcp);
                call.enqueue(new Callback<DepartmentCollectionPoint>() {
                    @Override
                    public void onResponse(Call<DepartmentCollectionPoint> call, Response<DepartmentCollectionPoint> response) {
                        if(response.isSuccessful() && response.body() != null){
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Change Collection Point")
                                    .setMessage("Collection Point is changed successfully.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            getFragmentManager().beginTransaction()
                                                    .replace(R.id.content_frame, new HodDashboardFragment())
                                                    .commit();
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DepartmentCollectionPoint> call, Throwable t) {
                        Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void loadData(final CollectionPointService cpService,
                          final RadioGroup radioGroup, int deptId){
        Call<DepartmentCollectionPoint> call = cpService.getActiveCollectionPoint(deptId);
        call.enqueue(new Callback<DepartmentCollectionPoint>() {
            @Override
            public void onResponse(Call<DepartmentCollectionPoint> call, Response<DepartmentCollectionPoint> response) {
                if(response.isSuccessful()){
                    DepartmentCollectionPoint temp = response.body();
                    populateCpRadioBtns(cpService, radioGroup, temp.getCpId());
                    currentcp.setText(new StringBuilder("Current collection point is ")
                            .append(temp.getCpName()));
                }
            }

            @Override
            public void onFailure(Call<DepartmentCollectionPoint> call, Throwable t) {

            }
        });
    }

    private void populateCpRadioBtns(CollectionPointService cpS, final RadioGroup rGrp, final int currentId){
        Call<List<CollectionPoint>> call = cpS.getCollectionPoints();
        call.enqueue(new Callback<List<CollectionPoint>>() {
            @Override
            public void onResponse(Call<List<CollectionPoint>> call, Response<List<CollectionPoint>> response) {
                if(response.isSuccessful()) {
                    for (CollectionPoint cP : response.body()) {
                        addBtn(rGrp, cP, currentId);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CollectionPoint>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBtn(RadioGroup rGrp, CollectionPoint cp, int currentId){
        RadioButton radioButton = new RadioButton(getContext());
        if(cp.getCpId() == currentId) {
            radioButton.setTypeface(null, Typeface.BOLD);
            radioButton.setChecked(true);
        }
        radioButton.setTextSize(17f);
        radioButton.setId(cp.getCpId());
        radioButton.setText(cp.getCpName());
        rGrp.addView(radioButton);
    }
}
