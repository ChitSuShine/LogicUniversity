package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RequisitionReportList {
    @SerializedName("Reqdate")
    private Date reqDate;

    @SerializedName("Status")
    private String status;

    @SerializedName("Deptid")
    private int deptId;

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
}
