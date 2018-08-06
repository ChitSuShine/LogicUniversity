package com.nusiss.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class ItemTrend {
    @SerializedName("Month")
    private String month;

    @SerializedName("Dept1Data")
    private int dept1;

    @SerializedName("Dept2Data")
    private int dept2;

    @SerializedName("Dept3Data")
    private int dept3;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDept1() {
        return dept1;
    }

    public void setDept1(int dept1) {
        this.dept1 = dept1;
    }

    public int getDept2() {
        return dept2;
    }

    public void setDept2(int dept2) {
        this.dept2 = dept2;
    }

    public int getDept3() {
        return dept3;
    }

    public void setDept3(int dept3) {
        this.dept3 = dept3;
    }
}
