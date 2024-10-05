package com.example.sprintproject.model;

public class User {
    private String userID;
    private String password;

    public User() {
        this.userID = null;
        this.password = null;
    }

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public boolean successfulLogin(String userID, String password) {
        return userID.equals(this.userID) && (password.equals(this.password));
    }
}
