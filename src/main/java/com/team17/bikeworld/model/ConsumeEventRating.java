package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class ConsumeEventRating {
    @Expose
    private int ratePoint;
    @Expose
    private String content;
    @Expose
    private int eventId;
    @Expose
    private String username;
    @Expose
    private Date rateDate;

    public int getRatePoint() {
        return ratePoint;
    }

    public void setRatePoint(int ratePoint) {
        this.ratePoint = ratePoint;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getRateDate() {
        return rateDate;
    }

    public void setRateDate(Date rateDate) {
        this.rateDate = rateDate;
    }

    @Override
    public String toString() {
        return "ConsumeEventRating{" +
                "ratePoint=" + ratePoint +
                ", content='" + content + '\'' +
                ", eventId=" + eventId +
                ", username='" + username + '\'' +
                ", rateDate=" + rateDate +
                '}';
    }
}
