package com.nusiss.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("Itemid")
    private int Itemid;

    @SerializedName("Catid")
    private int Catid;

    @SerializedName("CatName")
    private String CatName;

    @SerializedName("Description")
    private String Description;

    @SerializedName("Uom")
    private String Uom;

    public int getItemid() {
        return Itemid;
    }

    public void setItemid(int itemid) {
        Itemid = itemid;
    }

    public int getCatid() {
        return Catid;
    }

    public void setCatid(int catid) {
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

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }
}
