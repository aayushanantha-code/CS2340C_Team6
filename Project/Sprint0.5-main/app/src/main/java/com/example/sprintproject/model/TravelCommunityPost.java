package com.example.sprintproject.model;

import java.util.ArrayList;

public class TravelCommunityPost {
    private String user;
    private Destination destination;

    /**
     * Constructor for TravelCommunityPost
     */
    public TravelCommunityPost() {
    }

    public TravelCommunityPost(String username, Destination destination) {
        this.user = username;
        this.destination = destination;
    }

    public Destination getDestination() {
        return destination;
    }

    public String getUser() {
        return user;
    }
}
