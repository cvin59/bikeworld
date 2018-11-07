package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.CrawlStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface CrawlStatusRepository extends JpaRepository<CrawlStatus, Integer> {

    Optional<CrawlStatus> findByName(String name);
//    @Modifying
//    CrawlProductImage addCrawlProductImg(int crawlProId, String imgLink);

//    @Modifying
//    @Query(value = "DELETE FROM `crawlsite` WHERE site = ?1", nativeQuery = true)
//    int deleteAllBySite(String site);
//
//    @Query(value = "SELECT * FROM `crawlsite` WHERE site = ?1", nativeQuery = true)
//    List<CrawlProductImage> findAllBySite(String site);
}
