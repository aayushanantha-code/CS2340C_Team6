package com.example.sprintproject.model;

import java.util.ArrayList;

public class Group {
    private ArrayList<User> userList;
    private ArrayList<Destination> destinationList;
    private long allocatedVacationDays;
    private int plannedDays;

    /**
     * Constructor for Group
     */
    public Group() {
    }

    /**
     * Constructor for Group
     * @param user User
     */
    public Group(User user) {
        userList = new ArrayList<>();
        userList.add(user);
        this.destinationList = new ArrayList<>();
        this.allocatedVacationDays = 0;
        this.plannedDays = 0;
    }

    /**
     * Getters for User List
     * @return List of Users
     */
    public ArrayList<User> getUserList() {
        return userList;
    }

    /**
     * Getters for Destination List
     * @return List of Destinations
     */
    public ArrayList<Destination> getDestinationList() {
        return destinationList;
    }

    /**
     * Getters for Allocated Vacation Days
     * @return Allocated Vacation Days
     */
    public long getAllocatedVacationDays() {
        return allocatedVacationDays;
    }

    /**
     * Getters for Planned Days
     * @return Planned Days
     */
    public int getPlannedDays() {
        return plannedDays;
    }

    /**
     * Add Destination
     * @param destination Destination
     */
    public void addDestination(Destination destination) {
        destinationList.add(destination);
    }

    /**
     * Sets number of vacation days
     * @param vacationDays Number of vacation days
     */
    public void setAllocatedVacationDays(long vacationDays) {
        allocatedVacationDays = vacationDays;
    }

    /**
     * Add vacation days
     * @param plannedDays Number of planned days
     */
    public void addVacationDays(int plannedDays) {
        this.plannedDays += plannedDays;
    }
}