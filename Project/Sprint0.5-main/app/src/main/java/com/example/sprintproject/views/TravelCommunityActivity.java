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

public class TravelCommunityActivity extends
        BottomNavigationActivity implements TravelCommunityAdapter.PostClickListener {
    private RecyclerView recyclerView;
    private List<TravelCommunityPost> postList;
    private TravelCommunityAdapter postAdapter;
    private DatabaseReference travelDatabase;
    private DatabaseReference groupDatabase;

    // Post list section for groups and spinner
    private Button openPostList;
    private Button openAddPost;
    private Button post;
    private FrameLayout popupBox;
    private TextView transportation;
    private TextView duration;
    private TextView startEndDate;
    private TextView accommodationField;
    private TextView userNameAndGroup;
    private TextView diningReservationField;
    private TextView notes;
    private Button closeButton;
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

        popupBox = findViewById(R.id.frameLayout);
        userNameAndGroup = findViewById(R.id.userName);
        duration = findViewById(R.id.duration);
        startEndDate = findViewById(R.id.startEndDate);
        accommodationField = findViewById(R.id.accommodation);
        diningReservationField = findViewById(R.id.diningReservations);
        transportation = findViewById(R.id.transportation);
        notes = findViewById(R.id.notes);
        closeButton = findViewById(R.id.closeButton);

        // post ID here for clarity
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

        closeButton.setOnClickListener(v -> {
            togglePopupBox();
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
        togglePopupBox();
        Destination destination = post.getDestination();
        userNameAndGroup.setText(post.getUser() + "'s trip to " + destination.getName());
        duration.setText(post.getUser() + " will be staying for "
                + destination.getDuration() + " days");
        startEndDate.setText(destination.getStart() + " to " + destination.getEnd());
        if (destination.getAccommodationList().size() != 0) {
            accommodationField.setText(post.getUser() + " will be staying at "
                    + destination.getAccommodationList().get(0).getName());
        } else {
            accommodationField.setText("");
        }
        if (destination.getDiningList().size() != 0) {
            diningReservationField.setText(post.getUser() + " reserved reservation at "
                    + destination.getDiningList().get(0).getRestaurantName());
        } else {
            diningReservationField.setText("");
        }

        transportation.setText("Travelling via: " + post.getTravelType());
        notes.setText("'" + post.getNotes() + "'");
    }

    /**
     * Toggles the visibility of the popup box
     */
    public void togglePopupBox() {
        if (popupBox.getVisibility() == View.GONE) {
            addPostBox.setVisibility(View.GONE);
            popupBox.setVisibility(View.VISIBLE);
        } else {
            popupBox.setVisibility(View.GONE);
        }
    }

    /**
     * Opens the post list
     */
    private void openPostList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new TravelCommunityAdapter(this, postList, this);
        recyclerView.setAdapter(postAdapter);
    }

    /**
     * Fills the post list with data from the database
     */
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

                    String name = travelSnapshot.child("destination")
                            .child("name").getValue(String.class);
                    String start = travelSnapshot.child("destination")
                            .child("start").getValue(String.class);
                    String end = travelSnapshot.child("destination")
                            .child("end").getValue(String.class);
                    long duration = travelSnapshot.child("destination").child("duration")
                            .getValue(long.class);
                    Destination destination = new Destination(name, start, end, duration);

                    // Getting all of destination's accommodations
                    for (DataSnapshot accommodationsnapshot : travelSnapshot.child(
                            "destination").child("accomodationList").getChildren()) {
                        String cID = accommodationsnapshot.child("checkinDate")
                                .getValue(String.class);
                        String cOD = accommodationsnapshot.child("checkoutDate")
                                .getValue(String.class);
                        String accomName = accommodationsnapshot.child("name")
                                .getValue(String.class);
                        int numRooms = accommodationsnapshot.child("numRooms")
                                .getValue(Integer.class);
                        ArrayList<String> roomTypes = new ArrayList<String>();
                        // Getting roomTypes
                        for (DataSnapshot roomSnapshot : accommodationsnapshot.child(
                                "roomTypes").getChildren()) {
                            String room = roomSnapshot.getValue(String.class);
                            roomTypes.add(room);
                        }
                        Accommodation accommodation = new Accommodation(name, cID, cOD,
                                accomName, numRooms, roomTypes);
                        destination.addAccommodation(accommodation);
                    }

                    // Getting all of destination's dining reservations
                    for (DataSnapshot diningSnapshot : travelSnapshot.child("destination")
                            .child("diningList").getChildren()) {
                        String date = diningSnapshot.child("date").getValue(String.class);
                        String diningName = diningSnapshot.child("restaurantName")
                                .getValue(String.class);
                        String time = diningSnapshot.child("time").getValue(String.class);
                        String url = diningSnapshot.child("url").getValue(String.class);
                        Dining dining = new Dining(name, url, diningName, date, time);
                        destination.addDining(dining);
                    }

                    TravelCommunityPost travelPost = new TravelCommunityPost(username,
                            destination, travelType, notes);
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

    /**
     * Loads the destinations into the spinner
     */
    private void loadDestinations() {
        groupDatabase.child(groupName).child("destinationList")
                .addListenerForSingleValueEvent(new ValueEventListener() {
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
                            Toast.makeText(TravelCommunityActivity.this,
                                    "No Destinations Found", Toast.LENGTH_SHORT).show();
                        } else {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                    TravelCommunityActivity.this,
                                    android.R.layout.simple_spinner_item, destinationNames);
                            adapter.setDropDownViewResource(
                                    android.R.layout.simple_spinner_dropdown_item);
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

    /**
     * Toggles the visibility of the add post box
     */
    public void toggleAddPostBox() {
        if (addPostBox.getVisibility() == View.GONE) {
            loadDestinations();
            popupBox.setVisibility(View.GONE);
            addPostBox.setVisibility(View.VISIBLE);
            openAddPost.setText("-");
        } else {
            addPostBox.setVisibility(View.GONE);
            openAddPost.setText("+");
        }
    }

    /**
     * Adds a new post to the database
     */
    private void addNewPost() {
        EditText travelInput = findViewById(R.id.travel_input);
        EditText noteInput = findViewById(R.id.notes_input);

        String travelType = travelInput.getText().toString();
        String notes = noteInput.getText().toString();
        String destinationName = locationSpinner.getSelectedItem().toString();

        if (!travelType.isEmpty() &&  !notes.isEmpty() && !destinationName.isEmpty()) {
            groupDatabase.child(groupName).child("destinationList").
                    child(destinationName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        // Remaking the Destination Object out of its components
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("name").getValue(String.class);
                            String start = dataSnapshot.child("start").getValue(String.class);
                            String end = dataSnapshot.child("end").getValue(String.class);
                            long duration = dataSnapshot.child("duration").getValue(long.class);
                            Destination destination = new Destination(name, start, end, duration);

                            // Getting all of destination's accommodations
                            for (DataSnapshot accommodationsnapshot : dataSnapshot.
                                    child("accommodationList").getChildren()) {
                                String cID = accommodationsnapshot.child("checkinDate")
                                        .getValue(String.class);
                                String cOD = accommodationsnapshot.child("checkoutDate")
                                        .getValue(String.class);
                                String accomName = accommodationsnapshot.child("name")
                                        .getValue(String.class);
                                int numRooms = accommodationsnapshot.child("numRooms")
                                        .getValue(Integer.class);
                                ArrayList<String> roomTypes = new ArrayList<String>();
                                // Getting roomTypes
                                for (DataSnapshot roomSnapshot : accommodationsnapshot
                                        .child("roomTypes").getChildren()) {
                                    String room = roomSnapshot.getValue(String.class);
                                    roomTypes.add(room);
                                }
                                Accommodation accommodation = new Accommodation(name,
                                        cID, cOD, accomName, numRooms, roomTypes);
                                destination.addAccommodation(accommodation);
                            }

                            // Getting all of destination's dining reservations
                            for (DataSnapshot diningSnapshot : dataSnapshot
                                    .child("diningList").getChildren()) {
                                String date = diningSnapshot.child("date")
                                        .getValue(String.class);
                                String diningName = diningSnapshot.child("restaurantName")
                                        .getValue(String.class);
                                String time = diningSnapshot.child("time")
                                        .getValue(String.class);
                                String url = diningSnapshot.child("url")
                                        .getValue(String.class);
                                Dining dining = new Dining(name, url, diningName, date, time);
                                destination.addDining(dining);
                            }

                            TravelCommunityPost travelPost = new TravelCommunityPost(userName,
                                    destination, travelType, notes);
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

