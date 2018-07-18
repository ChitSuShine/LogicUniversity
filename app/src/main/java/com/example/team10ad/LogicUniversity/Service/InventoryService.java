package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Inventory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InventoryService {
    @GET("inventories")
    Call<List<Inventory>> getAllInventories();
}
