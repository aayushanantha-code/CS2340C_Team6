package com.example.sprintproject.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import java.util.Calendar;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;

public class DestinationsActivity extends AppCompatActivity {
    private EditText startDateEdit;
    private String startDateStore;
    private EditText endDateEdit;
    private String endDateStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);  // Tie this activity to its layout

        // Initialize buttons
        Button logisticsButton = findViewById(R.id.icon_logistics);
        Button destinationsButton = findViewById(R.id.icon_destinations);
        Button diningButton = findViewById(R.id.icon_dining);
        Button accommodationsButton = findViewById(R.id.icon_accommodations);
        Button communityButton = findViewById(R.id.icon_travel_community);

        // Set click listeners for each button
        logisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DestinationsActivity.this, LogisticsActivity.class));
            }
        });

        destinationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DestinationsActivity.this, DestinationsActivity.class));
            }
        });

        diningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DestinationsActivity.this, DiningEstablishmentsActivity.class));
            }
        });

        accommodationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DestinationsActivity.this, AccommodationsActivity.class));
            }
        });

        communityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DestinationsActivity.this, TravelCommunityActivity.class));
            }
        });

        //initialize start date edit
        startDateEdit = findViewById(R.id.calculate_start_date_input);
        startDateEdit.setOnClickListener(v-> showDateEdit(startDateEdit));

        //initialize end date edit
        endDateEdit = findViewById(R.id.calculate_end_date_input);
        endDateEdit.setOnClickListener(v->showDateEdit(endDateEdit));
    }

    //allows user to choose date and displays it
    //used for both start and end date inputs
    private void showDateEdit(EditText dateEditText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth,  selectedDay) -> {

            String storeDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            dateEditText.setText(storeDate);
        }, year, month, day);

        datePickerDialog.show();
    }


}
