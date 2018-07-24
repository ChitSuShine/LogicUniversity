package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Adjustment {
    @SerializedName("Adjid")
    private int adjId;

    @SerializedName("Raisedby")
    private int raisedBy;

    @SerializedName("Raisedbyname")
    private String raisedByName;

    @SerializedName("Raisedto")
    private int raisedTo;

    @SerializedName("Raisedtoname")
    private String raisedToName;

    @SerializedName("Issueddate")
    private String issuedDate;

    @SerializedName("Status")
    private int status;

    @SerializedName("Adjds")
    private ArrayList<AdjustmentDetail> adjDetails;

    public int getAdjId() {
        return adjId;
    }

    public void setAdjId(int adjId) {
        this.adjId = adjId;
    }

    public int getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(int raisedBy) {
        this.raisedBy = raisedBy;
    }

    public String getRaisedByName() {
        return raisedByName;
    }

    public void setRaisedByName(String raisedByName) {
        this.raisedByName = raisedByName;
    }

    public int getRaisedTo() {
        return raisedTo;
    }

    public void setRaisedTo(int raisedTo) {
        this.raisedTo = raisedTo;
    }

    public String getRaisedToName() {
        return raisedToName;
    }

    public void setRaisedToName(String raisedToName) {
        this.raisedToName = raisedToName;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<AdjustmentDetail> getAdjDetails() {
        return adjDetails;
    }

    public void setAdjDetails(ArrayList<AdjustmentDetail> adjDetails) {
        this.adjDetails = adjDetails;
    }
}
