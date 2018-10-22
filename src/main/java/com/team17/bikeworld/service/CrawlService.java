package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.repositories.CrawlRepository;

import java.util.List;

public class CrawlService {

    private final CrawlRepository crawlRepository;

    public CrawlService(CrawlRepository crawlRepository) {
        this.crawlRepository = crawlRepository;
    }

    public List<CrawlProduct> getAll(){
        List<CrawlProduct> products = crawlRepository.findAll();
        return products;
    }
}
