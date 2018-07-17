package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class OutstandingItem {
    @SerializedName("ItemId")
    private int itemId;

    @SerializedName("Description")
    private String description;

    @SerializedName("Uom")
    private String uom;

    @SerializedName("CatId")
    private int catdId;

    @SerializedName("CatName")
    private String catName;

    @SerializedName("Total")
    private int total;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public int getCatdId() {
        return catdId;
    }

    public void setCatdId(int catdId) {
        this.catdId = catdId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
