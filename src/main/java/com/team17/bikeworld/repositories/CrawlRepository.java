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

    @Query(value = "INSERT INTO `bikeworld`.`crawlproduct` (`name`, `url`, `category_id`, `brand_id`, `site_id`, `price`, `status`, `desc`) VALUES ('abc', 'abc', '1', '1', '1', '1', '1', 'sdf')", nativeQuery = true)
    CrawlProduct addCrawlProduct();

    @Modifying
    @Query(value = "DELETE FROM `crawlproduct` WHERE site = ?1", nativeQuery = true)
    int deleteAllBySite(String site);

    @Query(value = "SELECT * FROM `crawlproduct` ", nativeQuery = true)
    List<CrawlProduct> getAll();

    @Query(value = "SELECT * FROM `crawlproduct` WHERE site = ?1", nativeQuery = true)
    List<CrawlProduct> findAllBySite(String site);
}
