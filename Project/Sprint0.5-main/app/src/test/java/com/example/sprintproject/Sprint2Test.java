package com.example.sprintproject.views;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.view.View;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.User;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE) // Specify the SDK version and disable manifest lookup
public class Sprint2Test {
    private DestinationsActivity destinationsActivity;

    @Before
    public void setUp() {
        destinationsActivity = new DestinationsActivity();
    }

    @Test
    public void testCalculateDurationSingleInvalidStartDate() {
        long duration = destinationsActivity.calculateDuration("invalid", "01/01/2023");
        assertEquals(0, duration);
    }

    @Test
    public void testCalculateDurationSingleInvalidEndDate() {
        long duration = destinationsActivity.calculateDuration("01/01/2023", "invalid");
        assertEquals(0, duration);
    }

    @Test
    public void testCalculateEndDateInvalidDuration() {
        String endDate = destinationsActivity.calculateEndDate("25", "invalid");
        assertEquals("", endDate);
    }

    @Test
    public void testCalculateStartDateInvalidDuration() {
        String startDate = destinationsActivity.calculateStartDate("10", "invalid");
        assertEquals("", startDate);
    }



//    @Test
//    public void testToggleCalculatorBoxWhenGone() {
//        View mockView = mock(View.class);
//        // Simulate the view being GONE
//        when(mockView.getVisibility()).thenReturn(View.GONE);
//        // Call the method
//        destinationsActivity.toggleCalculatorBox(mockView);
//        // Verify that the visibility is set to VISIBLE
//        verify(mockView).setVisibility(View.VISIBLE);
//     }

    /*    @Test
        public void testToggleCalculatorBoxWhenVisible() {
            View mockView = mock(View.class);
            // Simulate the view being VISIBLE
            when(mockView.getVisibility()).thenReturn(View.VISIBLE);
            // Call the method
            destinationsActivity.toggleCalculatorBox(mockView);
            // Verify that the visibility is set to GONE
            verify(mockView).setVisibility(View.GONE);
        }*/

    @Test
    public void testCalculateDurationSingleInvalidDuration() {
        long duration = destinationsActivity.calculateDuration("invalid", "01/21/2023");
        assertEquals(0, duration);
    }

    @Test
    public void testCalculateDurationInvalidDateFormat() {
        long duration = destinationsActivity.calculateDuration("2023-01-01", "2023-01-10");
        assertEquals(0, duration);
    }

    @Test
    public void testGetStartDateValidDate() throws ParseException {
        destinationsActivity.setStartDateStore("01/01/2023");
        Date startDate = destinationsActivity.getStartDate();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(format.parse("01/01/2023"), startDate);
    }

    @Test
    public void testGetEndDateValidDate() throws ParseException {
        destinationsActivity.setEndDateStore("10/01/2023");
        Date endDate = destinationsActivity.getEndDate();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(format.parse("10/01/2023"), endDate);
    }

    @Test
    public void testGetStartDateInvalidDate() {
        destinationsActivity.setStartDateStore("invalid");
        Date startDate = destinationsActivity.getStartDate();
        assertNotNull(startDate);
    }
}