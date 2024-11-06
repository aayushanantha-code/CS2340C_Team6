package com.example.sprintproject.viewmodels;

import com.example.sprintproject.model.Dining;
import com.example.sprintproject.model.GroupDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DiningEstablishmentsViewModel {
    private DatabaseReference groupDatabase;

    /**
     * Constructor for DiningEstablishmentsViewModel
     */
    public DiningEstablishmentsViewModel() {
        groupDatabase = GroupDatabase.getInstance().getDatabaseReference();
    }

    /**
     * Logs a new dining reservation
     * @param group the group of the user
     * @param location the location of the destination
     * @param name the name of the dining place
     * @param date the date of the reservation
     * @param time the time of the reservation
     * @param url the URL of the dining place
     */
    public void logNewDiningReservation(String group, String location, String name, String date, String time, String url) {
        Dining newDining = new Dining(location, url, name, date, time);

        groupDatabase.child(group).child("destinationList").child(location).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if the diningList node exists; if not, it will be created
                groupDatabase.child(group).child("destinationList").child(location).child("diningList").child(name).setValue(newDining);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log failure or handle it as needed
            }
        });
    }

    /**
     * Allocates dining data if needed
     * @param group the group of the user
     * @param location the location of the destination
     * @param allocatedSlots number of slots or reservations
     */
    public void allocateDiningSlots(String group, String location, int allocatedSlots) {
        groupDatabase.child(group).child("destinationList").child(location).child("allocatedDiningSlots").setValue(allocatedSlots);
    }
}
