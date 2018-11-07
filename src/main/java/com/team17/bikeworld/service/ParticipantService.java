package com.team17.bikeworld.service;

import static com.team17.bikeworld.common.CoreConstant.*;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.Participant;
import com.team17.bikeworld.model.ConsumeParticipant;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.AccountRepository;
import com.team17.bikeworld.repositories.EventRepository;
import com.team17.bikeworld.repositories.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {
    private final Logger LOGGER = LoggerFactory.getLogger(ParticipantService.class);

    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final AccountRepository accountRepository;

    public ParticipantService(ParticipantRepository participantRepository, EventRepository eventRepository, AccountRepository accountRepository) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.accountRepository = accountRepository;
    }

    public Response<Participant> registerEvent(ConsumeParticipant consumeParticipant) {
        Response<Participant> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        if (consumeParticipant != null) {
            try {
                Event event = eventRepository.findById(consumeParticipant.getEventId()).get();
                int totalSlots = event.getTotalSlots();
                int currentSlot = event.getCurrentSlot();
                int quantity = consumeParticipant.getQuantity();
                if (totalSlots >= (currentSlot + quantity)) {
                    currentSlot = currentSlot + quantity;
                    event.setCurrentSlot(currentSlot);
                    eventRepository.save(event);

                    Participant participant = participantRepository.save(initParticipant(consumeParticipant));
                    response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS, participant);
                }
            } catch (Exception e) {
                response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
            }
        }
        return response;
    }

    private Participant initParticipant(ConsumeParticipant consumeParticipant) {
        if (consumeParticipant != null) {
            try {
                Participant participant = new Participant();
                participant.setQuantity(consumeParticipant.getQuantity());
                participant.setTotal(consumeParticipant.getTotal());
                participant.setRegisterDate(consumeParticipant.getRegisterDate());

                Event event = eventRepository.findById(consumeParticipant.getEventId()).get();
                Account account = accountRepository.findAccountByUsername(consumeParticipant.getUsername());

                participant.setEventId(event);
                participant.setAccountUsename(account);

                return participant;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e.getCause());
            }
        }
        return null;
    }


    public Response<List<Participant>> findPartcipantsByUsername(String username) {
        Response<List<Participant>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<Participant> participants = participantRepository.findByAccountUsename_Username(username);
            if (!participants.isEmpty()) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, participants);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return response;
    }

    public Page<Participant> getParticipantPage(String username, int pageNumber, int itemsPerPage, String sortBy, int direction) {
        if (direction == 1) {
            Pageable pageable = PageRequest.of(pageNumber - 1, itemsPerPage, Sort.Direction.ASC, sortBy);
            return participantRepository.findByAccountUsename_Username(username, pageable);
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, itemsPerPage, Sort.Direction.DESC, sortBy);
        return participantRepository.findByAccountUsename_Username(username, pageable);
    }

    public Response checkParticipant(int eventId, String username) {
        Response response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            List<Participant> participants = participantRepository
                    .findByEventId_IdAndAccountUsename_Username(eventId, username);
            if (!participants.isEmpty()) {
                response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS);
            } else {
                response.setResponse(STATUS_CODE_NO_RESULT, MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return response;
    }
}
