package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.EventImage;
import com.team17.bikeworld.repositories.EventImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventImageService {

    private final EventImageRepository eventImageRepository;

    public EventImageService(EventImageRepository eventImageRepository) {
        this.eventImageRepository = eventImageRepository;
    }

    public List<EventImage> getEventImageByEventId(Event event) {
        return eventImageRepository.findAllByEventId(event);
    }
}
