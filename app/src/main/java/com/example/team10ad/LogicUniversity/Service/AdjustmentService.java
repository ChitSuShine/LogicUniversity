package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Adjustment;
import com.example.team10ad.LogicUniversity.Model.Delegation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AdjustmentService {
    @POST("adjustment/create")
    Call<Adjustment> createAdjustment(@Body Adjustment adjustment);
}
