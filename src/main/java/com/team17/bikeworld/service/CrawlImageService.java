package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.CrawlProductImage;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.CrawlProductImageRepository;
import com.team17.bikeworld.repositories.CrawlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CrawlImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlService.class);
    private final Path rootLocation = Paths.get("src/main/resources/static/images/crawlProduct/").toAbsolutePath().normalize();

    @Autowired
    CrawlProductImageRepository crawlProductImageRepository;

    public List<CrawlProductImage> getImageOfCrawlProduct(Integer crawlProductId){
        return crawlProductImageRepository.findAllByCrawlProductId(crawlProductId);
    }
}
