package com.example.team10ad.LogicUniversity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.DepartmentHead.ChangeCollectionPoint;
import com.example.team10ad.LogicUniversity.DepartmentHead.HodRequisitionListFragment;
import com.example.team10ad.LogicUniversity.Model.Noti;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.NotiService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.LogicUniversity.Util.NotiAdapter;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import de.mrapp.android.dialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView notiList;
    List<Noti> result;



    public Notification() { }

    public static Notification newInstance(String param1, String param2) {
        Notification fragment = new Notification();
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
        final View view= inflater.inflate(R.layout.fragment_notification, container, false);

        String userInfo = MyApp.getPreferenceManager().getString(Constants.USER_GSON);
        final User user = new Gson().fromJson(userInfo, User.class);
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

        final NotiService notiService= ServiceGenerator.createService(NotiService.class,token);
        Call<List<Noti>> callnoti=notiService.getNotiByCondition(false,user.getDepId(),user.getRole());
        callnoti.enqueue(new Callback<List<Noti>>() {
            @Override
            public void onResponse(Call<List<Noti>> call, Response<List<Noti>> response) {
                if(response.isSuccessful()){
                    result=response.body();
                    final NotiAdapter notiadapter = new NotiAdapter(getContext(),R.layout.row_allnoti,result);
                    notiList = (ListView) view.findViewById(R.id.notiList);
                    notiList.setAdapter(notiadapter);
                    if(notiList.getAdapter().getCount()==0){
                        TextView emptyText = view.findViewById(android.R.id.empty);
                        notiList.setEmptyView(emptyText);
                    }
                    notiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            final int notiType = notiadapter.getItems().get(i).getNotiType();
                            int notiId = notiadapter.getItems().get(i).getNotiID();
                            Call<Noti> callNoti = notiService.markAsRead(notiId);
                            callNoti.enqueue(new Callback<Noti>() {
                                @Override
                                public void onResponse(Call<Noti> call, Response<Noti> response) {
                                    if(response.isSuccessful()){
                                        switch (notiType){
                                            case 0:
                                                break;
                                            case 1:
                                                break;
                                            case 2:
                                                //Requisition Approval
                                                HodRequisitionListFragment hodapprej=new HodRequisitionListFragment();
                                                FragmentManager fragmentManager=getFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.content_frame, hodapprej).commit();
                                                break;
                                            case 3:
                                                break;
                                            case 4:
                                                break;
                                            case 5:
                                                break;
                                            case 6:
                                                //HOD Requisition Approved/Rejected Noti
                                                MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(getContext());
                                                dialogBuilder.setTitle(R.string.note);
                                                dialogBuilder.setMessage(R.string.note_message);
                                                dialogBuilder.setIcon(R.drawable.ic_info);
                                                dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Notification notimain = new Notification();
                                                        FragmentManager fragmentMg = getFragmentManager();
                                                        fragmentMg.beginTransaction().detach(Notification.this)
                                                                .replace(R.id.content_frame, notimain).addToBackStack(null).commit();
                                                    }
                                                });
                                                MaterialDialog dialog = dialogBuilder.create();
                                                dialog.setTitleColor(Color.BLACK);
                                                dialog.setMessageColor(getContext().getResources().getColor(R.color.bg));
                                                dialog.setButtonTextColor(Color.BLACK);
                                                dialog.show();

                                                break;
                                            case 7:
                                                MaterialDialog.Builder dialogBuilder2 = new MaterialDialog.Builder(getContext());
                                                dialogBuilder2.setTitle(R.string.note);
                                                dialogBuilder2.setMessage(R.string.note2_message);
                                                dialogBuilder2.setIcon(R.drawable.ic_info);
                                                dialogBuilder2.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Notification notimain = new Notification();
                                                        FragmentManager fragmentMg = getFragmentManager();
                                                        fragmentMg.beginTransaction().detach(Notification.this)
                                                                .replace(R.id.content_frame, notimain).addToBackStack(null).commit();
                                                    }
                                                });
                                                MaterialDialog dialog2 = dialogBuilder2.create();
                                                dialog2.setTitleColor(Color.BLACK);
                                                dialog2.setMessageColor(getContext().getResources().getColor(R.color.bg));
                                                dialog2.setButtonTextColor(Color.BLACK);
                                                dialog2.show();


                                                break;
                                            case 8:
                                                //Approved Collection Point Change
                                                ChangeCollectionPoint appcollect=new ChangeCollectionPoint();
                                                FragmentTransaction fragmentTransaction8 = getActivity().getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction8.replace(R.id.content_frame, appcollect).commit();
                                                break;
                                            case 9:
                                                //Rejected Collection Point Change
                                                ChangeCollectionPoint rejcollect=new ChangeCollectionPoint();
                                                FragmentTransaction fragmentTransaction9 = getActivity().getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction9.replace(R.id.content_frame, rejcollect).commit();
                                                break;
                                            case 10:
                                                //Collection Point Change Request Approval
                                                ClerkApproveCollectionPoint clerkapprovecp=new ClerkApproveCollectionPoint();
                                                FragmentManager fragmentManager10=getFragmentManager();
                                                fragmentManager10.beginTransaction().replace(R.id.content_frame, clerkapprovecp).commit();
                                                break;
                                            case 11:
                                                break;
                                            case 12:
                                                //Authority Cancellation
                                                break;
                                            case 13:
                                                //Department Representative
                                                break;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Noti> call, Throwable t) {

                                }
                            });
                        }
                    });
                }
                else{
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Noti>> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
