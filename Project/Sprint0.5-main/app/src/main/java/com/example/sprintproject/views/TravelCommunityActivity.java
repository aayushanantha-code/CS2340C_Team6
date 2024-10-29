package com.example.sprintproject.views;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.sprintproject.R;

public class TravelCommunityActivity extends BottomNavigationActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tie this activity to its layout
        getLayoutInflater().inflate(R.layout.activity_travel_community,
                (FrameLayout) findViewById(R.id.content_frame), true);
    }
}
