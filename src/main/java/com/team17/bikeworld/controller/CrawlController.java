package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.service.CrawlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.API_CRAWL;

@Controller
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
}
