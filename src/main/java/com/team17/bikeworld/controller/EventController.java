package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.model.ConsumeEvent;
import com.team17.bikeworld.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = CoreConstant.API_EVENT)
public class EventController extends AbstractController{

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    //web service
    @GetMapping
    public ResponseEntity<List<Event>> getEvents() {
        ResponseEntity<List<Event>> response = eventService.findEvents();
        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Event>> getEvent(@PathVariable("id") Integer id) {
        ResponseEntity<Optional<Event>> response = eventService.findEvent(id);
        return response;
    }

    @PostMapping
    public ResponseEntity<Event> proposeEvent(@RequestBody ConsumeEvent consumeEvent){
        ResponseEntity<Event> response = eventService.proposeEvent(consumeEvent);
        return response;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ConsumeEvent> updateEvent(@RequestBody ConsumeEvent consumeEvent){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ConsumeEvent> updateEvent(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    //client view
    
}
