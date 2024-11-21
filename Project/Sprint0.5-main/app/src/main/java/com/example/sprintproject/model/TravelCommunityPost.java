package com.example.sprintproject.model;


public class TravelCommunityPost {
    private String user;
    private Destination destination;
    private String travelType;
    private String notes;

    /**
     * Constructor for TravelCommunityPost
     */
    public TravelCommunityPost() {
    }

    public TravelCommunityPost(String username, Destination destination,
                               String travelType, String notes) {
        this.user = username;
        this.destination = destination;
        this.notes = notes;
        this.travelType = travelType;
    }

    public Destination getDestination() {
        return destination;
    }

    public String getUser() {
        return user;
    }

    public String getTravelType() {
        return travelType;
    }

    public String getNotes() {
        return notes;
    }
}
