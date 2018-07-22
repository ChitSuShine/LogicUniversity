package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Requisition;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequisitionService {
    @GET("requisition")
    Call<List<Requisition>> getAllRequisitions();

    @GET("requisition/reqid/{id}")
    Call<Requisition> getReqById(@Path("id") String id);

    @POST("requisition/update")
    Call<Requisition> updateRequisition(@Body Requisition requisition);

    @GET("requisitionwithdisbursement/{id}")
    Call<Requisition> getScannedReqId(@Path("id") String id);
}
