package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStatusRepository extends JpaRepository<EventStatus, Integer> {
}
