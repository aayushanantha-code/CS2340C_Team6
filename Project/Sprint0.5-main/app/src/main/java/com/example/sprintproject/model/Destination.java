package com.example.sprintproject.model;

public class Destination {
    private String name;
    private Date start;
    private Date end;
    private long duration;

    public Destination(String name, Date start, Date end, long duration) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.duration = duration;
    }
}
