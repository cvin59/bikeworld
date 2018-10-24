package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductImageRepository<Pro> extends JpaRepository<ProductImage, Integer> {


//    @Query("SELECT u FROM ProductImage u WHERE u.product_id = ?1")
//    List<Product> findAllByProId(int proId);

    @Modifying
    @Query(value = "INSERT INTO `ProductImage` ( `name`, `price`, `description`, `longitude`, `latitude`, `address`,`postDate`, `brandId`, `categoryId`) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8,?9)", nativeQuery = true)
    ProductImage addNew(String link, Product pro);

}
