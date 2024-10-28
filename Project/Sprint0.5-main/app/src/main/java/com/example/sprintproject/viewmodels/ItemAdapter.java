package com.example.sprintproject.viewmodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Destination;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Destination> items;

    public ItemAdapter(List<Destination> items) {
        if (items != null) {
            this.items = items;
        } else {
            items = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Destination item = items.get(position);
        holder.itemText1.setText(item.getName());
        holder.itemText2.setText(String.valueOf(item.getDuration()));
    }

    //will figure out why its not working later
    @Override
    public int getItemCount() {
        return 0;
    }

    public void updateItems(List<Destination> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemText1;
        public TextView itemText2;

        public ViewHolder(View itemView) {
            super(itemView);
            itemText1 = itemView.findViewById(R.id.destination_list_name);
            itemText2 = itemView.findViewById(R.id.destination_list_duration);
        }
    }
}
