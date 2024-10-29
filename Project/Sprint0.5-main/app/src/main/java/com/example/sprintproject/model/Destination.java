package com.example.sprintproject.model;

import java.util.List;
import java.util.ArrayList;


public class Destination {
    private String name;
    private String start;
    private String end;
    private long duration;
    private List<String> userIDs;


    public Destination() {
        this.name = "";
        this.start = "";
        this.end = "";
        this.duration = 0;
        this.userIDs = new ArrayList<>();
    }
    public Destination(String name, String start, String end, long duration, String userId) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.userIDs = new ArrayList<>();
        this.userIDs.add(userId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public List<String> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(List<String> userIDs) {
        this.userIDs = userIDs;
    }

    public void addUserID(String userID) {
        if (!userIDs.contains(userID)) {
            userIDs.add(userID);
        }
    }

}
