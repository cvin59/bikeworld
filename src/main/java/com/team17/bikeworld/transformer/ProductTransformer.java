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
                List<Integer> imageIdList = new ArrayList<>();
                for (ProductImage image : images) {
                    imageList.add(image.getImageLink());
                    imageIdList.add(image.getId());
                }
                view.setProductImg(imageList);
                view.setProductImgId(imageIdList);
            }
            return view;
        }
        return null;
    }

    public Product ProductModelToEntity(ProductModel model, Product entity) {
        if (model != null) {
            if (model.getId() == null) {
                entity.setId(0);
            } else {
                entity.setId(model.getId());
            }

            entity.setPrice(model.getPrice());
            entity.setQuantity(model.getQuantity());
            entity.setDescription(model.getDescription());
            entity.setAddress(model.getAddress());

//            entity.setLatitude();
//            entity.setLongitude();
            if (entity.getName() == null) {
                entity.setName(model.getName());
            }
            if (entity.getPostDate() == null) {
                entity.setPostDate(model.getPostDate());
            }

            if (entity.getTotalRatePoint() == null) {
                entity.setTotalRatePoint(model.getTotalRatePoint());
            }

            if (entity.getTotalRates() == null) {
                entity.setTotalRates(model.getTotalRater());
            }


            if (entity.getAccountUsename() == null) {
                // Set seller
                Account account = new Account();
                account.setUsername(model.getSeller());
                entity.setAccountUsename(account);
            }

            if (entity.getCategoryId() == null) {
                // Set Category
                Category category = new Category();
                category.setId(model.getCategory());
                entity.setCategoryId(category);
            }

            if (entity.getBrandId() == null) {
                //Set Brand
                Brand brand = new Brand();
                brand.setId(model.getBrand());
                entity.setBrandId(brand);
            }


            if (entity.getStatusId() == null) {
                ProductStatus status = new ProductStatus();
                status.setId(model.getStatus());
                entity.setStatusId(status);
            }

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
