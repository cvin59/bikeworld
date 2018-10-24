package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.CrawlProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface CrawlProductImageRepository extends JpaRepository<CrawlProductImage, Integer> {

//    @Modifying
//
//    CrawlProductImage addCrawlProductImg(int crawlProId, String imgLink);
//
//    @Modifying
//    int deleteAllBySite(String site);
}
