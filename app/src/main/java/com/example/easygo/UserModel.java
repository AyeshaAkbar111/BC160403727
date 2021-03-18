package com.example.easygo;

public class UserModel {
    String uid,username,email,phoneNo;
    boolean isAdmin;
    public UserModel(){ }

    public UserModel(String uid, String username, String email, String phoneNo, boolean isAdmin) {
        this.isAdmin = isAdmin;
        this.phoneNo = phoneNo;
        this.uid = uid;
        this.username = username;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}


