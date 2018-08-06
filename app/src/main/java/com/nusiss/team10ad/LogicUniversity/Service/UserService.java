package com.nusiss.team10ad.LogicUniversity.Service;

import com.nusiss.team10ad.LogicUniversity.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    // getting current user info
    @GET("user")
    Call<User> getLoginUser();

    // getting users of each department
    @GET("user/assigndepreplist/{id}")
    Call<List<User>> getUsersByDeptId(@Path("id") int id);

    // getting user info filtered by role and department Id number
    @GET("user/role/{role}/{id}")
    Call<List<User>> getRepByDeptId(@Path("role") int role, @Path("id") int id);

    // assigning representative by user Id
    @POST("user/assign/{id}")
    Call<User> assignDepRep(@Path("id") int id);
}
