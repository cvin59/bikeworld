package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category getByName(String name);
    Optional<Category> findByName(String name);
}
