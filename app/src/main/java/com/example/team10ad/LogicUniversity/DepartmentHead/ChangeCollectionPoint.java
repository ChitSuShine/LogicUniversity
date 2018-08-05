package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.CollectionPoint;
import com.example.team10ad.LogicUniversity.Model.DepartmentCollectionPoint;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.CollectionPointService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;

import java.util.List;

import de.mrapp.android.dialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// Author: Htet Wai Yan
public class ChangeCollectionPoint extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    // text view for current collection point
    private TextView currentCp;

    private OnFragmentInteractionListener mListener;

    public ChangeCollectionPoint() { }

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
        currentCp =(TextView)view.findViewById(R.id.currentcp);
        final RadioGroup radioGroup1 = (RadioGroup)view.findViewById(R.id.changeRadioGroup);
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        // getting department Id from shared preference
        String json = MyApp.getInstance().getPreferenceManager().getString(Constants.USER_GSON);
        final User user = new Gson().fromJson(json, User.class);
        final int deptId = user.getDepId();
        // getting pending collection points
        final CollectionPointService cpService = ServiceGenerator.createService(CollectionPointService.class, token);
        Call<List<DepartmentCollectionPoint>> call = cpService.getPendingCollectionPoints();
        call.enqueue(new Callback<List<DepartmentCollectionPoint>>() {
            @Override
            public void onResponse(Call<List<DepartmentCollectionPoint>> call, Response<List<DepartmentCollectionPoint>> response) {
                if(response.isSuccessful()){
                    List<DepartmentCollectionPoint> dcpList = response.body();
                    DepartmentCollectionPoint pendingCp = new DepartmentCollectionPoint();
                    // checking if current department includes in pending list
                    boolean isPending = false;
                    for (DepartmentCollectionPoint dcp : dcpList) {
                        if(dcp.getDeptId() == deptId){
                            isPending = true;
                            pendingCp = dcp;
                            break;
                        }
                    }
                    // if pending, setting up the screen to let the user cancel the previous one
                    if(isPending){
                        currentCp.setText("Your previous request is pending.");
                        Button cancel = ((Button) view.findViewById(R.id.cpchange));
                        cancel.setText("Cancel");
                        TextView tv1=((TextView) view.findViewById(R.id.tvPendingName));
                        tv1.setText("Location Name:");
                        TextView tv2=((TextView) view.findViewById(R.id.tvPendingLoc));
                        tv2.setText("Location:");
                        ((TextView)view.findViewById(R.id.CpPendingName)).setText(pendingCp.getCpName());
                        ((TextView)view.findViewById(R.id.CpPendingLoc)).setText(pendingCp.getCpLocation());
                        final DepartmentCollectionPoint dcPoint = pendingCp;
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Call<DepartmentCollectionPoint> call = cpService.rejectCollectionPoint(dcPoint);
                                call.enqueue(new Callback<DepartmentCollectionPoint>() {
                                    @Override
                                    public void onResponse(Call<DepartmentCollectionPoint> call, Response<DepartmentCollectionPoint> response) {
                                        if (response.isSuccessful()) {
                                            ChangeCollectionPoint changeCollectionPoint = new ChangeCollectionPoint();
                                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                                            ft.replace(R.id.content_frame, changeCollectionPoint).commit();
                                        } else {
                                            Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<DepartmentCollectionPoint> call, Throwable t) {
                                        Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
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

        //Change Button
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
                            MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(getContext());
                            dialogBuilder.setTitle(R.string.info_title);
                            dialogBuilder.setMessage(R.string.info_message);
                            dialogBuilder.setIcon(R.drawable.ic_info);
                            dialogBuilder.setPositiveButton(android.R.string.ok,  new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) { }
                            });
                            MaterialDialog dialog = dialogBuilder.create();
                            dialog.setTitleColor(Color.BLACK);
                            dialog.setMessageColor(Color.BLACK);
                            dialog.setButtonTextColor(Color.BLACK);
                            dialog.show();
                            // refresh screen
                            FragmentTransaction ft =
                                    ((FragmentActivity)getContext())
                                            .getSupportFragmentManager()
                                            .beginTransaction();
                            ft.replace(R.id.content_frame, new ChangeCollectionPoint())
                                    .commit();

                        }
                    }

                    @Override
                    public void onFailure(Call<DepartmentCollectionPoint> call, Throwable t) {
                        Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        radioGroup1.setOnCheckedChangeListener(this);
        return view;
    }
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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if(radioGroup.getCheckedRadioButtonId() == (int) radioGroup.getTag()){
            getView().findViewById(R.id.cpchange).setEnabled(false);
        }else {
            getView().findViewById(R.id.cpchange).setEnabled(true);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    // populating collection points in radio buttons
    private void loadData(final CollectionPointService cpService,
                          final RadioGroup radioGroup, int deptId){
        Call<DepartmentCollectionPoint> call = cpService.getActiveCollectionPoint(deptId);
        call.enqueue(new Callback<DepartmentCollectionPoint>() {
            @Override
            public void onResponse(Call<DepartmentCollectionPoint> call, Response<DepartmentCollectionPoint> response) {
                if(response.isSuccessful()){
                    final DepartmentCollectionPoint temp = response.body();
                    // populate data
                    populateCpRadioBtns(cpService, radioGroup, temp.getCpId());
                    currentCp.setText(new StringBuilder("Current collection point is ")
                            .append(temp.getCpName()));
                    radioGroup.setTag(temp.getCpId());
                }
            }

            @Override
            public void onFailure(Call<DepartmentCollectionPoint> call, Throwable t) { }
        });
    }

    private void populateCpRadioBtns(CollectionPointService cpS, final RadioGroup rGrp, final int currentId){
        Call<List<CollectionPoint>> call = cpS.getCollectionPoints();
        call.enqueue(new Callback<List<CollectionPoint>>() {
            @Override
            public void onResponse(Call<List<CollectionPoint>> call, Response<List<CollectionPoint>> response) {
                if(response.isSuccessful()) {
                    // add radio buttons for collection points
                    for (CollectionPoint cP : response.body()) {
                        addRadioBtn(rGrp, cP, currentId);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CollectionPoint>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // adding radio button with suitable label and value
    private void addRadioBtn(RadioGroup rGrp, CollectionPoint cp, int currentId){
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setTextSize(17f);
        // setting different style for current collection point
        if(cp.getCpId() == currentId) {
            radioButton.setTypeface(null, Typeface.BOLD);
            radioButton.setChecked(true);
            radioButton.setTextSize(22f);
        }
        radioButton.setId(cp.getCpId());
        radioButton.setText(cp.getCpName());
        rGrp.addView(radioButton);
    }
}
