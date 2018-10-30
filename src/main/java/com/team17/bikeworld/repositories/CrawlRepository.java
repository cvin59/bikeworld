package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.CrawlProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Locale;

public interface CrawlRepository extends JpaRepository<CrawlProduct, Integer> {
    
    @Query(value = "INSERT INTO `crawlproduct` ( `name`, `site`, `link`, `img`, `price`, `cate`) VALUES (?3, ?1, ?4,  ?5, ?2)", nativeQuery = true)
    CrawlProduct addCrawlProduct(String site, Category cate,String name,String link, String price);

    @Modifying
    @Query(value = "DELETE FROM `crawlproduct` WHERE site = ?1", nativeQuery = true)
    int deleteAllBySite(String site);


    @Query(value = "SELECT * FROM `crawlproduct` ", nativeQuery = true)
    List<CrawlProduct> getAll();

    @Query(value = "SELECT * FROM `crawlproduct` WHERE site = ?1", nativeQuery = true)
    List<CrawlProduct> findAllBySite(String site);
}
