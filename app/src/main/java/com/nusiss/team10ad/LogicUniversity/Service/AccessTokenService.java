package com.nusiss.team10ad.LogicUniversity.Service;

import com.nusiss.team10ad.LogicUniversity.Model.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
// getting token for accessing system
public interface AccessTokenService {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("token")
    Call<AccessToken> getAccessToken(@Field("username")String username,
                                     @Field("password") String password,
                                     @Field("grant_type")String grantType);
}
