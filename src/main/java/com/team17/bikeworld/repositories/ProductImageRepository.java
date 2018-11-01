package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
//    @Query("SELECT u FROM ProductImage u WHERE u.product_id = ?1")
//    List<Product> findAllByProId(int proId);

    Optional<List<ProductImage>> findByProductId(Product product);

    @Override
    void deleteById(Integer integer);

}
