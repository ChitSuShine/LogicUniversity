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
    @GET("requisitions/preparing")
    Call<List<Disbursement>> getAllDisbursements();

    @GET("disbursement/clerk")
    Call<List<StationaryRetrieval>> getAllStationaryRetrieval();

    @GET("requisitionwithdisbursement/{id}")
    Call<Disbursement> getScannedReqId(@Path("id") String id);

    @GET("outstandingreq/requisition/{id}")
    Call<Disbursement> getOutstandingReq(@Path("id") String id);

    @POST("outstandingreq/complete")
    Call<Disbursement> changeOutstandingStatus(@Body Disbursement disbursement);
}