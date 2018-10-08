package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Event;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = CoreConstant.API_EVENT)
public class EventController extends AbstractController{
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;
    private final Path rootLocation = Paths.get("src/main/resources/static/images").toAbsolutePath().normalize();

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
    public ResponseEntity<Event> proposeEvent(@RequestParam String consumeEventString, @RequestParam MultipartFile imageUrl){
        LOGGER.info(consumeEventString.toString());
        ConsumeEvent consumeEvent = gson.fromJson(consumeEventString, ConsumeEvent.class);
        String fileName = "hinhanh_" + consumeEvent.getName() + ".jpg";
        try {

            Files.createDirectories(rootLocation);
            Files.copy(imageUrl.getInputStream(), this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        consumeEvent.setImageUrl("/downloadFile/" + fileName);
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
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        // Load file as Resource
        Resource resource = this.loadFileAsResource(fileName);

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

    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = this.rootLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + fileName, ex);
        }
    }
}
