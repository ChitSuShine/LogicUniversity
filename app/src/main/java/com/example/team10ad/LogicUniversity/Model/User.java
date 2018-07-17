package com.example.team10ad.LogicUniversity.Model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("Username")
    private String userName;
    @SerializedName("Email")
    private String email;
    @SerializedName("Password")
    private String password;
    @SerializedName("Role")
    private int role;
    @SerializedName("Fullname")
    private String fullName;
    @SerializedName("Deptid")
    private int depId;
    private String depName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getUserName() {

        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public String getFullName() {
        return fullName;
    }

    public int getDepId() {
        return depId;
    }

    public String getDepName() {
        return depName;
    }
}
