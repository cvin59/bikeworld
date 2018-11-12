package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.model.ProductRatingModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.*;
import com.team17.bikeworld.transformer.ProductRatingTransformer;
import com.team17.bikeworld.transformer.ProductTransformer;
import com.team17.bikeworld.viewModel.MultiProductModel;
import com.team17.bikeworld.viewModel.MultiRatingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductRatingTransformer productRatingTransformer;

    @GetMapping(CoreConstant.API_PRODUCT + "/viewall")
    public String viewAllProduct(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
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
        Pageable pageable = PageRequest.of(page - 1, size, sortable);


        Response<MultiProductModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            MultiProductModel data = new MultiProductModel();

            List<ProductModel> views = new ArrayList<>();
            Page<Product> products = productService.findAll(pageable);

            for (Product product : products
            ) {
                ProductModel view = new ProductModel();
                List<ProductImage> imgs = productService.getImagesByProduct(product);

                view = productTransformer.ProductEntityToView(product, view, imgs);
                views.add(view);
            }

            data.setTotalPage(products.getTotalPages());
            data.setTotalRecord(products.getTotalElements());
            data.setProductInfo(views);

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(CoreConstant.API_PRODUCT)
    public String createProduct(@RequestParam String productModelString, MultipartFile[] images) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info(productModelString);
            ProductModel newProduct = gson.fromJson(productModelString, ProductModel.class);
            productService.createProduct(newProduct, images);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
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
            LOGGER.info(deleteImgList.toString());
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
        Response<ProductModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            Product product = productService.getProductById(id);
            List<ProductImage> images = productService.getImagesByProduct(product);

            ProductModel view = new ProductModel();
            view = productTransformer.ProductEntityToView(product, view, images);

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, view);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/search")
    public String searchTradeItem(@RequestParam(name = "searchValue") String searchValue,
                                  @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", required = false, defaultValue = "12 ") Integer size,
                                  @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                  @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page - 1, size, sortable);

        Response<MultiProductModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        MultiProductModel data = new MultiProductModel();

        List<ProductModel> views = new ArrayList<>();
        Page<Product> products = productService.searchByName(searchValue, pageable);

        for (Product product : products
        ) {
            ProductModel view = new ProductModel();
            List<ProductImage> imgs = productService.getImagesByProduct(product);

            view = productTransformer.ProductEntityToView(product, view, imgs);
            views.add(view);
        }

        data.setTotalPage(products.getTotalPages());
        data.setTotalRecord(products.getTotalElements());
        data.setCurrentPage(page);
        data.setProductInfo(views);

        response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/{seller}/search")
    public String searchTradeItemBySeller(@PathVariable(name = "seller") String seller,
                                          @RequestParam(name = "searchValue") String searchValue,
                                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                          @RequestParam(name = "size", required = false, defaultValue = "5 ") Integer size,
                                          @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                          @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page - 1, size, sortable);

        Response<MultiProductModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        MultiProductModel data = new MultiProductModel();

        List<ProductModel> views = new ArrayList<>();
        Page<Product> products = productService.searchByNameAndSeller(searchValue, seller, pageable);

        for (Product product : products
        ) {
            ProductModel view = new ProductModel();
            List<ProductImage> imgs = productService.getImagesByProduct(product);

            view = productTransformer.ProductEntityToView(product, view, imgs);
            views.add(view);
        }

        data.setTotalPage(products.getTotalPages());
        data.setTotalRecord(products.getTotalElements());
        data.setCurrentPage(page);
        data.setProductInfo(views);

        response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/category/{id}")
    public String getByCategory(@PathVariable int id,
                                @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
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
        Pageable pageable = PageRequest.of(page - 1, size, sortable);


        Response<List<ProductModel>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<ProductModel> views = new ArrayList<>();


            List<Product> products = productService.getProductByCate(id, pageable);
            for (Product product : products
            ) {
                ProductModel view = new ProductModel();
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
                             @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
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
        Pageable pageable = PageRequest.of(page - 1, size, sortable);


        Response<List<ProductModel>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<ProductModel> views = new ArrayList<>();


            List<Product> products = productService.getProductByBrand(id, pageable);
            for (Product product : products
            ) {
                ProductModel view = new ProductModel();
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
                              @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                              @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
                              @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page - 1, size, sortable);

        Response<MultiProductModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            MultiProductModel data = new MultiProductModel();

            List<ProductModel> views = new ArrayList<>();
            Page<Product> products = productService.getProductBySeller(seller, pageable);

            for (Product product : products
            ) {
                ProductModel view = new ProductModel();
                List<ProductImage> imgs = productService.getImagesByProduct(product);

                view = productTransformer.ProductEntityToView(product, view, imgs);
                views.add(view);
            }

            data.setTotalPage(products.getTotalPages());
            data.setTotalRecord(products.getTotalElements());
            data.setCurrentPage(page);
            data.setProductInfo(views);

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/rate/right")
    public String checkRateRight(@RequestParam int productId,
                                 @RequestParam String rater) {

        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            if(orderService.checkRatingRight(productId,rater)){
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(CoreConstant.API_PRODUCT + "/rate")
    public String rateProduct(@RequestParam String rateModelString) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            ProductRatingModel newRate = gson.fromJson(rateModelString, ProductRatingModel.class);
            productService.rateProduct(newRate);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/rate/{productId}")
    public String showRates(@PathVariable int productId,
                            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
                            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {

        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page - 1, size, sortable);

        Response<MultiRatingModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            MultiRatingModel data = new MultiRatingModel();
            List<ProductRatingModel> views = new ArrayList<>();
            Page<ProductRating> ratings = productService.getRates(productId, pageable);

            for (ProductRating rating : ratings
            ) {
                ProductRatingModel view = new ProductRatingModel();

                view = productRatingTransformer.RatingEntityToModel(view, rating);
                String avatar = userService.findUserByUsername(view.getRater()).getProfileId().getAvatarLink();
                view.setAvatar(avatar);
                views.add(view);
            }

            data.setTotalPage(ratings.getTotalPages());
            data.setTotalRecord(ratings.getTotalElements());
            data.setCurrentPage(page);
            data.setProductRatings(views);

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_PRODUCT + "/rate")
    public String showRate(@RequestParam int productId,
                           @RequestParam String rater) {

        Response<ProductRatingModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            ProductRating rating = productService.getRate(productId, rater);
            if (rating != null) {
                ProductRatingModel ratingModel = new ProductRatingModel();
                ratingModel = productRatingTransformer.RatingEntityToModel(ratingModel, rating);

                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, ratingModel);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}