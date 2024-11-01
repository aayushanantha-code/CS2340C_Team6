package com.example.sprintproject.model;

import java.util.ArrayList;

public class Group {
    private ArrayList<User> userList;
    private ArrayList<Destination> destinationList;
    private long allocatedVacationDays;
    private int plannedDays;

    public Group() {
    }
    public Group(User user) {
        userList = new ArrayList<User>();
        userList.add(user);
        this.destinationList = new ArrayList<>();
        this.allocatedVacationDays = 0;
        this.plannedDays = 0;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public ArrayList<Destination> getDestinationList() {
        return destinationList;
    }

    public long getAllocatedVacationDays() {
        return allocatedVacationDays;
    }

    public int getPlannedDays() {
        return plannedDays;
    }
    /**
     * Adds new destiantion to the list of destinations
     * @param destination The destination
     */
    public void addDestination(Destination destination) {
        destinationList.add(destination);
    }

    public void addUser(User user) {
        userList.add(user);
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
