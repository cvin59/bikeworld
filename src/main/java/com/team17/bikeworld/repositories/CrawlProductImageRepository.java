package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.entity.CrawlProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrawlProductImageRepository extends JpaRepository<CrawlProductImage, Integer> {

//    @Modifying
//    CrawlProductImage addCrawlProductImg(int crawlProId, String imgLink);

    @Modifying
    @Query(value = "DELETE FROM `crawlproductimage` WHERE crawlProductid = ?1", nativeQuery = true)
    int deleteAllByCrawlProductId(CrawlProduct crawlProductId);

    @Modifying
    @Query(value = "DELETE FROM `crawlproductimage` WHERE site = ?1", nativeQuery = true)
    int deleteAllBySite(String site);

    @Query(value = "SELECT * FROM `crawlproduct` WHERE site = ?1", nativeQuery = true)
    List<CrawlProductImage> findAllBySite(String site);

    //Find all images of a crawl product based on crawlProduct ID, used in editing crawlProduct information
    @Query(value = "SELECT * FROM `crawlproductimage` WHERE crawlProduct_id = ?1", nativeQuery = true)
    List<CrawlProductImage> findAllByCrawlProductId(CrawlProduct crawlProductId);
}
