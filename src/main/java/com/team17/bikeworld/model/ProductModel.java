package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.team17.bikeworld.adapter.EmptyStringTypeAdapter;
import com.team17.bikeworld.entity.Brand;
import com.team17.bikeworld.entity.Category;

import java.util.Date;
import java.util.List;

public class ProductModel {
    @Expose
    @JsonAdapter(EmptyStringTypeAdapter.class)
    private  Integer id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private double price;
    @Expose
    private Double longtitude;
    @Expose
    private Double latitude;
    @Expose
    private String seller;
    @Expose
    private String address;
    @Expose
    private Date postDate;
    @Expose
    private double totalRatePoint;
    @Expose
    private int totalRater;
    @Expose
    private int status;
    @Expose
    private int category;
    @Expose
    private int brand;
    @Expose
    private List<String> images;

    public ProductModel() {
    }
      
    private Brand brandId;
    private Category categoryId;
    private String username;

    public ProductModel(int id, String name, String description, double price, Double longtitude, Double latitude, String seller, String address, Date postDate, double totalRatePoint, int totalRater, int status, int category, int brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.seller = seller;
        this.address = address;
        this.postDate = postDate;
        this.totalRatePoint = totalRatePoint;
        this.totalRater = totalRater;
        this.status = status;
        this.category = category;
        this.brand = brand;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public double getTotalRatePoint() {
        return totalRatePoint;
    }

    public void setTotalRatePoint(double totalRatePoint) {
        this.totalRatePoint = totalRatePoint;
    }

    public int getTotalRater() {
        return totalRater;
    }

    public void setTotalRater(int totalRater) {
        this.totalRater = totalRater;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Brand getBrandId() {
        return brandId;
    }

    public void setBrandId(Brand brandId) {
        this.brandId = brandId;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }
}
