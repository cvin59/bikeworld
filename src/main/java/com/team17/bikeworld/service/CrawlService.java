package com.team17.bikeworld.service;

import com.team17.bikeworld.crawl.crawler.RevzillaCrawler;
import com.team17.bikeworld.crawl.crawler.YnebikersCrawler;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.entity.CrawlProductImage;
import com.team17.bikeworld.entity.CrawlSite;
import com.team17.bikeworld.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrawlService {


    private final CrawlRepository crawlRepository;
    private final CrawlProductImageRepository crawlProductImageRepository;
    private final CategoryRepository categoryRepository;
    private final CrawlSiteRepository crawlSiteRepository;
    private final CrawlStatusRepository crawlStatusRepository;


    public CrawlService(CrawlRepository crawlRepository, CrawlProductImageRepository crawlProductImageRepository, CategoryRepository categoryRepository, CrawlSiteRepository crawlSiteRepository, CrawlStatusRepository crawlStatusRepository) {
        this.crawlRepository = crawlRepository;
        this.crawlProductImageRepository = crawlProductImageRepository;
        this.categoryRepository = categoryRepository;
        this.crawlSiteRepository = crawlSiteRepository;
        this.crawlStatusRepository = crawlStatusRepository;
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
                    RevzillaCrawler.instance = new Thread(new RevzillaCrawler(crawlRepository, categoryRepository, crawlProductImageRepository, crawlSiteRepository, crawlStatusRepository));
                    RevzillaCrawler.instance.start();
                    RevzillaCrawler.instance.join();
                    count = RevzillaCrawler.getCount();
                }
            } else if (site.equals("ynebikers")) {
                if (!YnebikersCrawler.isLock()) {
                    YnebikersCrawler.instance = new Thread(new YnebikersCrawler(crawlRepository, categoryRepository, crawlProductImageRepository, crawlSiteRepository, crawlStatusRepository));
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


//    public void DeleteBySite(String site) {
//        List<CrawlProductImage> imgBySite = crawlProductImageRepository.findAllBySite(site);
//        crawlProductImageRepository.deleteAll(imgBySite);
//        List<CrawlProduct> allBySite = crawlRepository.findAllBySite(site);
//        crawlRepository.deleteAll(allBySite);
//    }

}
