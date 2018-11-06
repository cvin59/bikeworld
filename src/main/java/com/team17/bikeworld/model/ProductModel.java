package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.team17.bikeworld.adapter.EmptyStringTypeAdapter;
import com.team17.bikeworld.entity.Brand;
import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.ProductImage;
import com.team17.bikeworld.entity.ProductStatus;

import java.util.Date;
import java.util.List;

public class ProductModel {

    @Expose
   // @JsonAdapter(EmptyStringTypeAdapter.class)
    private int id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private double price;
    @Expose
    private int quantity;
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
    List<Integer> ProductImgId;
    @Expose
    private List<String> images;
    @Expose
    private int statusId;
    @Expose
    private String status;
    @Expose
    private int categoryId;
    @Expose
    private String category;
    @Expose
    private int brandId;
    @Expose
    private String brand;

    public ProductModel() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public List<Integer> getProductImgId() {
        return ProductImgId;
    }

    public void setProductImgId(List<Integer> productImgId) {
        ProductImgId = productImgId;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
