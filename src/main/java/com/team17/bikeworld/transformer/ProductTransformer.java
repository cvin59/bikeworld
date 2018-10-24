package com.team17.bikeworld.transformer;

import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Brand;
import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTransformer {

    @Autowired
    AccountRepository accountRepository;

    public ProductTransformer() {
    }

    public Product ProductModelToEntity(ProductModel model) {
        if (model != null) {
            Product entity = new Product();
            if (model.getId() != 0) {
                entity.setId(model.getId());
            }

            // Set seller
//            Account account = accountRepository.findAccountByUsername(model.getSeller());
//            entity.setAccountUsename(account);
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
}
