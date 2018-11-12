package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.ProductImage;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.ProductImageService;
import com.team17.bikeworld.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.API_PRODUCT_IMAGE;
import static com.team17.bikeworld.common.CoreConstant.MESSAGE_SERVER_ERROR;
import static com.team17.bikeworld.common.CoreConstant.STATUS_CODE_SERVER_ERROR;

@RestController
@CrossOrigin
public class ProductImageController extends AbstractController{
    private final Logger LOGGER = LoggerFactory.getLogger(ProductImageController.class);

    private final ProductImageService productImageService;
    private final ProductService productService;

    public ProductImageController(ProductImageService productImageService, ProductService productService) {
        this.productImageService = productImageService;
        this.productService = productService;
    }


    @GetMapping(API_PRODUCT_IMAGE + "/product/{id}")
    public String getEvent(@PathVariable("id") Integer id) {
        Response<List<ProductImage>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<ProductImage> productImages = productImageService.getProductImagesByProductId(id);

            if (productImages != null) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, productImages);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}
