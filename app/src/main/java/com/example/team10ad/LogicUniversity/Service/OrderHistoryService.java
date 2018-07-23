package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.OrderHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderHistoryService {
    @GET("orderhistory/{id}")
    Call<List<OrderHistory>> getAllOrderHistory(@Path("id") int id);

}
