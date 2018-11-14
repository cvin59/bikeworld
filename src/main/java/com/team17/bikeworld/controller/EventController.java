package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.model.ConsumeEvent;
import com.team17.bikeworld.model.LatLng;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.model.ResponsePage;
import com.team17.bikeworld.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.*;

@RestController
@CrossOrigin
public class EventController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    //Web Service
    @GetMapping(API_EVENT)
    public String getEvents() {
        Response<List<Event>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);

        try {
            List<Event> events = eventService.findEvents();
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, events);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(API_EVENT + "/{id}")
    public String getEvent(@PathVariable("id") Integer id) {
        Response<Event> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            Event event = eventService.findEvent(id);

            if (event != null) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, event);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(API_EVENT + "/registering-event")
    public String getUpcomingEvent() {
        Response<List<Event>> response = eventService.getUpcomingEvent();
        return gson.toJson(response);
    }

    @GetMapping(API_EVENT + "/comingsoon-event")
    public String getComingSoonEvent() {
        Response<List<Event>> response = eventService.getComingSoon();
        return gson.toJson(response);
    }

    @PostMapping(API_EVENT)
    public String createEvent(@RequestParam String consumeEventString, MultipartFile image) {
        LOGGER.info(consumeEventString);
        ConsumeEvent consumeEvent = gson.fromJson(consumeEventString, ConsumeEvent.class);
        Response<Event> response = eventService.createEvent(consumeEvent, image);
        return gson.toJson(response);
    }

    @PutMapping(API_EVENT)
    public String updateEvent(@RequestParam String consumeEventString, MultipartFile image) {
        LOGGER.info(consumeEventString);
        ConsumeEvent consumeEvent = gson.fromJson(consumeEventString, ConsumeEvent.class);
        Response<Event> response = eventService.updateEvent(consumeEvent, image);
        return gson.toJson(response);
    }

    @GetMapping(API_EVENT + "/deactivate-event/{id}")
    public String deactivateEvent(@PathVariable("id") Integer id) {
        Response<Event> response = eventService.deactivateEvent(id);
        return gson.toJson(response);
    }

    @GetMapping(API_EVENT + "/activate-event/{id}")
    public String activateEvent(@PathVariable("id") Integer id) {
        Response<Event> response = eventService.activateEvent(id);
        return gson.toJson(response);
    }

    @GetMapping(API_EVENT + "/get")
    public String findPaginated(@RequestParam("page") int page
            , @RequestParam("sort") String sort
            , @RequestParam("direction") int direction) {
        Page<Event> resultPage = eventService.getEventPage(page, 6, sort, direction);
        ResponsePage responsePage = new ResponsePage();
        responsePage.setContent(resultPage.getContent());
        responsePage.setCurrPage(page);
        responsePage.setTotalPage(resultPage.getTotalPages());
        return gson.toJson(responsePage);
    }

    @GetMapping(API_EVENT + "/search")
    public String findPaginatedSearch(@RequestParam("q") String q
            ,@RequestParam("page") int page
            , @RequestParam("sort") String sort
            , @RequestParam("direction") int direction) {
        Page<Event> resultPage = eventService.getEventPage(q, page, 12, sort, direction);
        ResponsePage responsePage = new ResponsePage();
        responsePage.setContent(resultPage.getContent());
        responsePage.setCurrPage(page);
        responsePage.setTotalPage(resultPage.getTotalPages());
        return gson.toJson(responsePage);
    }

    @GetMapping(API_EVENT + "/result")
    public String searchEventByTitle(@RequestParam("q") String q
            ,@RequestParam("page") int page) {
        Page<Event> resultPage = eventService.getEventPage(q, page, 12);
        ResponsePage responsePage = new ResponsePage();
        responsePage.setContent(resultPage.getContent());
        responsePage.setCurrPage(page);
        responsePage.setTotalPage(resultPage.getTotalPages());
        return gson.toJson(responsePage);
    }

    @GetMapping(API_EVENT + "/event-home")
    public String viewEventHome() {
        Response<List<Event>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<Event> events = eventService.findEventHome();
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, events);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(API_EVENT + "/nearby-event")
    public String viewNearbyEvent(@RequestParam Double lat, @RequestParam Double lng) {
        Response<List<Event>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<Event> events = eventService.searchNearby(lat, lng);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, events);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


    ///TODO: chưa làm
//    @DeleteMapping(API_EVENT + "/{id}")
//    public ResponseEntity<ConsumeProposalEvent> deleteEvent(){
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }
}
