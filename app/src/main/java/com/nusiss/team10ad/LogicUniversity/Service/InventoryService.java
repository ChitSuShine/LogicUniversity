package com.nusiss.team10ad.LogicUniversity.Service;

import com.nusiss.team10ad.LogicUniversity.Model.Category;
import com.nusiss.team10ad.LogicUniversity.Model.Inventory;
import com.nusiss.team10ad.LogicUniversity.Model.Item;
import com.nusiss.team10ad.LogicUniversity.Model.Supplier;

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
