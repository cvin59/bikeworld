package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    List<Participant> findByAccountUsename_Username(String username);
    Page<Participant> findByAccountUsename_Username(String username, Pageable pageable);
}
