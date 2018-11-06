package com.team17.bikeworld.transformer;

import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.entity.CrawlProductImage;
import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.entity.ProductImage;
import com.team17.bikeworld.model.CrawlProductModel;
import com.team17.bikeworld.model.ProductModel;
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
        CrawlProduct result = new CrawlProduct();
        result.setId(0);
        result.setName(model.getName());
        //Find Category by Id,
        result.setCategoryId(categoryRepository.findById(model.getCatergoryId()).orElse(null));
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

    public CrawlProduct UpdateEditCrawlProductModelCrawlProductEntity(CrawlProductModel model , CrawlProduct etity){
        etity.setId(model.getId());
        etity.setName(model.getName());
        etity.setCategoryId(categoryRepository.findById(model.getCatergoryId()).orElse(null));
        etity.setPrice(Float.toString(model.getPrice()));
        etity.setBrandId(brandRepository.findById(model.getBranId()).orElse(null));
        etity.setStatus(crawlStatusRepository.findById(model.getStatus()).orElse(null));
        etity.setDescription(model.getDescription());
        etity.setUrl(null);
        etity.setSiteId(null);
        etity.setHash(null);
        return etity;
    }

    public List<CrawlProductImage> ImageModelToEntity(CrawlProduct entity, CrawlProductModel model) {
        List<CrawlProductImage> imagesEntities = new LinkedList<>();


        List<String> imageUrl = model.getImage();
        for (String url : imageUrl) {
            CrawlProductImage imageEntity = new CrawlProductImage();
            imageEntity.setImageLink(url);
            imageEntity.setCrawlProductid(entity);
            imagesEntities.add(imageEntity);
        }
        return imagesEntities;
    }
}
