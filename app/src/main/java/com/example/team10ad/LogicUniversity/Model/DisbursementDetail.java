package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class DisbursementDetail {

    @SerializedName("disid")
    private String disid;

    @SerializedName("itemid")
    private String itemid;

    @SerializedName("itemname")
    private String itemname;

    @SerializedName("qty")
    private String qty;

    public String getDisid() {
        return disid;
    }

    public void setDisid(String disid) {
        this.disid = disid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
