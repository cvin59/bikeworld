package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
