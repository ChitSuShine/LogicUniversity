package com.nusiss.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Noti {

    @SerializedName("NotiID")
    private int NotiID;

    @SerializedName("Datetime")
    private String Datetime;

    @SerializedName("Deptid")
    private int Deptid;

    @SerializedName("Role")
    private int Role;

    @SerializedName("Title")
    private String Title;

    @SerializedName("Remark")
    private String Remark;

    @SerializedName("Isread")
    private boolean Isread;

    @SerializedName("ResID")
    private int ResID;

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

    public int getDeptid() {
        return Deptid;
    }

    public void setDeptid(int deptid) {
        Deptid = deptid;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
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

    public boolean isIsread() {
        return Isread;
    }

    public void setIsread(boolean isread) {
        Isread = isread;
    }

    public int getResID() {
        return ResID;
    }

    public void setResID(int resID) {
        ResID = resID;
    }

    public int getNotiType() {
        return NotiType;
    }

    public void setNotiType(int notiType) {
        NotiType = notiType;
    }
}
