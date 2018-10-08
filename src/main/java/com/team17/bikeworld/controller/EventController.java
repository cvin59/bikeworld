package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.ProposalEvent;
import com.team17.bikeworld.model.ConsumeEvent;
import com.team17.bikeworld.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = CoreConstant.API_EVENT)
public class EventController extends AbstractController{
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

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
    public ResponseEntity<ProposalEvent> proposeEvent(@RequestParam String consumeEventString, @RequestParam MultipartFile image){
        LOGGER.info(consumeEventString.toString());
        ConsumeEvent consumeEvent = gson.fromJson(consumeEventString, ConsumeEvent.class);
        ResponseEntity<ProposalEvent> response = eventService.proposeEvent(consumeEvent, image);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumeEvent> createEvent(@RequestBody ConsumeEvent consumeEvent){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ConsumeEvent> deleteEvent(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/downloadFile/{fileName:.+}")
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


}
