package com.example.sprintproject.viewmodels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.DestinationDatabase;
import com.example.sprintproject.model.User;
import com.example.sprintproject.views.LoginActivity;
import com.example.sprintproject.views.LogisticsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DestinationsViewModel {
    private DatabaseReference destinationDatabase;
    private DatabaseReference userDatabase;

    //populates the destinations database


    public DestinationsViewModel(String name, String start, String end, long duration, String userId) {
        userDatabase = FirebaseDatabase.getInstance().getReference();
        destinationDatabase = DestinationDatabase.getInstance().getDatabaseReference();
        Destination newDestination = new Destination(name, start, end, duration, userId);
        userDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot destinationsSnapshot = dataSnapshot.child("destinations");
                ArrayList<Destination> destinationList = new ArrayList<>();

                for (DataSnapshot destSnapshot : destinationsSnapshot.getChildren()) {
                    Destination destination = destSnapshot.getValue(Destination.class);
                    if (destination != null) {
                        destinationList.add(destination);
                    }
                }
                destinationList.add(newDestination);
                userDatabase.child("users").child(userId).child("destinations").setValue(destinationList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failure
            }
        });
        destinationDatabase.child(name).setValue(newDestination);
    }

}
