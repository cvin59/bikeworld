package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class ConsumeParticipant {
    @Expose
    private Integer quantity;
    @Expose
    private double total;
    @Expose
    private Date registerDate;
    @Expose
    private Integer eventId;
    @Expose
    private String username;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ConsumeParticipant{" +
                "quantity='" + quantity + '\'' +
                ", total='" + total + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", eventId='" + eventId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
