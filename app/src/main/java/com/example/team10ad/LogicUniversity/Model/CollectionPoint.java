package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class CollectionPoint {
    @SerializedName("cpid")
    private int cpId;

    @SerializedName("cpname")
    private String cpName;

    @SerializedName("cplocation")
    private String cpLocation;

    @SerializedName("Latitude")
    private String latitude;

    @SerializedName("Longitude")
    private String longitude;

    public int getCpId() {
        return cpId;
    }

    public void setCpId(int cpId) {
        this.cpId = cpId;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getCpLocation() {
        return cpLocation;
    }

    public void setCpLocation(String cpLocation) {
        this.cpLocation = cpLocation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
