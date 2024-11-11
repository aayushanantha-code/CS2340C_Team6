package com.example.sprintproject.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.viewmodels.AccommodationsViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AccommodationsActivity extends BottomNavigationActivity {

    private DatabaseReference groupDatabase;
    private String groupName;
    private Spinner locationSpinner;
    private List<String> destinationNames;
    private List<Accommodation> allAccommodations;
    private AccommodationsViewModel accommodationsViewModel;

    private ListView accommodationListView;
    private AccommodationListAdapter accommodationListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_accommodations,
                (FrameLayout) findViewById(R.id.content_frame), true);

        groupName = getIntent().getStringExtra("groupName");
        Toast.makeText(this, groupName, Toast.LENGTH_SHORT).show();
        groupDatabase = FirebaseDatabase.getInstance().getReference().
                child("groups").child(groupName);

        locationSpinner = findViewById(R.id.location_spinner);
        destinationNames = new ArrayList<>();
        allAccommodations = new ArrayList<>();
        accommodationsViewModel = new AccommodationsViewModel();

        accommodationListView = findViewById(R.id.accommodation_list_view);
        accommodationListAdapter = new AccommodationListAdapter(this, allAccommodations);
        accommodationListView.setAdapter(accommodationListAdapter);

        loadDestinations();

        // Room types - this could be a list from a database or hardcoded
        List<String> roomTypes = Arrays.asList("Single", "Double", "Suite", "Family");
        Spinner roomTypeSpinner = findViewById(R.id.room_type_spinner);

        // Create an ArrayAdapter using the room types list
        ArrayAdapter<String> roomTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, roomTypes);
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomTypeSpinner.setAdapter(roomTypeAdapter);

        // Number of rooms spinner (assuming you want numbers like 1, 2, 3, 4, etc.)
        List<String> numberOfRoomsList = Arrays.asList("1", "2", "3", "4", "5");
        Spinner numberOfRoomsSpinner = findViewById(R.id.number_of_rooms_spinner);
        ArrayAdapter<String> numberOfRoomsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, numberOfRoomsList);
        numberOfRoomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberOfRoomsSpinner.setAdapter(numberOfRoomsAdapter);

        Button submitAccommodation = findViewById(R.id.submit_accommodation);
        Button toggleAccommodationBox = findViewById(R.id.add_accommodation);
        FrameLayout accommodationsBox = findViewById(R.id.accommodations_box);

        submitAccommodation.setOnClickListener(c -> {
            addAccommodationToDestination();
            toggleAccommodationBox(accommodationsBox);
        });

        toggleAccommodationBox.setOnClickListener(c -> toggleAccommodationBox(accommodationsBox));

        EditText checkInDate = findViewById(R.id.checkin_input);
        checkInDate.setOnClickListener(c -> showDateEdit(checkInDate));

        EditText checkOutDate = findViewById(R.id.checkout_input);
        checkOutDate.setOnClickListener(c -> showDateEdit(checkOutDate));
    }

    /**
     * Load the destinations for the group
     */
    private void loadDestinations() {
        groupDatabase.child("destinationList")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        destinationNames.clear();  // Clear the list to avoid duplication
                        allAccommodations.clear();  // Clear the accommodations list

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Ensure that the snapshot has a "name" field before adding
                            String destinationName = snapshot.child("name").getValue(String.class);
                            if (destinationName != null) {
                                destinationNames.add(destinationName);
                            }

                            // Now, load all accommodations for this destination
                            loadAccommodationsForDestination(snapshot);
                        }

                        if (destinationNames.isEmpty()) {
                            Toast.makeText(AccommodationsActivity.this,
                                    "No destinations found", Toast.LENGTH_SHORT).show();
                        } else {
                            // Create and set the adapter for the Spinner
                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<>(AccommodationsActivity.this,
                                    android.R.layout.simple_spinner_item, destinationNames);
                            adapter.setDropDownViewResource(android.R.layout.
                                    simple_spinner_dropdown_item);
                            locationSpinner.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(AccommodationsActivity.this,
                                "Failed to load destinations: "
                                + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Load accommodations for a specific destination
     * @param destinationSnapshot The snapshot of the destination
     */
    private void loadAccommodationsForDestination(DataSnapshot destinationSnapshot) {
        String destinationKey = destinationSnapshot.getKey();

        DatabaseReference accommodationsRef = groupDatabase.child("destinationList").
                child(destinationKey).child("accommodationList");

        accommodationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot accommodationSnapshot : dataSnapshot.getChildren()) {
                        try {
                            Accommodation accommodation
                                    = accommodationSnapshot.getValue(Accommodation.class);
                            if (accommodation != null) {
                                allAccommodations.add(accommodation);
                            } else {
                                // Log the issue for debugging
                                System.out.println("Failed to parse accommodation data for key: "
                                        + accommodationSnapshot.getKey());
                            }
                        } catch (Exception e) {
                            // Log any exceptions that occur during deserialization
                            System.out.println("Error parsing accommodation data: "
                                    + e.getMessage());
                        }
                    }
                    sortAccommodationsByDate();
                    accommodationListAdapter.notifyDataSetChanged();
                } else {
                    // Handle case where there are no accommodations for this destination
                    System.out.println("No accommodations found for destination: "
                            + destinationKey);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AccommodationsActivity.this,
                        "Failed to load accommodations: "
                                + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Toggle the visibility of the accommodations box
     * @param accommodationsBox The accommodations box to toggle
     */
    private void toggleAccommodationBox(FrameLayout accommodationsBox) {
        if (accommodationsBox.getVisibility() == View.VISIBLE) {
            accommodationsBox.setVisibility(View.GONE);
        } else {
            accommodationsBox.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Show a date picker dialog for the specified EditText
     * @param dateInput The EditText to show the date picker for
     */
    private void showDateEdit(EditText dateInput) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AccommodationsActivity.this,
                (view, year, month, dayOfMonth) ->
                        dateInput.setText(dayOfMonth + "/" + (month + 1) + "/" + year),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * Add an accommodation to the destination
     */
    private void addAccommodationToDestination() {
        EditText nameInput = findViewById(R.id.name_input);
        EditText checkInInput = findViewById(R.id.checkin_input);
        EditText checkOutInput = findViewById(R.id.checkout_input);
        Spinner roomTypeSpinner = findViewById(R.id.room_type_spinner);
        Spinner numberOfRoomsSpinner = findViewById(R.id.number_of_rooms_spinner);

        String name = nameInput.getText().toString();
        String checkInDate = checkInInput.getText().toString();
        String checkOutDate = checkOutInput.getText().toString();
        String roomType = roomTypeSpinner.getSelectedItem().toString();
        String numberOfRooms = numberOfRoomsSpinner.getSelectedItem().toString();
        String location = locationSpinner.getSelectedItem().toString();

        if (name.isEmpty() || checkInDate.isEmpty()
                || checkOutDate.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Check for duplicate accommodation (same name, check-in,
        // check-out, location, room type, and number of rooms)
        for (Accommodation accommodation : allAccommodations) {
            if (accommodation.getName().equals(name)
                    && accommodation.getDestination().equals(location)
                    && accommodation.getCheckinDate().equals(checkInDate)
                    && accommodation.getCheckoutDate().equals(checkOutDate)
                    && accommodation.getRoomTypes().contains(roomType)
                    && Integer.toString(accommodation.getNumRooms()).equals(numberOfRooms)) {
                // Duplicate found, show message and return
                Toast.makeText(this, "Can't have duplicates", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // No duplicates, proceed with adding the accommodation
        accommodationsViewModel.logNewAccommodationReservation(groupName, location, name,
                checkInDate, checkOutDate,
                Integer.parseInt(numberOfRooms), Arrays.asList(roomType));

        // Create a new Accommodation object
        Accommodation newAccommodation = new Accommodation(location, name, checkInDate,
                checkOutDate, Integer.parseInt(numberOfRooms), Arrays.asList(roomType));
        allAccommodations.add(newAccommodation);
        sortAccommodationsByDate();
        accommodationListAdapter.notifyDataSetChanged();  // Refresh the ListView
    }


    /**
     * Sort accommodations by check-in date
     */
    private void sortAccommodationsByDate() {
        allAccommodations.sort((a1, a2) -> {
            String date1 = a1.getCheckinDate();
            String date2 = a2.getCheckinDate();
            if (date1 == null || date2 == null) {
                return 0; // Handle null dates
            }
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date dateObj1 = format.parse(date1);
                Date dateObj2 = format.parse(date2);
                return dateObj1.compareTo(dateObj2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0; // Handle parse exceptions
            }
        });
    }
}
