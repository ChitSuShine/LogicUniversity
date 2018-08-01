package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class ItemUsageClerk {
    @SerializedName("Month")
    private String month;

    @SerializedName("Sup1Data")
    private int sup1;

    @SerializedName("Sup2Data")
    private int sup2;

    @SerializedName("Sup3Data")
    private int sup3;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getSup1() {
        return sup1;
    }

    public void setSup1(int sup1) {
        this.sup1 = sup1;
    }

    public int getSup2() {
        return sup2;
    }

    public void setSup2(int sup2) {
        this.sup2 = sup2;
    }

    public int getSup3() {
        return sup3;
    }

    public void setSup3(int sup3) {
        this.sup3 = sup3;
    }
}
