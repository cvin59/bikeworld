package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //thi du
    public List<Product> findProducts(){
        List<Product> products = productRepository.findAll();
        return products;
    }
}
