package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.ProposalEvent;
import com.team17.bikeworld.repositories.EventRepository;
import com.team17.bikeworld.repositories.ProposalEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProposalEventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
    private final ProposalEventRepository proposalEventRepository;
    private final Path rootLocation = Paths.get("src/main/resources/static/images").toAbsolutePath().normalize();

    public ProposalEventService(ProposalEventRepository proposalEventRepository) {
        this.proposalEventRepository = proposalEventRepository;
    }

    public List<ProposalEvent> findProposalEvents() {
        List<ProposalEvent> events = proposalEventRepository.findAll();
        return events;
    }

    public Optional<ProposalEvent> findProposalEvent(int id) {
        Optional<ProposalEvent> event = proposalEventRepository.findById(id);
        return event;
    }

    public ProposalEvent saveProposalEvent(ProposalEvent proposalEvent) {
        return proposalEventRepository.save(proposalEvent);
    }
}
