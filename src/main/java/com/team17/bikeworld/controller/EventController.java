package com.team17.bikeworld.controller;

import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.ProposalEvent;
import com.team17.bikeworld.model.ConsumeEvent;
import com.team17.bikeworld.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.team17.bikeworld.common.CoreConstant.API_EVENT;

@Controller
public class EventController extends AbstractController{

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public ModelAndView viewEventDetail(@PathVariable("id") Integer id){

        Optional<Event> event = eventService.findEvent(id);
        if (event.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("event-detail");
            modelAndView.addObject("event", event.get());
            return modelAndView;
        }
        return new ModelAndView("not-found");
    }

    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        // Load file as Resource
        Resource resource = eventService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOGGER.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    //Web Service
    @GetMapping(API_EVENT)
    public ResponseEntity<List<Event>> getEvents() {
        ResponseEntity<List<Event>> response;
        try {
            List<Event> events = eventService.findEvents();

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

    @GetMapping(API_EVENT + "/{id}")
    public ResponseEntity<Optional<Event>> getEvent(@PathVariable("id") Integer id) {
        ResponseEntity<Optional<Event>> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            Optional<Event> event = eventService.findEvent(id);

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



    //TODO: chưa làm
    @PutMapping(API_EVENT + "/{id}")
    public ResponseEntity<ConsumeEvent> createEvent(@RequestBody ConsumeEvent consumeEvent){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    ///TODO: chưa làm
    @DeleteMapping(API_EVENT + "/{id}")
    public ResponseEntity<ConsumeEvent> deleteEvent(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
