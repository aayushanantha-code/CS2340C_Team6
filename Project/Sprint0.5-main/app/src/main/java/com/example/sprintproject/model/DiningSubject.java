package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;

public class DiningSubject {
    private List<DiningObserver> observers = new ArrayList<>();
    private List<Dining> diningList = new ArrayList<>();

    public void addObserver(DiningObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(DiningObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (DiningObserver observer : observers) {
            observer.onDiningListChanged(diningList);
        }
    }

    public void addDining(Dining dining) {
        diningList.add(dining);
        notifyObservers();
    }

    public void removeDining(Dining dining) {
        diningList.remove(dining);
        notifyObservers();
    }

    public List<Dining> getDiningList() {
        return diningList;
    }
}