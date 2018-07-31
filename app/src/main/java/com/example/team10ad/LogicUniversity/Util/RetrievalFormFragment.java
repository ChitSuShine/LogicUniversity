package com.example.team10ad.LogicUniversity.Util;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.ClerkMapDeliveryPoint;
import com.example.team10ad.LogicUniversity.Model.Disbursement;
import com.example.team10ad.LogicUniversity.Model.StationaryRetrieval;
import com.example.team10ad.LogicUniversity.RequisitionList;
import com.example.team10ad.LogicUniversity.Service.DisbursementService;
import com.example.team10ad.LogicUniversity.Service.RequisitionService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.team10ad.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetrievalFormFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ListView retrievallist;
    List<StationaryRetrieval> result;
    Disbursement res;
    String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

    public RetrievalFormFragment() { }
    public static RetrievalFormFragment newInstance(String param1, String param2) {
        RetrievalFormFragment fragment = new RetrievalFormFragment();
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

        final View view= inflater.inflate(R.layout.fragment_retrieval_form, container, false);

        DisbursementService disbService= ServiceGenerator.createService(DisbursementService.class,token);
        Call<List<StationaryRetrieval>> call=disbService.getAllStationaryRetrieval();
        call.enqueue(new Callback<List<StationaryRetrieval>>() {
            @Override
            public void onResponse(Call<List<StationaryRetrieval>> call, Response<List<StationaryRetrieval>> response) {
                if(response.isSuccessful()){
                    result=response.body();
                    if(result.size() < 1) {
                        Button btn = view.findViewById(R.id.itemcollect);
                        btn.setEnabled(false);
                    }
                    final RetrievalAdapter retrievalAdapter=new RetrievalAdapter(getContext(),R.layout.row_retrievallist,result);
                    retrievallist=(ListView)view.findViewById(R.id.retrievallist);
                    retrievallist.setAdapter(retrievalAdapter);
                }
                else{
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<StationaryRetrieval>> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });

        //Item Collect Button

        Button itemcollect=(Button)view.findViewById(R.id.itemcollect);
        itemcollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                res.setStatus(3);
                DisbursementService dService = ServiceGenerator.createService(DisbursementService.class,token);
                Call<List<Disbursement>> call = dService.collectAllItems(res);
                call.enqueue(new Callback<List<Disbursement>>() {
                    @Override
                    public void onResponse(Call<List<Disbursement>> call, Response<List<Disbursement>> response) {
                        if(response.isSuccessful()){ }
                        ClerkMapDeliveryPoint cpMap=new ClerkMapDeliveryPoint();
                        FragmentManager fm=getFragmentManager();
                        fm.beginTransaction().replace(R.id.content_frame,cpMap).commit();
                    }
                    @Override
                    public void onFailure(Call<List<Disbursement>> call, Throwable t) {
                        Toast.makeText(getActivity(),Constants.NETWORK_ERROR_MSG,Toast.LENGTH_LONG).show();
                    }
                });
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
