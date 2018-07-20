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

    @SerializedName("CategoryName")
    private String CategoryName;

    @SerializedName("UOM")
    private String UOM;

    public Inventory(){
        Invid = "";
        Itemid = "";
        ItemDescription = "";
        Stock = "";
        ReorderLevel = "";
        ReorderQty = "";
        CategoryName = "";
        UOM = "";
    }

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
