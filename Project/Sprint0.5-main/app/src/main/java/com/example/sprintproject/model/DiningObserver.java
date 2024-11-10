package com.example.sprintproject.model;

import java.util.List;

public interface DiningObserver {
    void onDiningListChanged(List<Dining> updatedDiningList);
}