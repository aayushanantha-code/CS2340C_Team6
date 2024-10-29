package com.example.sprintproject.model;

public class Dining {
    private String location;
    private String url;
    private String name;
    private int stars;
    private String date;
    private String time;

    public Dining() {
        this.location = null;
        this.url = null;
        this.name = null;
        this.stars = 0;
        this.date = null;
        this.time = null;
    }

    public Dining(String location, String url, String name, int stars, String date, String time) {
        this.location = location;
        this.url = url;
        this.name = name;
        this.stars = stars;
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
    public int getStars() {
        return stars;
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
    public void setStars(int stars) {
        this.stars = stars;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
