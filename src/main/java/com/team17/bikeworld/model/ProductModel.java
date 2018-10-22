package com.team17.bikeworld.model;

import com.team17.bikeworld.entity.Brand;
import com.team17.bikeworld.entity.Category;

import java.util.Date;

public class ProductModel {

    private String name;
    private Double price;
    private String description;
    private Double longitude;
    private Double latitude;
    private String address;
    private Date postDate;
    private Brand brandId;
    private Category categoryId;

    public ProductModel(String name, Double price, String description, Double longitude, Double latitude, String address, Date postDate, Brand brandId, Category categoryId) {
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
}
