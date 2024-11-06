package com.example.sprintproject.model;

public class User {
    private String userID;
    private String password;
    private long allocatedVacationDays;
    private int plannedDays;
    private boolean isInGroup;
    private String note;
    private String groupName;

    public User() {
    }
    /**
     * Constructor for User
     * @param userID User's ID
     * @param password User's Password
     */
    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
        this.allocatedVacationDays = 0;
        this.plannedDays = 0;
        this.isInGroup = false;
        this.note = "";
        this.groupName = null;
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

    public String getNote() {
        return note;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) { this.groupName = groupName; }

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

    public boolean getIsInGroup() {
        return isInGroup;
    }

    public void joinGroup(String groupName) {
        isInGroup = true;
        this.groupName = groupName;
    }
}