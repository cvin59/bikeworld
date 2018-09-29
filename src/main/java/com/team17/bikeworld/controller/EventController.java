package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.model.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = CoreConstant.API_EVENT)
public class EventController{

    //web service
    @GetMapping
    public ResponseEntity<Event> getEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(new Event("Abc", 1));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(new Event("Abc", id));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event){
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Event> updateEvent(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    //client view
    
}
