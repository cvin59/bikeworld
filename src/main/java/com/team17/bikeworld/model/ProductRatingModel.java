package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class ProductRatingModel {
    @Expose
    private String rater;
    @Expose
    private int productId;
    @Expose
    private int id;
    @Expose
    private String content;
    @Expose
    private int point;
    @Expose
    private Date rateDate;
    @Expose
    private String avatar;

    public ProductRatingModel() {
    }

    public String getRater() {
        return rater;
    }

    public void setRater(String rater) {
        this.rater = rater;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Date getRateDate() {
        return rateDate;
    }

    public void setRateDate(Date rateDate) {
        this.rateDate = rateDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
