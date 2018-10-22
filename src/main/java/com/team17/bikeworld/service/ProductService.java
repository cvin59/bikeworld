package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        List<Product> products = productRepository.findAll();
        return products;
    }

    public List<Product> findProductByCate(int cateId){
        List<Product> products = productRepository.findProductByCategoryId(cateId);
        return products;
    }

    public List<Product> addProduct(ProductModel pro){

    }
}
