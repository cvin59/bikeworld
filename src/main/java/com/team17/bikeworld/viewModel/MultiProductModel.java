package com.team17.bikeworld.viewModel;

import com.google.gson.annotations.Expose;
import com.team17.bikeworld.model.ProductModel;

import java.util.List;

public class MultiProductModel {
    @Expose
    List<ProductModel> productInfo;
    @Expose
    int totalPage;
    @Expose
    long totalRecord;
    @Expose
    int currentPage;

    public MultiProductModel() {
    }

    public List<ProductModel> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(List<ProductModel> productInfo) {
        this.productInfo = productInfo;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }
}
