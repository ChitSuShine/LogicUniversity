package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Model.Department;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface DepartmentService {
    // getting all department details
    @GET("departments")
    Call<List<Department>> getAllDepartments();

    // getting department details by department Id number
    @GET("department/{id}")
    Call<Department> getDepartmentById(@Path("id") int id);
}
