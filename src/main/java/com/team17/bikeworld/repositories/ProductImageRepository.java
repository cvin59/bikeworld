package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ProductImageRepository<Pro> extends JpaRepository<ProductImage, Integer> {

    @Query("SELECT u FROM ProductImage u WHERE u.product_id = ?1")
    List<Product> findAllByProId(int proId);


    ProductImage addNew(String link, Product pro);
}
