package com.example.team10ad.LogicUniversity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.InventoryDetail;
import com.example.team10ad.LogicUniversity.Service.InventoryDetailService;
import com.example.team10ad.LogicUniversity.Service.InventoryService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.ClerkInventoryAdapter;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    List<InventoryDetail> result;
    ListView inventorylistView;

    public InventoryFragment() {
    }

    public static InventoryFragment newInstance(String param1, String param2) {
        InventoryFragment fragment = new InventoryFragment();
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
        final View view= inflater.inflate(R.layout.fragment_inventory, container, false);
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
        InventoryDetailService inventoryService=ServiceGenerator.createService(InventoryDetailService.class,token);
        Call<List<InventoryDetail>> call=inventoryService.getAllInventoryDetails();
        call.enqueue(new Callback<List<InventoryDetail>>() {
            @Override
            public void onResponse(Call<List<InventoryDetail>> call, Response<List<InventoryDetail>> response) {
                if(response.isSuccessful()){
                    result = response.body();
                    final ClerkInventoryAdapter adapter = new ClerkInventoryAdapter(getContext(),R.layout.row_clerkinventory,result);
                    inventorylistView = (ListView) view.findViewById(R.id.inventorylistview);
                    inventorylistView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<InventoryDetail>> call, Throwable t) {

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
