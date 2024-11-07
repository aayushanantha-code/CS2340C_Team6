package com.example.sprintproject.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;


import com.example.sprintproject.R;

import java.util.Calendar;

public class AccommodationsActivity extends BottomNavigationActivity {
    private Button toggleAccommodations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_accommodations,
                (FrameLayout) findViewById(R.id.content_frame),
                true);


        Spinner numberOfRooms = findViewById(R.id.number_of_rooms_spinner);
        ArrayAdapter<CharSequence> numberOfRoomsAdapter = ArrayAdapter.createFromResource(this,
                R.array.number_of_rooms, android.R.layout.simple_spinner_item);
        numberOfRoomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberOfRooms.setAdapter(numberOfRoomsAdapter);

        Spinner roomType = findViewById(R.id.room_type_spinner);
        ArrayAdapter<CharSequence> roomTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.room_type, android.R.layout.simple_spinner_item);
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomType.setAdapter(roomTypeAdapter);

        EditText checkIn = findViewById(R.id.checkin_input);
        EditText checkout = findViewById(R.id.checkout_input);
        checkIn.setOnClickListener(c -> showDateEdit(checkIn));
        checkout.setOnClickListener(c -> showDateEdit(checkout));

        FrameLayout accommodationsBox = findViewById(R.id.accommodations_box);
        toggleAccommodations = findViewById(R.id.add_accommodation);
        toggleAccommodations.setOnClickListener(c -> toggleAccomodationsView(accommodationsBox));
        // Gets the group Name from intents (Use this for all database stuff)
        String group = getIntent().getStringExtra("groupName");
    }

    /**
     * Toggles the input box for creating a new occommodation.
     * @param frameLayout the input box for creating new accommodations.
     */
    protected void toggleAccomodationsView(FrameLayout frameLayout) {
        if (frameLayout.getVisibility() == View.GONE) {
            frameLayout.setVisibility(View.VISIBLE);
            toggleAccommodations.setText("-");
        } else {
            frameLayout.setVisibility(View.GONE);
            toggleAccommodations.setText("+");
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





}
