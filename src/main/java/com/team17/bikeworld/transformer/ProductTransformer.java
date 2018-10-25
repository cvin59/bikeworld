package com.team17.bikeworld.transformer;

import com.team17.bikeworld.entity.*;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProductTransformer {

    @Autowired
    AccountRepository accountRepository;

    public ProductTransformer() {
    }

    public ProductModel ProductEntityToModel(Product entity, ProductModel model) {
        if (entity != null) {
            model.setId(entity.getId());
            model.setName(entity.getName());
            model.setDescription(entity.getDescription());
            model.setPrice(entity.getPrice());
            model.setAddress(entity.getAddress());
            model.setBrand(entity.getBrandId().getId());
            model.setCategory(entity.getCategoryId().getId());

            return model;
        }
        return null;
    }

    public Product ProductModelToEntity(ProductModel model, Product entity) {
        if (model != null) {
            if (model.getId() != 0) {
                entity.setId(model.getId());
            }
            // Set seller
            Account account = new Account();
            account.setUsername(model.getSeller());
            entity.setAccountUsename(account);


            // Set Category
            Category category = new Category();
            category.setId(model.getCategory());
            entity.setCategoryId(category);


            //Set Brand
            Brand brand = new Brand();
            brand.setId(model.getBrand());
            entity.setBrandId(brand);

            entity.setDescription(model.getDescription());
//            entity.setLatitude();
//            entity.setLongitude();
            entity.setName(model.getName());
            entity.setPostDate(model.getPostDate());

            entity.setAddress(model.getAddress());
            entity.setPrice(model.getPrice());
            entity.setTotalRatePoint(model.getTotalRatePoint());
            entity.setTotalRates(model.getTotalRater());
            entity.setStatus(model.getStatus());

            return entity;
        }
        return null;
    }

    public List<ProductImage> ImageModelToEntity(Product entity, ProductModel model) {
        List<ProductImage> imagesEntities = new LinkedList<>();


        List<String> imageUrl = model.getImages();
        for (String url : imageUrl) {
            ProductImage imageEntity = new ProductImage();
            imageEntity.setImageLink(url);
            imageEntity.setProductId(entity);
            imagesEntities.add(imageEntity);
        }
        return imagesEntities;
    }
}
