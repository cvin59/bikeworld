package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;

public class CrawlProductModel {
@Expose
    String name;
    @Expose
    int catergoryId;
    @Expose
    int branId;
    @Expose
    float price;
    @Expose
    int status;
    @Expose
    String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCatergoryId() {
        return catergoryId;
    }

    public void setCatergoryId(int catergoryId) {
        this.catergoryId = catergoryId;
    }

    public int getBranId() {
        return branId;
    }

    public void setBranId(int branId) {
        this.branId = branId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
