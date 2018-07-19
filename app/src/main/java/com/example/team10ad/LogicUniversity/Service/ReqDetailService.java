package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.RequisitionDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ReqDetailService {

    @GET("requisitiondetails")
    Call<List<RequisitionDetail>> getAllReqDetail();
}
