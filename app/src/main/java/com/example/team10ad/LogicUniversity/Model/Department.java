package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class Department {
    @SerializedName("deptname")
    private String deptName;

    @SerializedName("deptcontactname")
    private String deptContactName;

    @SerializedName("deptphone")
    private int deptPhone;

    @SerializedName("deptemail")
    private String deptEmail;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptContactName() {
        return deptContactName;
    }

    public void setDeptContactName(String deptContactName) { this.deptContactName = deptContactName; }

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
