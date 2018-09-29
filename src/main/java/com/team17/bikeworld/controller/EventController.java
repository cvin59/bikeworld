package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CoreConstant.API_EVENT)
public class EventController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getEvent() {
        return new ResponseEntity("Hello World", HttpStatus.OK);
    }
}
