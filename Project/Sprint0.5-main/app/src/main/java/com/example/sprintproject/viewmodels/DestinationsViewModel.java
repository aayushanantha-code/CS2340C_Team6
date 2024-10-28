package com.example.sprintproject.viewmodels;

import com.example.sprintproject.model.Destination;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DestinationsViewModel {
    private DatabaseReference destinationDatabase;

    //populates the destinations database


    public DestinationsViewModel(String name, String start, String end, long duration, String userId) {
        destinationDatabase = FirebaseDatabase.getInstance().getReference();
        Destination destination = new Destination(name, start, end, duration, userId);
        destinationDatabase.child("destinations").child(name).setValue(destination);
    }


}
