package com.nusiss.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class DisbursementDetail {

    @SerializedName("Reqid")
    private String reqID;

    @SerializedName("Itemid")
    private String Itemid;

    @SerializedName("RequestQty")
    private String RequestQty;

    @SerializedName("ApprovedQty")
    private String ApprovedQty;

    @SerializedName("Itemname")
    private String Itemname;

    @SerializedName("CategoryName")
    private String CategoryName;

    @SerializedName("UOM")
    private String UOM;

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public String getItemid() {
        return Itemid;
    }

    public void setItemid(String itemid) {
        Itemid = itemid;
    }

    public String getRequestQty() {
        return RequestQty;
    }

    public void setRequestQty(String requestQty) {
        RequestQty = requestQty;
    }

    public String getApprovedQty() {
        return ApprovedQty;
    }

    public void setApprovedQty(String approvedQty) {
        ApprovedQty = approvedQty;
    }

    public String getItemname() {
        return Itemname;
    }

    public void setItemname(String itemname) {
        Itemname = itemname;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }
}
