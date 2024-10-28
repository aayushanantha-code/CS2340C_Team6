package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userID;
    private String password;
    // should have a list of Destinations
    private ArrayList<Destination> destinationIDs;

    public User() {
        this.userID = null;
        this.password = null;
        this.destinationIDs = new ArrayList<>();
    }

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
        this.destinationIDs = new ArrayList<>();
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public List<Destination> getDestinations() {
        return destinationIDs;
    }

    public void addDestination(Destination destinationID) {
        destinationIDs.add(destinationID);
    }
}
