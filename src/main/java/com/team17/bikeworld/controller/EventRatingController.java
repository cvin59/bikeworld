package com.team17.bikeworld.controller;

import com.team17.bikeworld.entity.EventRating;
import com.team17.bikeworld.model.ConsumeEventRating;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.EventRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.*;
@RestController
@CrossOrigin
public class EventRatingController extends AbstractController{
    private final Logger LOGGER = LoggerFactory.getLogger(EventRatingController.class);

    private final EventRatingService eventRatingService;

    public EventRatingController(EventRatingService eventRatingService) {
        this.eventRatingService = eventRatingService;
    }

    @PostMapping(API_EVENT_RATING)
    public String rateEvent(@RequestBody ConsumeEventRating consumeEventRating) {
        LOGGER.info(consumeEventRating.toString());
        Response<EventRating> response = eventRatingService.rateEvent(consumeEventRating);
        return gson.toJson(response);
    }

    @GetMapping(API_EVENT_RATING + "/check")
    public String checkParticipant(@RequestParam("eventId") Integer eventId,
                                   @RequestParam("username") String username) {
        Response response = eventRatingService.checkEventRating(eventId, username);
        return gson.toJson(response);
    }

    @GetMapping(API_EVENT_RATING + "/event")
    public String getEventRatingByEventId(@RequestParam("eventId") Integer eventId) {
        Response<List<EventRating>> response = eventRatingService.getEventRatingByEventId(eventId);
        return gson.toJson(response);
    }
}
