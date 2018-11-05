package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.crawl.crawler.RevzillaCrawler;
import com.team17.bikeworld.crawl.crawler.YnebikersCrawler;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.entity.CrawlProductImage;
import com.team17.bikeworld.entity.CrawlSite;
import com.team17.bikeworld.model.*;
import com.team17.bikeworld.repositories.*;
import com.team17.bikeworld.transformer.ProductTransformer;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.team17.bikeworld.common.CoreConstant.rootLocation;

@Service
public class CrawlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlService.class);

    private final CrawlRepository crawlRepository;
    private final CrawlProductImageRepository crawlProductImageRepository;
    private final CategoryRepository categoryRepository;
    private final CrawlSiteRepository crawlSiteRepository;
    private final CrawlStatusRepository crawlStatusRepository;
    private final BrandRepository brandRepository;

    public CrawlService(CrawlRepository crawlRepository, CrawlProductImageRepository crawlProductImageRepository, CategoryRepository categoryRepository, CrawlSiteRepository crawlSiteRepository, CrawlStatusRepository crawlStatusRepository, BrandRepository brandRepository, ProductTransformer productTransformer) {
        this.crawlRepository = crawlRepository;
        this.crawlProductImageRepository = crawlProductImageRepository;
        this.categoryRepository = categoryRepository;
        this.crawlSiteRepository = crawlSiteRepository;
        this.crawlStatusRepository = crawlStatusRepository;
        this.brandRepository = brandRepository;
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

    public Response<CrawlProduct> createCrawlProduct(CrawlProductModel crawlProductModel){
        Response<CrawlProduct> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        //Check if model is null or don't have image
        if (crawlProductModel != null && crawlProductModel.getImages().length != 0){
            //Try to save crawl product information to database
            try {
                //Map Model to entity
                CrawlProduct crawlProduct = CrawlProductModel_CrawlProductEntity(crawlProductModel);
                CrawlProduct result = crawlRepository.save(crawlProduct);
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, result);

                //Save images information to disk and  database
                saveMultipleImagesToDatabase(saveMultipleImagesToDisk(crawlProductModel.getImages(), result.getId()), result);

            }
            catch (Exception e){
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
                LOGGER.error(e.getMessage());
            }
        }
        return response;
    }

    //Save image files to disk
    public ArrayList<String> saveMultipleImagesToDisk(MultipartFile[] images, int crawlItemId) throws IOException {
        //Prefix path
        String fileName = "/images/"
                .concat("crawlItem/")
                .concat(Integer.toString(crawlItemId));

        //List of saved files' path, used later to insert image information to database
        ArrayList<String> result = new ArrayList<>();

        if (images.length != 0) {
            for (MultipartFile image:images){
                    String sourceName = image.getOriginalFilename();
                    String sourceFileName = FilenameUtils.getBaseName(sourceName);
                    String sourceExt = FilenameUtils.getExtension(sourceName).toLowerCase();

                    //Path: ../images/crawlItem/crawlItemId/filename_RandomString.sourceExt
                    fileName.concat(sourceFileName)
                            .concat("_")
                            .concat(RandomStringUtils.randomAlphabetic(8))
                            .concat(".")
                            .concat(sourceExt);

                    System.out.println(fileName);
                    Files.createDirectories(rootLocation);
                    Files.copy(image.getInputStream(), rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    result.add(fileName);
            }
        }

        return result;
    }

    //Save image information to database
    public void saveMultipleImagesToDatabase(ArrayList<String> imagePaths, CrawlProduct crawlProductId){
        ArrayList<CrawlProductImage> result = new ArrayList<>();
        if (!imagePaths.isEmpty()) {
            for (String imagePath:imagePaths) {
                result.add(new CrawlProductImage(imagePath, crawlProductId));
            }

            //Delete all previous images
            crawlProductImageRepository.deleteAllByCrawlProductId(crawlProductId);

            //Save new ones
            crawlProductImageRepository.saveAll(result);
        }
    }

    public Response<CrawlProduct> editCrawlProduct(CrawlProductModel crawlProductModel) {
        Response<CrawlProduct> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (crawlProductModel!=null){
            Optional<CrawlProduct> crawlProductFromDatabase = crawlRepository.findById(crawlProductModel.getId());
            if (crawlProductFromDatabase.isPresent()){
                try {
                        CrawlProduct result = crawlProductFromDatabase.get();
                        crawlRepository.save(UpdateEditCrawlProductModelCrawlProductEntity(crawlProductModel, result));
                        response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, result);

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

//    public void DeleteBySite(String site) {
//        List<CrawlProductImage> imgBySite = crawlProductImageRepository.findAllBySite(site);
//        crawlProductImageRepository.deleteAll(imgBySite);
//        List<CrawlProduct> allBySite = crawlRepository.findAllBySite(site);
//        crawlRepository.deleteAll(allBySite);
//    }

}
