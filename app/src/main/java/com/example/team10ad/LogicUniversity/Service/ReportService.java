package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.FreqentlyItem;
import com.example.team10ad.LogicUniversity.Model.ItemTrend;
import com.example.team10ad.LogicUniversity.Model.ItemUsageClerk;
import com.example.team10ad.LogicUniversity.Model.ItemUsageHod;
import com.example.team10ad.LogicUniversity.Model.OrderCount;
import com.example.team10ad.LogicUniversity.Model.RequisitionReportList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReportService {
    @GET("FrequentlyItemList")
    Call<List<FreqentlyItem>> frequentlyOrderedItemList();

    @GET("OrderByDepartment")
    Call<List<OrderCount>> getOrderCounts();

    @GET("MonthlyItemUsageByHOD")
    Call<List<ItemUsageHod>> getItemUsageHod();

    @GET("requistionalist")
    Call<List<RequisitionReportList>> getReqListReport();

    @GET("ItemUsageByClerk")
    Call<List<ItemUsageClerk>> getItemUsageClerk();

    @GET("itemtrendanalysis/{d1}/{d2}/{d3}/{cat}")
    Call<List<ItemTrend>> getItemTrend(@Path("d1") int d1, @Path("d2") int d2,
                                       @Path("d3") int d3, @Path("cat") int cat);
}
