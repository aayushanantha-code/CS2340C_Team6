package com.example.sprintproject.views;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Dining;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DiningListAdapter extends ArrayAdapter<Dining> {
    private Context context;
    private List<Dining> diningList;

    public DiningListAdapter(Context context, List<Dining> diningList) {
        super(context, 0, diningList);
        this.context = context;
        this.diningList = diningList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dining_list_item, parent, false);
        }

        Dining dining = diningList.get(position);

        TextView locationTextView = convertView.findViewById(R.id.location_name);
        TextView restaurantNameTextView = convertView.findViewById(R.id.restaurant_name);
        TextView diningAddressTextView = convertView.findViewById(R.id.restaurant_address);
        TextView timeTextView = convertView.findViewById(R.id.time_display);

        locationTextView.setText(dining.getLocation());
        restaurantNameTextView.setText(dining.getRestaurantName());
        diningAddressTextView.setText(dining.getUrl());
        String formattedDateTime = formatDateTime(dining.getDate(), dining.getTime());
        timeTextView.setText(formattedDateTime);

        // Check if the reservation date and time have passed
        if (isReservationPassed(dining.getDate(), dining.getTime())) {
            applyStrikethrough(locationTextView);
            applyStrikethrough(restaurantNameTextView);
            applyStrikethrough(diningAddressTextView);
            applyStrikethrough(timeTextView);
            convertView.setBackgroundColor(context.getResources().getColor(R.color.passed_reservation_background));
        } else {
            removeStrikethrough(locationTextView);
            removeStrikethrough(restaurantNameTextView);
            removeStrikethrough(diningAddressTextView);
            removeStrikethrough(timeTextView);
            convertView.setBackgroundColor(context.getResources().getColor(R.color.dining_entry_background));
        }

        return convertView;
    }

    private String formatDateTime(String date, String time) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy, HH:mm");
        try {
            Date parsedDate = inputFormat.parse(date + " " + time);
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date + ", " + time;  // Fallback to original format in case of error
        }
    }

    private boolean isReservationPassed(String date, String time) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            Date reservationDate = format.parse(date + " " + time);
            return reservationDate.before(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void applyStrikethrough(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void removeStrikethrough(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }
}