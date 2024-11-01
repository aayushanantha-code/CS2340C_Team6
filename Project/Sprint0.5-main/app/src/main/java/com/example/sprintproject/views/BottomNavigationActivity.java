package com.example.sprintproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Group;
import com.example.sprintproject.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BottomNavigationActivity extends AppCompatActivity {
    private DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation);
        userDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Logistics Button
        ImageButton logisticsButton = findViewById(R.id.icon_logistics);
        logisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logisticsIntent = new Intent(BottomNavigationActivity.this,
                        LogisticsActivity.class);
                String username = getIntent().getStringExtra("username");
                userDatabase.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String group = dataSnapshot.getValue(User.class).getGroupName();
                        logisticsIntent.putExtra("username", username);// Pass the username
                        logisticsIntent.putExtra("groupName", group); // Pass the Group
                        startActivity(logisticsIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Firebase", "Failed to find group name",
                                databaseError.toException());
                    }
                });
            }
        });

        // Destinations Button
        ImageButton destinationsButton = findViewById(R.id.icon_destinations);
        destinationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent destinationsIntent = new Intent(BottomNavigationActivity.this,
                        DestinationsActivity.class);
                String username = getIntent().getStringExtra("username");
                System.out.println(username);
                userDatabase.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String group = dataSnapshot.getValue(User.class).getGroupName();
                        destinationsIntent.putExtra("username", username);// Pass the username
                        destinationsIntent.putExtra("groupName", group); // Pass the Group
                        startActivity(destinationsIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Firebase", "Failed to find group name",
                                databaseError.toException());
                    }
                });
            }
        });

        // Dining Button
        ImageButton diningButton = findViewById(R.id.icon_dining);
        diningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diningIntent = new Intent(BottomNavigationActivity.this,
                        DiningEstablishmentsActivity.class);
                String username = getIntent().getStringExtra("username");
                userDatabase.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String group = dataSnapshot.getValue(User.class).getGroupName();
                        diningIntent.putExtra("username", username);// Pass the username
                        diningIntent.putExtra("groupName", group); // Pass the Group
                        startActivity(diningIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Firebase", "Failed to find group name",
                                databaseError.toException());
                    }
                });
            }
        });

        // Accommodations Button
        ImageButton accommodationsButton = findViewById(R.id.icon_accommodations);
        accommodationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accomodationsIntent = new Intent(BottomNavigationActivity.this,
                        AccommodationsActivity.class);
                String username = getIntent().getStringExtra("username");
                userDatabase.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String group = dataSnapshot.getValue(User.class).getGroupName();
                        accomodationsIntent.putExtra("username", username);// Pass the username
                        accomodationsIntent.putExtra("groupName", group); // Pass the Group
                        startActivity(accomodationsIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Firebase", "Failed to find group name",
                                databaseError.toException());
                    }
                });
            }
        });

        // Community Button
        ImageButton communityButton = findViewById(R.id.icon_travel_community);
        communityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent communityIntent = new Intent(BottomNavigationActivity.this,
                        TravelCommunityActivity.class);
                String username = getIntent().getStringExtra("username");
                userDatabase.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String group = dataSnapshot.getValue(User.class).getGroupName();
                        communityIntent.putExtra("username", username);// Pass the username
                        communityIntent.putExtra("groupName", group); // Pass the Group
                        startActivity(communityIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Firebase", "Failed to find group name",
                                databaseError.toException());
                    }
                });
            }
        });
    }
}
