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

    /**
     * Constructor for TravelCommunityPost
     * @param username The username of the user
     * @param destination The destination of the post
     * @param travelType The type of travel
     * @param notes The notes for the post
     */
    public TravelCommunityPost(String username, Destination destination,
                               String travelType, String notes) {
        this.user = username;
        this.destination = destination;
        this.notes = notes;
        this.travelType = travelType;
    }

    /**
     * Gets the destination of the post
     * @return The destination of the post
     */
    public Destination getDestination() {
        return destination;
    }

    /**
     * Gets the user of the post
     * @return The user of the post
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the travel type of the post
     * @return The travel type of the post
     */
    public String getTravelType() {
        return travelType;
    }

    /**
     * Gets the notes of the post
     * @return The notes of the post
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the user of the post
     * @param user The user of the post
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Sets the destination of the post
     * @param destination The destination of the post
     */
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    /**
     * Sets the travel type of the post
     * @param travelType The travel type of the post
     */
    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }

    /**
     * Sets the notes of the post
     * @param notes The notes of the post
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
