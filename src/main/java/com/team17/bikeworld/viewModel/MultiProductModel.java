package com.team17.bikeworld.viewModel;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MultiProductModel {
    @Expose
    List<ProductViewModel> viewModels;
    @Expose
    int totalPage;
    @Expose
    long totalRecord;
    @Expose
    int currentPage;

    public MultiProductModel() {
    }

    public List<ProductViewModel> getViewModels() {
        return viewModels;
    }

    public void setViewModels(List<ProductViewModel> viewModels) {
        this.viewModels = viewModels;
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
