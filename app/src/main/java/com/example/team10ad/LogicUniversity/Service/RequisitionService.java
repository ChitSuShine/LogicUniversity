package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Requisition;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequisitionService {
    @GET("requisition")
    Call<List<Requisition>> getAllRequisitions();
}
