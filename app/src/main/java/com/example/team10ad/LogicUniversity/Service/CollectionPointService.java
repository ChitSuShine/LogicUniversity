package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.CollectionPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CollectionPointService {
    @GET("collectionpoints")
    Call<List<CollectionPoint>> getCollectionPoints();

    @GET("collectionpoint/department/{id}")
    Call<List<CollectionPoint>> getActiveCollectionPoint(@Path("id") int id);
}
