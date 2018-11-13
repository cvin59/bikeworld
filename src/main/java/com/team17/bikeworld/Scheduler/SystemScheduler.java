package com.team17.bikeworld.Scheduler;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Order;
import com.team17.bikeworld.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class SystemScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger( SystemScheduler.class);

    @Autowired
    OrderService orderService;

    // @Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")

    // Run this task for every 15 minutes
    @Scheduled(cron = "14 * * * * ?")
    public void checkExpiredOrder() {
        List<Order> orderList = orderService.getByStatus(CoreConstant.STATUS_ORDER_PENDING);
        LOGGER.info("Time"+ LocalDateTime.now());
        if (orderList != null) {
            if (orderList.size() > 0) {
                for (Order order : orderList) {
                    LocalDateTime dueDate = convertToLocalDateTimeViaInstant(order.getOrderDate()).plusDays(3);
                    if (dueDate.isBefore(LocalDateTime.now())) {
                        LOGGER.info("Found expired order: id="+order.getId());
                        orderService.changeStatus(order.getId(), CoreConstant.STATUS_ORDER_CANCEL);
                    }
                }
            }
        }
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
