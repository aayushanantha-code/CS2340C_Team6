package com.example.sprintproject.model;

import java.util.ArrayList;

public class User {
    private String userID;
    private String password;
    // should have a list of Destinations
    private ArrayList<Destination> destinationList;
    private long allocatedVacationDays;
    private int plannedDays;

    /**
     * Constructor for User
     */
    public User() {
        this.userID = null;
        this.password = null;
        this.destinationList = new ArrayList<>();
        this.allocatedVacationDays = 0;
        this.plannedDays = 0;
    }

    /**
     * Constructor for User
     * @param userID User's ID
     * @param password User's Password
     */
    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
        this.destinationList = new ArrayList<>();
        this.allocatedVacationDays = 0;
        this.plannedDays = 0;
    }

    /**
     * Getters for User ID
     * @return User's ID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Getters for Password
     * @return User's Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getters for Destinations
     * @return List of Destinations
     */
    public ArrayList<Destination> getDestinations() {
        return destinationList;
    }

    /**
     * Setter for User ID
     * @param userID User's ID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Setter for Password
     * @param password User's Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter for Destinations
     * @param destinationList List of Destinations
     */
    public void setDestination(ArrayList<Destination> destinationList) {
        this.destinationList = destinationList;
    }

    /**
     * Adds new destiantion to the list of destinations
     * @param destination The destination
     */
    public void addDestination(Destination destination) {
        destinationList.add(destination);
    }

    /**
     * Setter for Allocated Vacation Days
     * @param vacationDays Number of vacation days
     */
    public void setAllocatedVacationDays(long vacationDays) {
        allocatedVacationDays = vacationDays;
    }

    /**
     * Getter for Allocated Vacation Days
     * @param plannedDays Number of planned days
     */
    public void addVacationDays(int plannedDays) {
        this.plannedDays += plannedDays;
    }
}