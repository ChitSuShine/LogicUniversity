package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Delegation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DelegationService {
    // getting authorized users by department Id number
    @GET("delegation/search/{id}")
    Call<Delegation> getAuthorityUser(@Path("id") int id);

    // delegating employee
    @POST("delegation/create")
    Call<Delegation> createDelegation(@Body Delegation delegation);

    // changing delegation details
    @POST("delegation/update")
    Call<Delegation> updateDelegation(@Body Delegation delegation);

    // cancelling delegation process
    @POST("delegation/cancel")
    Call<Delegation> cancelDelegation(@Body Delegation delegation);
}
