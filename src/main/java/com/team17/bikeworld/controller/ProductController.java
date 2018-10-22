package com.team17.bikeworld.controller;

import com.team17.bikeworld.controllerInterface.ProductControllerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import com.team17.bikeworld.serviceInterface.ProductServiceInterface;

public class ProductController extends AbstractController implements ProductControllerInterface {

    @Autowired
    ProductServiceInterface productService;

    @Override
    public void createProduct(String name,
                              String description,
                              float price,
                              String longtitude,
                              String latitude,
                              String address,
                              String postDate,
                              String seller) {

    }
}
