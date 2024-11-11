package com.example.sprintproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;
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

        setupButtonWithGroupCheck(R.id.icon_logistics, LogisticsActivity.class);
        setupButtonWithGroupCheck(R.id.icon_destinations, DestinationsActivity.class);
        setupButtonWithGroupCheck(R.id.icon_dining, DiningEstablishmentsActivity.class);
        setupButtonWithGroupCheck(R.id.icon_accommodations, AccommodationsActivity.class);
        setupButtonWithGroupCheck(R.id.icon_travel_community, TravelCommunityActivity.class);
    }

    /**
     * Sets up a button to check if the user is in a group before starting the target activity
     * @param buttonId the ID of the button to set up
     * @param targetActivity the activity to start when the button is clicked
     */
    private void setupButtonWithGroupCheck(int buttonId, Class<?> targetActivity) {
        ImageButton button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                Intent intent = new Intent(BottomNavigationActivity.this, targetActivity);
                checkGroupAndStartActivity(username, intent);
            }
        });
    }

    /**
     * Checks if the user is in a group and starts the target activity if they are
     * Sends the group and username to the target activity
     * @param username the username of the user
     * @param intent the intent to start the target activity
     */
    private void checkGroupAndStartActivity(String username, Intent intent) {
        userDatabase.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null && user.getIsInGroup()) {
                    String group = user.getGroupName();
                    intent.putExtra("username", username);
                    intent.putExtra("groupName", group);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please join a group first", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Failed to find group name", databaseError.toException());
            }
        });
    }
}
