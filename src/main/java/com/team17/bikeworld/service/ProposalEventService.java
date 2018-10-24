package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.ProposalEvent;
import com.team17.bikeworld.entity.ProposalEventImage;
import com.team17.bikeworld.model.ConsumeProposalEvent;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.AccountRepository;
import com.team17.bikeworld.repositories.ProposalEventImageRepository;
import com.team17.bikeworld.repositories.ProposalEventRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.team17.bikeworld.common.CoreConstant.*;
import static com.team17.bikeworld.common.CoreConstant.rootLocation;

@Service
public class ProposalEventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    private final ProposalEventRepository proposalEventRepository;
    private final AccountRepository accountRepository;
    private final ProposalEventImageRepository proposalEventImageRepository;

    public ProposalEventService(ProposalEventRepository proposalEventRepository, AccountRepository accountRepository, ProposalEventImageRepository proposalEventImageRepository) {
        this.proposalEventRepository = proposalEventRepository;
        this.accountRepository = accountRepository;
        this.proposalEventImageRepository = proposalEventImageRepository;
    }

    public List<ProposalEvent> findProposalEvents() {
        return proposalEventRepository.findAll();
    }

    public Optional<ProposalEvent> findProposalEvent(int id) {
        return proposalEventRepository.findById(id);
    }

    public ProposalEvent saveProposalEvent(ProposalEvent proposalEvent) {
        return proposalEventRepository.save(proposalEvent);
    }

    public void changeStatus(@PathVariable("id") Integer id, Response<ProposalEvent> response, int statusProposaleventNotApproved) {
        try {
            Optional<ProposalEvent> optional = proposalEventRepository.findById(id);
            if (optional.isPresent()) {
                ProposalEvent proposalEvent = optional.get();
                proposalEvent.setStatus(statusProposaleventNotApproved);
                proposalEvent = proposalEventRepository.save(proposalEvent);

                response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS,proposalEvent);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
    }

    //proposal event section
    public Response<ProposalEvent>  proposeEvent(ConsumeProposalEvent consumeProposalEvent, MultipartFile image) {
        Response<ProposalEvent> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (consumeProposalEvent != null) {
            try {
                //xu ly luu hinh anh
                String sourceName = image.getOriginalFilename();
                String sourceFileName = FilenameUtils.getBaseName(sourceName);
                String sourceExt = FilenameUtils.getExtension(sourceName).toLowerCase();

                String fileName = RandomStringUtils.randomAlphabetic(8)
                        .concat(sourceFileName)
                        .concat(".")
                        .concat(sourceExt);
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
                event.setStatus(CoreConstant.STATUS_PROPOSALEVENT_PENDING);

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
