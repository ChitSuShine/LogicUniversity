package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class ItemTrend {
    @SerializedName("Deptname")
    private String deptName;

    @SerializedName("Description")
    private String description;

    @SerializedName("Qty")
    private int qty;

    @SerializedName("Deptid")
    private int deptId;

    @SerializedName("Itemid")
    private int itemId;

    @SerializedName("Monthofreq")
    private int month;

    @SerializedName("Yearofreq")
    private int year;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
