package com.team17.bikeworld.common;

import com.team17.bikeworld.entity.Product;

public class RelevantProduct {
    private int distance;
    private Product product;

    public RelevantProduct(int distance, Product product) {
        this.distance = distance;
        this.product = product;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
