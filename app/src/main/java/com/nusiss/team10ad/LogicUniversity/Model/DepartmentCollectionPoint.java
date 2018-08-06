package com.nusiss.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class DepartmentCollectionPoint {
    @SerializedName("DeptCpID")
    private int deptCpId;

    @SerializedName("DeptID")
    private int deptId;

    @SerializedName("DeptName")
    private String deptName;

    @SerializedName("CpID")
    private int cpId;

    @SerializedName("Status")
    private int status;

    @SerializedName("CpName")
    private String cpName;

    @SerializedName("CpLocation")
    private String cpLocation;

    public int getDeptCpId() {
        return deptCpId;
    }

    public void setDeptCpId(int deptCpId) {
        this.deptCpId = deptCpId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getCpId() {
        return cpId;
    }

    public void setCpId(int cpId) {
        this.cpId = cpId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getCpLocation() {
        return cpLocation;
    }

    public void setCpLocation(String cpLocation) {
        this.cpLocation = cpLocation;
    }
}
