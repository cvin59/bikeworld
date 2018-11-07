package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.crawl.crawler.RevzillaCrawler;
import com.team17.bikeworld.crawl.crawler.YnebikersCrawler;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.entity.CrawlProductImage;
import com.team17.bikeworld.entity.CrawlSite;
import com.team17.bikeworld.model.*;
import com.team17.bikeworld.repositories.*;
import com.team17.bikeworld.transformer.CrawlProductTransformer;
import com.team17.bikeworld.transformer.ProductTransformer;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CrawlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlService.class);
    private final Path rootLocation = Paths.get("src/main/resources/static/images/crawlProduct/").toAbsolutePath().normalize();

    @Autowired
    private  final CrawlProductTransformer crawlProductTransformer;

    private final CrawlRepository crawlRepository;
    private final CrawlProductImageRepository crawlProductImageRepository;
    private final CategoryRepository categoryRepository;
    private final CrawlSiteRepository crawlSiteRepository;
    private final CrawlStatusRepository crawlStatusRepository;
    private final BrandRepository brandRepository;

    public CrawlService(CrawlRepository crawlRepository, CrawlProductImageRepository crawlProductImageRepository, CategoryRepository categoryRepository, CrawlSiteRepository crawlSiteRepository, CrawlStatusRepository crawlStatusRepository, BrandRepository brandRepository, ProductTransformer productTransformer, CrawlProductTransformer crawlProductTransformer) {
        this.crawlRepository = crawlRepository;
        this.crawlProductImageRepository = crawlProductImageRepository;
        this.categoryRepository = categoryRepository;
        this.crawlSiteRepository = crawlSiteRepository;
        this.crawlStatusRepository = crawlStatusRepository;
        this.brandRepository = brandRepository;
        this.crawlProductTransformer = crawlProductTransformer;
    }

    public List<CrawlProduct> getAll() {
        List<CrawlProduct> products = crawlRepository.findAll();
        return products;
    }

//    public List<CrawlProduct> viewCrawlOLD(String site) {
//        String url = null;
//        if (site.equals("revzilla")) {
//            url = RevzillaCrawler.baseLink;
//        } else if (site.equals("ynebikers")) {
//            url = YnebikersCrawler.baseLink;
//        }
//        if (url != null) {
//            List<CrawlProduct> products = crawlRepository.findAllBySite(url);
//            return products;
//        } else {
//            return null;
//        }
//    }

    public List<CrawlProduct> viewCrawl(String siteName) {
        Optional<CrawlSite> crawlSite = crawlSiteRepository.findByName(siteName.trim().toLowerCase());
        if (crawlSite != null) {
            List<CrawlProduct> products = crawlRepository.findAllBySiteId(crawlSite.get());
            return products;
        } else {
            return null;
        }
    }


    public List<CrawlProduct> getNewByPage(int page, int pageSize) {
        int from = (page - 1) * pageSize;
        return crawlRepository.getNewByPage(from, pageSize);
    }

