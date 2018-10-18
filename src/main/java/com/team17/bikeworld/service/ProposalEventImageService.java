package com.team17.bikeworld.service;

import com.team17.bikeworld.repositories.ProposalEventImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ProposalEventImageService {

    private final ProposalEventImageRepository proposalEventImageRepository;

    public ProposalEventImageService(ProposalEventImageRepository proposalEventImageRepository) {
        this.proposalEventImageRepository = proposalEventImageRepository;
    }


}
