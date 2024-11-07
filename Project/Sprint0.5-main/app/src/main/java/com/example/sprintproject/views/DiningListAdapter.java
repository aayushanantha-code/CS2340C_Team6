package com.example.sprintproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Dining;

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
        locationTextView.setText(dining.getLocation());

        TextView restaurantNameTextView = convertView.findViewById(R.id.restaurant_name);
        restaurantNameTextView.setText(dining.getRestaurantName());

        // Add any other details you want to show for each dining establishment
        // For example:
        TextView diningAddressTextView = convertView.findViewById(R.id.restaurant_address);
        diningAddressTextView.setText(dining.getUrl());

        TextView timeTextView = convertView.findViewById(R.id.time_display);
        timeTextView.setText(dining.getTime());



        return convertView;
    }
}
