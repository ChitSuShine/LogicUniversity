package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;

public class Delegation {
    @SerializedName("Delid")
    private int delId;

    @SerializedName("Startdate")
    private String startDate;

    @SerializedName("Enddate")
    private String endDate;

    @SerializedName("Userid")
    private int userId;

    @SerializedName("Username")
    private String username;

    @SerializedName("Role")
    private int role;

    @SerializedName("Depname")
    private String depName;

    @SerializedName("Active")
    private int active;

    @SerializedName("AssignedbyId")
    private int assignedBy;

    @SerializedName("Assignedbyusername")
    private String aUsername;

    @SerializedName("Assignedbyrole")
    private int aRole;

    @SerializedName("Assignedbydepname")
    private String adeptName;

    public int getDelId() {
        return delId;
    }

    public void setDelId(int delId) {
        this.delId = delId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(int assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getaUsername() {
        return aUsername;
    }

    public void setaUsername(String aUsername) {
        this.aUsername = aUsername;
    }

    public int getaRole() {
        return aRole;
    }

    public void setaRole(int aRole) {
        this.aRole = aRole;
    }

    public String getAdeptName() {
        return adeptName;
    }

    public void setAdeptName(String adeptName) {
        this.adeptName = adeptName;
    }
}
