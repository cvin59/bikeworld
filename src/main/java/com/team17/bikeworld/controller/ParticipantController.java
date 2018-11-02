package com.team17.bikeworld.controller;

import com.team17.bikeworld.entity.Participant;
import com.team17.bikeworld.model.ConsumeParticipant;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.ParticipantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
