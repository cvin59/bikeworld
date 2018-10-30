package com.team17.bikeworld.viewModel;

import com.google.gson.annotations.Expose;
import com.team17.bikeworld.entity.Product;

import java.util.List;

public class ProductViewModel {
    @Expose
    Product productInfo;
    @Expose
    List<String> img;

    public ProductViewModel() {
    }

    public ProductViewModel(Product product, List<String> img) {
        this.productInfo = product;
        this.img = img;
    }

    public Product getProduct() {
        return productInfo;
    }

    public void setProduct(Product product) {
        this.productInfo = product;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}
