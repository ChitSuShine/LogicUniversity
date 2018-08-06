package com.nusiss.team10ad.LogicUniversity.Service;

import com.nusiss.team10ad.LogicUniversity.Model.FreqentlyItem;
import com.nusiss.team10ad.LogicUniversity.Model.FrequentItemHod;
import com.nusiss.team10ad.LogicUniversity.Model.ItemTrend;
import com.nusiss.team10ad.LogicUniversity.Model.ItemUsageClerk;
import com.nusiss.team10ad.LogicUniversity.Model.ItemUsageHod;
import com.nusiss.team10ad.LogicUniversity.Model.OrderCount;
import com.nusiss.team10ad.LogicUniversity.Model.RequisitionReportList;

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

    @GET("itemtrendanalysis/{d1}/{d2}/{d3}/{cat}")
    Call<List<ItemTrend>> getItemTrend(@Path("d1") int d1, @Path("d2") int d2,
                                       @Path("d3") int d3, @Path("cat") int cat);

    @GET("itemusage/{s1}/{s2}/{s3}/{item}")
    Call<List<ItemUsageClerk>> getItemUsage(@Path("s1") int s1, @Path("s2") int s2,
                                            @Path("s3") int s3, @Path("item") int item);

    @GET("frequentlyordered5hod/{id}")
    Call<List<FrequentItemHod>> getFrequentItemListHod(@Path("id") int deptid);
}
