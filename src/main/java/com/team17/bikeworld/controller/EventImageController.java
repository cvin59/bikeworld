package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.EventImage;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.EventImageService;
import com.team17.bikeworld.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.*;

@RestController
@CrossOrigin
public class EventImageController extends AbstractController {
    private final Logger LOGGER = LoggerFactory.getLogger(EventImageController.class);

    private final EventImageService eventImageService;
    private final EventService eventService;

    public EventImageController(EventImageService eventImageService, EventService eventService) {
        this.eventImageService = eventImageService;
        this.eventService = eventService;
    }


    @GetMapping(API_EVENT_IMAGE + "/event/{id}")
    public String getEvent(@PathVariable("id") Integer id) {
        Response<EventImage> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            EventImage eventImages = eventImageService.getEventImageByEventId(id);

            if (eventImages != null) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, eventImages);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}
