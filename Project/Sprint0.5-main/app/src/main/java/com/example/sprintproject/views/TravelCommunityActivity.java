package com.example.sprintproject.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
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

    private Button openPostList;
    private Button addPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tie this activity to its layout
        getLayoutInflater().inflate(R.layout.activity_travel_community,
                (FrameLayout) findViewById(R.id.content_frame), true);

        // Gets the group Name from intents (Use this for all database stuff)
        String group = getIntent().getStringExtra("groupName");
        String username = getIntent().getStringExtra("username");
        openPostList = findViewById(R.id.button_view_recycler);
        addPost = findViewById(R.id.add_post);

        recyclerView = findViewById(R.id.recyclerView);
        travelDatabase = TravelCommunityDatabase.getInstance().getDatabaseReference();

        //Testing right here
        Dining testDining = new Dining("testDest", "testDining.com", "testDining", "1/1/01", "time");
        Accommodation testAccom = new Accommodation("testDest", "testAccom", "1/1/01", "1/1/02", 1, new ArrayList<>());
        testAccom.addRoomType("Single");
        Destination testDest = new Destination("testDest", "1/1/01", "1/1/02", 1);
        testDest.addAccommodation(testAccom);
        testDest.addDining(testDining);
        TravelCommunityPost testPost = new TravelCommunityPost("TestUser1", testDest);
        String postKey = "TestUser1 - " + testDest.getName();
        travelDatabase.child(postKey).setValue(testPost);

        // Populate the postList
        postList = new ArrayList<>();
        fillPostList();

        // Opens the Post list Visually
        openPostList.setOnClickListener(v -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            postAdapter = new TravelCommunityAdapter(this, postList, this);
            recyclerView.setAdapter(postAdapter);
        });

        // Opens add Post box
        addPost.setOnClickListener(v -> {
            toggleAddPostBox();
        });
    }

    @Override
    public void onPostClick(TravelCommunityPost post) {
        // Will eventually update this to show a breakdown of the travel post
        Toast.makeText(this, "Clicked on: " + post.getUser(), Toast.LENGTH_SHORT).show();
    }

    public void fillPostList() {
        travelDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Pulling every Single Travel Post
                for (DataSnapshot travelSnapshot : dataSnapshot.getChildren()) {
                    // Getting the Destination values and the Username
                    String username = travelSnapshot.child("user").getValue(String.class);
                    String name = travelSnapshot.child("destination").child("name").getValue(String.class);
                    String start = travelSnapshot.child("destination").child("start").getValue(String.class);
                    String end = travelSnapshot.child("destination").child("end").getValue(String.class);
                    long duration = travelSnapshot.child("destination").child("duration").getValue(long.class);
                    Destination destination = new Destination(name, start, end, duration);

                    // Getting all of destination's accomodations
                    for (DataSnapshot accomodationSnapshot : travelSnapshot.child("destination").child("accomodationList").getChildren()) {
                        String cID = accomodationSnapshot.child("checkinDate").getValue(String.class);
                        String cOD = accomodationSnapshot.child("checkoutDate").getValue(String.class);
                        String accomName = accomodationSnapshot.child("name").getValue(String.class);
                        int numRooms = accomodationSnapshot.child("numRooms").getValue(Integer.class);
                        ArrayList<String> roomTypes = new ArrayList<String>();
                        // Getting roomTypes
                        for (DataSnapshot roomSnapshot : accomodationSnapshot.child("roomTypes").getChildren()) {
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

                    TravelCommunityPost travelPost = new TravelCommunityPost(username, destination);
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


    // Will toggle the Adding Post box once that is implemented
    public void toggleAddPostBox() {

    }
}
