package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.controller.ProductController;
import com.team17.bikeworld.crawl.crawler.RevzillaCrawler;
import com.team17.bikeworld.crawl.crawler.YnebikersCrawler;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.entity.CrawlProductImage;
import com.team17.bikeworld.model.CrawlProductModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.team17.bikeworld.entity.CrawlSite;
import com.team17.bikeworld.model.CrawlProductModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrawlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlService.class);

    private final CrawlRepository crawlRepository;
    private final CrawlProductImageRepository crawlProductImageRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final CrawlStatusRepository crawlStatusRepository;
    private final CrawlSiteRepository crawlSiteRepository;


    public CrawlService(CrawlRepository crawlRepository, CrawlProductImageRepository crawlProductImageRepository, CategoryRepository categoryRepository, CrawlSiteRepository crawlSiteRepository, CrawlStatusRepository crawlStatusRepository, BrandRepository brandRepository) {
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
        if (crawlProductModel != null){
            try {
                CrawlProduct crawlProduct = mapCrawlProduct(crawlProductModel);

                CrawlProduct result = crawlRepository.save(crawlProduct);
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, result);
            }
            catch (Exception e){
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
                LOGGER.error(e.getMessage());
            }
        }
        return response;
    }

    //Used to map from CrawlProductModel to CrawlProduct
    private CrawlProduct mapCrawlProduct(CrawlProductModel model){
        CrawlProduct result = new CrawlProduct();
        result.setId(0);
        result.setName(model.getName());
//        LOGGER.info("Mapped crawl product name:" + result.getName());
        //Find Category by Id,
        result.setCategoryId(categoryRepository.findById(model.getCatergoryId()).orElse(null));
//        LOGGER.info("Mapped crawl product category:" + result.getCategoryId().getName());
        //Convert price from float to string
        result.setPrice(Float.toString(model.getPrice()));
//        LOGGER.info("Mapped crawl product price:" + result.getPrice());
        result.setBrandId(brandRepository.findById(model.getBranId()).orElse(null));
//        LOGGER.info("Mapped crawl product brand:" + result.getBrandId().getName());
        result.setStatus(crawlStatusRepository.findById(model.getStatus()).orElse(null));
//        LOGGER.info("Mapped crawl product status:" + result.getStatus());
        result.setDescription(model.getDescription());
//        LOGGER.info("Mapped crawl product description:" + result.getDescription());
        result.setUrl(null);
        result.setSiteId(null);
//        LOGGER.info("Mapped crawl product:" + result);
        result.setHash(null);
        return result;
    }

    public Integer countPending() {
        Integer integer = crawlRepository.countPending();
        return integer;
    }


//    public void DeleteBySite(String site) {
//        List<CrawlProductImage> imgBySite = crawlProductImageRepository.findAllBySite(site);
//        crawlProductImageRepository.deleteAll(imgBySite);
//        List<CrawlProduct> allBySite = crawlRepository.findAllBySite(site);
//        crawlRepository.deleteAll(allBySite);
//    }


}
