package com.team17.bikeworld.controller;

<<<<<<< HEAD
<<<<<<< HEAD
import com.team17.bikeworld.controllerInterface.ProductControllerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import com.team17.bikeworld.serviceInterface.ProductServiceInterface;

public class ProductController extends AbstractController implements ProductControllerInterface {

    @Autowired
    ProductServiceInterface productService;

    @Override
    public void createProduct(String name,
                              String description,
                              float price,
                              String longtitude,
                              String latitude,
                              String address,
                              String postDate,
                              String seller) {

    }
=======
=======
import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.*;
>>>>>>> master
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.team17.bikeworld.entity.*;

<<<<<<< HEAD
@RestController
@CrossOrigin
public class ProductController {
>>>>>>> 42bc2ae27e48f54a1727b3683f59a650b4795bf3
=======
import java.util.List;

@Controller
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
>>>>>>> master
}
