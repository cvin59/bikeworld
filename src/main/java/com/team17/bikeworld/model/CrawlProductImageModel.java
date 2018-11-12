package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;

//used to return crawl product images from server to client, here to hide crawlProduct detail apart from crawlProduct_id
public class CrawlProductImageModel {
    @Expose
    private int id;

    @Expose
    private String imageLink;

    @Expose
    private Integer crawlProduct_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Integer getCrawlProduct_id() {
        return crawlProduct_id;
    }

    public void setCrawlProduct_id(Integer crawlProduct_id) {
        this.crawlProduct_id = crawlProduct_id;
    }
}
