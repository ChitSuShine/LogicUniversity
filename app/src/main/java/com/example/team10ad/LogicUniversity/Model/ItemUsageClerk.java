package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class ItemUsageClerk {
    @SerializedName("Description")
    private String description;

    @SerializedName("Qty")
    private int qty;

    @SerializedName("Podate")
    private int month;

    @SerializedName("Year")
    private int year;

    @SerializedName("Supname")
    private String supName;

    @SerializedName("Supid")
    private int supId;

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

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public int getSupId() {
        return supId;
    }

    public void setSupId(int supId) {
        this.supId = supId;
    }
}
