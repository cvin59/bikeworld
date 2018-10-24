package com.team17.bikeworld.model;

import com.team17.bikeworld.entity.Brand;
import com.team17.bikeworld.entity.Category;

import java.util.Date;

public class ProductModel {

    private  Integer id;
    private String name;
    private Double price;
    private String description;
    private Double longitude;
    private Double latitude;
    private String address;
    private Date postDate;
    private Brand brandId;
    private Category categoryId;
    private String username;

    public ProductModel(Integer id, String name, Double price, String description, Double longitude, Double latitude, String address, Date postDate, Brand brandId, Category categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.postDate = postDate;
        this.brandId = brandId;
        this.categoryId = categoryId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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
