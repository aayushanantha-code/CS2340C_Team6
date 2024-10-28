package com.example.sprintproject;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.example.sprintproject.views.DestinationsActivity;

import org.junit.Before;
import org.junit.Test;

public class MyStackTest {
    private DestinationsActivity destinationsActivity;
    @Before
    public void setupDestinationActivity() {
        destinationsActivity = new DestinationsActivity();
    }

    @Test
    public void testDuration() {
        String startDate = "01/10/2024";
        String endDate = "09/10/2024";
        String duration = "8";

        long calculatedDuration = destinationsActivity.calculateDuration(startDate, endDate);
        assertEquals(8, calculatedDuration);

    }

    @Test
    public void testStartDateCalculation() {
        String endDate = "09/10/2024";
        String duration = "8";
        String startDate = "01/10/2024";

        String calculatedStartDate = destinationsActivity.calculateStartDate(endDate,duration);
        assertEquals(startDate, calculatedStartDate);
    }

    @Test
    public void testEndDateCalculation() {
        String endDate = "09/10/2024";
        String duration = "8";
        String startDate = "01/10/2024";

        String calculatedEndDate = destinationsActivity.calculateEndDate(startDate, duration);
        assertEquals(endDate, calculatedEndDate);
    }


}
