package com.example.sprintproject;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.TravelCommunityPost;

public class Sprint4Test {

    private TravelCommunityPost defaultPost;
    private TravelCommunityPost parameterizedPost;
    private Destination destination;

    @Before
    public void setUp() {
        // Initialize test data with correct Destination constructor
        destination = new Destination("Paris", "12/28/24", "12/31/24", 3);
        defaultPost = new TravelCommunityPost(); // Using default constructor
        parameterizedPost = new TravelCommunityPost("testUser", destination, "Adventure", "Excited to visit the Eiffel Tower!"
        ); // Using parameterized constructor
    }

    @Test
    public void testDefaultConstructor() {
        // Assert that all fields are null
        assertNull(defaultPost.getUser());
        assertNull(defaultPost.getDestination());
        assertNull(defaultPost.getTravelType());
        assertNull(defaultPost.getNotes());
    }

    @Test
    public void testParameterizedConstructor() {
        // Assert that all fields are correctly set
        assertEquals("testUser", parameterizedPost.getUser());
        assertEquals(destination, parameterizedPost.getDestination());
        assertEquals("Adventure", parameterizedPost.getTravelType());
        assertEquals("Excited to visit the Eiffel Tower!", parameterizedPost.getNotes());
    }

    @Test
    public void testGetUser() {
        assertEquals("testUser", parameterizedPost.getUser());
    }

    @Test
    public void testGetDestination() {
        assertEquals(destination, parameterizedPost.getDestination());
    }

    @Test
    public void testGetTravelType() {
        assertEquals("Adventure", parameterizedPost.getTravelType());
    }

    @Test
    public void testGetNotes() {
        assertEquals("Excited to visit the Eiffel Tower!", parameterizedPost.getNotes());
    }

    @Test
    public void testInequalityBetweenPosts() {
        TravelCommunityPost differentPost = new TravelCommunityPost(
                "otherUser", destination, "Relaxation", "Can't wait to relax!"
        );
        assertNotEquals(parameterizedPost, differentPost);
    }

    @Test
    public void testSetUser() {
        TravelCommunityPost post = new TravelCommunityPost();
        post.setUser("newUser");
        assertEquals("newUser", post.getUser());
    }

    @Test
    public void testSetDestination() {
        TravelCommunityPost post = new TravelCommunityPost();
        Destination newDestination = new Destination("New York", "01/01/25", "01/10/25", 2);
        post.setDestination(newDestination);
        assertEquals(newDestination, post.getDestination());
    }

    @Test
    public void testSetTravelType() {
        TravelCommunityPost post = new TravelCommunityPost();
        post.setTravelType("Business");
        assertEquals("Business", post.getTravelType());
    }

    @Test
    public void testSetNotes() {
        TravelCommunityPost post = new TravelCommunityPost();
        post.setNotes("Looking forward to the conference!");
        assertEquals("Looking forward to the conference!", post.getNotes());
    }

    @Test
    public void testSetAllFields() {
        TravelCommunityPost post = new TravelCommunityPost();

        // Setting all fields
        post.setUser("updatedUser");
        Destination updatedDestination = new Destination("Tokyo", "02/15/25", "02/20/25", 5);
        post.setDestination(updatedDestination);
        post.setTravelType("Leisure");
        post.setNotes("Excited to try sushi in Japan!");

        // Verifying all fields
        assertEquals("updatedUser", post.getUser());
        assertEquals(updatedDestination, post.getDestination());
        assertEquals("Leisure", post.getTravelType());
        assertEquals("Excited to try sushi in Japan!", post.getNotes());
    }

}



