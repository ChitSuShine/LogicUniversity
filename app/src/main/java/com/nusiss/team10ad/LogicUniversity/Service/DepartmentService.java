package com.nusiss.team10ad.LogicUniversity.Service;

import com.nusiss.team10ad.LogicUniversity.Model.Department;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DepartmentService {
    @GET("departments")
    Call<List<Department>> getAllDepartments();

    @GET("department/{id}")
    Call<Department> getDepartmentById(@Path("id") int id);
}
