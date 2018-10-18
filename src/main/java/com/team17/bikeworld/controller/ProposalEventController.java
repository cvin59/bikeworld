package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.ProposalEvent;
import com.team17.bikeworld.model.ConsumeProposalEvent;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.EventService;
import com.team17.bikeworld.service.ProposalEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static com.team17.bikeworld.common.CoreConstant.*;

@RestController
public class ProposalEventController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalEventController.class);

    private final EventService eventService;
    private final ProposalEventService proposalEventService;

    public ProposalEventController(EventService eventService, ProposalEventService proposalEventService) {
        this.eventService = eventService;
        this.proposalEventService = proposalEventService;
    }

    @GetMapping("/event/propose-event")
    public ModelAndView viewProposeEvent() {
        return new ModelAndView("propose-event");
    }

    @GetMapping("/portal/proposal-event/create-event/{id}")
    public ModelAndView viewProposalCreateEvent(@PathVariable("id") Integer id){
        ModelAndView mav = new ModelAndView("not-found");
        Optional<ProposalEvent> optional = proposalEventService.findProposalEvent(id);
        if (optional.isPresent()) {
            ProposalEvent proposalEvent = optional.get();
            mav.addObject("event", proposalEvent);
            mav.setViewName("propose-event");
            return mav;
        }
        return mav;
    }

    //Web Service
    @PostMapping(API_PROPOSAL_EVENT)
    public String proposeEvent(@RequestParam String consumeEventString, @RequestParam MultipartFile image) {
        LOGGER.info(consumeEventString);
        ConsumeProposalEvent consumeProposalEvent = gson.fromJson(consumeEventString, ConsumeProposalEvent.class);
        Response<ProposalEvent> response = eventService.proposeEvent(consumeProposalEvent, image);
        return gson.toJson(response);
    }

    @GetMapping(API_PROPOSAL_EVENT)
    public String getEvents() {
        Response<List<ProposalEvent>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<ProposalEvent> proposalEvents = proposalEventService.findProposalEvents();
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, proposalEvents);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(API_PROPOSAL_EVENT + "/{id}")
    public String getEvent(@PathVariable("id") Integer id) {
        Response<ProposalEvent> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);

        try {
            Optional<ProposalEvent> proposalEvent = proposalEventService.findProposalEvent(id);
            if (proposalEvent.isPresent()) {
                response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS, proposalEvent.get());
            } else {
                response.setResponse(STATUS_CODE_NO_RESULT, MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(API_PROPOSAL_EVENT + "/approve-event/{id}")
    public String changeStatus(@PathVariable("id") Integer id) {
        Response<ProposalEvent> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        changeStatus(id, response, STATUS_PROPOSALEVENT_APPRROVED);
        return gson.toJson(response);
    }

    @PutMapping(API_PROPOSAL_EVENT + "/not-approve-event/{id}")
    public String notApproveEvent(@PathVariable("id") Integer id) {
        Response<ProposalEvent> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        changeStatus(id, response, STATUS_PROPOSALEVENT_NOT_APPROVED);
        return gson.toJson(response);
    }

    private void changeStatus(@PathVariable("id") Integer id, Response<ProposalEvent> response, int statusProposaleventNotApproved) {
        try {
            Optional<ProposalEvent> optional = proposalEventService.findProposalEvent(id);
            if (optional.isPresent()) {
                ProposalEvent proposalEvent = optional.get();
                proposalEvent.setStatus(statusProposaleventNotApproved);
                proposalEvent = proposalEventService.saveProposalEvent(proposalEvent);

                response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS,proposalEvent);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
    }

    //TODO: chưa làm
    @PutMapping(API_PROPOSAL_EVENT + "/{id}")
    public ResponseEntity<ConsumeProposalEvent> createEvent(@RequestBody ConsumeProposalEvent consumeProposalEvent) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    ///TODO: chưa làm
    @DeleteMapping(API_PROPOSAL_EVENT + "/{id}")
    public ResponseEntity<ConsumeProposalEvent> deleteEvent() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
