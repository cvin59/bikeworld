package com.team17.bikeworld.common;

import com.team17.bikeworld.entity.CrawlProduct;

public class CrawlDistance {

    private int distance;
    private CrawlProduct crawlProduct;

    public CrawlDistance(int distance, CrawlProduct crawlProduct) {
        this.distance = distance;
        this.crawlProduct = crawlProduct;
    }

    public int getDistance() {
        return distance;
    }

    public CrawlProduct getCrawlProduct() {
        return crawlProduct;
    }

}
