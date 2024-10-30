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
                Intent logisticsIntent = new Intent(BottomNavigationActivity.this,
                        LogisticsActivity.class);
                String username = getIntent().getStringExtra("username");
                String number = getIntent().getStringExtra("number");
                logisticsIntent.putExtra("number", number);
                logisticsIntent.putExtra("username", username); // Pass the username
                startActivity(logisticsIntent);
                //startActivity(new Intent(BottomNavigationActivity.this, LogisticsActivity.class));
            }
        });

        // Destinations Button
        ImageButton destinationsButton = findViewById(R.id.icon_destinations);
        destinationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent destionationsIntent = new Intent(BottomNavigationActivity.this,
                        DestinationsActivity.class);
                String username = getIntent().getStringExtra("username");
                String number = getIntent().getStringExtra("number");
                destionationsIntent.putExtra("number", number);
                destionationsIntent.putExtra("username", username); // Pass the username
                startActivity(destionationsIntent);
                //startActivity(new Intent(BottomNavigationActivity.this,
                //DestinationsActivity.class));
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
                String number = getIntent().getStringExtra("number");
                diningIntent.putExtra("number", number);
                diningIntent.putExtra("username", username); // Pass the username
                startActivity(diningIntent);
                //startActivity(new Intent(BottomNavigationActivity.this, LogisticsActivity.class));
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
                String number = getIntent().getStringExtra("number");
                accomodationsIntent.putExtra("number", number);
                accomodationsIntent.putExtra("username", username); // Pass the username
                startActivity(accomodationsIntent);
                //startActivity(new Intent(BottomNavigationActivity.this, LogisticsActivity.class));
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
                String number = getIntent().getStringExtra("number");
                communityIntent.putExtra("number", number);
                communityIntent.putExtra("username", username); // Pass the username
                startActivity(communityIntent);
                //startActivity(new Intent(BottomNavigationActivity.this, LogisticsActivity.class));
            }
        });
    }
}
