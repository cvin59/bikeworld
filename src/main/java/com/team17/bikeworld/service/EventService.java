package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.*;
import com.team17.bikeworld.model.ConsumeEvent;
import com.team17.bikeworld.model.ConsumeProposalEvent;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

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
        return eventRepository.findAll();
    }

    public Event findEvent(Integer id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        return optionalEvent.orElse(null);
    }

    public List<Event> findEventHome() {
        return eventRepository.findTop3By();
    }

    public Response<Event> createEvent(ConsumeEvent consumeEvent, MultipartFile image) {
        Response<Event> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (consumeEvent != null) {
            try {
                //xu ly luu hinh anh
                handleImage(consumeEvent, image);
                //save event
                Event event = eventRepository.save(initEvent(consumeEvent));

                //save event iamge
                if (image != null) {
                    List<EventImage> eventImages = initEventImages(event, consumeEvent);
                    eventImageRepository.saveAll(eventImages);
                }
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, event);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e.getCause());
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            }
        }
        return response;
    }

    public Response<Event> updateEvent(ConsumeEvent consumeEvent, MultipartFile image) {
        Response<Event> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (consumeEvent != null) {
            try {
                //xu ly luu hinh anh
                handleImage(consumeEvent, image);
                //save event
                Optional<Event> optional = eventRepository.findById(consumeEvent.getId());
                if (optional.isPresent()) {
                    Event event = optional.get();
                    eventRepository.save(updateEvent(event, consumeEvent));
                    //save event iamge
                    if (image != null) {
                        List<EventImage> eventImages = initEventImages(event, consumeEvent);
                        eventImageRepository.deleteByEventId_Id(event.getId());
                        eventImageRepository.saveAll(eventImages);
                    }
                    response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, event);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e.getCause());
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            }
        }
        return response;
    }

    private void handleImage(ConsumeEvent consumeEvent, MultipartFile image) throws IOException {
        if (image != null) {
            String sourceName = image.getOriginalFilename();
            String sourceFileName = FilenameUtils.getBaseName(sourceName);
            String sourceExt = FilenameUtils.getExtension(sourceName).toLowerCase();

            String fileName = RandomStringUtils.randomAlphabetic(8)
                    .concat(sourceFileName)
                    .concat(".")
                    .concat(sourceExt);
            System.out.println(fileName);
            Files.createDirectories(rootLocation);
            Files.copy(image.getInputStream(), rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            consumeEvent.setImageUrl("/images/" + fileName);
        }
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
                event.setId(consumeEvent.getId());
                event.setTitle(consumeEvent.getTitle());
                event.setDescription(consumeEvent.getDescription());

                //location
                event.setAddress(consumeEvent.getAddress());
                event.setLocation(consumeEvent.getLocation());
                event.setLatitude(consumeEvent.getLatitude());
                event.setLongitude(consumeEvent.getLongitude());

                //price-fee
                event.setFee(consumeEvent.getFee());
                event.setTotalSlots(consumeEvent.getTotalSlots());
                event.setMaxSlot(consumeEvent.getMaxSlot());
                event.setMinSlot(consumeEvent.getMinSlot());
                event.setCurrentSlot(0);

                //rating
                event.setTotalRates(0);
                event.setTotalRatesPoint(Double.valueOf(0));

                //start date - end date
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date startDate = format1.parse(consumeEvent.getStartDate());
                event.setStartDate(startDate);
                Date endDate = format1.parse(consumeEvent.getEndDate());
                event.setEndDate(endDate);

                //start register date - end register date
                Date startRegiDate = format1.parse(consumeEvent.getStartRegisterDate());
                event.setStartRegisterDate(startRegiDate);
                Date endRegiDate = format1.parse(consumeEvent.getEndRegisterDate());
                event.setEndRegisterDate(endRegiDate);

//                int checkStartRegister = Calendar.getInstance().getTime().compareTo(event.getStartRegisterDate());
//                int checkEndRegister = Calendar.getInstance().getTime().compareTo(event.getEndRegisterDate());
//                int checkStart = Calendar.getInstance().getTime().compareTo(event.getStartDate());
//                int checkEnd = Calendar.getInstance().getTime().compareTo(event.getEndDate());

                event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_INACTIVE).get());

                return event;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }

        }
        return null;
    }

    private void setEventStatus(Event event, int checkStartRegister, int checkEndRegister, int checkStart, int checkEnd) {
        if (checkStartRegister < 0) {
            event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_INACTIVE).get());
        } else if (checkStartRegister >= 0 && checkEndRegister <= 0) {
            event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_INACTIVE).get());
        } else if (checkEndRegister > 0 && checkStart < 0) {
            event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_INACTIVE).get());
        } else if (checkStart >= 0 && checkEnd <= 0) {
            event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_ACTIVE).get());
        } else {
            event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_FINISH).get());
        }
    }

    private Event updateEvent(Event event, ConsumeEvent consumeEvent) {
        if (consumeEvent != null) {
            try {
                event.setTitle(consumeEvent.getTitle());
                event.setDescription(consumeEvent.getDescription());

                //location
                event.setAddress(consumeEvent.getAddress());
                event.setLocation(consumeEvent.getLocation());
                event.setLatitude(consumeEvent.getLatitude());
                event.setLongitude(consumeEvent.getLongitude());

                //price-fee
                event.setFee(consumeEvent.getFee());
                event.setTotalSlots(consumeEvent.getTotalSlots());
                event.setMaxSlot(consumeEvent.getMaxSlot());
                event.setMinSlot(consumeEvent.getMinSlot());

                //start date - end date
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date startDate = format1.parse(consumeEvent.getStartDate());
                event.setStartDate(startDate);
                Date endDate = format1.parse(consumeEvent.getEndDate());
                event.setEndDate(endDate);

                //start register date - end register date
                Date startRegiDate = format1.parse(consumeEvent.getStartRegisterDate());
                event.setStartRegisterDate(startRegiDate);
                Date endRegiDate = format1.parse(consumeEvent.getEndRegisterDate());
                event.setEndRegisterDate(endRegiDate);

                int checkStartRegister = Calendar.getInstance().getTime().compareTo(event.getStartRegisterDate());
                int checkEndRegister = Calendar.getInstance().getTime().compareTo(event.getEndRegisterDate());
                int checkStart = Calendar.getInstance().getTime().compareTo(event.getStartDate());
                int checkEnd = Calendar.getInstance().getTime().compareTo(event.getEndDate());

                setEventStatus(event, checkStartRegister, checkEndRegister, checkStart, checkEnd);
                return event;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }

        }
        return null;
    }

    public Response<Event> deactivateEvent(Integer id) {
        Response<Event> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            Optional<Event> optionalEvent = eventRepository.findById(id);
            if (optionalEvent.isPresent()) {
                Event event = optionalEvent.get();
                EventStatus status = eventStatusRepository.findEventStatusById(STATUS_EVENT_CANCELED).get();
                event.setEventStautsid(status);

                event = eventRepository.save(event);
                response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS, event);
            } else {
                response.setResponse(STATUS_CODE_NO_RESULT, MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return response;
    }

    public Response<List<Event>> getUpcomingEvent() {
        Response<List<Event>> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        statusEvent(response, STATUS_EVENT_INACTIVE);
        return response;
    }

    public Response<List<Event>> getComingSoon() {
        Response<List<Event>> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        statusEvent(response, STATUS_EVENT_INACTIVE);
        return response;
    }

    private void statusEvent(Response<List<Event>> response, int statusEventComingSoon) {
        try {
            List<Event> events = eventRepository
                    .findByEventStautsid(eventStatusRepository
                            .findEventStatusById(statusEventComingSoon)
                            .get());
            if (!events.isEmpty()) {
                response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS, events);
            } else {
                response.setResponse(STATUS_CODE_NO_RESULT, MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
    }

    public Page<Event> getEventPage(int pageNumber, int itemsPerPage, String sortBy, int direction) {
        if (direction == 1) {
            Pageable pageable = PageRequest.of(pageNumber - 1, itemsPerPage, Sort.Direction.ASC, sortBy);
            return eventRepository.findAll(pageable);
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, itemsPerPage, Sort.Direction.DESC, sortBy);
        return eventRepository.findAll(pageable);
    }

    public Page<Event> getEventPage(String search, int pageNumber, int itemsPerPage, String sortBy, int direction) {
        if (direction == 1) {
            Pageable pageable = PageRequest.of(pageNumber - 1, itemsPerPage, Sort.Direction.ASC, sortBy);
            return eventRepository.findByTitleIgnoreCaseContaining(search, pageable);
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, itemsPerPage, Sort.Direction.DESC, sortBy);
        return eventRepository.findByTitleIgnoreCaseContaining(search, pageable);
    }

    public Page<Event> getEventPage(String search, int pageNumber, int itemsPerPage) {
        Pageable pageable = PageRequest.of(pageNumber - 1, itemsPerPage);
        return eventRepository.findByTitleIgnoreCaseContaining(search, pageable);
    }

    public List<Event> searchNearby(double lat, double lng) {

        List<Event> result;

        double radius = MIN_RADIUS;
        double latC, latF, lngC, lngF;

        do {
            latC = lat + radius;
            latF = lat - radius;
            lngC = lng + radius;
            lngF = lng - radius;
            LOGGER.info("Search event with radius: " + radius);
            result = eventRepository.findByLatitudeBetweenAndLongitudeBetween(latF, latC, lngF, lngC);
            radius = radius * 10;
        } while (radius < CoreConstant.MAX_RADIUS);
        return result;
    }
}
