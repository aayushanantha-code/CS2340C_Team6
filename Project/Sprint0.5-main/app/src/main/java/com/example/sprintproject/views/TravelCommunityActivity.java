package com.example.sprintproject.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;

public class TravelCommunityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_community);  // Tie this activity to its layout
    }

    private void setupBottomNavigation() {
        findViewById(R.id.icon_logistics).setOnClickListener(v -> {
            // Stay on the Logistics screen
        });

        findViewById(R.id.icon_destinations).setOnClickListener(v -> {
            Intent intent = new Intent(TravelCommunityActivity.this, DestinationsActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.icon_dining).setOnClickListener(v -> {
            Intent intent = new Intent(TravelCommunityActivity.this, DiningEstablishmentsActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.icon_accommodations).setOnClickListener(v -> {
            Intent intent = new Intent(TravelCommunityActivity.this, AccommodationsActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.icon_travel_community).setOnClickListener(v -> {
            Intent intent = new Intent(TravelCommunityActivity.this, TravelCommunityActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
