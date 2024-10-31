package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;

public class Accommodation {
    private Destination destination;
    private String name;
    private String checkinDate;
    private String checkoutDate;
    private int numRooms;
    private List<String> roomTypes;

    /**
     * Constructor for Accommodation
     */
    public Accommodation() {
        this.destination = new Destination();
        this.name = "";
        this.checkinDate = "";
        this.checkoutDate = "";
        this.numRooms = 0;
        this.roomTypes = new ArrayList<>();
    }

    /**
     * Constructor for Accommodation
     * @param dest Destination of the accommodation
     * @param name Name of the accommodation
     * @param ciD Check-in date of the accommodation
     * @param coD Check-out date of the accommodation
     * @param numRooms Number of rooms
     * @param roomTypes Types of rooms
     */
    public Accommodation(Destination dest, String name, String ciD, String coD, int numRooms, List<String> roomTypes) {
        this.destination = dest;
        this.name = name;
        this.checkinDate = ciD;
        this.checkoutDate = checkoutDate;
        this.numRooms = numRooms;
        this.roomTypes = roomTypes;
    }

    /**
     * Getters for Destination
     * @return Destination of the accommodation
     */
    public Destination getDestination() {
        return destination;
    }

    /**
     * Setters for Destination
     * @param destination Destination of the accommodation
     */
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    /**
     * Getters for Name
     * @return Name of the accommodation
     */
    public String getName() {
        return name;
    }

    /**
     * Setters for Name
     * @param name Name of the accommodation
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getters for Check-in Date
     * @return Check-in date of the accommodation
     */
    public String getCheckinDate() {
        return checkinDate;
    }

    /**
     * Setters for Check-in Date
     * @param checkinDate Check-in date of the accommodation
     */
    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    /**
     * Getters for Check-out Date
     * @return Check-out date of the accommodation
     */
    public String getCheckoutDate() {
        return checkoutDate;
    }

    /**
     * Setters for Check-out Date
     * @param checkoutDate Check-out date of the accommodation
     */
    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    /**
     * Getters for Number of Rooms
     * @return Number of rooms
     */
    public int getNumRooms() {
        return numRooms;
    }

    /**
     * Setters for Number of Rooms
     * @param numRooms Number of rooms
     */
    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    /**
     * Getters for Room Types
     * @return Types of rooms
     */
    public List<String> getRoomTypes() {
        return roomTypes;
    }

    /**
     * Setters for Room Types
     * @param roomTypes Types of rooms
     */
    public void setRoomTypes(List<String> roomTypes) {
        this.roomTypes = roomTypes;
    }

    /**
     * Adds a room type
     * @param roomType Type of room
     */
    public void addRoomType(String roomType) {
        roomTypes.add(roomType);
    }

    /**
     * Removes a room type
     * @param roomType Type of room
     */
    public void removeRoomType(String roomType) {
        roomTypes.remove(roomType);
    }

    /**
     * Clears all room types
     */
    public void clearRoomTypes() {
        roomTypes.clear();
    }
}
