package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.EventImage;
import com.team17.bikeworld.entity.ProposalEventImage;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.ProposalEventImageService;
import com.team17.bikeworld.service.ProposalEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.team17.bikeworld.common.CoreConstant.*;

@Controller
@CrossOrigin
public class ProposalEventImageController extends AbstractController {
    private final Logger LOGGER = LoggerFactory.getLogger(EventImageController.class);

    private final ProposalEventImageService eventImageService;
    private final ProposalEventService eventService;

    public ProposalEventImageController(ProposalEventImageService eventImageService, ProposalEventService eventService) {
        this.eventImageService = eventImageService;
        this.eventService = eventService;
    }


    @GetMapping(API_PROPOSAL_EVENT_IMAGE + "/proposal-event/{id}")
    public String getEvent(@PathVariable("id") Integer id) {
        Response<ProposalEventImage> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            ProposalEventImage eventImages = eventImageService.getEventImageByEventId(id);

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
