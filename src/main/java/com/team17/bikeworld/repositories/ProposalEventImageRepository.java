package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.ProposalEvent;
import com.team17.bikeworld.entity.ProposalEventImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalEventImageRepository extends JpaRepository<ProposalEventImage, Integer> {
    ProposalEventImage findByProposalEventid_Id(Integer eventId);
}
