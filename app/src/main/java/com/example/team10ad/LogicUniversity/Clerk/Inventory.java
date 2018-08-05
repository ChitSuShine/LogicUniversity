package com.example.team10ad.LogicUniversity.Clerk;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.Adjustment;
import com.example.team10ad.LogicUniversity.Model.AdjustmentDetail;
import com.example.team10ad.LogicUniversity.Model.InventoryDetail;
import com.example.team10ad.LogicUniversity.Model.User;
import com.example.team10ad.LogicUniversity.Service.AdjustmentService;
import com.example.team10ad.LogicUniversity.Service.InventoryDetailService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator.ServiceGenerator;
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

public class Inventory extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    List<InventoryDetail> result;
    ListView inventoryListView;
    Fragment fragment;
    String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);
    ArrayList<AdjustmentDetail> detailList = new ArrayList<AdjustmentDetail>();

    public Inventory() { }

    public static Inventory newInstance(String param1, String param2) {
        Inventory fragment = new Inventory();
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
                            if(!result.get(i).getIsPending())
                            {
                                showInputBox(i);
                            }
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
            public void onClick(View clickView) {
                processDiscrepancy();
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
        dialog.setContentView(R.layout.input_box);
        final EditText currentStock = dialog.findViewById(R.id.edit_currentStock);
        final EditText reason = dialog.findViewById(R.id.edit_reason);
        currentStock.setText(result.get(position).getCurrentStock());
        reason.setText(result.get(position).getReason());
        Button okButton = dialog.findViewById(R.id.btn_okInventory);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InventoryDetail inventoryDetail = result.get(position);
                inventoryDetail.setCurrentStock(currentStock.getText().toString());
                inventoryDetail.setReason(reason.getText().toString());
                if(inventoryDetail.getCurrentStock()!=null && !inventoryDetail.getCurrentStock().equals("")) {
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
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void processDiscrepancy()
    {
        if(detailList.size()>0)
        {
            // Getting current user's info & store in shared preferences
            Gson gson = new Gson();
            String json = MyApp.getInstance().getPreferenceManager().getString(Constants.USER_GSON);
            final User user = gson.fromJson(json, User.class);

            // Getting current date
            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
            String today = format.format(todayDate);

            // Creating Adjustment object to post
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
                        Toast.makeText(MyApp.getInstance(), Constants.INVENTORY_SUCCESS_MSG, Toast.LENGTH_SHORT).show();
                        detailList.clear();
                        // Reload the fragment
                        Fragment fragment = getFragmentManager().findFragmentByTag(R.id.inventory + "");
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(fragment);
                        ft.attach(fragment);
                        ft.commit();
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
        else
        {
            new android.support.v7.app.AlertDialog.Builder(getContext())
                    .setTitle(Constants.WARNING_MSG)
                    .setMessage(Constants.INVENTORY_WARNING_MSG)
                    .setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setIcon(R.drawable.alert)
                    .show();
        }
    }
}
