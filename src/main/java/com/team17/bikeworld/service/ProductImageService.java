package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.ProductImage;
import com.team17.bikeworld.repositories.ProductImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public List<ProductImage> getProductImagesByProductId(Integer id) {
        return productImageRepository.findByProductId_Id(id);
    }
}
