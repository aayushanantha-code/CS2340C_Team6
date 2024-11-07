package com.example.sprintproject.views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Dining;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.viewmodels.DiningEstablishmentsViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import java.util.ArrayList;
import java.util.List;

import java.util.Calendar;

public class DiningEstablishmentsActivity extends BottomNavigationActivity {
    private Button toggleDiningBox;
    private DatabaseReference groupDatabase;
    private String groupName;
    private Spinner locationSpinner;
    private List<String> destinationNames;
    private DiningEstablishmentsViewModel diningViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_dining_establishments,
                (FrameLayout) findViewById(R.id.content_frame), true);

        groupName = getIntent().getStringExtra("groupName");
        groupDatabase = FirebaseDatabase.getInstance().getReference().child("groups").child(groupName);

        locationSpinner = findViewById(R.id.location_spinner);
        destinationNames = new ArrayList<>();
        diningViewModel = new DiningEstablishmentsViewModel();
        loadDestinations();
        Button submitReservation = findViewById(R.id.submit_reservation);
        toggleDiningBox = findViewById(R.id.add_dining);
        FrameLayout diningFrame = findViewById(R.id.dining_reservation_box);

        submitReservation.setOnClickListener(c -> {
            addDiningToDestination();
            toggleDiningBox(diningFrame);
        });
        toggleDiningBox.setOnClickListener(c -> toggleDiningBox(diningFrame));

        EditText reservationDate = findViewById(R.id.date_input);
        reservationDate.setOnClickListener(c -> showDateEdit(reservationDate));

        EditText reservationTime = findViewById(R.id.time_input);
        reservationTime.setOnClickListener(c -> showTimeEdit(reservationTime));
    }

    private void loadDestinations() {
        groupDatabase.child("destinationList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                destinationNames.clear(); // Clear the list to avoid duplication

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Ensure that the snapshot has a "name" field before adding
                    String destinationName = snapshot.child("name").getValue(String.class);
                    if (destinationName != null) {
                        destinationNames.add(destinationName);
                    }
                }

                if (destinationNames.isEmpty()) {
                    Toast.makeText(DiningEstablishmentsActivity.this, "No destinations found", Toast.LENGTH_SHORT).show();
                } else {
                    // Create and set the adapter for the Spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DiningEstablishmentsActivity.this,
                            android.R.layout.simple_spinner_item, destinationNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    locationSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DiningEstablishmentsActivity.this, "Failed to load destinations: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Toggles the visibility of the dining box
     * @param frameLayout The frame layout to toggle
     */
    protected void toggleDiningBox(FrameLayout frameLayout) { // Unchanged: kept this method as it is
        if (frameLayout.getVisibility() == View.GONE) {
            frameLayout.setVisibility(View.VISIBLE);
            toggleDiningBox.setText("-");
        } else {
            frameLayout.setVisibility(View.GONE);
            toggleDiningBox.setText("+");
        }
    }

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
                new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
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
                    String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                    reservationTime.setText(formattedTime);
                },
                hour,
                minute,
                true // true for 24-hour format, false for 12-hour format
        );
        timePickerDialog.show();
    }

    /**
     * Adds a dining reservation to the destination's dining list
     */
    private void addDiningToDestination() {
        EditText nameInput = findViewById(R.id.name_input);
        EditText dateInput = findViewById(R.id.date_input);
        EditText timeInput = findViewById(R.id.time_input);
        EditText urlInput = findViewById(R.id.url_input);

        String name = nameInput.getText().toString();
        String date = dateInput.getText().toString();
        String time = timeInput.getText().toString();
        String url = urlInput.getText().toString();
        String location = locationSpinner.getSelectedItem().toString();

        if (name.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        diningViewModel.logNewDiningReservation(groupName, location, name, date, time, url);
        Toast.makeText(this, "Dining reservation added", Toast.LENGTH_SHORT).show();
    }
}
