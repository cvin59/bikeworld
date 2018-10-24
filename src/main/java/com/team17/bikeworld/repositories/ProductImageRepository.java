package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductImageRepository<Pro> extends JpaRepository<ProductImage, Integer> {
}
