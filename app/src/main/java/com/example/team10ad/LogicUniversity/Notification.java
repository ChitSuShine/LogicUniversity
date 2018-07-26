package com.example.team10ad.LogicUniversity;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
                    notiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext())
                                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                                    .setContentTitle("My notification")
                                    .setContentText("Much longer text that cannot fit one line...")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            mBuilder.setSound(alarmSound);
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
                                                break;
                                            case 7:
                                                break;
                                            case 8:
                                                break;
                                            case 9:
                                                break;
                                            case 10:
                                                ClerkApproveCollectionPoint clerkapprovecp=new ClerkApproveCollectionPoint();
                                                FragmentManager fragmentManager10=getFragmentManager();
                                                fragmentManager10.beginTransaction().replace(R.id.content_frame, clerkapprovecp).commit();
                                                break;
                                            case 11:
                                                break;
                                            case 12:
                                                break;
                                            case 13:
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
