package com.example.sprintproject.views;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Sprint1Test {
    private DestinationsActivity destinationsActivity;

    @Before
    public void setUp() {
        destinationsActivity = new DestinationsActivity();
    }

    @Test
    public void testCalculateDuration_validDates() {
        long duration = destinationsActivity.calculateDuration("01/01/2023", "10/01/2023");
        assertEquals(9, duration);
    }

    @Test
    public void testCalculateDuration_sameDates() {
        long duration = destinationsActivity.calculateDuration("01/01/2023", "01/01/2023");
        assertEquals(0, duration);
    }

    @Test
    public void testCalculateDuration_invalidDates() {
        long duration = destinationsActivity.calculateDuration("invalid", "invalid");
        assertEquals(0, duration);
    }

    @Test
    public void testCalculateEndDate_validInput() {
        String endDate = destinationsActivity.calculateEndDate("01/01/2023", "10");
        assertEquals("11/01/2023", endDate);
    }

    @Test
    public void testCalculateEndDate_invalidDate() {
        String endDate = destinationsActivity.calculateEndDate("invalid", "10");
        assertEquals("", endDate);
    }

    @Test
    public void testCalculateEndDate_invalidDuration() {
        String endDate = destinationsActivity.calculateEndDate("01/01/2023", "invalid");
        assertEquals("", endDate);
    }

    @Test
    public void testCalculateStartDate_validInput() {
        String startDate = destinationsActivity.calculateStartDate("11/01/2023", "10");
        assertEquals("01/01/2023", startDate);
    }

    @Test
    public void testCalculateStartDate_invalidDate() {
        String startDate = destinationsActivity.calculateStartDate("invalid", "10");
        assertEquals("", startDate);
    }

    @Test
    public void testCalculateStartDate_invalidDuration() {
        String startDate = destinationsActivity.calculateStartDate("11/01/2023", "invalid");
        assertEquals("", startDate);
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