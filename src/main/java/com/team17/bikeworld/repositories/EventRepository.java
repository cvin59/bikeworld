package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer>{
}
