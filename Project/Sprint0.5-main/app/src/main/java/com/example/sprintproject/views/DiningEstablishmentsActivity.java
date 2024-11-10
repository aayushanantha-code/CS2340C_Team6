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
import android.widget.ListView;
import android.widget.Toast;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Dining;
import com.example.sprintproject.viewmodels.DiningEstablishmentsViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class DiningEstablishmentsActivity extends BottomNavigationActivity {
    private Button toggleDiningBox;
    private DatabaseReference groupDatabase;
    private String groupName;
    private Spinner locationSpinner;
    private List<String> destinationNames;
    private List<Dining> allDiningEstablishments;  // List to store all dining establishments
    private DiningEstablishmentsViewModel diningViewModel;

    private ListView diningListView;  // ListView to display dining establishments
    private DiningListAdapter diningListAdapter;  // Adapter to bind data to ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_dining_establishments,
                (FrameLayout) findViewById(R.id.content_frame), true);

        groupName = getIntent().getStringExtra("groupName");
        groupDatabase = FirebaseDatabase.getInstance()
                .getReference().child("groups").child(groupName);

        locationSpinner = findViewById(R.id.location_spinner);
        destinationNames = new ArrayList<>();
        allDiningEstablishments = new ArrayList<>();  // Initialize the list
        diningViewModel = new DiningEstablishmentsViewModel();

        diningListView = findViewById(R.id.dining_list);  // Initialize the ListView
        diningListAdapter = new DiningListAdapter(this,
                allDiningEstablishments);  // Initialize the Adapter
        diningListView.setAdapter(diningListAdapter);  // Set the adapter for the ListView

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
        groupDatabase.child("destinationList")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        destinationNames.clear();  // Clear the list to avoid duplication
                        allDiningEstablishments.clear();  // Clear the dining establishments list

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Ensure that the snapshot has a "name" field before adding
                            String destinationName = snapshot.child("name").getValue(String.class);
                            if (destinationName != null) {
                                destinationNames.add(destinationName);
                            }

                            // Now, load all dining establishments for this destination
                            loadDiningForDestination(snapshot);
                        }

                        if (destinationNames.isEmpty()) {
                            Toast.makeText(DiningEstablishmentsActivity.this,
                                    "No destinations found", Toast.LENGTH_SHORT).show();
                        } else {
                            // Create and set the adapter for the Spinner
                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<>(DiningEstablishmentsActivity.this,
                                            android.R.layout.simple_spinner_item, destinationNames);
                            adapter.setDropDownViewResource(android.R.layout
                                    .simple_spinner_dropdown_item);
                            locationSpinner.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(DiningEstablishmentsActivity.this,
                                "Failed to load destinations: "
                                        + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadDiningForDestination(DataSnapshot destinationSnapshot) {
        // Get the dining list for the current destination
        String destinationKey = destinationSnapshot.getKey();
        DatabaseReference diningRef = groupDatabase.child("destinationList")
                .child(destinationKey).child("diningList");

        diningRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot diningSnapshot : dataSnapshot.getChildren()) {
                    Dining dining = diningSnapshot.getValue(Dining.class);
                    if (dining != null) {
                        // Add dining to the allDiningEstablishments list
                        allDiningEstablishments.add(dining);
                    }
                }

                sortDiningByDateTime();

                // Update the adapter to reflect the new data
                diningListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DiningEstablishmentsActivity.this,
                        "Failed to load dining establishments: "
                                + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleDiningBox(FrameLayout diningFrame) {
        if (diningFrame.getVisibility() == View.VISIBLE) {
            diningFrame.setVisibility(View.GONE);
        } else {
            diningFrame.setVisibility(View.VISIBLE);
        }
    }

    private void showDateEdit(EditText reservationDate) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(DiningEstablishmentsActivity.this,
                (view, year, month, dayOfMonth) ->
                        reservationDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimeEdit(EditText reservationTime) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(DiningEstablishmentsActivity.this,
                (view, hourOfDay, minute) -> reservationTime.setText(hourOfDay + ":" + minute),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    // Original addDiningToDestination() method
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

        // Add the new dining reservation to the list and notify the adapter
        Dining newDining = new Dining(location, url, name, date, time);
        allDiningEstablishments.add(newDining);
        sortDiningByDateTime();  // Re-sort the list
        diningListAdapter.notifyDataSetChanged();  // Refresh the ListView

        // Logging the new dining reservation
        System.out.println("Added Dining: " + newDining.getRestaurantName()
                + ", Date: " + newDining.getDate() + ", Time: " + newDining.getTime());
    }


    private void sortDiningByDateTime() {
        allDiningEstablishments.sort((d1, d2) -> {
            String dateTime1 = d1.getDate() + " " + d1.getTime();
            String dateTime2 = d2.getDate() + " " + d2.getTime();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                Date date1 = format.parse(dateTime1);
                Date date2 = format.parse(dateTime2);
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });

        // Logging the sorted list for debugging
        System.out.println("Sorted Dining List:");
        for (Dining dining : allDiningEstablishments) {
            System.out.println("Dining: " + dining.getRestaurantName()
                    + ", Date: " + dining.getDate() + ", Time: " + dining.getTime());
        }
    }

}
