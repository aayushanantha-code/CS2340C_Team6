package com.example.sprintproject.views;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.sprintproject.R;

public class AccommodationsActivity extends BottomNavigationActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_accommodations,
                (FrameLayout) findViewById(R.id.content_frame),
                true);

    }



}
