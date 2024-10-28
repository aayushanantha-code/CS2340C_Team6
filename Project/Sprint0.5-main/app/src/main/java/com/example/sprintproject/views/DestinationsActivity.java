package com.example.sprintproject.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Date;
import com.example.sprintproject.R;

public class DestinationsActivity extends BottomNavigationActivity {
    private EditText estimatedStart;
    private EditText estimatedEnd;
    private EditText startDateEdit;
    private String startDateStore;
    private EditText endDateEdit;
    private String endDateStore;
    private EditText durationEdit;
    private String durationStore;
    private Button submitButton;
    private ConstraintLayout logTravelBox;
    private ConstraintLayout calculateVacationTimeBox;

    /**
     * Constructor for DestinationsActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_destinations, (FrameLayout) findViewById(R.id.content_frame), true);  // Tie this activity to its layout

        Button logTravelToggle = findViewById(R.id.log_travel);
        Button calculatorToggle = findViewById(R.id.calculate_vacation_time);
        Button cancelLogTravelButton = findViewById(R.id.cancel_log_travel_button);

        estimatedStart = findViewById(R.id.estimated_start_input);
        estimatedEnd = findViewById(R.id.estimated_end_input);
        estimatedStart.setOnClickListener(v->showDateEdit(estimatedStart));
        estimatedEnd.setOnClickListener(v->showDateEdit(estimatedEnd));

        logTravelBox = findViewById(R.id.log_travel_box);
        logTravelToggle.setOnClickListener(v -> toggleLogTravelBox(logTravelBox));
        cancelLogTravelButton.setOnClickListener(v -> toggleLogTravelBox(logTravelBox));
        calculateVacationTimeBox = findViewById(R.id.calculator_box);
        calculatorToggle.setOnClickListener(v -> toggleCalculatorBox(calculateVacationTimeBox));
        

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

    /**
     * Toggle the log travel box
     * @param view the view
     */
    private void toggleLogTravelBox(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * Toggle the calculator box
     * @param view the view
     */
    private void toggleCalculatorBox(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    //allows user to choose date and displays it
    //used for both start and end date inputs
    /**
     * Allows user to choose date and displays it
     * @param dateEditText the date edit text
     */
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
    /**
     * Calculate the missing field if one is present
     */
    public void calculate() {
        String startDate = startDateEdit.getText().toString().trim();
        String endDate = endDateEdit.getText().toString().trim();
        String duration = durationEdit.getText().toString().trim();

        if (!startDate.isEmpty() && !endDate.isEmpty() && !duration.isEmpty()) {
            calculateDuration(startDate, endDate);
        } else if (!startDate.isEmpty() && !endDate.isEmpty()) {
            calculateDuration(startDate, endDate);
        } else if (!startDate.isEmpty() && !duration.isEmpty()) {
            calculateEndDate(startDate, duration);
        } else if (!endDate.isEmpty() && !duration.isEmpty()) {
            calculateStartDate(endDate, duration);
        }
        // if nothing happened, then there's only 1 or 0 inputs
    }

    /**
     * Calculate the duration based on the start and end date
     * @param startDate the start date
     * @param endDate the end date
     * @return the duration
     */
    public long calculateDuration(String startDate, String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        long duration = 0;
        try {
            Date formattedStartDate = format.parse(startDate);
            Date formattedEndDate = format.parse(endDate);

            duration = (formattedEndDate.getTime() - formattedStartDate.getTime()) / (1000 * 60 * 60 * 24);
            durationEdit.setText(String.valueOf(duration));
            return duration;
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return duration;
    }

    /**
     * Calculate the end date based on the start date and duration
     * @param startDate the start date
     * @param duration the duration
     * @return the end date
     */
    public String calculateEndDate(String startDate, String duration) {
        String endDate = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date formatStartDate = format.parse(startDate);
            int durationInt = Integer.parseInt(duration);

            long endDateMilliseconds = formatStartDate.getTime() + (durationInt * 1000L * 60 * 60 * 24);
            endDate = format.format(new Date(endDateMilliseconds));
            endDateEdit.setText(endDate);
            return endDate;
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return endDate;
    }

    /**
     * Calculate the start date based on the end date and duration
     * @param endDate the end date
     * @param duration the duration
     * @return the start date
     */
    public String calculateStartDate(String endDate, String duration) {
        String startDate = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date formatEndDate = format.parse(endDate);
            int durationInt = Integer.parseInt(duration);

            long endDateMilliseconds = formatEndDate.getTime() - (durationInt * 1000L * 60 * 60 * 24);
            startDate = format.format(new Date(endDateMilliseconds));
            startDateEdit.setText(startDate);
            return startDate;
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return startDate;
    }

    /**
     * Get the start date in string format
     * @return the start date in string format
     */
    public Date getStartDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = new Date();
        try {
            startDate = format.parse(startDateStore);
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return startDate;
    }

    /**
     * Get the end date in string format
     * @return the end date in string format
     */
    public Date getEndDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date endDate = new Date();
        try {
            endDate = format.parse(endDateStore);
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return endDate;
    }

    /**
     * Get the start date in string format
     * @return the start date in string format
     */
    public String getStartDateStore() {
        return startDateStore;
    }

    /**
     * Set the start date in string format
     * @param startDateStore the start date in string format
     */
    public void setStartDateStore(String startDateStore) {
        this.startDateStore = startDateStore;
    }

    /**
     * Get the end date in string format
     * @return the end date in string format
     */
    public String getEndDateStore() {
        return endDateStore;
    }

    /**
     * Set the end date in string format
     * @param endDateStore the end date in string format
     */
    public void setEndDateStore(String endDateStore) {
        this.endDateStore = endDateStore;
    }
}
