package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Brand;
import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.CrawlProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Locale;

public interface CrawlRepository extends JpaRepository<CrawlProduct, Integer> {

    @Query(value = "INSERT INTO `crawlproduct` ( `name`, `url`, `category_id`, `brand_id`, `site_id`, `price`, 'status', 'desc') VALUES (?1, ?2, ?3,  ?4, ?5, ?6, ?7, ?8)", nativeQuery = true)
    CrawlProduct addCrawlProduct(String name, String url, Category category_id, Brand brand_id, String site_id, String price, Integer status, String description);

    @Modifying
    @Query(value = "DELETE FROM `crawlproduct` WHERE site = ?1", nativeQuery = true)
    int deleteAllBySite(String site);

    @Query(value = "SELECT * FROM `crawlproduct` ", nativeQuery = true)
    List<CrawlProduct> getAll();

    @Query(value = "SELECT * FROM `crawlproduct` WHERE site = ?1", nativeQuery = true)
    List<CrawlProduct> findAllBySite(String site);
}
