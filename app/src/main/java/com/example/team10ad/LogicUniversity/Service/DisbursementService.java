package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Disbursement;
import com.example.team10ad.LogicUniversity.Model.Requisition;
import com.example.team10ad.LogicUniversity.Model.StationaryRetrieval;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DisbursementService {
    // updating
    @GET("requisition/updatetopreparing")
    Call<List<Disbursement>> collectAllItems();

    @GET("requisitions/preparing")
    Call<List<Disbursement>> getAllDisbursements();

    @POST("requisition/update")
    Call<Requisition> UpdateRequisition(@Body Requisition requisition);

    @GET("disbursement/clerk")
    Call<List<StationaryRetrieval>> getAllStationaryRetrieval();

    @GET("requisitionwithdisbursement/{id}")
    Call<Disbursement> getScannedReqId(@Path("id") String id);

    @GET("outstandingreq/requisition/{id}")
    Call<Disbursement> getOutstandingReq(@Path("id") String id);

    @POST("outstandingreq/complete")
    Call<Disbursement> changeOutstandingStatus(@Body Disbursement disbursement);
}