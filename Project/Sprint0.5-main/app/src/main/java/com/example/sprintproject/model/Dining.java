package com.example.sprintproject.model;

public class Dining {
    private String location;
    private String url;
    private String name;
    private String date;
    private String time;

    /**
     * Constructor for Dining
     */
    public Dining() {
        this(null, null, null, null, null);
    }

    /**
     * Constructor for Dining
     * @param location Location of the restaurant
     * @param url URL of the restaurant
     * @param name Name of the restaurant
     * @param date Date of the reservation
     * @param time Time of the reservation
     */
    public Dining(String location, String url, String name, String date, String time) {
        this.location = location;
        this.url = url;
        this.name = name;
        this.date = date;
        this.time = time;

    }

    /**
     * Getters for Location
     * @return Location of the restaurant
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getters for URL
     * @return URL of the restaurant
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getters for Name
     * @return Name of the restaurant
     */
    public String getRestaurantName() {
        return name;
    }

    /**
     * Getters for Date
     * @return Date of the reservation
     */
    public String getDate() {
        return date;
    }

    /**
     * Getters for Time
     * @return Time of the reservation
     */
    public String getTime() {
        return time;
    }



    /**
     * Setters for Location
     * @param location Location of the restaurant
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Setters for URL
     * @param url URL of the restaurant
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Setters for Name
     * @param name Name of the restaurant
     */
    public void setRestaurantName(String name) {
        this.name = name;
    }

    /**
     * Setters for Date
     * @param date Date of the reservation
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Setters for Time
     * @param time Time of the reservation
     */
    public void setTime(String time) {
        this.time = time;
    }


}
