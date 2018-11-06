package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Participant;
import com.team17.bikeworld.model.ConsumeParticipant;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.model.ResponsePage;
import com.team17.bikeworld.service.ParticipantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.*;

@RestController
public class ParticipantController extends AbstractController{
    private final Logger LOGGER = LoggerFactory.getLogger(ParticipantController.class);

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping(API_PARTICIPANT)
    public String registerEven(@RequestBody ConsumeParticipant participant) {
        LOGGER.info(participant.toString());
        Response<Participant> response = participantService.registerEvent(participant);
        return gson.toJson(response);
    }

    @GetMapping(API_PARTICIPANT + "/user/{username}")
    public String getParticipant(@PathVariable("username") String username) {
        Response<List<Participant>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<Participant> participants = participantService.findPartcipantsByUsername(username);
            if (!participants.isEmpty()) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, participants);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(API_PARTICIPANT + "/{username}")
    public String findPaginatedSearch(@PathVariable("username") String q
            ,@RequestParam("page") int page
            , @RequestParam("sort") String sort
            , @RequestParam("direction") int direction) {
        Page<Participant> resultPage = participantService.getParticipantPage(q, page, 6, sort, direction);
        ResponsePage responsePage = new ResponsePage();
        responsePage.setContent(resultPage.getContent());
        responsePage.setCurrPage(page);
        responsePage.setTotalPage(resultPage.getTotalPages());
        return gson.toJson(responsePage);
    }
}
