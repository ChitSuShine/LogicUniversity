package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("Itemid")
    private String Itemid;

    @SerializedName("Catid")
    private String Catid;

    @SerializedName("CatName")
    private String CatName;

    @SerializedName("Description")
    private String Description;

    @SerializedName("Uom")
    private String Uom;

    public String getItemid() {
        return Itemid;
    }

    public void setItemid(String itemid) {
        Itemid = itemid;
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

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }
}
