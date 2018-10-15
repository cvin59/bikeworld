package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.ProposalEvent;
import com.team17.bikeworld.model.ConsumeEvent;
import com.team17.bikeworld.repositories.EventRepository;
import com.team17.bikeworld.repositories.ProposalEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;
    private final ProposalEventRepository proposalEventRepository;
    private final Path rootLocation = Paths.get("src/main/resources/static/images").toAbsolutePath().normalize();

    public EventService(EventRepository eventRepository, ProposalEventRepository proposalEventRepository) {
        this.eventRepository = eventRepository;
        this.proposalEventRepository = proposalEventRepository;
    }

    public List<Event> findEvents() {
        List<Event> events = eventRepository.findAll();
        return events;
    }

    public Optional<Event> findEvent(int id) {
        Optional<Event> event = eventRepository.findById(id);
        return event;
    }

    public ResponseEntity<ProposalEvent> proposeEvent(ConsumeEvent consumeEvent, MultipartFile image) {
        ResponseEntity<ProposalEvent> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (consumeEvent != null) {
            try {
                //xu ly luu hinh anh
                String fileName = "hinhanh_" + consumeEvent.getName() + ".jpg";
                Files.createDirectories(rootLocation);
                Files.copy(image.getInputStream(), this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

                consumeEvent.setImageUrl("/images/" + fileName);

                ProposalEvent event = proposalEventRepository.save(initProposalEvent(consumeEvent));
                response = ResponseEntity.status(HttpStatus.OK).body(event);
            } catch (Exception e) {
                response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return response;
    }

    private ProposalEvent initProposalEvent(ConsumeEvent consumeEvent) {
        if (consumeEvent != null) {
            try {
                ProposalEvent event = new ProposalEvent();
                event.setName(consumeEvent.getName());
                event.setImageUrl(consumeEvent.getImageUrl());
                return event;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }

        }
        return null;
    }

    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = this.rootLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + fileName, ex);
        }
    }
}
