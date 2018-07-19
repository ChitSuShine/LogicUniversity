package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class Disbursement {
    @SerializedName("Disid")
    private String disID;

    @SerializedName("Reqid")
    private String reqid;

    @SerializedName("Status")
    private String status;

    @SerializedName("Ackby")
    private String ackby;

    @SerializedName("Username")
    private String username;

    @SerializedName("Departmentname")
    private String Departmentname;

    @SerializedName("Cpname")
    private String cpname;

    @SerializedName("Reqdate")
    private String reqdate;

    @SerializedName("Disbursementlist")
    private String disbursementlist;

    public String getDisID() {
        return disID;
    }

    public void setDisID(String disID) {
        this.disID = disID;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAckby() {
        return ackby;
    }

    public void setAckby(String ackby) {
        this.ackby = ackby;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartmentname() {
        return Departmentname;
    }

    public void setDepartmentname(String departmentname) {
        Departmentname = departmentname;
    }

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    public String getReqdate() {
        return reqdate;
    }

    public void setReqdate(String reqdate) {
        this.reqdate = reqdate;
    }

    public String getDisbursementlist() {
        return disbursementlist;
    }

    public void setDisbursementlist(String disbursementlist) {
        this.disbursementlist = disbursementlist;
    }
}