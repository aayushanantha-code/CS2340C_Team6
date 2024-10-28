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

        Button datePickerButton = findViewById(R.id.button_date_picker);
        Button graphButton = findViewById(R.id.button_graph);
        Button inviteButton = findViewById(R.id.button_invite);
        Button addNoteButton = findViewById(R.id.button_add_note);

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setVisibility(View.GONE);

        dbRef = FirebaseDatabase.getInstance().getReference("InvitedGroups");
        getOrCreateGroupId();

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

        inviteButton.setOnClickListener(view -> showInviteDialog());

        ListView notesListView = findViewById(R.id.notesListView);
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        notesListView.setAdapter(notesAdapter);

        ListView invitedUsersListView = findViewById(R.id.invitedUsersListView);
        invitedUsersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, invitedUsers);
        invitedUsersListView.setAdapter(invitedUsersAdapter);

        addNoteButton.setOnClickListener(view -> showAddNoteDialog());
        loadInvitedUsers();

        // Call to search for user's current destination
        String username = getIntent().getStringExtra("username");
        searchUserDestination(username); // Replace with the actual logged-in user ID
    }

    private void showInviteDialog() {
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
                addInvitedUser(username);
            }

            Intent inviteIntent = new Intent(Intent.ACTION_SEND);
            inviteIntent.setType("text/plain");
            inviteIntent.putExtra(Intent.EXTRA_TEXT, inviteMessage);
            startActivity(Intent.createChooser(inviteIntent, "Invite via"));
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
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
        // Create entries for the pie chart
        List<PieEntry> entries = new ArrayList<>();

        // Check for the edge case when plannedDays is 0
        if (plannedDays == 0) {
            Toast.makeText(this, "No planned days available to visualize.", Toast.LENGTH_SHORT).show();
            return; // Exit the method if there are no planned days
        }

        if (allottedDays == 0) {
            Toast.makeText(this, "No allotted days available to visualize.", Toast.LENGTH_SHORT).show();
            return; // Exit the method if there are no planned days
        }

        // Add entries for allotted days and planned days
        entries.add(new PieEntry(allottedDays, "Allotted Days"));
        entries.add(new PieEntry(plannedDays, "Planned Days"));

        // Create a data set for the entries
        PieDataSet dataSet = new PieDataSet(entries, "Trip Days");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS); // Set colors for the pie slices

        // Create PieData object and set it to the PieChart
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(12f); // Set text size for percentages

        // Configure the PieChart
        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false); // Disable the description
        pieChart.setUsePercentValues(true); // Use percent values for pie slices
        pieChart.setDrawHoleEnabled(true); // Enable hole in the center (optional)
        pieChart.setHoleColor(Color.WHITE); // Set hole color (optional)
        pieChart.setTransparentCircleColor(Color.WHITE); // Set color for the transparent circle
        pieChart.setTransparentCircleAlpha(100); // Set transparency for the circle
        pieChart.animateY(1000); // Add animation (optional)
        pieChart.invalidate(); // Refresh the chart
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

    private void addInvitedUser(String username) {
        dbRef.child(groupId).child(username).setValue(true)
                .addOnSuccessListener(aVoid -> Log.d("Firebase", "User invited: " + username))
                .addOnFailureListener(e -> Log.e("Firebase", "Failed to invite user", e));
    }

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

    private void getOrCreateGroupId() {
        String userId = "someUniqueUserId"; // Replace with actual user ID retrieval logic
        DatabaseReference userGroupRef = dbRef.child("userGroups").child(userId).child("groupId");

        String username = getIntent().getStringExtra("username");
        groupId = username + "'s group";

        userGroupRef.setValue(groupId);
        userGroupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    groupId = dataSnapshot.getValue(String.class);
                    loadInvitedUsers();
                } else {
                    userGroupRef.setValue(groupId)
                            .addOnSuccessListener(aVoid -> {
                                Log.d("Firebase", "New group created: " + groupId);
                                loadInvitedUsers();
                            })
                            .addOnFailureListener(e -> {
                                Log.e("Firebase", "Failed to create group ID", e);
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Failed to retrieve group ID", databaseError.toException());
            }
        });
    }

    private void searchUserDestination(String userId) {
        DatabaseReference destinationsRef = FirebaseDatabase.getInstance().getReference("destinations");

        destinationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean found = false;

                for (DataSnapshot destinationSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot userIDsSnapshot = destinationSnapshot.child("userIDs");
                    for (DataSnapshot userIDSnapshot : userIDsSnapshot.getChildren()) {
                        String storedUserId = userIDSnapshot.getValue(String.class);
                        if (storedUserId != null && storedUserId.equals(userId)) {
                            found = true;
                            String startDate = destinationSnapshot.child("start").getValue(String.class);
                            String endDate = destinationSnapshot.child("end").getValue(String.class);
                            String destinationName = destinationSnapshot.child("name").getValue(String.class);
                            int plannedDays = destinationSnapshot.child("duration").getValue(Integer.class);
                            int number = 0;
                            if (getIntent().getStringExtra("number") != null) {
                                number = Integer.parseInt(getIntent().getStringExtra("number"));
                            }



                            drawPieChart(number, plannedDays);

                            Toast.makeText(LogisticsActivity.this, "Destination: " + destinationName + ", Start: " + startDate + ", End: " + endDate, Toast.LENGTH_LONG).show();
                            break; // Exit loop if found
                        }
                    }
                    if (found) break; // Exit outer loop if found
                }

                if (!found) {
                    Toast.makeText(LogisticsActivity.this, "No destinations found for this user.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Failed to retrieve user destinations", databaseError.toException());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInvitedUsers(); // Load data whenever the activity is resumed
        String username = getIntent().getStringExtra("username");
        searchUserDestination(username); // Re-check user destination
    }


}
