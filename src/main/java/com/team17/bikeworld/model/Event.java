package com.team17.bikeworld.model;

public class Event {
    private String name;
    private int status;

    public Event() {
    }

    public Event(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }


}
