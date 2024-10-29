package com.example.sprintproject.views;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Date;
import com.example.sprintproject.R;
import com.example.sprintproject.model.DateComparison;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.DestinationDatabase;
import com.example.sprintproject.viewmodels.DestinationsViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DestinationsActivity extends BottomNavigationActivity implements DateComparison {
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
    private EditText locationInput;
    private DatabaseReference destinationDatabase;
    private DatabaseReference userDatabase;
    private Button travelLogButton;
    private ListView destinationList;
    private List<String> destinationData = new ArrayList<>();
    private ArrayAdapter<String> destinationAdapter;
    private List<String> userDestinations; // Added to store user's destinations
    private ListView destinationsView; // ListView to display destinations
    private ArrayAdapter<String> destinationsAdapter; // Adapter for the ListView

    @Override
    /**
     * Creates the DestinationsActivity
     * @param savedInstanceState the saved instance state
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_destinations,
                (FrameLayout) findViewById(R.id.content_frame),
                true);  // Tie this activity to its layout

        Button logTravelToggle = findViewById(R.id.log_travel);
        Button calculatorToggle = findViewById(R.id.calculate_vacation_time);
        Button cancelLogTravelButton = findViewById(R.id.cancel_log_travel_button);
        TextView successfulText = findViewById(R.id.travel_successfully_logged);

        estimatedStart = findViewById(R.id.estimated_start_input);
        estimatedEnd = findViewById(R.id.estimated_end_input);
        estimatedStart.setOnClickListener(v -> showDateEdit(estimatedStart));
        estimatedEnd.setOnClickListener(v -> showDateEdit(estimatedEnd));

        logTravelBox = findViewById(R.id.log_travel_box);
        logTravelToggle.setOnClickListener(v -> {
            successfulText.setVisibility(View.GONE);
            toggleLogTravelBox(logTravelBox);
        });
        cancelLogTravelButton.setOnClickListener(v -> toggleLogTravelBox(logTravelBox));
        calculateVacationTimeBox = findViewById(R.id.calculator_box);
        calculatorToggle.setOnClickListener(v -> {
            successfulText.setVisibility(View.GONE);
            toggleCalculatorBox(calculateVacationTimeBox);
        });
        String username = getIntent().getStringExtra("username");

        userDestinations = new ArrayList<>();

        // Initialize ListView and Adapter
        destinationsView = findViewById(R.id.destinations_View);
        destinationsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userDestinations);
        destinationsView.setAdapter(destinationsAdapter);
        //Create the List of Previous Destinations
        fetchUserDestinations(username);

        //initialize start date edit
        startDateEdit = findViewById(R.id.calculate_start_date_input);
        startDateEdit.setOnClickListener(v -> showDateEdit(startDateEdit));

        //initialize end date edit
        endDateEdit = findViewById(R.id.calculate_end_date_input);
        endDateEdit.setOnClickListener(v -> showDateEdit(endDateEdit));

        durationEdit = findViewById(R.id.calculate_duration_input);
        submitButton = findViewById(R.id.calculate_button);

        //calculates duration
        submitButton.setOnClickListener(c -> {
            long days = calculate();
            DestinationsViewModel account = new DestinationsViewModel();
            SharedPreferences sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);
            String userId = sharedPreferences.getString("userId", null);
            account.allocateVacationDays(days, userId);
        });

        locationInput = findViewById(R.id.travel_location_input);
        travelLogButton = findViewById(R.id.submit_log_travel_button);


        // adds the destination to the list
        travelLogButton.setOnClickListener(c -> {
            String locationName = locationInput.getText().toString().trim();
            String startDate = estimatedStart.getText().toString().trim();
            String endDate = estimatedEnd.getText().toString().trim();
            long duration = calculateDuration(startDate, endDate);

            if (!locationName.isEmpty() && this.isStartDateBeforeEndDate(startDate, endDate)) {
                destinationDatabase = DestinationDatabase.getInstance().getDatabaseReference();
                destinationDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean destinationExists = false;

                        for (DataSnapshot destinationSnapshot : snapshot.getChildren()) {
                            String existingDestination =
                                    destinationSnapshot.child("name").getValue(String.class);
                            if (existingDestination != null
                                    && existingDestination.equals(locationName)) {
                                destinationExists = true;
                                break;
                            }
                        }

                        if (!destinationExists) {
                            //grabs userId from storage
                            SharedPreferences sharedPreferences =
                                    getSharedPreferences("MyApp", MODE_PRIVATE);
                            String userId = sharedPreferences.getString("userId", null);
                            //Continue to add new location
                            DestinationsViewModel account = new DestinationsViewModel();
                            account.logNewDestination(locationName, startDate,
                                    endDate, duration, userId);
                            toggleLogTravelBox(logTravelBox);
                            successfulText.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //error
                    }
                });
            }
        });
    }
    /**
     * Fetches the user's destinations
     * @param username the username of the user
     */
    public void fetchUserDestinations(String username) {
        userDatabase = FirebaseDatabase.getInstance().getReference();
        userDatabase.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Setting up new Destination
                DataSnapshot destinationsSnapshot = dataSnapshot.child("destinations");
                ArrayList<Destination> destinationList = new ArrayList<>();

                for (DataSnapshot destSnapshot : destinationsSnapshot.getChildren()) {
                    Destination destination = destSnapshot.getValue(Destination.class);
                    if (destination != null) {
                        destinationList.add(destination);
                    }
                }
                if (destinationList != null && destinationList.size() > 0) {
                    for (int i = 0; i < destinationList.size(); i++) {
                        userDestinations.add(destinationList.get(i).getName()
                                + " - " + destinationList.get(i).getDuration() + " days");
                        System.out.println(userDestinations.get(i));
                    }
                        if (userDestinations.size() > 5) {
                            userDestinations.remove(0);
                        }
                    }
                destinationsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failure
            }
        });
    }
    @Override
    public boolean isStartDateBeforeEndDate(String startDateStr, String endDateStr) {
        // Define the date format
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        Date endDate = null;

        try {
            // Parse the date strings into Date objects
            startDate = sdf.parse(startDateStr);
            endDate = sdf.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return false; // Return false if parsing fails
        }

        // Compare the dates
        return startDate.before(endDate);
    }

    /**
     * Toggles the log travel box
     * @param view the view to toggle
     */
    protected void toggleLogTravelBox(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * Toggles the calculator box
     * @param view the view to toggle
     */
    protected void toggleCalculatorBox(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
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

    //should calculate the missing field if one is present
    /**
     * Calculates the missing field if one is present
     * @return date
     */
    public long calculate() {
        String startDate = startDateEdit.getText().toString().trim();
        String endDate = endDateEdit.getText().toString().trim();
        String duration = durationEdit.getText().toString().trim();
        long days = 0;

        if (!startDate.isEmpty() && !endDate.isEmpty() && !duration.isEmpty()) {
            days = calculateDuration(startDate, endDate);
        } else if (!startDate.isEmpty() && !endDate.isEmpty()) {
            days = calculateDuration(startDate, endDate);
        } else if (!startDate.isEmpty() && !duration.isEmpty()) {
            days = Long.parseLong(duration);
            calculateEndDate(startDate, duration);
        } else if (!endDate.isEmpty() && !duration.isEmpty()) {
            days = Long.parseLong(duration);
            calculateStartDate(endDate, duration);
        }
        return days;
        // if nothing happened, then there's only 1 or 0 inputs
    }

    /**
     * Calculates the duration of the trip
     * @param startDate the start date of the trip
     * @param endDate the end date of the trip
     * @return the duration of the trip
     */
    public long calculateDuration(String startDate, String endDate) {
        long duration = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date formattedStartDate = format.parse(startDate);
            Date formattedEndDate = format.parse(endDate);

            duration = (formattedEndDate.getTime() - formattedStartDate.getTime())
                    / (1000 * 60 * 60 * 24);
            durationEdit.setText(String.valueOf(duration));
            return duration;
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return duration;
    }

    /**
     * Calculates the end date of the trip
     * @param startDate the start date of the trip
     * @param duration the duration of the trip
     * @return the end date of the trip
     */
    public String calculateEndDate(String startDate, String duration) {
        String endDate = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date formatStartDate = format.parse(startDate);
            int durationInt = Integer.parseInt(duration);

            long endDateMilliseconds = formatStartDate.getTime()
                    + (durationInt * 1000L * 60 * 60 * 24);
            endDate = format.format(new Date(endDateMilliseconds));
            endDateEdit.setText(endDate);
            return endDate;
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return endDate;
    }

    /**
     * Calculates the start date of the trip
     * @param endDate the end date of the trip
     * @param duration the duration of the trip
     * @return the start date of the trip
     */
    public String calculateStartDate(String endDate, String duration) {
        String startDate = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date formatEndDate = format.parse(endDate);
            int durationInt = Integer.parseInt(duration);

            long endDateMilliseconds = formatEndDate.getTime()
                    - (durationInt * 1000L * 60 * 60 * 24);
            startDate = format.format(new Date(endDateMilliseconds));
            startDateEdit.setText(startDate);
            return startDate;
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return startDate;
    }

    /**
     * Gets the start date of the trip
     * @return the start date of the trip
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
     * Gets the start date of the trip
     * @return the start date of the trip
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
     * Gets the start date of the trip
     * @return the start date of the trip
     */
    public String getStartDateScore() {
        return startDateStore;
    }

    /**
     * Gets the end date of the trip
     * @return the end date of the trip
     */
    public String getEndDateStore() {
        return endDateStore;
    }

    /**
     * Sets the start date of the trip
     * @param startDate the start date of the trip
     */
    public void setStartDateStore(String startDate) {
        startDateStore = startDate;
    }

    /**
     * Sets the end date of the trip
     * @param endDate the end date of the trip
     */
    public void setEndDateStore(String endDate) {
        endDateStore = endDate;
    }
}
