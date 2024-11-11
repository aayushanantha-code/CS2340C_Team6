package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;

public class DiningSubject {
    private List<DiningObserver> observers = new ArrayList<>();
    private List<Dining> diningList = new ArrayList<>();

    /**
     * Adds an observer to the list of observers
     * @param observer The observer to add
     */
    public void addObserver(DiningObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of observers
     * @param observer The observer to remove
     */
    public void removeObserver(DiningObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers that the dining list has changed
     */
    public void notifyObservers() {
        for (DiningObserver observer : observers) {
            observer.onDiningListChanged(diningList);
        }
    }

    /**
     * Adds a dining reservation to the list of dining reservations
     * @param dining The dining reservation to add
     */
    public void addDining(Dining dining) {
        diningList.add(dining);
        notifyObservers();
    }

    /**
     * Removes a dining reservation from the list of dining reservations
     * @param dining The dining reservation to remove
     */
    public void removeDining(Dining dining) {
        diningList.remove(dining);
        notifyObservers();
    }

    /**
     * Gets the list of dining reservations
     * @return The list of dining reservations
     */
    public List<Dining> getDiningList() {
        return diningList;
    }
}