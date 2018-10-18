package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.ProposalEvent;
import com.team17.bikeworld.entity.ProposalEventImage;
import com.team17.bikeworld.model.ConsumeEvent;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.EventRepository;
import com.team17.bikeworld.repositories.ProposalEventImageRepository;
import com.team17.bikeworld.repositories.ProposalEventRepository;
import com.team17.bikeworld.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;
    private final ProposalEventRepository proposalEventRepository;
    private final ProposalEventImageRepository proposalEventImageRepository;
    private final UserRepository userRepository;
    private final Path rootLocation = Paths.get("src/main/resources/static/images").toAbsolutePath().normalize();

    public EventService(EventRepository eventRepository, ProposalEventRepository proposalEventRepository, ProposalEventImageRepository proposalEventImageRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.proposalEventRepository = proposalEventRepository;
        this.proposalEventImageRepository = proposalEventImageRepository;
        this.userRepository = userRepository;
    }

    public List<Event> findEvents() {
        List<Event> events = eventRepository.findAll();
        return events;
    }

    public Optional<Event> findEvent(int id) {
        Optional<Event> event = eventRepository.findById(id);
        return event;
    }

    public Response<ProposalEvent> proposeEvent(ConsumeEvent consumeEvent, MultipartFile image) {
        Response<ProposalEvent> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (consumeEvent != null) {
            try {
                //xu ly luu hinh anh
                String fileName = image.getOriginalFilename() + "_" + consumeEvent.getTitle() + ".jpg";
                Files.createDirectories(rootLocation);
                Files.copy(image.getInputStream(), this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

                consumeEvent.setImageUrl("/images/" + fileName);

                ProposalEvent event = proposalEventRepository.save(initProposalEvent(consumeEvent));

                List<ProposalEventImage> proposalEventImages = initProposalEventImages(event, consumeEvent);
                proposalEventImageRepository.saveAll(proposalEventImages);

                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, event);
            } catch (Exception e) {
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            }
        }
        return response;
    }

    private ProposalEvent initProposalEvent(ConsumeEvent consumeEvent) {
        if (consumeEvent != null) {
            try {
                ProposalEvent event = new ProposalEvent();
                event.setTitle(consumeEvent.getTitle());
                event.setAddress(consumeEvent.getAddress());
                event.setDescription(consumeEvent.getDescription());
                event.setLocation(consumeEvent.getLocation());
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date startDate = format1.parse(consumeEvent.getStartDate());
                event.setStartDate(startDate);
                Date endDate = format1.parse(consumeEvent.getEndDate());
                event.setEndDate(endDate);
                event.setAccountUsename(userRepository.findAccountByUsername("a"));
                event.setStatus(CoreConstant.STATUS_PROPOSALEVENT_NOT_APPROVED);

                return event;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }

        }
        return null;
    }

    public List<ProposalEventImage> initProposalEventImages(ProposalEvent proposalEvent, ConsumeEvent consumeEvent) {
        List<ProposalEventImage> proposalEventImages = new LinkedList<>();
        ProposalEventImage proposalEventImage = new ProposalEventImage();
        proposalEventImage.setImageLink(consumeEvent.getImageUrl());
        proposalEventImage.setProposalEventid(proposalEvent);
        proposalEventImages.add(proposalEventImage);
        return proposalEventImages;
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
