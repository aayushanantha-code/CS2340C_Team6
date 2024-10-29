package com.example.sprintproject.model;

import java.util.List;
import java.util.ArrayList;


public class Destination {
    private String name;
    private String start;
    private String end;
    private long duration;
    private List<String> userIDs;


    /**
     * Constructor for Destination
     */
    public Destination() {
        this.name = "";
        this.start = "";
        this.end = "";
        this.duration = 0;
        this.userIDs = new ArrayList<>();
    }
    /**
     * Constructor for Destination
     * @param name Name of the destination
     * @param start Start date of the destination
     * @param end End date of the destination
     * @param duration Duration of the destination
     * @param userId User ID
     */
    public Destination(String name, String start, String end, long duration, String userId) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.userIDs = new ArrayList<>();
        this.userIDs.add(userId);
    }

    /**
     * Getters for Name
     * @return Name of the destination
     */
    public String getName() {
        return name;
    }

    /**
     * Setters for Name
     * @param name Name of the destination
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getters for Start
     * @return Start date of the destination
     */
    public String getStart() {
        return start;
    }

    /**
     * Setters for Start
     * @param start Start date of the destination
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Getters for End
     * @return End date of the destination
     */
    public String getEnd() {
        return end;
    }

    /**
     * Setters for End
     * @param end End date of the destination
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Getters for Duration
     * @return Duration of the destination
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Setters for Duration
     * @param duration Duration of the destination
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * Getters for User IDs
     * @return List of User IDs
     */
    public List<String> getUserIDs() {
        return userIDs;
    }

    /**
     * Setters for User IDs
     * @param userIDs List of User IDs
     */
    public void setUserIDs(List<String> userIDs) {
        this.userIDs = userIDs;
    }

    /**
     * Add User ID
     * @param userID User ID
     */
    public void addUserID(String userID) {
        if (!userIDs.contains(userID)) {
            userIDs.add(userID);
        }
    }
}
