package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.crawl.crawler.RevzillaCrawler;
import com.team17.bikeworld.crawl.crawler.YnebikersCrawler;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.entity.CrawlProductImage;
import com.team17.bikeworld.model.CrawlProductModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrawlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlService.class);

    private final CrawlRepository crawlRepository;
    private final CrawlProductImageRepository crawlProductImageRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final CrawlStatusRepository crawlStatusRepository;


    public CrawlService(CrawlRepository crawlRepository, CrawlProductImageRepository crawlProductImageRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, CrawlStatusRepository crawlStatusRepository) {
        this.crawlRepository = crawlRepository;
        this.crawlProductImageRepository = crawlProductImageRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.crawlStatusRepository = crawlStatusRepository;
    }

    public List<CrawlProduct> getAll() {
        List<CrawlProduct> products = crawlRepository.findAll();
        return products;
    }

    public List<CrawlProduct> viewCrawl(String site) {
        String url = null;
        if (site.equals("revzilla")) {
            url = RevzillaCrawler.baseLink;
        } else if (site.equals("ynebikers")) {
            url = YnebikersCrawler.baseLink;
        }
        if (url != null) {
            List<CrawlProduct> products = crawlRepository.findAllBySite(url);
            return products;
        } else {
            return null;
        }
    }

    public Response<CrawlProduct> createCrawlProduct(CrawlProductModel crawlProductModel){
        LOGGER.info("Request create crawl model: " + crawlProductModel);
        Response<CrawlProduct> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (crawlProductModel != null){
            try {
                LOGGER.info("Before Save");
                CrawlProduct crawlProduct = mapCrawlProduct(crawlProductModel);

                CrawlProduct result = crawlRepository.addCrawlProduct();
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, result);
            }
            catch (Exception e){
                LOGGER.error(e.getMessage(), e.getCause());
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            }
        }
        return response;
    }


    public Integer deleteAllBySite(String site) {
        int i = crawlRepository.deleteAllBySite(site);
        return i;
    }

    public int runCrawl(String site) {
        int count = 0;
        try {
            if (site.equals("revzilla")) {
                if (!RevzillaCrawler.isLock()) {
                    RevzillaCrawler.instance = new Thread(new RevzillaCrawler(crawlRepository, categoryRepository, crawlProductImageRepository));
                    RevzillaCrawler.instance.start();
                    RevzillaCrawler.instance.join();
                    count = RevzillaCrawler.getCount();
                }
            } else if (site.equals("ynebikers")) {
                if (!YnebikersCrawler.isLock()) {
                    YnebikersCrawler.instance = new Thread(new YnebikersCrawler(crawlRepository, categoryRepository, crawlProductImageRepository));
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


    public void DeleteBySite(String site) {
        List<CrawlProductImage> imgBySite = crawlProductImageRepository.findAllBySite(site);
        crawlProductImageRepository.deleteAll(imgBySite);
        List<CrawlProduct> allBySite = crawlRepository.findAllBySite(site);
        crawlRepository.deleteAll(allBySite);
    }

    //Used to map from CrawlProductModel to CrawlProduct
    private CrawlProduct mapCrawlProduct(CrawlProductModel model){
        CrawlProduct result = new CrawlProduct();
        result.setId(0);
        result.setName(model.getName());
        LOGGER.info("Mapped crawl product name:" + result.getName());
        //Find Category by Id,
        result.setCategoryId(categoryRepository.findById(model.getCatergoryId()).orElse(null));
        LOGGER.info("Mapped crawl product category:" + result.getCategoryId().getName());
        //Convert price from float to string
        result.setPrice(Float.toString(model.getPrice()));
        LOGGER.info("Mapped crawl product price:" + result.getPrice());
        result.setBrandId(brandRepository.findById(model.getBranId()).orElse(null));
        LOGGER.info("Mapped crawl product brand:" + result.getBrandId().getName());
        result.setStatus(crawlStatusRepository.findById(model.getStatus()).orElse(null));
        LOGGER.info("Mapped crawl product status:" + result.getStatus());
        result.setDesc(model.getDescription());
        LOGGER.info("Mapped crawl product description:" + result.getDesc());
        result.setUrl(null);
        result.setSiteId(null);
        LOGGER.info("Mapped crawl product:" + result);
        return result;
    }

}
