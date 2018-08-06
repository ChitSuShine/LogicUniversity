package com.nusiss.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class NumberofRequisition {

    @SerializedName("NumberofReq")
    private int reqID;

    @SerializedName("deptname")
    private String deptname;

    @SerializedName("ProceduceMonth")
    private int ProceduceMonth;

    @SerializedName("ProceduceYear")
    private int ProceduceYear;

    public int getReqID() {
        return reqID;
    }

    public void setReqID(int reqID) {
        this.reqID = reqID;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public int getProceduceMonth() {
        return ProceduceMonth;
    }

    public void setProceduceMonth(int proceduceMonth) {
        ProceduceMonth = proceduceMonth;
    }

    public int getProceduceYear() {
        return ProceduceYear;
    }

    public void setProceduceYear(int proceduceYear) {
        ProceduceYear = proceduceYear;
    }
}
