package com.example.sprintproject.model;

public class Dining {
    private String location;
    private String url;
    private String name;
    private String date;
    private String time;

    public Dining() {
        this.location = null;
        this.url = null;
        this.name = null;
        this.date = null;
        this.time = null;
    }

    public Dining(String location, String url, String name, int stars, String date, String time) {
        this.location = location;
        this.url = url;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getLocation() {
        return location;
    }
    public String getUrl() {
        return url;
    }
    public String getRestaurantName() {
        return name;
    }

    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setRestaurantName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
