package com.example.sprintproject.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GroupDatabase {
    private static GroupDatabase instance;
    private DatabaseReference databaseReference;

    // Private constructor to prevent instantiation
    /**
     * Constructor for GroupDatabase
     */
    private GroupDatabase() {
        // Initialize the Firebase Database instance
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Replace with your reference
        databaseReference = firebaseDatabase.getReference().child("groups");
    }

    /**
     * Method to get the instance of the GroupDatabase
     * @return GroupDatabase
     */
    public static GroupDatabase getInstance() {
        if (instance == null) {
            instance = new GroupDatabase();
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
