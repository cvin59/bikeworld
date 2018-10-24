package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.CrawlProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrawlProductImageRepository extends JpaRepository<CrawlProductImage, Integer> {

//    @Modifying
<<<<<<< HEAD
=======
//
>>>>>>> master
//    CrawlProductImage addCrawlProductImg(int crawlProId, String imgLink);
//
//    @Modifying
//    int deleteAllBySite(String site);
}
