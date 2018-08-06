package com.nusiss.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class AdjustmentDetail {
    @SerializedName("Adjid")
    private int adjId;

    @SerializedName("Itemid")
    private int itemId;

    @SerializedName("Itemdescription")
    private String itemDescription;

    @SerializedName("CategoryName")
    private String categoryName;

    @SerializedName("UOM")
    private String uom;

    @SerializedName("Adjustedqty")
    private int adjustedQty;

    @SerializedName("Reason")
    private String reason;

    public int getAdjId() {
        return adjId;
    }

    public void setAdjId(int adjId) {
        this.adjId = adjId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public int getAdjustedQty() {
        return adjustedQty;
    }

    public void setAdjustedQty(int adjustedQty) {
        this.adjustedQty = adjustedQty;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
