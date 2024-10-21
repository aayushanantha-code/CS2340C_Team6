package com.example.sprintproject.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Date;
import com.example.sprintproject.R;

public class DestinationsActivity extends AppCompatActivity {
    private EditText startDateEdit;
    private String startDateStore;
    private EditText endDateEdit;
    private String endDateStore;
    private EditText durationEdit;
    private String durationStore;
    private Button submitButton;

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
        startDateEdit.setOnClickListener(v -> showDateEdit(startDateEdit));

        //initialize end date edit
        endDateEdit = findViewById(R.id.calculate_end_date_input);
        endDateEdit.setOnClickListener(v -> showDateEdit(endDateEdit));

        durationEdit = findViewById(R.id.calculate_duration_input);
        submitButton = findViewById(R.id.calculate_button);

        submitButton.setOnClickListener(c -> calculate());

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

    //should calculate the missing field if one is present
    public void calculate() {
        String startDate = startDateEdit.getText().toString().trim();
        String endDate = endDateEdit.getText().toString().trim();
        String duration = durationEdit.getText().toString().trim();

        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            calculateDuration(startDate, endDate);
        } else if (!startDate.isEmpty() && !duration.isEmpty()) {
            calculateEndDate(startDate, duration);
        } else if (!endDate.isEmpty() && !duration.isEmpty()) {
            calculateStartDate(endDate, duration);
        } else {
            //implement toast to enter another input
        }
    }

    public void calculateDuration(String startDate, String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date formattedStartDate = format.parse(startDate);
            Date formattedEndDate = format.parse(endDate);

            long duration = (formattedEndDate.getTime() - formattedStartDate.getTime()) / (1000 * 60 * 60 * 24);
            durationEdit.setText(String.valueOf(duration));
        } catch (ParseException p) {
            p.printStackTrace();
        }

    }

    public void calculateEndDate(String startDate, String duration) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date formatStartDate = format.parse(startDate);
            int durationInt = Integer.parseInt(duration);

            long endDateMilliseconds = formatStartDate.getTime() + (durationInt * 1000L * 60 * 60 * 24);
            String endDate = format.format(new Date(endDateMilliseconds));
            endDateEdit.setText(endDate);
        } catch (ParseException p) {
            p.printStackTrace();
        }
    }

    public void calculateStartDate(String endDate, String duration) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date formatEndDate = format.parse(endDate);
            int durationInt = Integer.parseInt(duration);

            long endDateMilliseconds = formatEndDate.getTime() - (durationInt * 1000L * 60 * 60 * 24);
            String startDate = format.format(new Date(endDateMilliseconds));
            startDateEdit.setText(startDate);
        } catch (ParseException p) {
            p.printStackTrace();
        }
    }



}
