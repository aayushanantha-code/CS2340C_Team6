package com.example.sprintproject.views;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Group;
import com.example.sprintproject.model.GroupDatabase;
import com.example.sprintproject.model.User;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
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
import java.util.List;




public class LogisticsActivity extends BottomNavigationActivity {

    private List<String> notes = new ArrayList<>();
    private ArrayAdapter<String> notesAdapter;
    private List<String> invitedUsers = new ArrayList<>();
    private ArrayAdapter<String> invitedUsersAdapter;

    private TextView makeAGroupWarning;
    private TextView tooManyDays;
    private FrameLayout pieChartFrame;
    private FrameLayout listBox;
    private Button graphButton;
    private Button listButton;
    private Button createGroup;
    private Button inviteButton;
    private Button addNoteButton;

    private DatabaseReference userDatabase;
    private DatabaseReference groupDatabase;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_logistics,
                (FrameLayout) findViewById(R.id.content_frame), true);

        // Initialize components
        makeAGroupWarning = findViewById(R.id.make_a_group);
        makeAGroupWarning.setVisibility(View.GONE);
        graphButton = findViewById(R.id.button_graph);
        listButton = findViewById(R.id.button_userList);
        createGroup = findViewById(R.id.button_create_group);
        inviteButton = findViewById(R.id.button_invite);
        addNoteButton = findViewById(R.id.button_add_note);
        tooManyDays = findViewById(R.id.too_many_planned_warning);

        // Initialize Pie Chart
        PieChart pieChart = findViewById(R.id.pieChart);
        pieChartFrame = findViewById(R.id.pieChartBox);
        pieChartFrame.setVisibility(View.GONE);

        // Initialize Group and User Database
        groupDatabase = GroupDatabase.getInstance().getDatabaseReference();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Set up ListView for notes and invited users
        ListView notesListView = findViewById(R.id.notesListView);
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        notesListView.setAdapter(notesAdapter);

        ListView invitedUsersListView = findViewById(R.id.invitedUsersListView);
        invitedUsersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                invitedUsers);
        invitedUsersListView.setAdapter(invitedUsersAdapter);

        listBox = findViewById(R.id.usersAndNotes_Box);

        // Retrieve the logged-in username
        String username = getIntent().getStringExtra("username");
        groupId = getIntent().getStringExtra("groupName");
        findUserGroup(username, makeAGroupWarning); // Check which group the user belongs to

        // Other button listeners
        graphButton.setOnClickListener(view -> togglePieChart(pieChartFrame, pieChart));
        listButton.setOnClickListener(view -> toggleUserList(listBox));
        createGroup.setOnClickListener(view -> createNewGroup(username));
        inviteButton.setOnClickListener(view -> showInviteDialog());
        addNoteButton.setOnClickListener(view -> showAddNoteDialog());

    }

    /**
     * Check if the user is already part of a group. If not, create a new group.
     * @param username The username of the logged-in user
     * @param makeAGroupWarning Warning when group cannot be created
     */
    private void findUserGroup(String username, TextView makeAGroupWarning) {
        userDatabase.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (username != null && dataSnapshot.getValue(User.class).getIsInGroup()) {
                    groupDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot groupSnapshots : snapshot.getChildren()) {
                                for (DataSnapshot userSnapshots
                                        : groupSnapshots.child("userList").getChildren()) {
                                    if (username.equals(userSnapshots.getValue(User.class)
                                            .getUserID())) {
                                        groupId = groupSnapshots.getKey();
                                        loadInvitedUsers(groupId);
                                        break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("Firebase", "Failed to check groupBase",
                                    databaseError.toException());
                        }
                    });
                } else {
                    makeAGroupWarning.setVisibility(View.VISIBLE);
                    graphButton.setVisibility(View.GONE);
                    listButton.setVisibility(View.GONE);
                    inviteButton.setVisibility(View.GONE);
                    addNoteButton.setVisibility(View.GONE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Failed to check userBase",
                        databaseError.toException());
            }
        });
    }

    private void createNewGroup(String username) {
        groupId = username + "'s group"; // Use the username as part of the group name
        // Add user to this new group
        userDatabase.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if (!user.getIsInGroup()) {
                        Group group = new Group();
                        groupDatabase.child(groupId).setValue(group).addOnSuccessListener(aVoid -> {
                            user.joinGroup(groupId);
                            groupDatabase.child(groupId).child("userList")
                                    .child(username).setValue(user);
                            userDatabase.child(username).setValue(user);
                            invitedUsers.add(username); // Add inviter to the list
                            invitedUsersAdapter.notifyDataSetChanged();
                            Toast.makeText(LogisticsActivity.this,
                                    "New group created with you as the first member.",
                                    Toast.LENGTH_SHORT).show();
                            makeAGroupWarning.setVisibility(View.GONE);
                            graphButton.setVisibility(View.VISIBLE);
                            listButton.setVisibility(View.VISIBLE);
                            inviteButton.setVisibility(View.VISIBLE);
                            addNoteButton.setVisibility(View.VISIBLE);
                        }).addOnFailureListener(e -> Log.e("Firebase",
                                "Failed to create new group", e));
                    } else {
                        Toast.makeText(LogisticsActivity.this,
                                "You are already in a group.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Firebase", "Failed to find user",
                            databaseError.toException());
                }
            });
    }

    /**
     * Load the invited users for the given group and display them in the ListView.
     * @param groupName The name of the group
     */
    private void loadInvitedUsers(String groupName) {
        groupDatabase.child(groupName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                invitedUsers.clear();

                // Add invited users with their notes
                for (DataSnapshot dataSnapshot : snapshot.child("userList").getChildren()) {
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


    /**
     * Toggle the visibility of the pie chart.
     * @param pieChartBox The pie chart to toggle
     * @param pieChart The specific pie chart to display
     */
    private void togglePieChart(FrameLayout pieChartBox, PieChart pieChart) {
        listBox.setVisibility(View.GONE);
        if (pieChartBox.getVisibility() == View.VISIBLE) {
            pieChartBox.setVisibility(View.GONE);
            tooManyDays.setVisibility(View.GONE);
            graphButton.setText("Show Vacation Days");
        } else {
            drawPieChart(pieChart);
            pieChartBox.setVisibility(View.VISIBLE);
            graphButton.setText("Close");

        }
    }

    private void toggleUserList(FrameLayout userList) {
        pieChartFrame.setVisibility(View.GONE);
        if (userList.getVisibility() == View.VISIBLE) {
            userList.setVisibility(View.GONE);
            listButton.setText("Show Notes and Users");
        } else {
            userList.setVisibility(View.VISIBLE);
            listButton.setText("Close");

        }
    }


    /**
     * Show a dialog to invite a friend to the group.
     */
    private void showInviteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invite a Friend");

        final EditText input = new EditText(this);
        input.setHint("Enter username");
        builder.setView(input);

        builder.setPositiveButton("Send Invite", (dialog, which) -> {
            String invitedUsername = input.getText().toString().trim();

            if (!invitedUsername.isEmpty()) {
                inviteUser(invitedUsername);
            } else {
                Toast.makeText(LogisticsActivity.this,
                        "Please enter a username.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    public void inviteUser(String invitedUsername) {
        userDatabase.child(invitedUsername)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // User exists, proceed with the invitation
                            User user = dataSnapshot.getValue(User.class);
                            if (!user.getIsInGroup()) {
                                groupDatabase.child(groupId)
                                        .child("userList").child(invitedUsername)
                                        .setValue(user).addOnSuccessListener(aVoid -> {
                                            // Add invited user to the list
                                            user.joinGroup(groupId);
                                            userDatabase.child(invitedUsername).setValue(user);
                                            invitedUsers.add(invitedUsername);
                                            invitedUsersAdapter.notifyDataSetChanged();
                                            Log.d("Firebase", "User invited: " + invitedUsername);
                                            Toast.makeText(LogisticsActivity.this, invitedUsername
                                                    + " has been invited.",
                                                    Toast.LENGTH_SHORT).show();
                                        }).addOnFailureListener(e -> Log.e("Firebase",
                                                "Failed to invite user", e));
                            } else {
                                // User doesn't exist, show a message
                                Toast.makeText(LogisticsActivity.this,
                                        "User already in a group. :(",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LogisticsActivity.this,
                                    "User doesn't exist. Try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Firebase", "Failed to check if user exists",
                                databaseError.toException());
                    }
                });
    }


    /**
     * Draw a pie chart with the given data.
     * @param pieChart The pie chart to draw
     */
    public void drawPieChart(PieChart pieChart) {
        groupDatabase.child(groupId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Integer allocatedDays = dataSnapshot
                                .child("allocatedVacationDays").getValue(Integer.class);
                        Integer plannedDays = dataSnapshot.child("plannedDays")
                                .getValue(Integer.class);
                        System.out.println("allocated:" + allocatedDays
                                + "   planned:" + plannedDays);
                        if (allocatedDays != null && plannedDays
                                != null && plannedDays <= allocatedDays) {
                            int unplannedAllocatedDays = allocatedDays - plannedDays;


                            List<PieEntry> entries = new ArrayList<>();
                            entries.add(new PieEntry(unplannedAllocatedDays,
                                    "Unplanned Allotted Days"));
                            entries.add(new PieEntry(plannedDays, "Planned Days"));

                            PieDataSet dataSet = new PieDataSet(entries, "Trip Days");
                            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                            PieData pieData = new PieData(dataSet);
                            pieData.setValueTextSize(30f);
                            Legend legend = pieChart.getLegend();
                            legend.setEnabled(true);
                            legend.setTextSize(18f);
                            legend.setTextColor(Color.WHITE);

                            Description description = new Description();
                            description.setText("Days of Vacation");
                            description.setTextSize(30f); // Set title text size
                            description.setTextColor(Color.WHITE); // Set title text color
                            pieChart.setDescription(description);

                            pieChart.setData(pieData);
                            pieChart.setUsePercentValues(true);
                            pieChart.setDrawHoleEnabled(true);
                            pieChart.setHoleColor(Color.WHITE);
                            pieChart.setTransparentCircleColor(Color.WHITE);
                            pieChart.setTransparentCircleAlpha(100);
                            pieChart.animateY(1000);
                            pieChart.invalidate();
                        } else {
                            tooManyDays.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Failure
                    }
                });
    }

    /**
     * Show a dialog to add a note to the group.
     */
    private void showAddNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Note");

        final EditText input = new EditText(this);
        input.setHint("Enter your notes on the Itinerary!");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String note = input.getText().toString();
            if (!note.isEmpty()) {
                addNote(note);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void addNote(String note) {
        String username = getIntent().getStringExtra("username");
        // Save the note under the correct groupId and username
        groupDatabase.child(groupId).child("userList").child(username).child("note").setValue(note)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(LogisticsActivity.this,
                            "Note added successfully.", Toast.LENGTH_SHORT).show();
                    // Refresh the notes list to display the newly added note
                    loadInvitedUsers(groupId);
                })
                .addOnFailureListener(e -> Log.e("Firebase", "Failed to add note", e));
    }
}