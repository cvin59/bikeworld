package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Brand;
import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.entity.CrawlSite;
import com.team17.bikeworld.entity.CrawlStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface CrawlRepository extends JpaRepository<CrawlProduct, Integer> {

    @Query(value = "INSERT INTO `bikeworld`.`crawlproduct` (`name`, `url`, `category_id`, `brand_id`, `site_id`, `price`, `status`, `desc`) VALUES ('abc', 'abc', '1', '1', '1', '1', '1', 'sdf')", nativeQuery = true)
    CrawlProduct addCrawlProduct();


    @Query(value = "INSERT INTO `crawlproduct` ( `name`, `url`, `category_id`, `site_id`, `price`, `status`, `desc`, `hash`) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)", nativeQuery = true)
    void addCrawlProduct(String name, String url, Category category_id, CrawlSite site_id, String price, CrawlStatus status, String desc, String hash);

//    @Modifying
//    @Query(value = "DELETE FROM `crawlproduct` WHERE site_id = ?1", nativeQuery = true)
//    int deleteAllBySite(CrawlSite site_id);

    @Query(value = "SELECT * FROM `crawlproduct` ", nativeQuery = true)
    List<CrawlProduct> getAll();

//    @Query(value = "SELECT * FROM `crawlproduct` WHERE site_id = ?1", nativeQuery = true)
//    List<CrawlProduct> findAllBySite(CrawlSite site_id);


    @Query(value = "SELECT * FROM `crawlproduct` WHERE `status`='1' LIMIT ?1,?2", nativeQuery = true)
    List<CrawlProduct> getNewByPage(int from, int pageSize);

    Optional<CrawlProduct> findByHash(String hash);

    List<CrawlProduct> findAllBySiteId(CrawlSite crawlSite);

    @Query(value = "SELECT COUNT(*) FROM `crawlproduct` WHERE `status`='1'", nativeQuery = true)
    Integer countPending();
}
