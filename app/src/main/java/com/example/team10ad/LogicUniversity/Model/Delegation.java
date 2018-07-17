package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Delegation {
    @SerializedName("startdate")
    private LocalDateTime startDate;

    @SerializedName("enddate")
    private LocalDateTime endDate;

    @SerializedName("userid")
    private int userId;

    @SerializedName("username")
    private String username;

    @SerializedName("role")
    private int role;

    @SerializedName("depname")
    private String depName;

    @SerializedName("active")
    private int active;

    @SerializedName("assignedbyId")
    private int assignedBy;

    @SerializedName("assignedbyusername")
    private String aUsername;

    @SerializedName("assignedbyrole")
    private int aRole;

    @SerializedName("assignedbydepname")
    private String adeptName;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
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
