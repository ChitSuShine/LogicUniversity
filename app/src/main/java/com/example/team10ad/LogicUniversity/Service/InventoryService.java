package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Category;
import com.example.team10ad.LogicUniversity.Model.Inventory;
import com.example.team10ad.LogicUniversity.Model.Item;
import com.example.team10ad.LogicUniversity.Model.Supplier;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InventoryService {
    @GET("inventories")
    Call<List<Inventory>> getAllInventories();

    @GET("categories")
    Call<List<Category>> getAllCategories();

    @GET("suppliers")
    Call<List<Supplier>> getAllSuppliers();

    @GET("items")
    Call<List<Item>> getAllItems();
}
