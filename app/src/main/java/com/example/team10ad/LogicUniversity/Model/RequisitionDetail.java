package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class RequisitionDetail {

    @SerializedName("Reqid")
    private String reqid;

    @SerializedName("Itemid")
    private String itemid;

    @SerializedName("Qty")
    private String qty;

    @SerializedName("Stock")
    private String Stock;

    @SerializedName("Itemname")
    private String itemname;

    @SerializedName("CategoryName")
    private String CategoryName;

    @SerializedName("UOM")
    private String UOM;

    public RequisitionDetail(){
        reqid = "";
        itemid = "";
        qty = "";
        itemname = "";
        CategoryName = "";
        UOM = "";
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getStock() { return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
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
