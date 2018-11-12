package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.model.ChangeStatusCrawlModel;
import com.team17.bikeworld.model.CrawlProductModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.service.CrawlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.API_CRAWL;

@Controller
@CrossOrigin
public class CrawlController extends AbstractController {

    private final Logger LOGGER = LoggerFactory.getLogger(CrawlController.class);

    private final CrawlService crawlService;

    public CrawlController(CrawlService crawlService) {
        this.crawlService = crawlService;
    }


    @PostMapping(API_CRAWL + "/run/{site}")
    public String runCrawl(@PathVariable String site) {
        LOGGER.info(site);
        Response<Integer> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        Integer count = crawlService.runCrawl(site);
        response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, count);
        return gson.toJson(response);
    }


    @PostMapping(API_CRAWL + "/stop/{site:.+}")
    public String stopCrawl(@PathVariable String site) {

        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        crawlService.stopCrawl(site);
        response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
        return gson.toJson(response);
    }


    @GetMapping(API_CRAWL+ "/view/{site:.+}")
    public String viewCrawl(@PathVariable String site) {
        Response<List<CrawlProduct>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<CrawlProduct> crawlPros = crawlService.viewCrawl(site);
//            LOGGER.info(crawlPros.toString());
            if (crawlPros != null) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, crawlPros);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(API_CRAWL+ "/pending/{page:.+}")
    public String getPending(@PathVariable int page) {
        Response<List<CrawlProduct>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<CrawlProduct> crawlPros = crawlService.getNewByPage(page, 20);
//            LOGGER.info(crawlPros.toString());
            if (crawlPros != null) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, crawlPros);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


    @GetMapping(API_CRAWL+ "/approve/{page:.+}")
    public String getApprove(@PathVariable int page) {
        Response<List<CrawlProduct>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<CrawlProduct> crawlPros = crawlService.getShowByPage(page, 20);
//            LOGGER.info(crawlPros.toString());
            if (crawlPros != null) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, crawlPros);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


    @PostMapping(API_CRAWL)
    public String createCrawl(@RequestParam String crawlString, MultipartFile[] images){
        LOGGER.info("Request Param: " + crawlString);
        //Translate json to CrawlProduct
        CrawlProductModel crawlProductModel = gson.fromJson(crawlString, CrawlProductModel.class);
        //Add crawl product to database and response
        Response<CrawlProduct> response = crawlService.createCrawlProduct(crawlProductModel, images);
        return gson.toJson(response);
    }

    @PutMapping(API_CRAWL)
    public String editCrawl(@RequestParam String objectString, MultipartFile[] addedImages){
        LOGGER.info("Request Param: " + objectString);
        //Translate json to EditCrawlProductModel
        CrawlProductModel crawlProductModel = gson.fromJson(objectString, CrawlProductModel.class);
        //Edit crawl product in database and response
        Response<CrawlProduct> response = crawlService.editCrawlProduct(crawlProductModel, addedImages);
        return gson.toJson(response);
    }

    @PutMapping(API_CRAWL + "/status")
    public String changeStatus(@RequestParam String objectString){
        //Translate json to ChangeStatusCrawlModel
        ChangeStatusCrawlModel changeStatusCrawlModel = gson.fromJson(objectString, ChangeStatusCrawlModel.class);
        //Edit crawl product in database and response
        Response response = crawlService.changeCrawlProductStatus(changeStatusCrawlModel);
        return gson.toJson(response);
    }

    @GetMapping(API_CRAWL + "/countpending")
    public String getCountPending() {
        Integer countPending = crawlService.countPending();
        return countPending.toString();
    }

    @GetMapping(API_CRAWL + "/{id}")
    public String getById(@PathVariable("id") Integer id){
        Response<CrawlProduct> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            CrawlProduct result = crawlService.getById(id);
            if (result != null){
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, result);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        }catch (Exception e){
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


    @GetMapping(API_CRAWL)
    public String getAll() {
        Response<List<CrawlProduct>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<CrawlProduct> crawlPros = crawlService.getAll();
            if (crawlPros != null) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, crawlPros);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(API_CRAWL+ "/detail/{id:.+}")
    public String findByid(@PathVariable int id) {
        Response<List<CrawlProduct>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<CrawlProduct> crawlPros = crawlService.findWithGuess(id);
            if (crawlPros != null) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, crawlPros);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}
