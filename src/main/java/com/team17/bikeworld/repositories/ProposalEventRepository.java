package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.ProposalEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalEventRepository extends JpaRepository<ProposalEvent, Integer> {
    List<ProposalEvent> findByAccountUsename_Username(String username);
}
