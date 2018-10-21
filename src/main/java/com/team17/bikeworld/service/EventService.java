package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.EventImage;
import com.team17.bikeworld.entity.ProposalEvent;
import com.team17.bikeworld.entity.ProposalEventImage;
import com.team17.bikeworld.model.ConsumeEvent;
import com.team17.bikeworld.model.ConsumeProposalEvent;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.*;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.team17.bikeworld.common.CoreConstant.*;

@Service
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final EventImageRepository eventImageRepository;
    private final ProposalEventRepository proposalEventRepository;
    private final ProposalEventImageRepository proposalEventImageRepository;
    private final EventStatusRepository eventStatusRepository;
    private final AccountRepository accountRepository;

    private final Path rootLocation = Paths.get("src/main/resources/static/images").toAbsolutePath().normalize();

    public EventService(EventRepository eventRepository, EventImageRepository eventImageRepository, ProposalEventRepository proposalEventRepository, ProposalEventImageRepository proposalEventImageRepository, EventStatusRepository eventStatusRepository, AccountRepository accountRepository) {
        this.eventRepository = eventRepository;
        this.eventImageRepository = eventImageRepository;
        this.proposalEventRepository = proposalEventRepository;
        this.proposalEventImageRepository = proposalEventImageRepository;
        this.eventStatusRepository = eventStatusRepository;
        this.accountRepository = accountRepository;
    }

    //event section
    public List<Event> findEvents() {
        List<Event> events = eventRepository.findAll();
        return events;
    }

    public Optional<Event> findEvent(int id) {
        Optional<Event> event = eventRepository.findById(id);
        return event;
    }

    public Response<Event> createEvent(ConsumeEvent consumeEvent, MultipartFile image) {
        Response<Event> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (consumeEvent != null) {
            try {
                //xu ly luu hinh anh
                String fileName = image.getOriginalFilename() + "_" + consumeEvent.getTitle() + ".jpg";
                Files.createDirectories(rootLocation);
                Files.copy(image.getInputStream(), this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

                consumeEvent.setImageUrl("/images/" + fileName);

                Event event = eventRepository.save(initEvent(consumeEvent));

                List<EventImage> eventImages = initEventImages(event, consumeEvent);
                eventImageRepository.saveAll(eventImages);

                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, event);
            } catch (Exception e) {
                e.printStackTrace();
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            }
        }
        return response;
    }

    private List<EventImage> initEventImages(Event event, ConsumeEvent consumeEvent) {
        List<EventImage> eventImages = new LinkedList<>();
        EventImage eventImage = new EventImage();
        eventImage.setImageLink(consumeEvent.getImageUrl());
        eventImage.setEventId(event);
        eventImages.add(eventImage);
        return eventImages;
    }

    private Event initEvent(ConsumeEvent consumeEvent) {
        if (consumeEvent != null) {
            try {
                Event event = new Event();

                event.setTitle(consumeEvent.getTitle());
                event.setAddress(consumeEvent.getAddress());
                event.setDescription(consumeEvent.getDescription());
                event.setLocation(consumeEvent.getLocation());
                event.setLatitude(consumeEvent.getLatitude());
                event.setLongitude(consumeEvent.getLongitude());
                event.setFee(consumeEvent.getFee());

                //start date - end date
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date startDate = format1.parse(consumeEvent.getStartDate());
                event.setStartDate(startDate);
                Date endDate = format1.parse(consumeEvent.getEndDate());
                event.setEndDate(endDate);

                //start register date - end register date
                Date startRegiDate = format1.parse(consumeEvent.getStartRegiDate());
                event.setStartRegisterDate(startRegiDate);
                Date endRegiDate = format1.parse(consumeEvent.getEndRegiDate());
                event.setEndRegisterDate(endRegiDate);

                event.setEventStautsid(eventStatusRepository.findById(STATUS_EVENT_PENDING).get());

                return event;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }

        }
        return null;
    }


    //proposal event section
    public Response<ProposalEvent> proposeEvent(ConsumeProposalEvent consumeProposalEvent, MultipartFile image) {
        Response<ProposalEvent> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (consumeProposalEvent != null) {
            try {
                //xu ly luu hinh anh
                String fileName = image.getOriginalFilename() + "_" + consumeProposalEvent.getTitle() + ".jpg";
                Files.createDirectories(rootLocation);
                Files.copy(image.getInputStream(), this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

                consumeProposalEvent.setImageUrl("/images/" + fileName);

                ProposalEvent event = proposalEventRepository.save(initProposalEvent(consumeProposalEvent));

                List<ProposalEventImage> proposalEventImages = initProposalEventImages(event, consumeProposalEvent);
                proposalEventImageRepository.saveAll(proposalEventImages);

                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, event);
            } catch (Exception e) {
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            }
        }
        return response;
    }

    private ProposalEvent initProposalEvent(ConsumeProposalEvent consumeProposalEvent) {
        if (consumeProposalEvent != null) {
            try {
                ProposalEvent event = new ProposalEvent();
                event.setTitle(consumeProposalEvent.getTitle());
                event.setAddress(consumeProposalEvent.getAddress());
                event.setDescription(consumeProposalEvent.getDescription());
                event.setLocation(consumeProposalEvent.getLocation());
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date startDate = format1.parse(consumeProposalEvent.getStartDate());
                event.setStartDate(startDate);
                Date endDate = format1.parse(consumeProposalEvent.getEndDate());
                event.setEndDate(endDate);
                event.setAccountUsename(accountRepository.findAccountByUsername ("user"));
                event.setStatus(CoreConstant.STATUS_PROPOSALEVENT_NOT_APPROVED);

                return event;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }

        }
        return null;
    }

    public List<ProposalEventImage> initProposalEventImages(ProposalEvent proposalEvent, ConsumeProposalEvent consumeProposalEvent) {
        List<ProposalEventImage> proposalEventImages = new LinkedList<>();
        ProposalEventImage proposalEventImage = new ProposalEventImage();
        proposalEventImage.setImageLink(consumeProposalEvent.getImageUrl());
        proposalEventImage.setProposalEventid(proposalEvent);
        proposalEventImages.add(proposalEventImage);
        return proposalEventImages;
    }

    //load hinh
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
