package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.team17.bikeworld.entity.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
public class ProductController extends AbstractController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/viewall")
    public String viewAllProduct() {

        Response<List<Product>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<Product> pros = productService.findAll();
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, pros);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/create")
    public void createProduct(String txtName, String txtDescription, Double txtPrice, String txtLongtitude, String txtLatitude, String txtAddress, String txtSeller, Integer txtCategory, Integer txtBrand, MultipartFile images) {
        ProductModel newProduct = new ProductModel();

        newProduct.setName(txtName);
        newProduct.setDescription(txtDescription);
        newProduct.setPrice(txtPrice);
        newProduct.setLongtitude(txtLongtitude);
        newProduct.setLatitude(txtLatitude);
        newProduct.setAddress(txtAddress);
        newProduct.setSeller(txtSeller);
        newProduct.setCategory(txtCategory);
        newProduct.setBrand(txtBrand);

        productService.createProduct(newProduct, null);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/update")
    public void updateProduct(@RequestParam(value = "txtId") int txtId,
                              @RequestParam(value = "txtName", required = false) String txtName,
                              @RequestParam(value = "txtDescription", required = false) String txtDescription,
                              @RequestParam(value = "txtPrice", required = false) Double txtPrice,
                              @RequestParam(value = "txtLongtitude", required = false) String txtLongtitude,
                              @RequestParam(value = "txtLatitude", required = false) String txtLatitude,
                              @RequestParam(value = "txtAddress", required = false) String txtAddress,
                              @RequestParam(value = "txtSeller", required = false) String txtSeller,
                              @RequestParam(value = "txtCategory", required = false) Integer txtCategory,
                              @RequestParam(value = "txtBrand", required = false) Integer txtBrand,
                              @RequestParam(required = false) MultipartFile images) {

        ProductModel updatedProduct = new ProductModel();

        updatedProduct.setId(txtId);
        updatedProduct.setName(txtName);
        updatedProduct.setDescription(txtDescription);
        updatedProduct.setPrice(txtPrice);
        updatedProduct.setLongtitude(txtLongtitude);
        updatedProduct.setLatitude(txtLatitude);
        updatedProduct.setAddress(txtAddress);
        updatedProduct.setSeller(txtSeller);
        updatedProduct.setCategory(txtCategory);
        updatedProduct.setBrand(txtBrand);

        productService.updateProduct(updatedProduct,null);
    }
}
