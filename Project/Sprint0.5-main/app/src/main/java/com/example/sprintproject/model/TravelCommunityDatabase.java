package com.example.sprintproject.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TravelCommunityDatabase {
    private static TravelCommunityDatabase instance;
    private DatabaseReference databaseReference;

    // Private constructor to prevent instantiation
    /**
     * Constructor for TravelCommunityDatabase
     */
    private TravelCommunityDatabase() {
        // Initialize the Firebase Database instance
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Replace with your reference
        databaseReference = firebaseDatabase.getReference().child("travelPosts");
    }

    /**
     * Method to get the instance of the TravelCommunityDatabase
     * @return TravelCommunityDatabase
     */
    public static TravelCommunityDatabase getInstance() {
        if (instance == null) {
            instance = new TravelCommunityDatabase();
        }
        return instance;
    }

    /**
     * Method to get the database reference
     * @return DatabaseReference
     */
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
