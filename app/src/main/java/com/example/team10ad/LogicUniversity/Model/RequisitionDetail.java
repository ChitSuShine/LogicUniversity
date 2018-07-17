package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class RequisitionDetail {

    @SerializedName("reqid")
    private String reqid;

    @SerializedName("itemid")
    private String itemid;

    @SerializedName("qty")
    private String qty;

    @SerializedName("itemname")
    private String itemname;

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
}
