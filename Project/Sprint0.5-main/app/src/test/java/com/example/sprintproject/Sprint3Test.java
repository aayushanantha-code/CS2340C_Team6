package com.example.sprintproject;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.Dining;

import java.util.Arrays;
import java.util.List;

public class Sprint3Test {
    private Dining dining;
    private Accommodation accommodation;

    @Before
    public void setUp() {
        // Initialize the Dining object with test data
        dining = new Dining("TestLocation", "http://example.com", "Test Restaurant", "12/12/2024", "18:30");
        List<String> roomTypes = Arrays.asList("Single", "Double");
        accommodation = new Accommodation("TestDestination", "TestName", "12/11/2024", "12/15/2024", 2, roomTypes);
    }

    //Dining Test
    @Test
    public void diningTestGetRestaurantName() {
        assertEquals("Test Restaurant", dining.getRestaurantName());
    }

    @Test
    public void diningTestGetLocation() {
        assertEquals("TestLocation", dining.getLocation());
    }

    @Test
    public void diningTestGetDate() {
        assertEquals("12/12/2024", dining.getDate());
    }

    @Test
    public void diningTestGetTime() {
        assertEquals("18:30", dining.getTime());
    }

    @Test
    public void diningTestGetUrl() {
        assertEquals("http://example.com", dining.getUrl());
    }

    // Accommodation Tests
    @Test
    public void accommodationTestGetDestination() {
        assertEquals("TestDestination", accommodation.getDestination());
    }

    @Test
    public void accommodationTestGetName() {
        assertEquals("TestName", accommodation.getName());
    }

    @Test
    public void accommodationTestGetCheckinDate() {
        assertEquals("12/11/2024", accommodation.getCheckinDate());
    }

    @Test
    public void accommodationTestGetCheckoutDate() {
        assertEquals("12/15/2024", accommodation.getCheckoutDate());
    }

    @Test
    public void accommodationTestGetNumRooms() {
        assertEquals(2, accommodation.getNumRooms());
    }

    @Test
    public void accommodationTestGetRoomTypes() {
        List<String> expectedRoomTypes = Arrays.asList("Single", "Double");
        assertEquals(expectedRoomTypes, accommodation.getRoomTypes());
    }

    @Test
    public void accommodationTestSetRoomTypes() {
        List<String> newRoomTypes = Arrays.asList("Suite", "Penthouse", "Single");
        accommodation.setRoomTypes(newRoomTypes);
        assertEquals(newRoomTypes, accommodation.getRoomTypes());
    }

}

