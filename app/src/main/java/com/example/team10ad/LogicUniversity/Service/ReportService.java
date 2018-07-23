package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.FreqentlyItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ReportService {
    @GET("frequentlyitemlist")
    Call<List<FreqentlyItem>> frequetlyOrderedItemList();
}
