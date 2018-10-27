//package com.team17.bikeworld.service;
//
//import com.team17.bikeworld.entity.Event;
//import com.team17.bikeworld.entity.EventStatus;
//import com.team17.bikeworld.repositories.EventRepository;
//import com.team17.bikeworld.repositories.EventStatusRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//@Component
//@EnableScheduling
//public class ScheduledTasks {
//
//    private final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);
//
//    private final EventRepository eventRepository;
//    private final EventStatusRepository eventStatusRepository;
//
//    public ScheduledTasks(EventRepository eventRepository, EventStatusRepository eventStatusRepository) {
//        this.eventRepository = eventRepository;
//        this.eventStatusRepository = eventStatusRepository;
//    }
//
//    @Scheduled(cron = "*/30 * * * * *")
//    public void updateStatusEvent() {
//        List<Event> events = eventRepository.findAll();
//        for (Event event: events) {
//            int checkDate = event.getStartDate().compareTo(Calendar.getInstance().getTime());
//            if (checkDate > 0) {
//                event.setEventStautsid(eventStatusRepository.findEventStatusById(1).get());
//                eventRepository.save(event);
//            } else if (checkDate < 0){
//                event.setEventStautsid(eventStatusRepository.findEventStatusById(2).get());
//                eventRepository.save(event);
//            }
//        }
//    }
//}
