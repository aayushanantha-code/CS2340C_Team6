package com.example.sprintproject.viewmodels;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.GroupDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AccommodationsViewModel {
    private DatabaseReference groupDatabase;

    /**
     * Constructor for AccommodationsViewModel
     */
    public AccommodationsViewModel() {
        groupDatabase = GroupDatabase.getInstance().getDatabaseReference();
    }

    /**
     * Logs a new accommodation reservation
     * @param group the group of the user
     * @param location the location of the accommodation
     * @param name the name of the accommodation
     * @param checkinDate the check-in date of the accommodation
     * @param checkoutDate the check-out date of the accommodation
     * @param numRooms the number of rooms
     * @param roomTypes the types of rooms
     */
    public void logNewAccommodationReservation(String group, String location, String name,
                                               String checkinDate, String checkoutDate,
                                               int numRooms, List<String> roomTypes) {
        // Create a new Accommodation object
        Accommodation newAccommodation = new Accommodation(location, name, checkinDate, checkoutDate, numRooms, roomTypes);

        // Reference to the Firebase node for the group, destinationList, and location
        groupDatabase.child(group).child("destinationList").child(location).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if the accommodationList node exists; if not, it will be created
                groupDatabase.child(group).child("destinationList").child(location).child("accommodationList").child(name).setValue(newAccommodation);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log failure or handle it as needed
            }
        });
    }

    /**
     * Allocates accommodation rooms if needed
     * @param group the group of the user
     * @param location the location of the accommodation
     * @param allocatedRooms the number of rooms allocated
     */
    public void allocateAccommodationRooms(String group, String location, int allocatedRooms) {
        groupDatabase.child(group).child("destinationList").child(location).child("allocatedAccommodationRooms").setValue(allocatedRooms);
    }

    /**
     * Adds a new room type to an accommodation
     * @param group the group of the user
     * @param location the location of the accommodation
     * @param accommodationName the name of the accommodation
     * @param roomType the room type to be added
     */
    public void addRoomTypeToAccommodation(String group, String location, String accommodationName, String roomType) {
        groupDatabase.child(group).child("destinationList").child(location)
                .child("accommodationList").child(accommodationName).child("roomTypes")
                .push().setValue(roomType);
    }

    /**
     * Removes a room type from an accommodation
     * @param group the group of the user
     * @param location the location of the accommodation
     * @param accommodationName the name of the accommodation
     * @param roomType the room type to be removed
     */
    public void removeRoomTypeFromAccommodation(String group, String location, String accommodationName, String roomType) {
        groupDatabase.child(group).child("destinationList").child(location)
                .child("accommodationList").child(accommodationName).child("roomTypes")
                .orderByValue().equalTo(roomType).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Log failure or handle it as needed
                    }
                });
    }
}
