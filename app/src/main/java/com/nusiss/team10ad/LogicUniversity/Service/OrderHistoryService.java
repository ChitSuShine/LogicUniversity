package com.nusiss.team10ad.LogicUniversity.Service;

import com.nusiss.team10ad.LogicUniversity.Model.Requisition;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OrderHistoryService {
    @GET("requisition/status/6")
    Call<List<Requisition>> getAllOrderHistory();
}
