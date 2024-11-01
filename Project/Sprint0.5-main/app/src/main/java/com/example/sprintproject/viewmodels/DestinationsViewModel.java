package com.example.sprintproject.viewmodels;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.GroupDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DestinationsViewModel {
    private DatabaseReference groupDatabase;

    //populates the destinations database

    /**
     * Constructor for DestinationsViewModel
     */
    public DestinationsViewModel() {
    }

    /**
     * Logs a new destination
     * @param name of the destination
     * @param start date
     * @param end date
     * @param duration of the trip
     * @param group of the user
     */
    public void logNewDestination(String name, String start, String end, long duration, String group) {
        groupDatabase = GroupDatabase.getInstance().getDatabaseReference();
        Destination newDestination = new Destination(name, start, end, duration);
        groupDatabase.child(group).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Setting up total vacationDuration
                DataSnapshot groupSnapshot = dataSnapshot.child("plannedDays");
                int duration = 0;
                if (groupSnapshot.getValue() != null) {
                    duration = groupSnapshot.getValue(Integer.class);
                }
                duration += newDestination.getDuration();

                // Setting up new Destination
                DataSnapshot destinationsSnapshot = dataSnapshot.child("destinationList");
                ArrayList<Destination> destinationList = new ArrayList<>();

                for (DataSnapshot destSnapshot : destinationsSnapshot.getChildren()) {
                    Destination destination = destSnapshot.getValue(Destination.class);
                    if (destination != null) {
                        destinationList.add(destination);
                    }
                }
                destinationList.add(newDestination);
                groupDatabase.child(group).child("plannedDays").setValue(duration);
                groupDatabase.child(group).child("destinationList").setValue(destinationList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failure
            }
        });
    }

    /**
     * Allocates vacation days
     * @param days of the trip
     * @param group of the user
     */
    public void allocateVacationDays(long days, String group) {
        groupDatabase = GroupDatabase.getInstance().getDatabaseReference();
        groupDatabase.child(group).child("allocatedVacationDays").setValue(days);
    }
}
