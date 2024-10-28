package com.example.sprintproject.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class LogisticsActivity extends BottomNavigationActivity {

    private boolean isGraphVisible = false;
    private List<String> notes = new ArrayList<>();
    private ArrayAdapter<String> notesAdapter;
    private List<String> invitedUsers = new ArrayList<>(); // List to store invited users
    private ArrayAdapter<String> invitedUsersAdapter;

    private DatabaseReference dbRef; // Firebase database reference
    private String groupId; // Unique group ID for invited users

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_logistics, (FrameLayout) findViewById(R.id.content_frame), true);

        Button datePickerButton = findViewById(R.id.button_date_picker);
        Button graphButton = findViewById(R.id.button_graph);
        Button inviteButton = findViewById(R.id.button_invite);
        Button addNoteButton = findViewById(R.id.button_add_note);

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setVisibility(View.GONE);

        dbRef = FirebaseDatabase.getInstance().getReference("InvitedGroups"); // Firebase database path
        getOrCreateGroupId(); // Get or create group ID for the session

        datePickerButton.setOnClickListener(view -> showDatePickerDialog());

        graphButton.setOnClickListener(view -> {
            if (isGraphVisible) {
                pieChart.setVisibility(View.GONE);
            } else {
                drawPieChart(5, 10);
                pieChart.setVisibility(View.VISIBLE);
            }
            isGraphVisible = !isGraphVisible;
        });

        inviteButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Invite a Friend");

            final EditText input = new EditText(this);
            input.setHint("Enter username");
            builder.setView(input);

            builder.setPositiveButton("Send Invite", (dialog, which) -> {
                String username = input.getText().toString().trim();
                String inviteMessage = "Join us for an exciting trip planning experience!";
                if (!username.isEmpty()) {
                    inviteMessage = "Hi " + username + "! " + inviteMessage;
                    addInvitedUser(username); // Add invited user to Firebase
                }

                Intent inviteIntent = new Intent(Intent.ACTION_SEND);
                inviteIntent.setType("text/plain");
                inviteIntent.putExtra(Intent.EXTRA_TEXT, inviteMessage);
                startActivity(Intent.createChooser(inviteIntent, "Invite via"));
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        ListView notesListView = findViewById(R.id.notesListView);
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        notesListView.setAdapter(notesAdapter);

        ListView invitedUsersListView = findViewById(R.id.invitedUsersListView); // Ensure you have this ListView in XML
        invitedUsersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, invitedUsers);
        invitedUsersListView.setAdapter(invitedUsersAdapter);

        addNoteButton.setOnClickListener(view -> showAddNoteDialog());
        loadInvitedUsers(); // Load invited users from Firebase
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                LogisticsActivity.this,
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    // Optionally, do something with the selected date
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    public void drawPieChart(int allottedDays, int plannedDays) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(allottedDays, "Allotted Days"));
        entries.add(new PieEntry(plannedDays - allottedDays, "Remaining Days"));

        PieDataSet dataSet = new PieDataSet(entries, "Trip Days");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setData(new PieData(dataSet));
        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.invalidate();
    }

    private void showAddNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Note");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String note = input.getText().toString();
            if (!note.isEmpty()) {
                notes.add(note);
                notesAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Adds an invited user to Firebase under a group
    private void addInvitedUser(String username) {
        // Add the user to the group
        dbRef.child(groupId).child(username).setValue(true)
                .addOnSuccessListener(aVoid -> Log.d("Firebase", "User invited: " + username))
                .addOnFailureListener(e -> Log.e("Firebase", "Failed to invite user", e));
    }

    // Loads invited users from Firebase and updates the ListView
    private void loadInvitedUsers() {
        dbRef.child(groupId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                invitedUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String user = dataSnapshot.getKey();
                    invitedUsers.add(user);
                }
                invitedUsersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to load invited users", error.toException());
            }
        });
    }

    // Function to get or create a unique group ID for the current user session
    private void getOrCreateGroupId() {
        String userId = "someUniqueUserId"; // Replace with actual user ID retrieval logic
        DatabaseReference userGroupRef = dbRef.child("userGroups").child(userId).child("groupId");

        String username = getIntent().getStringExtra("username");
        groupId = username + "'s group"; // Create the group ID based on the username

        // Store the new group ID in userGroups
        userGroupRef.setValue(groupId);; // Store the new group ID
        userGroupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User already has a group ID
                    groupId = dataSnapshot.getValue(String.class);
                    loadInvitedUsers(); // Load invited users for the existing group
                } else {
                    // User does not have a group ID, create a new one
                    groupId = username + "'s group"; // Create the group ID based on the username
                    userGroupRef.setValue(groupId) // Store the new group ID
                            .addOnSuccessListener(aVoid -> {
                                // Optionally handle success
                                Log.d("Firebase", "New group created: " + groupId);
                                loadInvitedUsers(); // Load invited users after creating the group
                            })
                            .addOnFailureListener(e -> {
                                // Handle the error
                                Log.e("Firebase", "Failed to create group ID", e);
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Log.e("Firebase", "Failed to retrieve group ID", databaseError.toException());
            }
        });


    }

}
