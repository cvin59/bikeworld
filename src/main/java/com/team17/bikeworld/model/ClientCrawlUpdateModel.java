package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class ClientCrawlUpdateModel {

    @Expose
    private Long lastEdit;
    @Expose
    private List<ClientCrawlModel> crawlModelList;
    @Expose
    private List<Integer> delList;

    public ClientCrawlUpdateModel() {
    }

    public Long getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Long lastEdit) {
        this.lastEdit = lastEdit;
    }

    public List<ClientCrawlModel> getCrawlModelList() {
        return crawlModelList;
    }

    public void setCrawlModelList(List<ClientCrawlModel> crawlModelList) {
        this.crawlModelList = crawlModelList;
    }

    public List<Integer> getDelList() {
        return delList;
    }

    public void setDelList(List<Integer> delList) {
        this.delList = delList;
    }
}
