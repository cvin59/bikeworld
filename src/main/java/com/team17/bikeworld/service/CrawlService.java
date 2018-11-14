package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.common.CrawlDistance;
import com.team17.bikeworld.common.Damerau;
import com.team17.bikeworld.crawl.crawler.RevzillaCrawler;
import com.team17.bikeworld.crawl.crawler.YnebikersCrawler;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.entity.CrawlProductImage;
import com.team17.bikeworld.entity.CrawlSite;
import com.team17.bikeworld.entity.CrawlStatus;
import com.team17.bikeworld.model.*;
import com.team17.bikeworld.repositories.*;
import com.team17.bikeworld.transformer.CrawlProductTransformer;
import com.team17.bikeworld.transformer.ProductTransformer;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@Service
public class CrawlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlService.class);
    private final Path rootLocation = Paths.get("src/main/resources/static/images/").toAbsolutePath().normalize();

    @Autowired
    private final CrawlProductTransformer crawlProductTransformer;

    private final CrawlRepository crawlRepository;
    private final CrawlProductImageRepository crawlProductImageRepository;
    private final CategoryRepository categoryRepository;
    private final CrawlSiteRepository crawlSiteRepository;
    private final CrawlStatusRepository crawlStatusRepository;
    private final BrandRepository brandRepository;
    private CrawlStatus statPending;
    private CrawlStatus statShow;

    public CrawlService(CrawlRepository crawlRepository, CrawlProductImageRepository crawlProductImageRepository, CategoryRepository categoryRepository, CrawlSiteRepository crawlSiteRepository, CrawlStatusRepository crawlStatusRepository, BrandRepository brandRepository, ProductTransformer productTransformer, CrawlProductTransformer crawlProductTransformer) {
        this.crawlRepository = crawlRepository;
        this.crawlProductImageRepository = crawlProductImageRepository;
        this.categoryRepository = categoryRepository;
        this.crawlSiteRepository = crawlSiteRepository;
        this.crawlStatusRepository = crawlStatusRepository;
        this.brandRepository = brandRepository;
        this.crawlProductTransformer = crawlProductTransformer;
        this.statPending = crawlStatusRepository.findByName("NEW").get();
        this.statShow = crawlStatusRepository.findByName("SHOW").get();
    }

    public CrawlProduct getById(Integer id) {
        return crawlRepository.findById(id).orElse(null);
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

    public List<CrawlProduct> findWithGuess(int id) {
        Optional<CrawlProduct> optional = crawlRepository.findById(id);
        if (optional != null) {
            CrawlProduct mainProduct = optional.get();
            Damerau damerau = new Damerau();
            String proName = mainProduct.getName();
            List<CrawlProduct> products = crawlRepository.findAll();
            List<CrawlDistance> distanceList = new ArrayList<>();
            for (int i = 0; i < products.size(); i++) {
                CrawlProduct crawlProduct = products.get(i);
                distanceList.add(new CrawlDistance(damerau.distance(crawlProduct.getName(), proName), crawlProduct));
            }
            Collections.sort(distanceList, new Comparator<CrawlDistance>() {
                @Override
                public int compare(CrawlDistance o1, CrawlDistance o2) {
                    int d1 = o1.getDistance();
                    int d2 = o2.getDistance();
                    if (d1 == d2)
                        return 0;
                    else if (d1 < d2)
                        return -1;
                    else
                        return 1;
                }
            });
            List<CrawlProduct> crawlList = new ArrayList<>();
            for (int i = 0; i < 6 && i < distanceList.size(); i++) {
                crawlList.add(distanceList.get(i).getCrawlProduct());
            }
            return crawlList;
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
//                    RevzillaCrawler.instance = new Thread(new RevzillaCrawler(crawlRepository, categoryRepository, crawlProductImageRepository, crawlSiteRepository, crawlStatusRepository, brandRepository));
                    RevzillaCrawler.instance = new Thread(new RevzillaCrawler(crawlRepository, categoryRepository, crawlProductImageRepository, crawlSiteRepository, brandRepository, statPending));
                    RevzillaCrawler.instance.start();
                    RevzillaCrawler.instance.join();
                    count = RevzillaCrawler.getCount();
                }
            } else if (site.equals("ynebikers")) {
                if (!YnebikersCrawler.isLock()) {
//                    YnebikersCrawler.instance = new Thread(new YnebikersCrawler(crawlRepository, categoryRepository, crawlProductImageRepository, crawlSiteRepository, crawlStatusRepository, brandRepository));
                    YnebikersCrawler.instance = new Thread(new YnebikersCrawler(crawlRepository, categoryRepository, crawlProductImageRepository, crawlSiteRepository, brandRepository, statPending));
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

    //Tri, add new crawl
    public Response<CrawlProduct> createCrawlProduct(CrawlProductModel crawlProductModel, MultipartFile[] images) {
        Response<CrawlProduct> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        //Check if model is null or don't have image
        if (crawlProductModel != null && images.length != 0) {
            //Try to save crawl product information to database
            try {
                //Map Model to entity
                CrawlProduct crawlProduct = crawlProductTransformer.CrawlProductModel_CrawlProductEntity(crawlProductModel);
                CrawlProduct result = crawlRepository.save(crawlProduct);
                if (images != null) {
                    addImagesToDatabase(result, crawlProductModel, images);
                }
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, result);
            } catch (Exception e) {
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
                LOGGER.error(e.getMessage());
            }
        }
        return response;
    }

    public void addImagesToDatabase(CrawlProduct entity, CrawlProductModel crawlProductModel, MultipartFile[] images) throws IOException {
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
        if (crawlProductModel != null) {
            //Find CrawlProduct in database
            Optional<CrawlProduct> crawlProductFromDatabase = crawlRepository.findById(crawlProductModel.getId());
            if (crawlProductFromDatabase.isPresent()) {
                try {
                    CrawlProduct result = crawlProductFromDatabase.get();
                    crawlRepository.save(crawlProductTransformer.UpdateEditCrawlProductModelCrawlProductEntity(crawlProductModel, result));
                    response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, result);

                    //Add Images
                    if (addedImages.length > 0) {
                        addImagesToDatabase(result, crawlProductModel, addedImages);
                    }

                    //Delete Images
                    if (!crawlProductModel.getDeleteImages().isEmpty()) {
                        ArrayList<CrawlProductImage> deleteImagesFromDatabase = new ArrayList<>();

                        for (int i : crawlProductModel.getDeleteImages()) {
                            CrawlProductImage tempDeleteImage = crawlProductImageRepository.findById(i).orElse(null);

                            if (tempDeleteImage != null) {
                                deleteImagesFromDatabase.add(tempDeleteImage);
                            }
                        }

                        if (!deleteImagesFromDatabase.isEmpty()) {
                            //Delete from database
                            crawlProductImageRepository.deleteAll(deleteImagesFromDatabase);

                            //Delete from Disk
                            for (CrawlProductImage i : deleteImagesFromDatabase) {
                                LOGGER.info("Root path : " + rootLocation);
                                LOGGER.info("ImageLink path : " + i.getImageLink());
                                LOGGER.info("File path : " + rootLocation + i.getImageLink());
                                File file = new File(rootLocation + "\\" + i.getImageLink());
                                file.delete();
                            }
                        }


                    }

                } catch (Exception e) {
                    response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return response;
    }

    public Response changeCrawlProductStatus(ChangeStatusCrawlModel changeStatusCrawlModel) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (changeStatusCrawlModel != null) {
            Optional<CrawlProduct> crawlProductFromDatabase = crawlRepository.findById(changeStatusCrawlModel.getCrawlProductId());
            //Check if there's a crawl product with the id in the database
            if (crawlProductFromDatabase.isPresent()) {
                //Change from Optional to CrawlProduct
                CrawlProduct result = crawlProductFromDatabase.get();
                //Check if the status already existed
                if (result.getStatus().getId() != changeStatusCrawlModel.getCrawlProductStatus()) {
                    //try to update status
                    try {
                        result.getStatus().setId(changeStatusCrawlModel.getCrawlProductStatus());
                        crawlRepository.save(result);
                        response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, result);
                    } catch (Exception e) {
                        response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
                        LOGGER.error(e.getMessage());
                    }
                }
            }
        }
        return response;
    }

    public Integer countPending() {
        Integer integer = crawlRepository.countPending();
        return integer;
    }

    public Page<CrawlProduct> getNewByPageable(Pageable pageable) {
        Page<CrawlProduct> products = crawlRepository.findAllByStatus(statPending, pageable);
        return products;
    }

    public List<CrawlProduct> getShowByPage(int page, int pageSize) {
        int from = (page - 1) * pageSize;
        return crawlRepository.getShowByPage(from, pageSize);
    }

    public List<ClientCrawlModel> getShowFrom(long time) {
        List<CrawlProduct> crawlProducts = crawlRepository.getShowFrom(time);
        List<ClientCrawlModel> clientCrawlModelList = new ArrayList<>();
        for (CrawlProduct crawl : crawlProducts) {
            ClientCrawlModel clientCrawlModel = new ClientCrawlModel();
            clientCrawlModel.setBranId(1);
            clientCrawlModel.setCategoryId(crawl.getSiteId().getId());
            clientCrawlModel.setId(crawl.getId());
            clientCrawlModel.setName(crawl.getName());
            clientCrawlModel.setPrice(crawl.getPrice());
            clientCrawlModel.setLastEdit(crawl.getLastEdit());
            List<CrawlProductImage> allByCrawlProductId = crawlProductImageRepository.findAllByCrawlProductId(crawl);
            String imgStr = "";
            for (int j = 0; j < allByCrawlProductId.size(); j++) {
                imgStr += allByCrawlProductId.get(j).getImageLink() + " ";
            }
            clientCrawlModel.setImages(imgStr);
            clientCrawlModelList.add(clientCrawlModel);
        }
        return clientCrawlModelList;
    }
//    public void DeleteBySite(String site) {
//        List<CrawlProductImage> imgBySite = crawlProductImageRepository.findAllBySite(site);
//        crawlProductImageRepository.deleteAll(imgBySite);
//        List<CrawlProduct> allBySite = crawlRepository.findAllBySite(site);
//        crawlRepository.deleteAll(allBySite);
//    }

}
