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
import com.example.sprintproject.model.DiningObserver;
import com.example.sprintproject.model.DiningSubject;
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

public class DiningEstablishmentsActivity
        extends BottomNavigationActivity implements DiningObserver {
    private Button toggleDiningBox;
    private DatabaseReference groupDatabase;
    private String groupName;
    private Spinner locationSpinner;
    private List<String> destinationNames;
    private DiningSubject diningSubject;
    private DiningEstablishmentsViewModel diningViewModel;

    private ListView diningListView;
    private DiningListAdapter diningListAdapter;

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
        diningSubject = new DiningSubject();
        diningSubject.addObserver(this);
        diningViewModel = new DiningEstablishmentsViewModel();

        diningListView = findViewById(R.id.dining_list);
        diningListAdapter = new DiningListAdapter(this, diningSubject.getDiningList());
        diningListView.setAdapter(diningListAdapter);

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

    /**
     * Loads the destinations for the group
     */
    private void loadDestinations() {
        groupDatabase.child("destinationList")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        destinationNames.clear();
                        diningSubject.getDiningList().clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String destinationName = snapshot.child("name").getValue(String.class);
                            if (destinationName != null) {
                                destinationNames.add(destinationName);
                            }

                            loadDiningForDestination(snapshot);
                        }

                        if (destinationNames.isEmpty()) {
                            Toast.makeText(DiningEstablishmentsActivity.this,
                                    "No destinations found", Toast.LENGTH_SHORT).show();
                        } else {
                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<>(DiningEstablishmentsActivity.this,
                                            android.R.layout.simple_spinner_item, destinationNames);
                            adapter.setDropDownViewResource(
                                    android.R.layout.simple_spinner_dropdown_item);
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

    /**
     * Loads the dining establishments for a destination
     * @param destinationSnapshot The snapshot of the destination
     */
    private void loadDiningForDestination(DataSnapshot destinationSnapshot) {
        String destinationKey = destinationSnapshot.getKey();
        DatabaseReference diningRef = groupDatabase.child("destinationList")
                .child(destinationKey).child("diningList");

        diningRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot diningSnapshot : dataSnapshot.getChildren()) {
                    Dining dining = diningSnapshot.getValue(Dining.class);
                    if (dining != null) {
                        diningSubject.addDining(dining);
                    }
                }

                sortDiningByDateTime();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DiningEstablishmentsActivity.this,
                        "Failed to load dining establishments: "
                                + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Toggles the visibility of the dining reservation box
     * @param diningFrame The frame layout containing the dining reservation box
     */
    private void toggleDiningBox(FrameLayout diningFrame) {
        if (diningFrame.getVisibility() == View.VISIBLE) {
            diningFrame.setVisibility(View.GONE);
        } else {
            diningFrame.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Shows a date picker dialog for editing the reservation date
     * @param reservationDate The edit text for the reservation date
     */
    private void showDateEdit(EditText reservationDate) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(DiningEstablishmentsActivity.this,
                (view, year, month, dayOfMonth) ->
                        reservationDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * Shows a time picker dialog for editing the reservation time
     * @param reservationTime The edit text for the reservation time
     */
    private void showTimeEdit(EditText reservationTime) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(DiningEstablishmentsActivity.this,
                (view, hourOfDay, minute) -> reservationTime.setText(hourOfDay + ":" + minute),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    /**
     * Adds a dining reservation to the destination
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

        for (Dining dining : diningSubject.getDiningList()) {
            if (dining.getRestaurantName().equals(name)) {
                Toast.makeText(this, "Dining reservation with this name already exists", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        diningViewModel.logNewDiningReservation(groupName, location, name, date, time, url);
        Toast.makeText(this, "Dining reservation added", Toast.LENGTH_SHORT).show();

        Dining newDining = new Dining(location, url, name, date, time);
        diningSubject.addDining(newDining);
        sortDiningByDateTime();
    }

    /**
     * Sorts the dining establishments by date and time
     */
    private void sortDiningByDateTime() {
        diningSubject.getDiningList().sort((d1, d2) -> {
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
    }

    @Override
    public void onDiningListChanged(List<Dining> updatedDiningList) {
        diningListAdapter.notifyDataSetChanged();
    }
}