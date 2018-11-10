package com.team17.bikeworld.viewModel;

import com.google.gson.annotations.Expose;
import com.team17.bikeworld.entity.ProductRating;
import com.team17.bikeworld.model.ProductRatingModel;

import java.util.List;

public class MultiRatingModel {
    @Expose
    List<ProductRatingModel> productRatings;
    @Expose
    int totalPage;
    @Expose
    long totalRecord;
    @Expose
    int currentPage;

    public List<ProductRatingModel> getProductRatings() {
        return productRatings;
    }

    public void setProductRatings(List<ProductRatingModel> productRatings) {
        this.productRatings = productRatings;
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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
