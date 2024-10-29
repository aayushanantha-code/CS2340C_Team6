package com.example.sprintproject.model;

import java.util.ArrayList;

public class User {
    private String userID;
    private String password;
    // should have a list of Destinations
    private ArrayList<Destination> destinationList;

    public User() {
        this.userID = null;
        this.password = null;
        this.destinationList = new ArrayList<>();
    }

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
        this.destinationList = new ArrayList<>();
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Destination> getDestinations() {
        return destinationList;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDestination(ArrayList<Destination> destinationList) {
        this.destinationList = destinationList;
    }

    public void addDestination(Destination destination) {
        destinationList.add(destination);
        for (int i = 0; i < destinationList.size(); i++) {
            System.out.println(destinationList.get(i).getName());
        }
    }
}
