package com.team17.bikeworld.transformer;

import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.entity.CrawlProductImage;
import com.team17.bikeworld.model.CrawlProductModel;
import com.team17.bikeworld.repositories.BrandRepository;
import com.team17.bikeworld.repositories.CategoryRepository;
import com.team17.bikeworld.repositories.CrawlStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CrawlProductTransformer {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CrawlStatusRepository crawlStatusRepository;

    //Used to map from CrawlProductModel to CrawlProduct
    public CrawlProduct CrawlProductModel_CrawlProductEntity(CrawlProductModel model){
        System.out.println("CategoryId: " + model.getCategoryId());
        CrawlProduct result = new CrawlProduct();
        result.setId(0);
        result.setName(model.getName());
        //Find Category by Id,
        result.setCategoryId(categoryRepository.findById(model.getCategoryId()).orElse(null));
        //Convert price from float to string
        result.setPrice(Float.toString(model.getPrice()));
        result.setBrandId(brandRepository.findById(model.getBranId()).orElse(null));
        result.setStatus(crawlStatusRepository.findById(model.getStatus()).orElse(null));
        result.setDescription(model.getDescription());
        result.setUrl(null);
        result.setSiteId(null);
        result.setHash(null);
        return result;
    }

    public CrawlProduct UpdateEditCrawlProductModelCrawlProductEntity(CrawlProductModel model , CrawlProduct entity){
        entity.setName(model.getName());
        entity.setCategoryId(categoryRepository.findById(model.getCategoryId()).orElse(null));
        entity.setPrice(Float.toString(model.getPrice()));
        entity.setBrandId(brandRepository.findById(model.getBranId()).orElse(null));
        entity.setStatus(crawlStatusRepository.findById(model.getStatus()).orElse(null));
        entity.setDescription(model.getDescription());
        entity.setUrl(null);
        entity.setSiteId(null);
        entity.setHash(null);
        return entity;
    }

    public List<CrawlProductImage> ImageModelToEntity(CrawlProduct entity, CrawlProductModel model) {
        List<CrawlProductImage> imagesEntities = new LinkedList<>();
        List<String> imageUrl = model.getImages();
        for (String url : imageUrl) {
            CrawlProductImage imageEntity = new CrawlProductImage();
            imageEntity.setImageLink(url);
            imageEntity.setCrawlProductid(entity);
            imagesEntities.add(imageEntity);
        }
        return imagesEntities;
    }
}
