package com.nusiss.team10ad.LogicUniversity.Service;

import com.nusiss.team10ad.LogicUniversity.Model.Requisition;
import com.nusiss.team10ad.LogicUniversity.Model.RequisitionDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequisitionService {
    // getting all requisitions
    @GET("requisition")
    Call<List<Requisition>> getAllRequisitions();

    // getting requisition details by requisition Id number
    @GET("requisition/reqid/{id}")
    Call<Requisition> getReqById(@Path("id") String id);

    // updating specific requisition
    @POST("requisition/update")
    Call<Requisition> updateRequisition(@Body Requisition requisition);

    // changing the status of specific requisition to completed
    @POST("requisition/status/completed")
    Call<Requisition> changeRequisitionStatus(@Body Requisition requisition);
}
