package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.CrawlStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrawlStatusRepository extends JpaRepository<CrawlStatus, Integer> {
}

