package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.EventRating;
import com.team17.bikeworld.model.ConsumeEventRating;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.AccountRepository;
import com.team17.bikeworld.repositories.EventRatingRepository;
import com.team17.bikeworld.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.*;
import static com.team17.bikeworld.common.CoreConstant.MESSAGE_SERVER_ERROR;

@Service
public class EventRatingService {
    private final Logger LOGGER = LoggerFactory.getLogger(EventRatingService.class);

    private final EventRatingRepository eventRatingRepository;
    private final EventRepository eventRepository;
    private final AccountRepository accountRepository;

    public EventRatingService(EventRatingRepository eventRatingRepository, EventRepository eventRepository, AccountRepository accountRepository) {
        this.eventRatingRepository = eventRatingRepository;
        this.eventRepository = eventRepository;
        this.accountRepository = accountRepository;
    }

    public Response<EventRating> rateEvent(ConsumeEventRating consumeEventRating) {
        Response<EventRating> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (consumeEventRating != null) {
            try {
                EventRating eventRating = eventRatingRepository.save(initEventRating(consumeEventRating));

                Event event = eventRepository.getOne(consumeEventRating.getEventId());

                double eventTotalRatingPoint = event.getTotalRatesPoint();
                int eventTotalRates = event.getTotalRates();

                int newEventTotalRates = eventTotalRates + 1;
                double newEventTotalRatingPoint = ((
                        (eventTotalRatingPoint * eventTotalRates) + consumeEventRating.getRatePoint())
                        / newEventTotalRates);

                event.setTotalRates(newEventTotalRates);
                event.setTotalRatesPoint(newEventTotalRatingPoint);

                eventRepository.save(event);
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, eventRating);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e.getCause());
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            }
        }
        return response;
    }

    private EventRating initEventRating(ConsumeEventRating consumeEventRating) {
        if (consumeEventRating != null) {
            try {
                EventRating eventRating = new EventRating();
                eventRating.setRatePoint(consumeEventRating.getRatePoint());
                eventRating.setContent(consumeEventRating.getContent());

                //location
                eventRating.setEventId(eventRepository.getOne(consumeEventRating.getEventId()));
                eventRating.setAccountUsename(accountRepository.getOne(consumeEventRating.getUsername()));

                //register date
                eventRating.setRateDate(consumeEventRating.getRateDate());

                //status
                eventRating.setIsActive((short) 1);

                return eventRating;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }

        }
        return null;
    }

    public Response checkEventRating(int eventId, String username) {
        Response response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            List<EventRating> participants = eventRatingRepository
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

    public Response<List<EventRating>> getEventRatingByEventId(Integer eventId) {
        Response<List<EventRating>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<EventRating> eventRatings = eventRatingRepository.findByEventId_Id(eventId);
            if (!eventRatings.isEmpty()) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, eventRatings);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return response;

    }
}
