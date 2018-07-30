package com.example.team10ad.LogicUniversity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.DepartmentCollectionPoint;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.CollectionPointService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.ClerkApproveCPAdapter;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClerkApproveCollectionPoint extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    List<DepartmentCollectionPoint> result;
    DepartmentCollectionPoint dcp ;
    ListView approveCPList;

    String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

    public ClerkApproveCollectionPoint() { }

    public static ClerkApproveCollectionPoint newInstance(String param1, String param2) {
        ClerkApproveCollectionPoint fragment = new ClerkApproveCollectionPoint();
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

        final View view=inflater.inflate(R.layout.fragment_clerk_approve_collection_point, container, false);

        CollectionPointService cpService= ServiceGenerator.createService(CollectionPointService.class,token);

        Call<List<DepartmentCollectionPoint>> callCP=cpService.getPendingCollectionPoints();
        callCP.enqueue(new Callback<List<DepartmentCollectionPoint>>() {
            @Override
            public void onResponse(Call<List<DepartmentCollectionPoint>> call, Response<List<DepartmentCollectionPoint>> response) {
                if(response.isSuccessful()){

                    result=response.body();

                    ClerkApproveCPAdapter CPAdapter=new ClerkApproveCPAdapter(getContext(),R.layout.row_approvecp,result);
                    approveCPList=(ListView)view.findViewById(R.id.approveCPList);
                    approveCPList.setAdapter(CPAdapter);
                    if(approveCPList.getAdapter().getCount()==0){
                        TextView emptyText = view.findViewById(android.R.id.empty);
                        approveCPList.setEmptyView(emptyText);
                    }
                }
                else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<DepartmentCollectionPoint>> call, Throwable t) {
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
