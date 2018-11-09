package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.Brand;
import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.CrawlStatus;
import com.team17.bikeworld.repositories.BrandRepository;
import com.team17.bikeworld.repositories.CategoryRepository;
import com.team17.bikeworld.repositories.CrawlStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CrawlStatusRepository crawlStatusRepository;

    public List<Category> loadCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public List<Brand> loadBrand(){
        List<Brand> brands= brandRepository.findAll();
        return brands;
    }

    public List<CrawlStatus> loadCrawlStatus(){
        List<CrawlStatus> crawlStatuses = crawlStatusRepository.findAll();
        return crawlStatuses;
    }
}
