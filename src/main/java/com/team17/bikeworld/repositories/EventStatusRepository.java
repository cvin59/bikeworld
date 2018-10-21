package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface EventStatusRepository extends JpaRepository<EventStatus, Integer> {
    Optional<EventStatus> findEventStatusById(int id);
}
