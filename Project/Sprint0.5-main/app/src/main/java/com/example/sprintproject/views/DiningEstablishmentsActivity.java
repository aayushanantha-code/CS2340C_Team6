package com.example.sprintproject.views;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.Toast;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Dining;
import com.example.sprintproject.model.Group;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DiningEstablishmentsActivity extends BottomNavigationActivity {
    private Button toggleDiningBox;
    private DatabaseReference groupDatabase;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_dining_establishments,
                (FrameLayout) findViewById(R.id.content_frame), true);

        groupName = getIntent().getStringExtra("groupName");
        groupDatabase = FirebaseDatabase.getInstance().getReference().child("groups").child(groupName);

        Button submitReservation = findViewById(R.id.submit_reservation);
        toggleDiningBox = findViewById(R.id.add_dining);
        FrameLayout diningFrame = findViewById(R.id.dining_reservation_box);

        submitReservation.setOnClickListener(c -> {
            addDiningToGroup();
            toggleDiningBox(diningFrame);
        });
        toggleDiningBox.setOnClickListener(c -> toggleDiningBox(diningFrame));
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

    /**
     * Adds a dining reservation to the group's dining list
     */
    private void addDiningToGroup() {
        EditText nameInput = findViewById(R.id.name_input);
        EditText dateInput = findViewById(R.id.date_input);
        EditText timeInput = findViewById(R.id.time_input);
        EditText urlInput = findViewById(R.id.url_input);
        EditText locationInput = findViewById(R.id.location_input);

        String name = nameInput.getText().toString();
        String date = dateInput.getText().toString();
        String time = timeInput.getText().toString();
        String url = urlInput.getText().toString();
        String location = locationInput.getText().toString();

        if (name.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Dining dining = new Dining(location, url, name, date, time);
        groupDatabase.child("diningList").child(name).setValue(dining);
        Toast.makeText(this, "Dining reservation added", Toast.LENGTH_SHORT).show();
    }
}