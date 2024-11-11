package com.example.sprintproject.model;

public class User {
    private String userID;
    private String password;
    private long allocatedVacationDays;
    private int plannedDays;
    private boolean isInGroup;
    private String note;
    private String groupName;

    /**
     * Default constructor for User
     */
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

    /**
     * Getter for the mote
     * @return The note
     */
    public String getNote() {
        return note;
    }

    /**
     * Getting for group name
     * @return The group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Getter for Allocated Vacation Days
     * @param groupName The group name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    /**
     * Getter for Allocated Vacation Days
     * @return Whether the user is in a group
     */
    public boolean getIsInGroup() {
        return isInGroup;
    }

    /**
     * Method that sets the user to be in a group
     * @param groupName The group to join
     */
    public void joinGroup(String groupName) {
        isInGroup = true;
        this.groupName = groupName;
    }
}