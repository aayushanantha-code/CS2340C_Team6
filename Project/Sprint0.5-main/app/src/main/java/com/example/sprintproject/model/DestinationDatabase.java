package com.example.sprintproject.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DestinationDatabase {
    private static DestinationDatabase instance;
    private DatabaseReference databaseReference;

    // Private constructor to prevent instantiation
    /**
     * Constructor for DestinationDatabase
     */
    private DestinationDatabase() {
        // Initialize the Firebase Database instance
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Replace with your reference
        databaseReference = firebaseDatabase.getReference("destinations");
    }

    /**
     * Method to get the instance of the DestinationDatabase
     * @return DestinationDatabase
     */
    public static DestinationDatabase getInstance() {
        if (instance == null) {
            instance = new DestinationDatabase();
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
