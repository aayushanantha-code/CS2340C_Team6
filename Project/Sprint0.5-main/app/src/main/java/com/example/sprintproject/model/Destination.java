package com.example.sprintproject.model;

import java.util.List;

public class Destination {
    private String name;
    private Date start;
    private Date end;
    private long duration;
    private List<User> users;

    public Destination(String name, Date start, Date end, long duration, User user) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.users = new ArrayList<>();
        this.users.add(user);
    }
}
