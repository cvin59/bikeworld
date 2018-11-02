package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.CrawlProductImage;
import com.team17.bikeworld.entity.CrawlSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrawlSiteRepository extends JpaRepository<CrawlSite, Integer> {

    Optional<CrawlSite> findByName(String name);
//    @Modifying
//    CrawlProductImage addCrawlProductImg(int crawlProId, String imgLink);

//    @Modifying
//    @Query(value = "DELETE FROM `crawlsite` WHERE site = ?1", nativeQuery = true)
//    int deleteAllBySite(String site);
//
//    @Query(value = "SELECT * FROM `crawlsite` WHERE site = ?1", nativeQuery = true)
//    List<CrawlProductImage> findAllBySite(String site);
}
