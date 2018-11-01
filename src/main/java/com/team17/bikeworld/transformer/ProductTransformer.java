package com.team17.bikeworld.transformer;

import com.team17.bikeworld.entity.*;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.repositories.AccountRepository;
import com.team17.bikeworld.viewModel.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductTransformer {

    @Autowired
    AccountRepository accountRepository;

    public ProductTransformer() {
    }

    public ProductViewModel ProductEntityToView(Product entity, ProductViewModel view, List<ProductImage> images) {
        if (entity != null) {
            view.setProductInfo(entity);
            if (images != null) {
                List<String> imageList = new ArrayList<>();
                for (ProductImage image : images) {
                    imageList.add(image.getImageLink());
                }
                view.setProductImg(imageList);
            }
            return view;
        }
        return null;
    }

    public Product ProductModelToEntity(ProductModel model, Product entity) {
        if (model != null) {
            entity.setId(0);
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

            entity.setQuantity(model.getQuantity());
            entity.setDescription(model.getDescription());
//            entity.setLatitude();
//            entity.setLongitude();
            entity.setName(model.getName());
            entity.setPostDate(model.getPostDate());

            entity.setAddress(model.getAddress());
            entity.setPrice(model.getPrice());
            entity.setTotalRatePoint(model.getTotalRatePoint());
            entity.setTotalRates(model.getTotalRater());

            ProductStatus status = new ProductStatus();
            status.setId(model.getStatus());
            entity.setStatusId(status);

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
