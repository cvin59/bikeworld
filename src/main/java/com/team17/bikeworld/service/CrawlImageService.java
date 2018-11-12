package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.entity.CrawlProductImage;
import com.team17.bikeworld.model.CrawlProductImageModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.CrawlProductImageRepository;
import com.team17.bikeworld.repositories.CrawlRepository;
import com.team17.bikeworld.transformer.CrawlProductTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CrawlImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlService.class);
    private final Path rootLocation = Paths.get("src/main/resources/static/images/crawlProduct/").toAbsolutePath().normalize();

    @Autowired
    CrawlProductImageRepository crawlProductImageRepository;

    @Autowired
    CrawlRepository crawlRepository;

    @Autowired
    CrawlProductTransformer crawlProductTransformer;

    public List<CrawlProductImageModel> getImageOfCrawlProduct(Integer crawlProductId){
        //Get crawlProduct form database to find images
        CrawlProduct crawlProduct = crawlRepository.findById(crawlProductId).orElse(null);
        //Get images based on crawlProduct_id from database
        List<CrawlProductImage> imagesFromDatabase = crawlProductImageRepository.findAllByCrawlProductId(crawlProduct);

        //Go through list of images from database and transform them to CrawlProductImageModel
        List<CrawlProductImageModel> result = new ArrayList<>();
        for (CrawlProductImage image : imagesFromDatabase) {
            result.add(crawlProductTransformer.ImageEntityToImageModel(image));
        }
        return result;
    }
}
