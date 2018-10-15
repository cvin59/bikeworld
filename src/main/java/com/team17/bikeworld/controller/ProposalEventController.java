package com.team17.bikeworld.controller;

import com.team17.bikeworld.entity.ProposalEvent;
import com.team17.bikeworld.model.ConsumeEvent;
import com.team17.bikeworld.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import static com.team17.bikeworld.common.CoreConstant.*;

@Controller
public class ProposalEventController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalEventController.class);

    private final EventService eventService;

    public ProposalEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/event/propose-event")
    public ModelAndView viewProposeEvent(){
        return new ModelAndView("propose-event");
    }

    //Web Service
    @PostMapping(API_EVENT)
    public ResponseEntity<ProposalEvent> proposeEvent(@RequestParam String consumeEventString, @RequestParam MultipartFile image){
        LOGGER.info(consumeEventString);
        ConsumeEvent consumeEvent = gson.fromJson(consumeEventString, ConsumeEvent.class);
        ResponseEntity<ProposalEvent> response = eventService.proposeEvent(consumeEvent, image);
        return response;
    }
}
