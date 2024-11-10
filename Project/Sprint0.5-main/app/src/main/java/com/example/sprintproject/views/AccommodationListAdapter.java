package com.example.sprintproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Accommodation;

import java.util.List;

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
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.accommodation_list_item, parent, false);
        }

        Accommodation accommodation = accommodationList.get(position);

        // Set destination (location of accommodation)
        TextView locationTextView = convertView.findViewById(R.id.location_name);
        // Assuming Destination class has a getLocation() method
        locationTextView.setText(accommodation.getDestination());

        // Set accommodation name
        TextView accommodationNameTextView = convertView.findViewById(R.id.accommodation_name);
        accommodationNameTextView.setText(accommodation.getName());

        // Set check-in date
        TextView checkinDateTextView = convertView.findViewById(R.id.checkin_date);
        checkinDateTextView.setText(accommodation.getCheckinDate());

        // Set check-out date
        TextView checkoutDateTextView = convertView.findViewById(R.id.checkout_date);
        checkoutDateTextView.setText(accommodation.getCheckoutDate());

        // Set number of rooms
        TextView numRoomsTextView = convertView.findViewById(R.id.num_rooms);
        numRoomsTextView.setText(String.valueOf(accommodation.getNumRooms()));

        // Optionally, you could display the room types
        TextView roomTypesTextView = convertView.findViewById(R.id.room_types);
        roomTypesTextView.setText(accommodation.getRoomTypes().toString());

        return convertView;
    }
}
