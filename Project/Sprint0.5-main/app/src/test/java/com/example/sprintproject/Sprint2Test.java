package com.example.sprintproject.views;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

import android.view.View;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.User;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE) // Specify the SDK version and disable manifest lookup
public class Sprint2Test {
    private DestinationsActivity destinationsActivity;

    @Before
    public void setUp() {
        destinationsActivity = new DestinationsActivity();
    }

    @Test
    public void testCalculateDuration_singleInvalidStartDate() {
        long duration = destinationsActivity.calculateDuration("invalid", "01/01/2023");
        assertEquals(0, duration);
    }

    @Test
    public void testCalculateDuration_singleInvalidEndDate() {
        long duration = destinationsActivity.calculateDuration("01/01/2023", "invalid");
        assertEquals(0, duration);
    }

    @Test
    public void testCalculateEndDate_invalidDuration() {
        String endDate = destinationsActivity.calculateEndDate("25", "invalid");
        assertEquals("", endDate);
    }

    @Test
    public void testCalculateStartDate_invalidDuration() {
        String startDate = destinationsActivity.calculateStartDate("10", "invalid");
        assertEquals("", startDate);
    }

    @Test
    public void testAddingUserToDestination() {
        // Given an initial empty Destination
        Destination testDestination = new Destination();
        User testUser = new User("testUsername", "testPassword");

        // Add User to Destination
        testDestination.addUserID(testUser.getUserID());

        assertEquals(1, testDestination.getUserIDs().size());
        assertEquals("testUsername", testDestination.getUserIDs().get(0));
    }

    @Test
    public void testToggleCalculatorBoxWhenGone() {
        View mockView = mock(View.class);
        // Simulate the view being GONE
        when(mockView.getVisibility()).thenReturn(View.GONE);
        // Call the method
        destinationsActivity.toggleCalculatorBox(mockView);
        // Verify that the visibility is set to VISIBLE
        verify(mockView).setVisibility(View.VISIBLE);
    }

    @Test
    public void testToggleCalculatorBoxWhenVisible() {
        View mockView = mock(View.class);
        // Simulate the view being VISIBLE
        when(mockView.getVisibility()).thenReturn(View.VISIBLE);
        // Call the method
        destinationsActivity.toggleCalculatorBox(mockView);
        // Verify that the visibility is set to GONE
        verify(mockView).setVisibility(View.GONE);
    }

    @Test
    public void testCalculateDuration_singleInvalidDuration() {
        long duration = destinationsActivity.calculateDuration("invalid", "01/21/2023");
        assertEquals(0, duration);
    }

    @Test
    public void testCalculateDuration_invalidDateFormat() {
        long duration = destinationsActivity.calculateDuration("2023-01-01", "2023-01-10");
        assertEquals(0, duration);
    }

    @Test
    public void testGetStartDate_validDate() throws ParseException {
        destinationsActivity.setStartDateStore("01/01/2023");
        Date startDate = destinationsActivity.getStartDate();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(format.parse("01/01/2023"), startDate);
    }

    @Test
    public void testGetEndDate_validDate() throws ParseException {
        destinationsActivity.setEndDateStore("10/01/2023");
        Date endDate = destinationsActivity.getEndDate();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(format.parse("10/01/2023"), endDate);
    }

    @Test
    public void testGetStartDate_invalidDate() {
        destinationsActivity.setStartDateStore("invalid");
        Date startDate = destinationsActivity.getStartDate();
        assertNotNull(startDate);
    }
}