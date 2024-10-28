package com.example.sprintproject.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DestinationDatabase {
    private static DestinationDatabase instance;
    private DatabaseReference databaseReference;

    // Private constructor to prevent instantiation
    private DestinationDatabase() {
        // Initialize the Firebase Database instance
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("destinations"); // Replace with your reference
    }

    public static DestinationDatabase getInstance() {
        if (instance == null) {
            instance = new DestinationDatabase();
        }
        return instance;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
