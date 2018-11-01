package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.*;
import com.team17.bikeworld.transformer.ProductTransformer;
import com.team17.bikeworld.viewModel.ProductViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ProductController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;
    @Autowired
    ProductTransformer productTransformer;

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


        Response<List<ProductViewModel>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<ProductViewModel> views = new ArrayList<>();


            List<Product> products = productService.findAll();
            for (Product product : products
            ) {
                ProductViewModel view = new ProductViewModel();
                List<ProductImage> imgs = productService.getImagesByProduct(product);
                view = productTransformer.ProductEntityToView(product, view, imgs);
                views.add(view);
            }

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, views);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(CoreConstant.API_PRODUCT)
    public String createProduct(@RequestParam String productModelString, MultipartFile[] images) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            ProductModel newProduct = gson.fromJson(productModelString, ProductModel.class);
            productService.createProduct(newProduct, images);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(CoreConstant.API_PRODUCT)
    public String updateProduct(@RequestParam String productModelString, @RequestParam List<Integer> deleteImgList, MultipartFile[] images) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            ProductModel updatedProduct = gson.fromJson(productModelString, ProductModel.class);
            productService.updateProduct(updatedProduct, images);
            if (deleteImgList != null) {
                productService.deleteImage(deleteImgList);
            }
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/{id}")
    public String getProductById(@PathVariable int id) {
        Response<ProductViewModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            Product product = productService.getProductById(id);
            List<ProductImage> images = productService.getImagesByProduct(product);

            ProductViewModel view = new ProductViewModel();
            view = productTransformer.ProductEntityToView(product, view, images);

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, view);
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


        Response<List<ProductViewModel>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<ProductViewModel> views = new ArrayList<>();


            List<Product> products = productService.getProductByCate(id, pageable);
            for (Product product : products
            ) {
                ProductViewModel view = new ProductViewModel();
                List<ProductImage> imgs = productService.getImagesByProduct(product);
                view = productTransformer.ProductEntityToView(product, view, imgs);
                views.add(view);
            }

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, views);
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


        Response<List<ProductViewModel>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<ProductViewModel> views = new ArrayList<>();


            List<Product> products = productService.getProductByBrand(id, pageable);
            for (Product product : products
            ) {
                ProductViewModel view = new ProductViewModel();
                List<ProductImage> imgs = productService.getImagesByProduct(product);
                view = productTransformer.ProductEntityToView(product, view, imgs);
                views.add(view);
            }

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, views);
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

        Response<List<ProductViewModel>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<ProductViewModel> views = new ArrayList<>();


            List<Product> products = productService.getProductBySeller(seller, pageable);
            for (Product product : products
            ) {
                ProductViewModel view = new ProductViewModel();
                List<ProductImage> imgs = productService.getImagesByProduct(product);
                view = productTransformer.ProductEntityToView(product, view, imgs);
                views.add(view);
            }

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, views);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}