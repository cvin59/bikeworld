package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.team17.bikeworld.adapter.EmptyStringTypeAdapter;

import java.util.List;

public class ClientCrawlModel {

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private int categoryId;
    @Expose
    private int branId;
    @Expose
    private String price;
    @Expose
    private String description;

    @Expose
    private String images;

    public ClientCrawlModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBranId() {
        return branId;
    }

    public void setBranId(int branId) {
        this.branId = branId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
