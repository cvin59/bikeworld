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

    public Response<Event>  updateEvent(ConsumeEvent consumeEvent, MultipartFile image) {
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
                        eventImageRepository.deleteByEventId(event);
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
            String fileName = image.getOriginalFilename() + "_" + consumeEvent.getTitle() + ".jpg";
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

                event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_PENDING).get());

                return event;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }

        }
        return null;
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

//                event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_PENDING).get());

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
                Files.copy(image.getInputStream(), rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

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

    private List<ProposalEventImage> initProposalEventImages(ProposalEvent proposalEvent, ConsumeProposalEvent consumeProposalEvent) {
        List<ProposalEventImage> proposalEventImages = new LinkedList<>();
        ProposalEventImage proposalEventImage = new ProposalEventImage();
        proposalEventImage.setImageLink(consumeProposalEvent.getImageUrl());
        proposalEventImage.setProposalEventid(proposalEvent);
        proposalEventImages.add(proposalEventImage);
        return proposalEventImages;
    }


}
