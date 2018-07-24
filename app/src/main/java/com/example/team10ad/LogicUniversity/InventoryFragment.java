package com.example.team10ad.LogicUniversity;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.Adjustment;
import com.example.team10ad.LogicUniversity.Model.AdjustmentDetail;
import com.example.team10ad.LogicUniversity.Model.InventoryDetail;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.AdjustmentService;
import com.example.team10ad.LogicUniversity.Service.InventoryDetailService;
import com.example.team10ad.LogicUniversity.Service.InventoryService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.ClerkInventoryAdapter;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.team10ad.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    ListView inventoryListView;
    Fragment fragment;
    String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

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
        InventoryDetailService inventoryService=ServiceGenerator.createService(InventoryDetailService.class,token);
        Call<List<InventoryDetail>> call=inventoryService.getAllInventoryDetails();
        call.enqueue(new Callback<List<InventoryDetail>>() {
            @Override
            public void onResponse(Call<List<InventoryDetail>> call, Response<List<InventoryDetail>> response) {
                if(response.isSuccessful()){
                    result = response.body();
                    final ClerkInventoryAdapter adapter = new ClerkInventoryAdapter(getContext(),R.layout.row_clerkinventory,result);
                    inventoryListView = (ListView) view.findViewById(R.id.inventorylistview);
                    inventoryListView.setAdapter(adapter);
                    inventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View viewClick, int i, long l) {
                            showInputBox(i);
                        }
                    });
                }
                else
                {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<InventoryDetail>> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
        Button updateInventory = view.findViewById(R.id.btn_updateInventory);
        updateInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processDiscrepency();
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

    public void showInputBox (final int position)
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.setTitle("Modify quantity");
        dialog.setContentView(R.layout.input_box);
        final EditText currentStock = dialog.findViewById(R.id.edit_currentStock);
        final EditText reason = dialog.findViewById(R.id.edit_reason);
        currentStock.setText(result.get(position).getCurrentStock());
        reason.setText(result.get(position).getReason());
        Button okButton = dialog.findViewById(R.id.btn_okInventory);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InventoryDetail detail = result.get(position);
                detail.setCurrentStock(currentStock.getText().toString());
                detail.setReason(reason.getText().toString());
                dialog.dismiss();
            }
        });

        Button cancelButton = dialog.findViewById(R.id.btn_cancelInventory);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void processDiscrepency()
    {
        ArrayList<AdjustmentDetail> detailList = new ArrayList<AdjustmentDetail>();
        for(InventoryDetail inventoryDetail: result)
        {
            if(inventoryDetail.getCurrentStock()!=null && !inventoryDetail.getCurrentStock().equals(""))
            {
                AdjustmentDetail adjustmentDetail = new AdjustmentDetail();
                adjustmentDetail.setItemId(Integer.parseInt(inventoryDetail.getItemid()));
                adjustmentDetail.setItemDescription(inventoryDetail.getItemDescription());
                adjustmentDetail.setCategoryName(inventoryDetail.getCatName());
                adjustmentDetail.setUom(inventoryDetail.getUom());
                int adjustedQty = Integer.parseInt(inventoryDetail.getCurrentStock()) - Integer.parseInt(inventoryDetail.getStock());
                adjustmentDetail.setAdjustedQty(adjustedQty);
                adjustmentDetail.setReason(inventoryDetail.getReason());
                detailList.add(adjustmentDetail);
            }
        }
        // Getting current user's info & store in shared preferences
        Gson gson = new Gson();
        String json = MyApp.getInstance().getPreferenceManager().getString(Constants.USER_GSON);
        final User user = gson.fromJson(json, User.class);

        // Getting current date
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(todayDate);

        Toast.makeText(MyApp.getInstance(),"MG "+detailList.size(), Toast.LENGTH_SHORT).show();
        Adjustment adjustment = new Adjustment();
        adjustment.setRaisedBy(user.getUserId());
        adjustment.setIssuedDate(today);
        adjustment.setAdjDetails(detailList);

        AdjustmentService adjustmentService = ServiceGenerator.createService(AdjustmentService.class, token);
        Call<Adjustment> call = adjustmentService.createAdjustment(adjustment);
        call.enqueue(new Callback<Adjustment>() {
            @Override
            public void onResponse(Call<Adjustment> call, Response<Adjustment> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(MyApp.getInstance(), Constants.INVENTORY_SUCCESS_MSG + response.body().getAdjId(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Adjustment> call, Throwable t) {
                Toast.makeText(MyApp.getInstance(), Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
