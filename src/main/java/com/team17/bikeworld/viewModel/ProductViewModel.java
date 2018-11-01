package com.team17.bikeworld.viewModel;

import com.google.gson.annotations.Expose;
import com.team17.bikeworld.entity.Product;

import java.util.List;

public class ProductViewModel {
    @Expose
    Product productInfo;
    @Expose
    List<String> ProductImg;

    public ProductViewModel() {
    }

    public ProductViewModel(Product productInfo, List<String> productImg) {
        this.productInfo = productInfo;
        ProductImg = productImg;
    }

    public Product getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(Product productInfo) {
        this.productInfo = productInfo;
    }

    public List<String> getProductImg() {
        return ProductImg;
    }

    public void setProductImg(List<String> productImg) {
        ProductImg = productImg;
    }
}
