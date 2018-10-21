package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.model.ConsumeEvent;
import com.team17.bikeworld.model.ConsumeProposalEvent;
import com.team17.bikeworld.model.Response;
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
import static com.team17.bikeworld.common.CoreConstant.MESSAGE_SERVER_ERROR;
import static com.team17.bikeworld.common.CoreConstant.STATUS_CODE_SERVER_ERROR;

@RestController
@CrossOrigin
public class EventController extends AbstractController{

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
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
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    //Web Service
    @GetMapping(API_EVENT)
    public String getEvents() {
        Response<List<Event>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);

        try {
            List<Event> events = eventService.findEvents();
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, events);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(API_EVENT + "/{id}")
    public String getEvent(@PathVariable("id") Integer id) {
        Response<Event> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            Event event = eventService.findEvent(id);

            if (event != null) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, event);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(API_EVENT)
    public String createEvent(@RequestParam String consumeEventString, MultipartFile image) {
        LOGGER.info(consumeEventString);
        ConsumeEvent consumeEvent = gson.fromJson(consumeEventString, ConsumeEvent.class);
        Response<Event> response = eventService.createEvent(consumeEvent, image);
        return gson.toJson(response);
    }

    //TODO: chưa làm
    @PutMapping(API_EVENT + "/{id}")
    public ResponseEntity<ConsumeProposalEvent> createEvent(@RequestBody ConsumeProposalEvent consumeProposalEvent){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    ///TODO: chưa làm
    @DeleteMapping(API_EVENT + "/{id}")
    public ResponseEntity<ConsumeProposalEvent> deleteEvent(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
