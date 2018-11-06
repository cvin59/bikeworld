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
@CrossOrigin
public class ProposalEventController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalEventController.class);
    private final ProposalEventService proposalEventService;

    public ProposalEventController(ProposalEventService proposalEventService) {
        this.proposalEventService = proposalEventService;
    }
    //Web Service
    @PostMapping(API_PROPOSAL_EVENT)
    public String proposeEvent(@RequestParam String consumeEventString, MultipartFile image) {
        LOGGER.info(consumeEventString);
        ConsumeProposalEvent consumeProposalEvent = gson.fromJson(consumeEventString, ConsumeProposalEvent.class);
        Response<ProposalEvent> response = proposalEventService.proposeEvent(consumeProposalEvent, image);
        return gson.toJson(response);
    }

    @GetMapping(API_PROPOSAL_EVENT)
    public String getEvents() {
        Response<List<ProposalEvent>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<ProposalEvent> proposalEvents = proposalEventService.findProposalEvents();
            for (ProposalEvent proposalEvent :
                    proposalEvents) {
                LOGGER.info(proposalEvent.getAccountUsename().getUsername());
            }
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, proposalEvents);
        } catch (Exception e) {
            e.printStackTrace();
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

    @GetMapping(API_PROPOSAL_EVENT + "/user/{username}")
    public String getEventByUsername(@PathVariable("username") String username) {
        Response<List<ProposalEvent>> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);

        try {
            List<ProposalEvent> proposalEvents = proposalEventService.findProposalEvent(username);
            if (!proposalEvents.isEmpty()) {
                response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS, proposalEvents);
            } else {
                response.setResponse(STATUS_CODE_NO_RESULT, MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(API_PROPOSAL_EVENT + "/approve-event/{id}")
    public String changeStatus(@PathVariable("id") Integer id) {
        Response<ProposalEvent> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        proposalEventService.changeStatus(id, response, STATUS_PROPOSALEVENT_APPRROVED);
        return gson.toJson(response);
    }

    @GetMapping(API_PROPOSAL_EVENT + "/not-approve-event/{id}")
    public String notApproveEvent(@PathVariable("id") Integer id) {
        Response<ProposalEvent> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        proposalEventService.changeStatus(id, response, STATUS_PROPOSALEVENT_NOT_APPROVED);
        return gson.toJson(response);
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
