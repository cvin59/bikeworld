package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.entity.EventStatus;
import com.team17.bikeworld.repositories.EventRepository;
import com.team17.bikeworld.repositories.EventStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.*;
import static com.team17.bikeworld.common.CoreConstant.STATUS_EVENT_FINISH;

@Component
@EnableScheduling
public class ScheduledTasks {

    private final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    private final EventRepository eventRepository;
    private final EventStatusRepository eventStatusRepository;

    public ScheduledTasks(EventRepository eventRepository, EventStatusRepository eventStatusRepository) {
        this.eventRepository = eventRepository;
        this.eventStatusRepository = eventStatusRepository;
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void updateStatusEvent() {
        List<Event> events = eventRepository.findAll();
        for (Event event : events) {
            int checkStartRegister = Calendar.getInstance().getTime().compareTo(event.getStartRegisterDate());
            int checkEndRegister = Calendar.getInstance().getTime().compareTo(event.getEndRegisterDate());
            int checkStart = Calendar.getInstance().getTime().compareTo(event.getStartDate());
            int checkEnd = Calendar.getInstance().getTime().compareTo(event.getEndDate());

//            if (checkStartRegister < 0) {
//                event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_COMING_SOON).get());
//                eventRepository.save(event);
//            } else if (checkStartRegister >= 0 && checkEndRegister <= 0){
//                event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_REGISTERING).get());
//                eventRepository.save(event);
//            } else if (checkEndRegister > 0 && checkStart < 0) {
//                event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_UPCOMING).get());
//                eventRepository.save(event);
//            } else
            if (checkStart >= 0 && checkEnd <= 0) {
                event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_ACTIVE).get());
                eventRepository.save(event);
            } else if (checkEnd > 0) {
                event.setEventStautsid(eventStatusRepository.findEventStatusById(STATUS_EVENT_FINISH).get());
                eventRepository.save(event);
            }
        }
    }
}
