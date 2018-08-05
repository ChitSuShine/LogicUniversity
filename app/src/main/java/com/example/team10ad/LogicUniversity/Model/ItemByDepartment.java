package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class ItemByDepartment {

    @SerializedName("Quantity")
    private int Quantity;

    @SerializedName("description")
    private int description;

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }
}
