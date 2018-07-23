package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Disbursement;
import com.example.team10ad.LogicUniversity.Model.Requisition;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DisbursementService {
    @GET("requisitions/preparing")
    Call<List<Disbursement>> getAllDisbursements();

    @GET("disbursement/BreakDown")
    Call<List<Disbursement>> getAllStationaryRetrieval();

    @GET("requisitionwithdisbursement/{id}")
    Call<Disbursement> getScannedReqId(@Path("id") String id);
}