package com.example.sprintproject.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

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

public class LogisticsActivity extends BottomNavigationActivity {

    private boolean isGraphVisible = false;
    private List<String> notes = new ArrayList<>();
    private ArrayAdapter<String> notesAdapter;
    private List<String> invitedUsers = new ArrayList<>();
    private ArrayAdapter<String> invitedUsersAdapter;

    private DatabaseReference dbRef;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_logistics, (FrameLayout) findViewById(R.id.content_frame), true);

        // Initialize components
        Button datePickerButton = findViewById(R.id.button_date_picker);
        Button graphButton = findViewById(R.id.button_graph);
        Button inviteButton = findViewById(R.id.button_invite);
        Button addNoteButton = findViewById(R.id.button_add_note);
        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setVisibility(View.GONE);

        dbRef = FirebaseDatabase.getInstance().getReference("InvitedGroups");

        // Retrieve the logged-in username
        String username = getIntent().getStringExtra("username");
        findUserGroup(username); // Check which group the user belongs to

        // Other button listeners
        datePickerButton.setOnClickListener(view -> showDatePickerDialog());
        graphButton.setOnClickListener(view -> togglePieChart(pieChart));
        inviteButton.setOnClickListener(view -> showInviteDialog());
        addNoteButton.setOnClickListener(view -> showAddNoteDialog());

        // Set up ListView for notes and invited users
        ListView notesListView = findViewById(R.id.notesListView);
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        notesListView.setAdapter(notesAdapter);

        ListView invitedUsersListView = findViewById(R.id.invitedUsersListView);
        invitedUsersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, invitedUsers);
        invitedUsersListView.setAdapter(invitedUsersAdapter);
    }

    private void findUserGroup(String username) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean groupFound = false;

                // Check if user is already part of a group
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    String groupName = groupSnapshot.getKey();
                    if (groupSnapshot.hasChild(username)) {
                        groupFound = true;
                        groupId = groupName;
                        loadInvitedUsers(groupName);
                        break;
                    }
                }

                // If no group was found, create a new group named with the user's username
                if (!groupFound) {
                    groupId = username + "'s group"; // Use the username as part of the group name
                    dbRef.child(groupId).child(username).setValue(true) // Add user to this new group
                            .addOnSuccessListener(aVoid -> {
                                invitedUsers.add(username); // Add inviter to the list
                                invitedUsersAdapter.notifyDataSetChanged();
                                Toast.makeText(LogisticsActivity.this, "New group created with you as the first member.", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> Log.e("Firebase", "Failed to create new group", e));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Failed to check user groups", databaseError.toException());
            }
        });
    }


    private void loadInvitedUsers(String groupName) {
        dbRef.child(groupName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                invitedUsers.clear();

                // Add invited users with their notes
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String user = dataSnapshot.getKey();
                    String note = dataSnapshot.child("note").getValue(String.class);
                    String displayText = user + (note != null ? " - Note: " + note : "");
                    invitedUsers.add(displayText);
                }

                invitedUsersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to load invited users", error.toException());
            }
        });
    }




    private void togglePieChart(PieChart pieChart) {
        if (isGraphVisible) {
            pieChart.setVisibility(View.GONE);
        } else {
            drawPieChart(5, 10);  // Example values; replace with dynamic data
            pieChart.setVisibility(View.VISIBLE);
        }
        isGraphVisible = !isGraphVisible;
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

    private void showInviteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invite a Friend");

        final EditText input = new EditText(this);
        input.setHint("Enter username");
        builder.setView(input);

        builder.setPositiveButton("Send Invite", (dialog, which) -> {
            String invitedUsername = input.getText().toString().trim();

            if (!invitedUsername.isEmpty()) {
                // Check if the user exists in the "users" branch
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                usersRef.child(invitedUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // User exists, proceed with the invitation
                            dbRef.child(groupId).child(invitedUsername).setValue(true)
                                    .addOnSuccessListener(aVoid -> {
                                        invitedUsers.add(invitedUsername); // Add invited user to the list
                                        invitedUsersAdapter.notifyDataSetChanged();
                                        Log.d("Firebase", "User invited: " + invitedUsername);
                                        Toast.makeText(LogisticsActivity.this, invitedUsername + " has been invited.", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> Log.e("Firebase", "Failed to invite user", e));
                        } else {
                            // User doesn't exist, show a message
                            Toast.makeText(LogisticsActivity.this, "User doesn't exist. Try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Firebase", "Failed to check if user exists", databaseError.toException());
                    }
                });
            } else {
                Toast.makeText(LogisticsActivity.this, "Please enter a username.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }


    public void drawPieChart(int allottedDays, int plannedDays) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(allottedDays, "Allotted Days"));
        entries.add(new PieEntry(plannedDays, "Planned Days"));

        PieDataSet dataSet = new PieDataSet(entries, "Trip Days");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(12f);

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(100);
        pieChart.animateY(1000);
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
                String username = getIntent().getStringExtra("username");
                // Save the note under the correct groupId and username
                dbRef.child(groupId).child(username).child("note").setValue(note)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(LogisticsActivity.this, "Note added successfully.", Toast.LENGTH_SHORT).show();
                            loadInvitedUsers(groupId); // Refresh the notes list to display the newly added note
                        })
                        .addOnFailureListener(e -> Log.e("Firebase", "Failed to add note", e));
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }




}