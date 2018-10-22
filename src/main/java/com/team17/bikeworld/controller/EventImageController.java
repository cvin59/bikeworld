package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.EventImage;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.EventImageService;
import com.team17.bikeworld.service.EventService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.*;

@RestController
@CrossOrigin
public class EventImageController extends AbstractController {

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
            EventImage eventImages = eventImageService.getEventImageByEventId(eventService.findEvent(id));

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
