package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.CrawlProductImage;
import com.team17.bikeworld.entity.EventImage;
import com.team17.bikeworld.model.CrawlProductImageModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.CrawlImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.*;

@Controller
@CrossOrigin
public class CrawlImageController extends AbstractController{
    private final Logger LOGGER = LoggerFactory.getLogger(CrawlController.class);

    @Autowired
    CrawlImageService crawlImageService;

    //Get all images of a crawl product based on its id
    @GetMapping(API_CRAWL_IMAGE + "/crawl/{id}")
    public String getImagesByCrawlId(@PathVariable("id") Integer id){
        Response<List<CrawlProductImageModel>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<CrawlProductImageModel> result = crawlImageService.getImageOfCrawlProduct(id);
            LOGGER.info("result list:" + result);
            if (result != null) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, result);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}
