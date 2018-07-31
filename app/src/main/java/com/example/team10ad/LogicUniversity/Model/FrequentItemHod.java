package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class FrequentItemHod {
    @SerializedName("Quantity")
    private int qty;

    @SerializedName("description")
    private String description;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
