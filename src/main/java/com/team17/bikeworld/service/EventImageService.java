package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.EventImage;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.EventImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.*;

@Service
public class EventImageService {

    private final EventImageRepository eventImageRepository;

    public EventImageService(EventImageRepository eventImageRepository) {
        this.eventImageRepository = eventImageRepository;
    }

    public Response<EventImage> getEventImageByEventId(int eventId) {
        Response<EventImage> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            EventImage eventImages = eventImageRepository.findByEventId_Id(eventId);

            if (eventImages != null) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, eventImages);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }

        return response;
    }
}
