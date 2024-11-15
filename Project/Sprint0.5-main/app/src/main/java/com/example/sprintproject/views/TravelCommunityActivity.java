package com.example.sprintproject.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Dining;
import com.example.sprintproject.model.GroupDatabase;
import com.example.sprintproject.model.TravelCommunityDatabase;
import com.example.sprintproject.model.TravelCommunityPost;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TravelCommunityActivity extends BottomNavigationActivity implements TravelCommunityAdapter.postClickListener {
    private RecyclerView recyclerView;
    private List<TravelCommunityPost> postList;
    private TravelCommunityAdapter postAdapter;
    private DatabaseReference travelDatabase;
    private DatabaseReference groupDatabase;

    private Button openPostList;
    private Button openAddPost;
    private Button post;
    private TextView initialText;
    private FrameLayout addPostBox;
    private Spinner locationSpinner;

    private List<String> destinationNames;
    private String groupName;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tie this activity to its layout
        getLayoutInflater().inflate(R.layout.activity_travel_community,
                (FrameLayout) findViewById(R.id.content_frame), true);

        // Gets the group Name from intents (Use this for all database stuff)
        groupName = getIntent().getStringExtra("groupName");
        userName = getIntent().getStringExtra("username");
        openPostList = findViewById(R.id.button_view_recycler);
        openAddPost = findViewById(R.id.add_post);
        initialText = findViewById(R.id.initial_travel_text);
        addPostBox = findViewById(R.id.travel_post_box);
        locationSpinner = findViewById(R.id.destination_spinner);
        post = findViewById(R.id.post);

        recyclerView = findViewById(R.id.recyclerView);
        travelDatabase = TravelCommunityDatabase.getInstance().getDatabaseReference();
        groupDatabase = GroupDatabase.getInstance().getDatabaseReference();

        // Populate the postList
        destinationNames = new ArrayList<>();
        postList = new ArrayList<>();
        fillPostList();

        // Opens the Post list Visually
        openPostList.setOnClickListener(v -> {
            initialText.setVisibility(View.GONE);
            fillPostList();
            openPostList();
        });

        // Opens add Post box
        openAddPost.setOnClickListener(v -> {
            toggleAddPostBox();
        });

        // Adds a new Post
        post.setOnClickListener(v -> {
            addNewPost();
            fillPostList();
        });
    }

    @Override
    public void onPostClick(TravelCommunityPost post) {
        // Will eventually update this to show a breakdown of the travel post
        // This is the next BIG thing to implement
        Toast.makeText(this, "Clicked on: " + post.getUser(), Toast.LENGTH_SHORT).show();
    }

    private void openPostList () {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new TravelCommunityAdapter(this, postList, this);
        recyclerView.setAdapter(postAdapter);
    }

    private void fillPostList() {
        travelDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();

                // Pulling every Single Travel Post
                for (DataSnapshot travelSnapshot : dataSnapshot.getChildren()) {
                    // Getting the Destination values and the Username
                    String username = travelSnapshot.child("user").getValue(String.class);
                    String notes = travelSnapshot.child("notes").getValue(String.class);
                    String travelType = travelSnapshot.child("travelType").getValue(String.class);

                    String name = travelSnapshot.child("destination").child("name").getValue(String.class);
                    String start = travelSnapshot.child("destination").child("start").getValue(String.class);
                    String end = travelSnapshot.child("destination").child("end").getValue(String.class);
                    long duration = travelSnapshot.child("destination").child("duration").getValue(long.class);
                    Destination destination = new Destination(name, start, end, duration);

                    // Getting all of destination's accommodations
                    for (DataSnapshot accommodationsnapshot : travelSnapshot.child("destination").child("accomodationList").getChildren()) {
                        String cID = accommodationsnapshot.child("checkinDate").getValue(String.class);
                        String cOD = accommodationsnapshot.child("checkoutDate").getValue(String.class);
                        String accomName = accommodationsnapshot.child("name").getValue(String.class);
                        int numRooms = accommodationsnapshot.child("numRooms").getValue(Integer.class);
                        ArrayList<String> roomTypes = new ArrayList<String>();
                        // Getting roomTypes
                        for (DataSnapshot roomSnapshot : accommodationsnapshot.child("roomTypes").getChildren()) {
                            String room = roomSnapshot.getValue(String.class);
                            roomTypes.add(room);
                        }
                        Accommodation accommodation = new Accommodation(name, cID, cOD, accomName, numRooms, roomTypes);
                        destination.addAccommodation(accommodation);
                    }

                    // Getting all of destination's dining reservations
                    for (DataSnapshot diningSnapshot : travelSnapshot.child("destination").child("diningList").getChildren()) {
                        String date = diningSnapshot.child("date").getValue(String.class);
                        String diningName = diningSnapshot.child("restaurantName").getValue(String.class);
                        String time = diningSnapshot.child("time").getValue(String.class);
                        String url = diningSnapshot.child("url").getValue(String.class);
                        Dining dining = new Dining(name, url, diningName, date, time);
                        destination.addDining(dining);
                    }

                    TravelCommunityPost travelPost = new TravelCommunityPost(username, destination, travelType, notes);
                    postList.add(travelPost);
                    // Set up the RecyclerView with the adapter
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failure
            }
        });
    }

    private void loadDestinations() {
        groupDatabase.child(groupName).child("destinationList").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        destinationNames.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String destinationName = snapshot.child("name").getValue(String.class);
                            if (destinationName != null) {
                                destinationNames.add(destinationName);
                            }
                        }

                        if (destinationNames.isEmpty()) {
                            Toast.makeText(TravelCommunityActivity.this, "No Destinations Found", Toast.LENGTH_SHORT).show();
                        } else {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(TravelCommunityActivity.this, android.R.layout.simple_spinner_item, destinationNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            locationSpinner.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(TravelCommunityActivity.this,
                                "Failed to load destinations: "
                                        + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void toggleAddPostBox() {
        if (addPostBox.getVisibility() == View.GONE) {
            loadDestinations();
            addPostBox.setVisibility(View.VISIBLE);
            openAddPost.setText("-");
        } else {
            addPostBox.setVisibility(View.GONE);
            openAddPost.setText("+");
        }
    }

    private void addNewPost() {
        EditText travelInput = findViewById(R.id.travel_input);
        EditText noteInput = findViewById(R.id.notes_input);

        String travelType = travelInput.getText().toString();
        String notes = noteInput.getText().toString();
        String destinationName = locationSpinner.getSelectedItem().toString();

        if (!travelType.isEmpty() &&  !notes.isEmpty() && !destinationName.isEmpty()) {
            groupDatabase.child(groupName).child("destinationList").child(destinationName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                // Remaking the Destination Object out of its components
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String start = dataSnapshot.child("start").getValue(String.class);
                    String end = dataSnapshot.child("end").getValue(String.class);
                    long duration = dataSnapshot.child("duration").getValue(long.class);
                    Destination destination = new Destination(name, start, end, duration);

                    // Getting all of destination's accommodations
                    for (DataSnapshot accommodationsnapshot : dataSnapshot.child("accommodationList").getChildren()) {
                        String cID = accommodationsnapshot.child("checkinDate").getValue(String.class);
                        String cOD = accommodationsnapshot.child("checkoutDate").getValue(String.class);
                        String accomName = accommodationsnapshot.child("name").getValue(String.class);
                        int numRooms = accommodationsnapshot.child("numRooms").getValue(Integer.class);
                        ArrayList<String> roomTypes = new ArrayList<String>();
                        // Getting roomTypes
                        for (DataSnapshot roomSnapshot : accommodationsnapshot.child("roomTypes").getChildren()) {
                            String room = roomSnapshot.getValue(String.class);
                            roomTypes.add(room);
                        }
                        Accommodation accommodation = new Accommodation(name, cID, cOD, accomName, numRooms, roomTypes);
                        destination.addAccommodation(accommodation);
                    }

                    // Getting all of destination's dining reservations
                    for (DataSnapshot diningSnapshot : dataSnapshot.child("diningList").getChildren()) {
                        String date = diningSnapshot.child("date").getValue(String.class);
                        String diningName = diningSnapshot.child("restaurantName").getValue(String.class);
                        String time = diningSnapshot.child("time").getValue(String.class);
                        String url = diningSnapshot.child("url").getValue(String.class);
                        Dining dining = new Dining(name, url, diningName, date, time);
                        destination.addDining(dining);
                    }

                    TravelCommunityPost travelPost = new TravelCommunityPost(userName, destination, travelType, notes);
                    String postKey = userName + " - " + destination.getName();
                    travelDatabase.child(postKey).setValue(travelPost);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(TravelCommunityActivity.this,
                            "Failed to create Destination Object: "
                                    + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

