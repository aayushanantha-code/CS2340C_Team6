package com.example.sprintproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;

public class AccommodationsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodations);  // Tie this activity to its layout

        // Initialize buttons
        Button logisticsButton = findViewById(R.id.icon_logistics);
        Button destinationsButton = findViewById(R.id.icon_destinations);
        Button diningButton = findViewById(R.id.icon_dining);
        Button accommodationsButton = findViewById(R.id.icon_accommodations);
        Button communityButton = findViewById(R.id.icon_travel_community);

        logisticsButton.setOnClickListener(view -> startActivity(new Intent(AccommodationsActivity.this, LogisticsActivity.class)));
        destinationsButton.setOnClickListener(view -> startActivity(new Intent(AccommodationsActivity.this, DestinationsActivity.class)));
        diningButton.setOnClickListener(view -> startActivity(new Intent(AccommodationsActivity.this, DiningEstablishmentsActivity.class)));
        accommodationsButton.setOnClickListener(view -> startActivity(new Intent(AccommodationsActivity.this, AccommodationsActivity.class)));
        communityButton.setOnClickListener(view -> startActivity(new Intent(AccommodationsActivity.this, TravelCommunityActivity.class)));
    }



}
