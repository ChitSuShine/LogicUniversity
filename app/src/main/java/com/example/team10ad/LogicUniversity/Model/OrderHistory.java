package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class OrderHistory {

    @SerializedName("Reqdate")
    private String Reqdate;

    @SerializedName("Deptname")
    private String Deptname;

    @SerializedName("Raisename")
    private String Raisename;

    @SerializedName("Cpname")
    private String Cpname;

    @SerializedName("Deptid")
    private String Deptid;

    @SerializedName("Status")
    private String Status;

    public String getReqdate() {
        return Reqdate;
    }

    public void setReqdate(String reqdate) {
        Reqdate = reqdate;
    }

    public String getDeptname() {
        return Deptname;
    }

    public void setDeptname(String deptname) {
        Deptname = deptname;
    }

    public String getRaisename() {
        return Raisename;
    }

    public void setRaisename(String raisename) {
        Raisename = raisename;
    }

    public String getCpname() {
        return Cpname;
    }

    public void setCpname(String cpname) {
        Cpname = cpname;
    }

    public String getDeptid() {
        return Deptid;
    }

    public void setDeptid(String deptid) {
        Deptid = deptid;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
