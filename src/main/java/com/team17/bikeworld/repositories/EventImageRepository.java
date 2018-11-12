package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface EventImageRepository extends JpaRepository<EventImage, Integer> {
    EventImage findByEventId_Id(Integer eventId);
    @Transactional
    void deleteByEventId_Id(Integer eventId);
}
