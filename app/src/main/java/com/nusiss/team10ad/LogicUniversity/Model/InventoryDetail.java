package com.nusiss.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class InventoryDetail {

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

    @SerializedName("Catid")
    private String Catid;

    @SerializedName("CatName")
    private String CatName;

    @SerializedName("Description")
    private String Description;

    @SerializedName("ShelfLocation")
    private String ShelfLocation;

    @SerializedName("ShelfLevel")
    private String ShelfLevel;

    @SerializedName("Uom")
    private String Uom;

    @SerializedName("IsPending")
    private boolean isPending;

    @SerializedName("CurrentStock")
    private String currentStock;

    @SerializedName("Reason")
    private String reason;

    public String getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(String currentStock) {
        this.currentStock = currentStock;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getCatid() {
        return Catid;
    }

    public void setCatid(String catid) {
        Catid = catid;
    }

    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getShelfLocation() {
        return ShelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        ShelfLocation = shelfLocation;
    }

    public String getShelfLevel() {
        return ShelfLevel;
    }

    public void setShelfLevel(String shelfLevel) {
        ShelfLevel = shelfLevel;
    }

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }

    public boolean getIsPending() {
        return isPending;
    }

    public void setIsPending(boolean isPending) {
        this.isPending = isPending;
    }
}
