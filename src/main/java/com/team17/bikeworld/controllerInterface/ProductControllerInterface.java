package com.team17.bikeworld.controllerInterface;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public interface ProductControllerInterface {

    @RequestMapping(value = "/product/create", method = RequestMethod.GET)
    public void createProduct(@RequestParam(value = "txtName") String name,
                              @RequestParam(value = "txtDescription") String description,
                              @RequestParam(value = "txtPrice") float price,
                              @RequestParam(value = "txtLongtitude") String longtitude,
                              @RequestParam(value = "txtLatitude") String latitude,
                              @RequestParam(value = "txtAddress") String address,
                              @RequestParam(value = "txtPostdate") String postDate,
                              @RequestParam(value = "txtSeller") String seller);
}
