package com.example.sprintproject.views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Button;


import com.example.sprintproject.R;

import java.util.Calendar;

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
        toggleDiningBox.setOnClickListener(c -> toggleDiningBox(diningFrame));

        EditText reservationDate = findViewById(R.id.date_input);
        reservationDate.setOnClickListener(c->showDateEdit(reservationDate));


        EditText reservationTime = findViewById(R.id.time_input);
        reservationTime.setOnClickListener(c->showTimeEdit(reservationTime));
    }

    /**
     * Toggles the visibility of the dining box
     * @param frameLayout The frame layout to toggle
     */
    protected void toggleDiningBox(FrameLayout frameLayout) {
        if (frameLayout.getVisibility() == View.GONE) {
            frameLayout.setVisibility(View.VISIBLE);
            toggleDiningBox.setText("-");
        } else {
            frameLayout.setVisibility(View.GONE);
            toggleDiningBox.setText("+");
        }
    }

    //allows user to choose date and displays it
    //used for both start and end date inputs
    /**
     * Allows the user to choose a date and displays it
     * @param dateEditText the date input field
     */
    private void showDateEdit(EditText dateEditText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this, (view, selectedYear, selectedMonth,  selectedDay) -> {

                    String storeDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    dateEditText.setText(storeDate);
                }, year, month, day);

        datePickerDialog.show();
    }

    /**
     * Allows the user to pick the time of their reservation.
     * @param reservationTime the time input field
     */
    private void showTimeEdit(EditText reservationTime) {
        // Get the current hour and minute
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create and show a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, selectedHour, selectedMinute) -> {
                    // Format and set the selected time to the EditText
                    String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                    reservationTime.setText(formattedTime);
                },
                hour,
                minute,
                true // true for 24-hour format, false for 12-hour format
        );
        timePickerDialog.show();
    }


}
