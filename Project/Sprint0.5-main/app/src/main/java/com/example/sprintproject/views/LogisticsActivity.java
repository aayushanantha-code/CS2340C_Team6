package com.example.sprintproject.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
//import android.widget.PieChart;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LogisticsActivity extends AppCompatActivity {

    private boolean isGraphVisible = false; // Track graph visibility
    private List<String> notes = new ArrayList<>(); // List for collaborative notes
    private ArrayAdapter<String> notesAdapter;

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
        Button graphButton = findViewById(R.id.button_graph);
        Button inviteButton = findViewById(R.id.button_invite);
        Button addNoteButton = findViewById(R.id.button_add_note);

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setVisibility(View.GONE); // Initially hide the chart

        // Set up button listeners
        logisticsButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, LogisticsActivity.class)));
        destinationsButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, DestinationsActivity.class)));
        diningButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, DiningEstablishmentsActivity.class)));
        accommodationsButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, AccommodationsActivity.class)));
        communityButton.setOnClickListener(view -> startActivity(new Intent(LogisticsActivity.this, TravelCommunityActivity.class)));
        datePickerButton.setOnClickListener(view -> showDatePickerDialog());

        // Toggle graph visibility when button is clicked
        graphButton.setOnClickListener(view -> {
            if (isGraphVisible) {
                // If the graph is visible, hide it
                pieChart.setVisibility(View.GONE);
            } else {
                // Draw and show the chart with actual data for allotted vs planned days
                drawPieChart(5, 10); // Replace with real data values for allotted and planned days
                pieChart.setVisibility(View.VISIBLE);
            }
            isGraphVisible = !isGraphVisible;
        });

        // Set up invite button to send invitation
        inviteButton.setOnClickListener(view -> {
            Intent inviteIntent = new Intent(Intent.ACTION_SEND);
            inviteIntent.setType("text/plain");
            inviteIntent.putExtra(Intent.EXTRA_TEXT, "Join us for an exciting trip planning experience!");
            startActivity(Intent.createChooser(inviteIntent, "Invite via"));
        });

        // Set up notes ListView and adapter
        ListView notesListView = findViewById(R.id.notesListView);
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        notesListView.setAdapter(notesAdapter);

        // Set up add note button
        addNoteButton.setOnClickListener(view -> showAddNoteDialog());
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
                    // Optionally do something with the selected date
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    // Method to draw a pie chart representing allotted vs planned trip days
    public void drawPieChart(int allottedDays, int plannedDays) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(allottedDays, "Allotted Days"));
        entries.add(new PieEntry(plannedDays - allottedDays, "Remaining Days"));

        PieDataSet dataSet = new PieDataSet(entries, "Trip Days");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setData(new PieData(dataSet));
        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.invalidate();
    }

    // Method to display a dialog for adding a new note
    private void showAddNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Note");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String note = input.getText().toString();
            if (!note.isEmpty()) {
                notes.add(note);
                notesAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
