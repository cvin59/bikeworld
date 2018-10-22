package com.team17.bikeworld.service;

import com.team17.bikeworld.crawl.crawler.RevzillaCrawler;
import com.team17.bikeworld.crawl.crawler.YnebikersCrawler;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.repositories.CategoryRepository;
import com.team17.bikeworld.repositories.CrawlProductImageRepository;
import com.team17.bikeworld.repositories.CrawlRepository;

import java.util.List;

public class CrawlService {

    private final CrawlRepository crawlRepository;
    private final CrawlProductImageRepository crawlProductImageRepository;
    private  final CategoryRepository categoryRepository;


    public CrawlService(CrawlRepository crawlRepository, CrawlProductImageRepository crawlProductImageRepository, CategoryRepository categoryRepository) {
        this.crawlRepository = crawlRepository;
        this.crawlProductImageRepository = crawlProductImageRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<CrawlProduct> getAll(){
        List<CrawlProduct> products = crawlRepository.findAll();
        return products;
    }

    public int RunCrawl(String site){
        int count = 0;
        try {

            if (site.equals("revzilla")) {
                if (!RevzillaCrawler.isLock()) {
                    RevzillaCrawler.instance = new Thread(new RevzillaCrawler(crawlRepository, categoryRepository, crawlProductImageRepository));
                    RevzillaCrawler.instance.start();
                    RevzillaCrawler.instance.join();
                }
            } else if (site.equals("ynebikers")) {
                if (!YnebikersCrawler.isLock()) {
                    YnebikersCrawler.instance = new Thread(new YnebikersCrawler(crawlRepository, categoryRepository, crawlProductImageRepository));
                    YnebikersCrawler.instance.start();
                    YnebikersCrawler.instance.join();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
