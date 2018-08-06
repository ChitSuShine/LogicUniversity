package com.nusiss.team10ad.LogicUniversity.Service;

import com.nusiss.team10ad.LogicUniversity.Model.Department;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DepartmentService {
    // getting all department
    @GET("departments")
    Call<List<Department>> getAllDepartments();

    // getting department details by department Id
    @GET("department/{id}")
    Call<Department> getDepartmentById(@Path("id") int id);
}
