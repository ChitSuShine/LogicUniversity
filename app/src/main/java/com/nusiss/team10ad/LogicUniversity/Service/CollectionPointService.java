package com.nusiss.team10ad.LogicUniversity.Service;

import com.nusiss.team10ad.LogicUniversity.Model.CollectionPoint;
import com.nusiss.team10ad.LogicUniversity.Model.DepartmentCollectionPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CollectionPointService {
    // getting all collection points and their details
    @GET("collectionpoints")
    Call<List<CollectionPoint>> getCollectionPoints();

    // getting active collection point by department Id number
    @GET("departmentcollectionpoint/department/{id}")
    Call<DepartmentCollectionPoint> getActiveCollectionPoint(@Path("id") int id);

    // adding collection point change request into database
    @POST("departmentcollectionpoint/create")
    Call<DepartmentCollectionPoint> changeCollectionPoint(@Body DepartmentCollectionPoint dcp);

    // getting all pending collection point change requests
    @GET("departmentcollectionpoint/status/0")
    Call<List<DepartmentCollectionPoint>> getPendingCollectionPoints();

    // approving collection point request
    @POST("departmentcollectionpoint/confirm")
    Call<DepartmentCollectionPoint> approveCollectionPoint(@Body DepartmentCollectionPoint dcp);

    // rejecting collection point request
    @POST("departmentcollectionpoint/reject")
    Call<DepartmentCollectionPoint> rejectCollectionPoint(@Body DepartmentCollectionPoint dcp);
}
