package com.example.sprintproject.model;

import java.util.ArrayList;

public class Group {
    private ArrayList<User> userList;
    private ArrayList<Destination> destinationList;
    private ArrayList<Dining> diningList;
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
        this.diningList = new ArrayList<>();
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
     * Getters for Dining List
     * @return List of Dinings
     */
    public ArrayList<Dining> getDiningList() {
        return diningList;
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
     * Add Dining
     * @param dining Dining
     */
    public void addDining(Dining dining) {
        diningList.add(dining);
    }

    public void setAllocatedVacationDays(long vacationDays) {
        allocatedVacationDays = vacationDays;
    }

    public void addVacationDays(int plannedDays) {
        this.plannedDays += plannedDays;
    }
}