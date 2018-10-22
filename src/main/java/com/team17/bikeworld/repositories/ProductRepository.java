package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Brand;
import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepository<Pro> extends JpaRepository<Product, Integer> {

    @Query("SELECT u FROM Product u WHERE u.category_id = ?1")
    List<Product> findProductByCategoryId(int cateId);


    Product addNew(String name,
                             Double price,
                             String description,
                             Double longitude,
                             Double latitude,
                             String address,
                             Date postDate,
                             Brand brandId,
                             Category categoryId);
}
