package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class Inventory {

    @SerializedName("Invid")
    private String Invid;

    @SerializedName("Itemid")
    private String Itemid;

    @SerializedName("ItemDescription")
    private String ItemDescription;

    @SerializedName("Stock")
    private String Stock;

    @SerializedName("ReorderLevel")
    private String ReorderLevel;

    @SerializedName("ReorderQty")
    private String ReorderQty;

    public String getInvid() {
        return Invid;
    }

    public void setInvid(String invid) {
        Invid = invid;
    }

    public String getItemid() {
        return Itemid;
    }

    public void setItemid(String itemid) {
        Itemid = itemid;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getReorderLevel() {
        return ReorderLevel;
    }

    public void setReorderLevel(String reorderLevel) {
        ReorderLevel = reorderLevel;
    }

    public String getReorderQty() {
        return ReorderQty;
    }

    public void setReorderQty(String reorderQty) {
        ReorderQty = reorderQty;
    }
}
