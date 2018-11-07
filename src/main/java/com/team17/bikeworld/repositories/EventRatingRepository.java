package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.EventRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  EventRatingRepository extends JpaRepository<EventRating, Integer> {
    List<EventRating> findByEventId_IdAndAccountUsename_Username(int eventId, String username);
}
