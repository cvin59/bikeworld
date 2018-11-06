package com.team17.bikeworld.viewModel;

import com.google.gson.annotations.Expose;
import com.team17.bikeworld.model.OrderModel;

import java.util.List;

public class MultiOrderModel {
    @Expose
    List<OrderModel> orders;
    @Expose
    int totalPage;
    @Expose
    long totalRecord;
    @Expose
    int currentPage;

    public MultiOrderModel() {
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
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
