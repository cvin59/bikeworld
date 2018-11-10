package com.team17.bikeworld.transformer;

import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.entity.ProductRating;
import com.team17.bikeworld.model.ProductRatingModel;
import org.springframework.stereotype.Service;

@Service
public class ProductRatingTransformer {
    public ProductRating RatingModelToEntity(ProductRating entity, ProductRatingModel model) {
        Account account = new Account();
        account.setUsername(model.getRater());
        entity.setAccountUsename(account);

        Product product = new Product();
        product.setId(model.getProductId());
        entity.setProductId(product);

        entity.setId(model.getId());
        entity.setContent(model.getContent());
        entity.setRateDate(model.getRateDate());
        entity.setPoint(model.getPoint());

        return entity;
    }

    public ProductRatingModel RatingEntityToModel(ProductRatingModel model, ProductRating entity) {
        model.setId(entity.getId());
        model.setRateDate(entity.getRateDate());
        model.setContent(entity.getContent());
        model.setPoint(entity.getPoint());
        model.setRater(entity.getAccountUsename().getUsername());

        return model;
    }
}
