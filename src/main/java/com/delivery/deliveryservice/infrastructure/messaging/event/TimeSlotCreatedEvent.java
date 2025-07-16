package com.delivery.deliveryservice.infrastructure.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotCreatedEvent {
    private UUID timeSlotId;
    private String date;
    private String startTime;
    private String endTime;
    private String deliveryMode;
}