package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class OutstandingReqDetail {

    @SerializedName("OutReqId")
    private String OutReqId;

    @SerializedName("ItemId")
    private String ItemId;

    @SerializedName("Description")
    private String Description;

    @SerializedName("Qty")
    private String Qty;

    public String getOutReqId() {
        return OutReqId;
    }

    public void setOutReqId(String outReqId) {
        OutReqId = outReqId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }
}
