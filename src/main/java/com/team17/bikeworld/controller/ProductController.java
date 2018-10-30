package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.team17.bikeworld.entity.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@RestController
@CrossOrigin
public class ProductController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/viewall")
    public String viewAllProduct(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                 @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                 @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                 @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);


        Response<List<Product>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<Product> pros = productService.findAll();
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, pros);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(CoreConstant.API_PRODUCT)
    public void createProduct(@RequestParam String productModelString, MultipartFile[] images) {
        ProductModel newProduct = gson.fromJson(productModelString, ProductModel.class);
        productService.createProduct(newProduct, images);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/update")
    public void updateProduct(@RequestParam String productModelString, MultipartFile[] images) {
        ProductModel updatedProduct = gson.fromJson(productModelString, ProductModel.class);
        productService.updateProduct(updatedProduct, images);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/{id}")
    public @ResponseBody
    String getProductById(@PathVariable int id) {
        Response<Product> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            Product product = productService.getProductById(id);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, product);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/search/{id}/{name}")
    public String searchTradeItem(@PathVariable int id, @PathVariable String name) {

        Response<List<Product>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<Product> pros = null;
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, pros);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/category/{id}")
    public String getByCategory(@PathVariable int id,
                                @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);


        Response<List<Product>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<Product> pros = productService.getProductByCate(id, pageable);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, pros);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/brand/{id}")
    public String getByBrand(@PathVariable int id,
                             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                             @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                             @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                             @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {

        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);


        Response<List<Product>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<Product> pros = productService.getProductByCate(id, pageable);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, pros);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/seller/{seller}")
    public String getBySeller(@PathVariable String seller,
                              @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                              @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                              @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);

        Response<List<Product>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<Product> pros = productService.getProductBySeller(seller, pageable);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, pros);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}