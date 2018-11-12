package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Brand;
import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAll();

    List<Product> findByCategoryId(Category category, Pageable pageable);

    List<Product> findByCategoryId(Category category);

    List<Product> findByBrandId(Brand brand, Pageable pageable);

    Page<Product> findByAccountUsename(Account account, Pageable pageable);

    Page<Product> findByNameIgnoreCaseContainingAndAccountUsename(String name, Account account, Pageable pageable);

    Page<Product> findByNameIgnoreCaseContaining(String name, Pageable pageable);

    @Query(nativeQuery=true, value="SELECT *  FROM product ORDER BY rand() LIMIT 12")
    List<Product> randomProduct();


}
