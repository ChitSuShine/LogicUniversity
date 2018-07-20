package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.RequisitionDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReqDetailService {

    @GET("requisition/reqid/{id}")
    Call<RequisitionDetail> getReqDetailById(@Path("id") int id);

    @GET("requisitiondetails")
    Call<List<RequisitionDetail>> getAllReqDetail();
}