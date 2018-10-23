package com.team17.bikeworld.controller;

import com.team17.bikeworld.controllerInterface.ProductControllerInterface;
import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.ProductRepository;
import com.team17.bikeworld.serviceInterface.ProductServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ProductController extends AbstractController implements ProductControllerInterface {

    @Autowired
    ProductServiceInterface productService;

    @Autowired
    ProductRepository productRepository;

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


    @GetMapping(CoreConstant.API_PRODUCT + "/viewall")
    public String viewAllProduct() {

        Response<List<Product>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<Product> pros = productRepository.findAll();
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, pros);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

}
