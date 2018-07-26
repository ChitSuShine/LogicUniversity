package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.CollectionPoint;
import com.example.team10ad.LogicUniversity.Model.DepartmentCollectionPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CollectionPointService {
    @GET("collectionpoints")
    Call<List<CollectionPoint>> getCollectionPoints();

    @GET("departmentcollectionpoint/department/{id}")
    Call<DepartmentCollectionPoint> getActiveCollectionPoint(@Path("id") int id);

    @POST("departmentcollectionpoint/create")
    Call<DepartmentCollectionPoint> changeCollectionPoint(@Body DepartmentCollectionPoint dcp);

    @GET("departmentcollectionpoint/status/0")
    Call<List<DepartmentCollectionPoint>> getPendingCollectionPoints();

    @POST("departmentcollectionpoint/confirm")
    Call<DepartmentCollectionPoint> approveCollectionPoint(@Body DepartmentCollectionPoint dcp);
}
