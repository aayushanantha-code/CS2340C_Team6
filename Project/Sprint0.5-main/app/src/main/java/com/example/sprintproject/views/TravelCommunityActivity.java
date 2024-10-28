package com.example.sprintproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;

public class TravelCommunityActivity extends BottomNavigationActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_travel_community, (FrameLayout) findViewById(R.id.content_frame), true);  // Tie this activity to its layout
    }
}
