package com.example.sprintproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

public class LogisticsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);

        Button logisticsButton = findViewById(R.id.icon_logistics);
        Button destinationsButton = findViewById(R.id.icon_destinations);
        Button diningButton = findViewById(R.id.icon_dining);
        Button accommodationsButton = findViewById(R.id.icon_accommodations);
        Button communityButton = findViewById(R.id.icon_travel_community);
        Button datePickerButton = findViewById(R.id.button_date_picker);

        logisticsButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, LogisticsActivity.class)));
        destinationsButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, DestinationsActivity.class)));
        diningButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, DiningEstablishmentsActivity.class)));
        accommodationsButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, AccommodationsActivity.class)));
        communityButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, TravelCommunityActivity.class)));
        datePickerButton.setOnClickListener(view -> showDatePickerDialog());

        drawRandomPieChart();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                LogisticsActivity.this,
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    public void drawRandomPieChart() {
        // Prepare random data for the pie chart
        List<PieEntry> entries = new ArrayList<>();
        Random random = new Random();

        // Generate random data for 5 slices
        for (int i = 0; i < 5; i++) {
            entries.add(new PieEntry(random.nextInt(100), "Slice " + (i + 1)));
        }

        // Create a PieDataSet from the data entries
        PieDataSet dataSet = new PieDataSet(entries, "Random Data");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS); // Set colors for the slices

        // Find the PieChart view by ID and set the data
        PieChart pieChart = findViewById(R.id.pieChart); // Match the new ID
        pieChart.setData(new PieData(dataSet)); // Bind data to the chart

        // Optional chart customization
        pieChart.getDescription().setEnabled(false); // Hide description
        pieChart.setUsePercentValues(true); // Display percentages

        // Customize legend
        pieChart.getLegend().setEnabled(true);

        // Redraw the chart
        pieChart.invalidate(); // Refresh the chart
    }


}