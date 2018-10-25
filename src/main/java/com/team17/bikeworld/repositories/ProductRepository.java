package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Brand;
import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(Category category);

    List<Product> findByBrandId(Brand brand);

    @Modifying
    @Query(value = "UPDATE `product` SET status = '2' WHERE id = ?1", nativeQuery = true)
    Integer disableProduct(int id);

    @Query("SELECT u FROM Product u WHERE u.name like  %?1%")
    List<Product> searchByName(String name);

    @Modifying
    @Query(value = "UPDATE `product` SET status = '1' WHERE id = ?1", nativeQuery = true)
    Integer activateTradeItem(int id);
}
