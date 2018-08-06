package com.nusiss.team10ad.LogicUniversity.Service;

import com.nusiss.team10ad.LogicUniversity.Model.Delegation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DelegationService {
    @GET("delegation/search/{id}")
    Call<Delegation> getAuthorityUser(@Path("id") int id);

    @POST("delegation/create")
    Call<Delegation> createDelegation(@Body Delegation delegation);

    @POST("delegation/update")
    Call<Delegation> updateDelegation(@Body Delegation delegation);

    @POST("delegation/cancel")
    Call<Delegation> cancelDelegation(@Body Delegation delegation);
}
