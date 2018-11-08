package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.team17.bikeworld.adapter.EmptyStringTypeAdapter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class CrawlProductModel {
    @Expose
    @JsonAdapter(EmptyStringTypeAdapter.class)
    private int id;

    @Expose
    String name;

    @Expose
    private int catergoryId;

    @Expose
    private int branId;

    @Expose
    private float price;

    @Expose
    private int status;

    @Expose
    private String description;

    @Expose
    @JsonAdapter(EmptyStringTypeAdapter.class)
    private List<String> images;

    @Expose
    @JsonAdapter(EmptyStringTypeAdapter.class)
    private List<Integer> deleteImages;


    public List<Integer> getDeleteImages() {
        return deleteImages;
    }

    public void setDeleteImages(List<Integer> deleteImages) {
        this.deleteImages = deleteImages;
    }
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> image) {
        this.images = image;
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
