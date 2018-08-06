package com.nusiss.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class StationaryRetrieval {

    @SerializedName("ItemId")
    private String ItemId;

    @SerializedName("Description")
    private String Description;

    @SerializedName("Uom")
    private String Uom;

    @SerializedName("CatId")
    private String CatId;

    @SerializedName("CatName")
    private String CatName;

    @SerializedName("ShelfLocaition")
    private String ShelfLocaition;

    @SerializedName("ShelfLevel")
    private String ShelfLevel;

    @SerializedName("Total")
    private String Total;

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }

    public String getCatId() {
        return CatId;
    }

    public void setCatId(String catId) {
        CatId = catId;
    }

    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }

    public String getShelfLocaition() {
        return ShelfLocaition;
    }

    public void setShelfLocaition(String shelfLocaition) {
        ShelfLocaition = shelfLocaition;
    }

    public String getShelfLevel() {
        return ShelfLevel;
    }

    public void setShelfLevel(String shelfLevel) {
        ShelfLevel = shelfLevel;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}