//    public Integer deleteAllBySite(String site) {
//        int i = crawlRepository.deleteAllBySite(site);
//        return i;
//    }

    public int runCrawl(String site) {
        int count = 0;
        try {
            if (site.equals("revzilla")) {
                if (!RevzillaCrawler.isLock()) {
                    RevzillaCrawler.instance = new Thread(new RevzillaCrawler(crawlRepository, categoryRepository, crawlProductImageRepository, crawlSiteRepository, crawlStatusRepository, brandRepository));
                    RevzillaCrawler.instance.start();
                    RevzillaCrawler.instance.join();
                    count = RevzillaCrawler.getCount();
                }
            } else if (site.equals("ynebikers")) {
                if (!YnebikersCrawler.isLock()) {
                    YnebikersCrawler.instance = new Thread(new YnebikersCrawler(crawlRepository, categoryRepository, crawlProductImageRepository, crawlSiteRepository, crawlStatusRepository, brandRepository));
                    YnebikersCrawler.instance.start();
                    YnebikersCrawler.instance.join();
                    count = YnebikersCrawler.getCount();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void stopCrawl(String site) {
        if (site.equals("revzilla")) {
            if (RevzillaCrawler.instance.isAlive()) {
                RevzillaCrawler.instance.stop();
            }
        } else if (site.equals("ynebikers")) {
            if (YnebikersCrawler.instance.isAlive()) {
                YnebikersCrawler.instance.stop();
            }
        }
    }

    public Response<CrawlProduct> createCrawlProduct(CrawlProductModel crawlProductModel, MultipartFile[] images){
        Response<CrawlProduct> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        //Check if model is null or don't have image
        if (crawlProductModel != null && images.length != 0){
            //Try to save crawl product information to database
            try {
                //Map Model to entity
                CrawlProduct crawlProduct = crawlProductTransformer.CrawlProductModel_CrawlProductEntity(crawlProductModel);
                CrawlProduct result = crawlRepository.save(crawlProduct);
                if (images != null) {
                    addImagesToDatabase(result, crawlProductModel, images);
                }
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, result);
            }
            catch (Exception e){
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
                LOGGER.error(e.getMessage());
            }
        }
        return response;
    }

    public void addImagesToDatabase(CrawlProduct entity,CrawlProductModel crawlProductModel, MultipartFile[] images ) throws IOException {
        List<String> imageList = new ArrayList<>();
        crawlProductModel.setImages(imageList);

        for (MultipartFile image : images) {
            handleImage(crawlProductModel, image);
        }

        List<CrawlProductImage> saveImage = crawlProductTransformer.ImageModelToEntity(entity, crawlProductModel);
        for (CrawlProductImage image : saveImage) {
            crawlProductImageRepository.save(image);
        }

    }

    private void handleImage(CrawlProductModel model, MultipartFile image) throws IOException {
        if (image != null) {
            String fileName = RandomStringUtils.randomAlphabetic(8) + image.getOriginalFilename();
            Files.createDirectories(rootLocation);
            Files.copy(image.getInputStream(), rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info(rootLocation.resolve(fileName).toString());
            model.getImages().add(fileName);
            LOGGER.info("file name:" + fileName);
        }
    }



    public Response<CrawlProduct> editCrawlProduct(CrawlProductModel crawlProductModel, MultipartFile[] addedImages) {
        Response<CrawlProduct> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (crawlProductModel!=null){
            //Find CrawlProduct in database
            Optional<CrawlProduct> crawlProductFromDatabase = crawlRepository.findById(crawlProductModel.getId());
            if (crawlProductFromDatabase.isPresent()){
                try {
                        CrawlProduct result = crawlProductFromDatabase.get();
                        //crawlRepository.save(UpdateEditCrawlProductModelCrawlProductEntity(crawlProductModel, result));
                        response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, result);

                        //Add Images
                        if (addedImages.length > 0){
                            addImagesToDatabase(result, crawlProductModel, addedImages);
                        }

                        //Delete Images
                        if (!crawlProductModel.getDeleteImages().isEmpty()){
                            ArrayList<CrawlProductImage> deleteImagesFromDatabase = new ArrayList<>();

                            for (int i : crawlProductModel.getDeleteImages()){
                                CrawlProductImage tempDeleteImage = crawlProductImageRepository.findById(i).orElse(null);

                                if (tempDeleteImage != null){
                                    deleteImagesFromDatabase.add(tempDeleteImage);
                                }
                            }

                            if (!deleteImagesFromDatabase.isEmpty()){
                                //Delete from database
                                crawlProductImageRepository.deleteAll(deleteImagesFromDatabase);

                                //Delete from Disk
                                for (CrawlProductImage i : deleteImagesFromDatabase){
                                    LOGGER.info("Root path : " + rootLocation);
                                    LOGGER.info("ImageLink path : " +  i.getImageLink());
                                    LOGGER.info("File path : " + rootLocation + i.getImageLink());
                                    File file = new File(rootLocation + "\\" + i.getImageLink());
                                    file.delete();
                                }
                            }


                        }

                }catch (Exception e){
                    response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return response;
    }

    public Response changeCrawlProductStatus(ChangeStatusCrawlModel changeStatusCrawlModel){
        Response response =  new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (changeStatusCrawlModel != null){
            Optional<CrawlProduct> crawlProductFromDatabase = crawlRepository.findById(changeStatusCrawlModel.getCrawlProductId());
            //Check if there's a crawl product with the id in the database
            if (crawlProductFromDatabase.isPresent()){
                //Change from Optional to CrawlProduct
                CrawlProduct result = crawlProductFromDatabase.get();
                //Check if the status already existed
                if (result.getStatus().getId() != changeStatusCrawlModel.getCrawlProductStatus()){
                    //try to update status
                    try {
                        result.getStatus().setId(changeStatusCrawlModel.getCrawlProductStatus());
                        crawlRepository.save(result);
                        response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, result);
                    }catch (Exception e){
                        response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
                        LOGGER.error(e.getMessage());
                    }
                }
            }
        }
        return response;
    }
//    public void DeleteBySite(String site) {
//        List<CrawlProductImage> imgBySite = crawlProductImageRepository.findAllBySite(site);
//        crawlProductImageRepository.deleteAll(imgBySite);
//        List<CrawlProduct> allBySite = crawlRepository.findAllBySite(site);
//        crawlRepository.deleteAll(allBySite);
//    }

}
