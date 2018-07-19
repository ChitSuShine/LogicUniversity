package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.InventoryDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InventoryDetailService {
    @GET("inventorydetails")
    Call<List<InventoryDetail>> getAllInventoryDetails();
}
