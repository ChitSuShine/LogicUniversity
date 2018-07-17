package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class Requisition {

    @SerializedName("reqid")
    private String reqid;

    @SerializedName("raisedby")
    private String raisedby;

    @SerializedName("rasiedbyname")
    private String rasiedbyname;

    @SerializedName("approvedby")
    private String approvedby;

    @SerializedName("approvedbyname")
    private String approvedbyname;

    @SerializedName("depid")
    private String depid;

    @SerializedName("depname")
    private String depname;

    @SerializedName("cpid")
    private String cpid;

    @SerializedName("cpname")
    private String cpname;

    @SerializedName("status")
    private String status;

    @SerializedName("reqdate")
    private String reqdate;

    @SerializedName("requisitiondetails")
    private String requisitiondetails;

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public String getRaisedby() {
        return raisedby;
    }

    public void setRaisedby(String raisedby) {
        this.raisedby = raisedby;
    }

    public String getRasiedbyname() {
        return rasiedbyname;
    }

    public void setRasiedbyname(String rasiedbyname) {
        this.rasiedbyname = rasiedbyname;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public String getApprovedbyname() {
        return approvedbyname;
    }

    public void setApprovedbyname(String approvedbyname) {
        this.approvedbyname = approvedbyname;
    }

    public String getDepid() {
        return depid;
    }

    public void setDepid(String depid) {
        this.depid = depid;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReqdate() {
        return reqdate;
    }

    public void setReqdate(String reqdate) {
        this.reqdate = reqdate;
    }

    public String getRequisitiondetails() {
        return requisitiondetails;
    }

    public void setRequisitiondetails(String requisitiondetails) {
        this.requisitiondetails = requisitiondetails;
    }
}
