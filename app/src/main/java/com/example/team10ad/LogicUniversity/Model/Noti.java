package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class Noti {

    @SerializedName("NotiID")
    private int NotiID;

    @SerializedName("Datetime")
    private String Datetime;

    @SerializedName("Deptid")
    private String Deptid;

    @SerializedName("Role")
    private String Role;

    @SerializedName("Title")
    private String Title;

    @SerializedName("Remark")
    private String Remark;

    @SerializedName("Isread")
    private String Isread;

    @SerializedName("ResID")
    private String ResID;

    @SerializedName("NotiType")
    private int NotiType;

    public int getNotiID() {
        return NotiID;
    }

    public void setNotiID(int notiID) {
        NotiID = notiID;
    }

    public String getDatetime() {
        return Datetime.substring(11,16);
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }

    public String getDeptid() {
        return Deptid;
    }

    public void setDeptid(String deptid) {
        Deptid = deptid;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getIsread() {
        return Isread;
    }

    public void setIsread(String isread) {
        Isread = isread;
    }

    public String getResID() {
        return ResID;
    }

    public void setResID(String resID) {
        ResID = resID;
    }

    public int getNotiType() {
        return NotiType;
    }

    public void setNotiType(int notiType) {
        NotiType = notiType;
    }
}
