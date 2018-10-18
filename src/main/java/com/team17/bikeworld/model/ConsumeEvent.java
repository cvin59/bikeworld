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
    private String startRegiDate;
    @Expose
    private String endRegiDate;
    @Expose
    private String description;
    @Expose
    private double latitude;
    @Expose
    private double longitude;
    @Expose
    private int status;
    @Expose
    private double fee;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getStartRegiDate() {
        return startRegiDate;
    }

    public void setStartRegiDate(String startRegiDate) {
        this.startRegiDate = startRegiDate;
    }

    public String getEndRegiDate() {
        return endRegiDate;
    }

    public void setEndRegiDate(String endRegiDate) {
        this.endRegiDate = endRegiDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
