package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Requisition {

    @SerializedName("reqid")
    private String reqID;

    @SerializedName("raisedby")
    private String raisedBy;

    @SerializedName("rasiedbyname")
    private String rasiedByname;

    @SerializedName("approvedby")
    private String approvedBy;

    @SerializedName("approvedbyname")
    private String approvedByname;

    @SerializedName("depid")
    private String depID;

    @SerializedName("depname")
    private String depName;

    @SerializedName("cpid")
    private String cpID;

    @SerializedName("cpname")
    private String cpName;

    @SerializedName("status")
    private String Status;

    @SerializedName("reqdate")
    private String reqDate;

    @SerializedName("requisitiondetails")
    private ArrayList<RequisitionDetail> requisitionDetails;

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public String getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(String raisedBy) {
        this.raisedBy = raisedBy;
    }

    public String getRasiedByname() {
        return rasiedByname;
    }

    public void setRasiedByname(String rasiedByname) {
        this.rasiedByname = rasiedByname;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedByname() {
        return approvedByname;
    }

    public void setApprovedByname(String approvedByname) {
        this.approvedByname = approvedByname;
    }

    public String getDepID() {
        return depID;
    }

    public void setDepID(String depID) {
        this.depID = depID;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getCpID() {
        return cpID;
    }

    public void setCpID(String cpID) {
        this.cpID = cpID;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public ArrayList<RequisitionDetail> getRequisitionDetails() {
        return requisitionDetails;
    }

    public void setRequisitionDetails(ArrayList<RequisitionDetail> requisitionDetails) {
        this.requisitionDetails = requisitionDetails;
    }
}
