package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.ProposalEvent;
import com.team17.bikeworld.entity.ProposalEventImage;
import com.team17.bikeworld.repositories.ProposalEventImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ProposalEventImageService {

    private final ProposalEventImageRepository proposalEventImageRepository;

    public ProposalEventImageService(ProposalEventImageRepository proposalEventImageRepository) {
        this.proposalEventImageRepository = proposalEventImageRepository;
    }

    public ProposalEventImage getEventImageByEventId(Integer eventId) {
        return proposalEventImageRepository.findByProposalEventid_Id(eventId);
    }
}
