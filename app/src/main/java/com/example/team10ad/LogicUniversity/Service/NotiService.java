package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Noti;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotiService {

    @GET("notis")
    Call<List<Noti>> getNoti();

    @GET("noti/isread/{isread}/{deptid}/{role}")
    Call<List<Noti>> getNotiByCondition(@Path("isread") boolean isread,@Path("deptid") int deptid,@Path("role") int role);
}