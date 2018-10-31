package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>{
    List<Event> findByEventStautsid(EventStatus eventStatus);

    Page<Event> findByTitleIgnoreCaseContaining(String title, Pageable pageable);

    List<Event> findTop3By();
}
