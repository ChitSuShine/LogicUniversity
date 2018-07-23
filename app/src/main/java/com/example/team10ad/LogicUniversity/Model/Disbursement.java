package com.example.team10ad.LogicUniversity.Model;

import com.example.team10ad.LogicUniversity.Util.Constants;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Disbursement {

    @SerializedName("Reqid")
    private String reqID;

    @SerializedName("Raisedby")
    private String raisedBy;

    @SerializedName("Rasiedbyname")
    private String rasiedByname;

    @SerializedName("Approvedby")
    private String approvedBy;

    @SerializedName("Approvedbyname")
    private String approvedByname;

    @SerializedName("Depid")
    private String depID;

    @SerializedName("Depname")
    private String depName;

    @SerializedName("Cpid")
    private String cpID;

    @SerializedName("Cpname")
    private String cpName;

    @SerializedName("Status")
    private String Status;

    @SerializedName("Reqdate")
    private String reqDate;

    @SerializedName("LockerID")
    private String LockerID;

    @SerializedName("LockerName")
    private String LockerName;

    @SerializedName("Requisitiondetails")
    private ArrayList<DisbursementDetail> disbursementDetails;

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
        return reqDate.substring(0, Constants.TEN);
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getLockerID() {
        return LockerID;
    }

    public void setLockerID(String lockerID) {
        LockerID = lockerID;
    }

    public String getLockerName() {
        return LockerName;
    }

    public void setLockerName(String lockerName) {
        LockerName = lockerName;
    }

    public ArrayList<DisbursementDetail> getDisbursementDetails() {
        return disbursementDetails;
    }

    public void setDisbursementDetails(ArrayList<DisbursementDetail> disbursementDetails) {
        this.disbursementDetails = disbursementDetails;
    }
}
