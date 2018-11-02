package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
}
