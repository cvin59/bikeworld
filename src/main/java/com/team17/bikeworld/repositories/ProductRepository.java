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
}
