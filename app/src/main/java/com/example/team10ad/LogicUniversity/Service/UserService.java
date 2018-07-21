package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Department;
import com.example.team10ad.LogicUniversity.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @GET("user")
    Call<User> getLoginUser();

    @GET("user/hod/{id}")
    Call<List<User>> getUsersByDeptId(@Path("id") int id);

    @POST("user/assign/{id}")
    Call<User> assignDepRep(@Path("id") int id);
}
