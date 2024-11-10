package com.example.sprintproject.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Accommodation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AccommodationListAdapter extends ArrayAdapter<Accommodation> {

    private Context context;
    private List<Accommodation> accommodationList;

    public AccommodationListAdapter(Context context, List<Accommodation> accommodationList) {
        super(context, 0, accommodationList);
        this.context = context;
        this.accommodationList = accommodationList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.accommodation_list_item,
                    parent, false);
        }

        // Get the current accommodation object
        Accommodation accommodation = accommodationList.get(position);

        // Set the accommodation details (destination, name, etc.)
        TextView locationTextView = convertView.findViewById(R.id.location_name);
        locationTextView.setText(accommodation.getDestination());

        TextView accommodationNameTextView = convertView.findViewById(R.id.accommodation_name);
        accommodationNameTextView.setText(accommodation.getName());

        TextView checkinDateTextView = convertView.findViewById(R.id.checkin_date);
        checkinDateTextView.setText(accommodation.getCheckinDate());

        TextView checkoutDateTextView = convertView.findViewById(R.id.checkout_date);
        checkoutDateTextView.setText(accommodation.getCheckoutDate());

        TextView numRoomsTextView = convertView.findViewById(R.id.num_rooms);
        numRoomsTextView.setText(String.valueOf(accommodation.getNumRooms()));

        TextView roomTypesTextView = convertView.findViewById(R.id.room_types);
        roomTypesTextView.setText(accommodation.getRoomTypes().toString());

        // Check if the accommodation has expired
        boolean isExpired = isAccommodationExpired(accommodation);

        // Apply strikethrough and background color change for expired items
        if (isExpired) {
            applyStrikethrough(locationTextView);
            applyStrikethrough(accommodationNameTextView);
            applyStrikethrough(checkinDateTextView);
            applyStrikethrough(checkoutDateTextView);
            // Light red background for expired items
            convertView.setBackgroundColor(Color.parseColor("#FFE0E0"));
        } else {
            removeStrikethrough(locationTextView);
            removeStrikethrough(accommodationNameTextView);
            removeStrikethrough(checkinDateTextView);
            removeStrikethrough(checkoutDateTextView);
            convertView.setBackgroundColor(Color.WHITE); // Default white background
        }

        return convertView;
    }

    // Method to check if the accommodation has expired using the checkout date
    private boolean isAccommodationExpired(Accommodation accommodation) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            // Retrieve checkout date from the accommodation object
            String checkoutDateStr = accommodation.getCheckoutDate();
            Date checkoutDate = dateFormat.parse(checkoutDateStr);
            return checkoutDate != null && checkoutDate.before(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to apply strikethrough effect on a TextView
    private void applyStrikethrough(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        textView.setTextColor(Color.RED); // Change text color to gray for expired items
    }

    // Method to remove strikethrough effect on a TextView
    private void removeStrikethrough(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        textView.setTextColor(Color.BLACK); // Default black color for active items
    }
}
