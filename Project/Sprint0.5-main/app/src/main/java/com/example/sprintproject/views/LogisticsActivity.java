package com.example.sprintproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;

public class LogisticsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);

        Button logisticsButton = findViewById(R.id.icon_logistics);
        Button destinationsButton = findViewById(R.id.icon_destinations);
        Button diningButton = findViewById(R.id.icon_dining);
        Button accommodationsButton = findViewById(R.id.icon_accommodations);
        Button communityButton = findViewById(R.id.icon_travel_community);
        Button datePickerButton = findViewById(R.id.button_date_picker);

        logisticsButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, LogisticsActivity.class)));
        destinationsButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, DestinationsActivity.class)));
        diningButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, DiningEstablishmentsActivity.class)));
        accommodationsButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, AccommodationsActivity.class)));
        communityButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, TravelCommunityActivity.class)));
        datePickerButton.setOnClickListener(view -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                LogisticsActivity.this,
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}