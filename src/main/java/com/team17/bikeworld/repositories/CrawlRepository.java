package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.CrawlProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Locale;

public interface CrawlRepository extends JpaRepository<CrawlProduct, Integer> {

    @Modifying
    CrawlProduct addCrawlProduct(String site, Category cate,String name,String link, String price);

    @Modifying
    @Query(value = "delete FROM Product  WHERE category_id = ?1", nativeQuery = true)
    void deleteAllBysi();

}
