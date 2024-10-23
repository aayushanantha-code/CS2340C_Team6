package com.example.sprintproject.model;

public class User {
    private String userID;
    private String password;
    // should have a list of Destinations
    private List<Destination> destinations;

    public User() {
        this.userID = null;
        this.password = null;
        this.destinations = new ArrayList<>();
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

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void addDestination(Destination destination) {
        destinations.add(destination);
    }

    public boolean successfulLogin(String userID, String password) {
        return userID.equals(this.userID) && (password.equals(this.password));
    }
}
