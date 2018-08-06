package com.nusiss.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class Department {
    @SerializedName("Deptid")
    private int deptId;

    @SerializedName("Deptname")
    private String deptName;

    @SerializedName("Deptcontactname")
    private String deptContactName;

    @SerializedName("Deptphone")
    private int deptPhone;

    @SerializedName("Deptemail")
    private String deptEmail;

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

    public String getDeptContactName() {
        return deptContactName;
    }

    public void setDeptContactName(String deptContactName) {
        this.deptContactName = deptContactName;
    }

    public int getDeptPhone() {
        return deptPhone;
    }

    public void setDeptPhone(int deptPhone) {
        this.deptPhone = deptPhone;
    }

    public String getDeptEmail() {
        return deptEmail;
    }

    public void setDeptEmail(String deptEmail) {
        this.deptEmail = deptEmail;
    }
}
