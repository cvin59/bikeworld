package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface EventImageRepository extends JpaRepository<EventImage, Integer> {
    Collection<EventImage> findAllByEventId(Event eventId);
}
