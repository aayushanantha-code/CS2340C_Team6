package com.example.sprintproject.views;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Button;


import com.example.sprintproject.R;

public class DiningEstablishmentsActivity extends BottomNavigationActivity {
    private Button toggleDiningBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_dining_establishments,
                (FrameLayout) findViewById(R.id.content_frame), true);

        Button submitReservaton =  findViewById(R.id.submit_reservation);
        toggleDiningBox = findViewById(R.id.add_dining);
        FrameLayout diningFrame = findViewById(R.id.dining_reservation_box);

        submitReservaton.setOnClickListener(c -> toggleDiningBox(diningFrame));
        toggleDiningBox.setOnClickListener(c-> toggleDiningBox(diningFrame));


    }

    protected void toggleDiningBox(FrameLayout frameLayout) {
        if (frameLayout.getVisibility() == View.GONE) {
            frameLayout.setVisibility(View.VISIBLE);
            toggleDiningBox.setText("-");
        } else {
            frameLayout.setVisibility(View.GONE);
            toggleDiningBox.setText("+");
        }
    }


}
