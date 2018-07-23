package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class FreqentlyItem {
    @SerializedName("Itemid")
    private int deptId;

    @SerializedName("Description")
    private String description;

    @SerializedName("Qty")
    private int qty;

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
