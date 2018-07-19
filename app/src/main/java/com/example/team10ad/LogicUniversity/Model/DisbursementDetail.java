package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class DisbursementDetail {

    @SerializedName("Disid")
    private String disid;

    @SerializedName("Itemid")
    private String itemid;

    @SerializedName("Itemname")
    private String itemname;

    @SerializedName("Qty")
    private String qty;

    @SerializedName("CategoryName")
    private String CategoryName;

    @SerializedName("UOM")
    private String UOM;

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
