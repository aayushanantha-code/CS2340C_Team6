package com.example.sprintproject.model;

import java.util.List;

public interface DiningObserver {
    /**
     * Method to be called when the dining list has changed
     * @param updatedDiningList The updated dining list
     */
    void onDiningListChanged(List<Dining> updatedDiningList);
}