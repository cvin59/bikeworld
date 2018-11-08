package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;

public class ChangeStatusCrawlModel {
    @Expose
    int crawlProductId;

    @Expose
    int crawlProductStatus;

    public int getCrawlProductId() {
        return crawlProductId;
    }

    public void setCrawlProductId(int crawlProductId) {
        this.crawlProductId = crawlProductId;
    }

    public int getCrawlProductStatus() {
        return crawlProductStatus;
    }

    public void setCrawlProductStatus(int crawlProductStatus) {
        this.crawlProductStatus = crawlProductStatus;
    }
}
