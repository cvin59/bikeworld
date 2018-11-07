package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.model.CrawlProductModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.service.CrawlService;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.API_CRAWL;

@Controller
public class CrawlController extends AbstractController {

    private final Logger LOGGER = LoggerFactory.getLogger(CrawlController.class);
    private final CrawlService crawlService;
    private final int defaultPageSize = 20;


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

    @PostMapping(API_CRAWL + "/create")
    public String createCrawl(@RequestParam String crawlString) {
        LOGGER.info("Request Param: " + crawlString);
        //Translate json to CrawlProduct
        CrawlProductModel crawlProductModel = gson.fromJson(crawlString, CrawlProductModel.class);
        //Add crawl product to database and response
        Response<CrawlProduct> response = crawlService.createCrawlProduct(crawlProductModel);
        return gson.toJson(response);
    }


    @GetMapping(API_CRAWL + "/view/{site:.+}")
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

    @GetMapping(API_CRAWL + "/countpending")
    public String getCountPending() {
        Integer countPending = crawlService.countPending();
        return countPending.toString();
    }

    @GetMapping(API_CRAWL + "/pending/{page:.+}")
    public String getPending(@PathVariable int page) {

        Response<List<CrawlProduct>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            Sort sortable = Sort.by("id").ascending();
            Pageable pageable = PageRequest.of(page - 1, defaultPageSize, sortable);
            List<CrawlProduct> crawlPros = crawlService.getNewByPageable(pageable).getContent();

            if (crawlPros != null) {
//                LOGGER.info(crawlPros.toString());
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, crawlPros);
            }
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

//    @GetMapping(API_CRAWL + "/saveAllPending")
//    public String saveAllPending() {
//
//        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
//        try {
//
//            crawlService.getNewByPageable(pageable).getContent();
//
//            if (crawlPros != null) {
////                LOGGER.info(crawlPros.toString());
//                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
//            }
//        } catch (Exception e) {
//            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
//        }
//        return gson.toJson(response);
//    }



//    @GetMapping(API_CRAWL + "/pending/{page:.+}")
//    public String getPending(@PathVariable int page) {
//        Response<List<CrawlProduct>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
//        try {
//            List<CrawlProduct> crawlPros = crawlService.getNewByPage(page, 20);]
//
//            Sort sortable = null;
//            if (sort.equals("ASC")) {
//                sortable = Sort.by(sortBy).ascending();
//            }
//            if (sort.equals("DESC")) {
//                sortable = Sort.by(sortBy).descending();
//            }
//            Pageable pageable = PageRequest.of(page - 1, size, sortable);
//
////            LOGGER.info(crawlPros.toString());
//            if (crawlPros != null) {
//                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, crawlPros);
//            } else {
//                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
//            }
//        } catch (Exception e) {
//            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
//        }
//        return gson.toJson(response);
//    }


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
