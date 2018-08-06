package com.nusiss.team10ad.LogicUniversity.Service;

import com.nusiss.team10ad.LogicUniversity.Model.Disbursement;
import com.nusiss.team10ad.LogicUniversity.Model.Requisition;
import com.nusiss.team10ad.LogicUniversity.Model.StationaryRetrieval;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DisbursementService {
    // updating all request pending requisitions to preparing step
    @GET("requisition/updatetopreparing")
    Call<List<Disbursement>> collectAllItems();

    // get all preparing disbursements
    @GET("requisitions/preparing")
    Call<List<Disbursement>> getAllDisbursements();

    // updating specific requisition
    @POST("requisition/update")
    Call<Requisition> UpdateRequisition(@Body Requisition requisition);

    // get all item list and quantities that are to be collected from warehouse
    @GET("disbursement/clerk")
    Call<List<StationaryRetrieval>> getAllStationaryRetrieval();

    // getting disbursement details by requisition Id number
    @GET("requisitionwithdisbursement/{id}")
    Call<Disbursement> getScannedReqId(@Path("id") String id);

    // getting outstanding item list by requisition Id number
    @GET("outstandingreq/requisition/{id}")
    Call<Disbursement> getOutstandingReq(@Path("id") String id);

    // completing outstanding disbursement
    @POST("outstandingreq/complete")
    Call<Disbursement> changeOutstandingStatus(@Body Disbursement disbursement);
}