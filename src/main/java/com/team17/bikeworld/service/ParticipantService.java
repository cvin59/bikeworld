package com.team17.bikeworld.service;

import static com.team17.bikeworld.common.CoreConstant.*;

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
import org.springframework.stereotype.Service;

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
                int quantity = consumeParticipant.getQuantity();
                if (totalSlots >= quantity) {
                    totalSlots = totalSlots - quantity;
                    event.setTotalSlots(totalSlots);
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
}
