package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.model.ConsumeEvent;
import com.team17.bikeworld.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public ResponseEntity<List<Event>> findEvents() {
        ResponseEntity<List<Event>> response;
        try {
            List<Event> events = eventRepository.findAll();

            if (events != null) {
                response = ResponseEntity.status(HttpStatus.OK).body(events);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<Optional<Event>> findEvent(int id) {
        ResponseEntity<Optional<Event>> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            Optional<Event> event = eventRepository.findById(id);

            if (event != null) {
                response = ResponseEntity.status(HttpStatus.OK).body(event);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<Event> proposeEvent(ConsumeEvent consumeEvent) {
        ResponseEntity<Event> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (consumeEvent != null) {
            try {
                Event event = eventRepository.save(initEvent(consumeEvent));
                response = ResponseEntity.status(HttpStatus.OK).body(event);
            } catch (Exception e) {
                response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return response;
    }

    private Event initEvent(ConsumeEvent consumeEvent) {
        if (consumeEvent != null){
            try {
                Event event = new Event();
                event.setName(consumeEvent.getName());
                event.setStatus(CoreConstant.STATUS_EVENT_PENDING);
                event.setImageUrl(consumeEvent.getImageUrl());
                return event;
            } catch (Exception e){
                LOGGER.error(e.getMessage());
            }

        }
        return null;
    }
}
