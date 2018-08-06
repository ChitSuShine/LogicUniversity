package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Category;
import com.example.team10ad.LogicUniversity.Model.Inventory;
import com.example.team10ad.LogicUniversity.Model.Item;
import com.example.team10ad.LogicUniversity.Model.Supplier;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InventoryService {
    // getting all inventories
    @GET("inventories")
    Call<List<Inventory>> getAllInventories();

    // getting all categories
    @GET("categories")
    Call<List<Category>> getAllCategories();

    // getting all suppliers
    @GET("suppliers")
    Call<List<Supplier>> getAllSuppliers();

    // getting all items
    @GET("items")
    Call<List<Item>> getAllItems();
}
