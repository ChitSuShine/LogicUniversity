package com.nusiss.team10ad.LogicUniversity.Service;

import com.nusiss.team10ad.LogicUniversity.Model.InventoryDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InventoryDetailService {
    // getting inventory details including status
    @GET("inventorydetailswithstatus")
    Call<List<InventoryDetail>> getAllInventoryDetails();
}
