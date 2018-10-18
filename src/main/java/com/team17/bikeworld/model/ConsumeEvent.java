package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;

public class ConsumeEvent {
    private int id;
    @Expose
    private String title;
    @Expose
    private String location;
    @Expose
    private String address;
    @Expose
    private String startDate;
    @Expose
    private String endDate;
    @Expose
    private String description;
    @Expose
    private int status;
    @Expose
    private String imageUrl;

    public ConsumeEvent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ConsumeEvent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", imageUrl=" + imageUrl +
                '}';
    }
}
