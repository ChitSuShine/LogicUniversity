package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class LockerCollectionPoint {

    @SerializedName("lockerid")
    private String lockerid;

    @SerializedName("lockername")
    private String lockername;

    @SerializedName("lockersize")
    private String lockersize;

    @SerializedName("cpid")
    private String cpid;

    @SerializedName("status")
    private String status;

    @SerializedName("cpname")
    private String cpname;

    public String getLockerid() {
        return lockerid;
    }

    public void setLockerid(String lockerid) {
        this.lockerid = lockerid;
    }

    public String getLockername() {
        return lockername;
    }

    public void setLockername(String lockername) {
        this.lockername = lockername;
    }

    public String getLockersize() {
        return lockersize;
    }

    public void setLockersize(String lockersize) {
        this.lockersize = lockersize;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname;
    }
}
