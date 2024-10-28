package com.example.sprintproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;

public class AccommodationsActivity extends BottomNavigationActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_accommodations, (FrameLayout) findViewById(R.id.content_frame), true);

    }



}
