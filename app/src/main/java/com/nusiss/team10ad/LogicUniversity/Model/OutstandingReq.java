package com.nusiss.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class OutstandingReq {

    @SerializedName("OutReqId")
    private String OutReqId;

    @SerializedName("ReqId")
    private String ReqId;

    @SerializedName("Reason")
    private String Reason;

    @SerializedName("OutReqId")
    private String Status;

    @SerializedName("OutReqDetails")
    private String OutReqDetails;

    public String getOutReqId() {
        return OutReqId;
    }

    public void setOutReqId(String outReqId) {
        OutReqId = outReqId;
    }

    public String getReqId() {
        return ReqId;
    }

    public void setReqId(String reqId) {
        ReqId = reqId;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOutReqDetails() {
        return OutReqDetails;
    }

    public void setOutReqDetails(String outReqDetails) {
        OutReqDetails = outReqDetails;
    }
}
