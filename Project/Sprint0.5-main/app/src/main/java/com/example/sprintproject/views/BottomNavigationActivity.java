package com.example.sprintproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;

public class BottomNavigationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation);

        // Logistics Button
        ImageButton logisticsButton = findViewById(R.id.icon_logistics);
        logisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(BottomNavigationActivity.this,
                        LogisticsActivity.class);
                String username = getIntent().getStringExtra("username");
                String number = getIntent().getStringExtra("number");
                loginIntent.putExtra("number", number);
                loginIntent.putExtra("username", username); // Pass the username
                startActivity(loginIntent);
                //startActivity(new Intent(BottomNavigationActivity.this, LogisticsActivity.class));
            }
        });

        // Destinations Button
        ImageButton destinationsButton = findViewById(R.id.icon_destinations);
        destinationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(BottomNavigationActivity.this,
                        DestinationsActivity.class);
                String username = getIntent().getStringExtra("username");
                String number = getIntent().getStringExtra("number");
                loginIntent.putExtra("number", number);
                loginIntent.putExtra("username", username); // Pass the username
                startActivity(loginIntent);
                //startActivity(new Intent(BottomNavigationActivity.this,
                //DestinationsActivity.class));
            }
        });

        // Dining Button
        ImageButton diningButton = findViewById(R.id.icon_dining);
        diningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        BottomNavigationActivity.this,
                                DiningEstablishmentsActivity.class));
            }
        });

        // Accommodations Button
        ImageButton accommodationsButton = findViewById(R.id.icon_accommodations);
        accommodationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        BottomNavigationActivity.this, AccommodationsActivity.class));
            }
        });

        // Community Button
        ImageButton communityButton = findViewById(R.id.icon_travel_community);
        communityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        BottomNavigationActivity.this, TravelCommunityActivity.class));
            }
        });
    }
}
