package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.CrawlProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CrawlRepository extends JpaRepository<CrawlProduct, Integer> {

    @Query("SELECT u FROM Product u WHERE u.category_id = ?1")
    List<CrawlProduct> addCrawlProduct(int cateId);
}
