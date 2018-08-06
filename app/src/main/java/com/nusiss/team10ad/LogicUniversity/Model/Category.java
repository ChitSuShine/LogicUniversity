package com.nusiss.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("Catid")
    private int catId;

    @SerializedName("Name")
    private String name;

    @SerializedName("Shelflocation")
    private String shelfLocation;

    @SerializedName("Shelflevel")
    private String shelfLevel;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    public String getShelfLevel() {
        return shelfLevel;
    }

    public void setShelfLevel(String shelfLevel) {
        this.shelfLevel = shelfLevel;
    }
}
