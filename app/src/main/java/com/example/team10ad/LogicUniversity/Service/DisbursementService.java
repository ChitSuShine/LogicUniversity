package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Disbursement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DisbursementService {
    @GET("requisitions/preparing")
    Call<List<Disbursement>> getAllDisbursements();

    @GET("disbursement/BreakDown")
    Call<List<Disbursement>> getAllStationaryRetrieval();

}