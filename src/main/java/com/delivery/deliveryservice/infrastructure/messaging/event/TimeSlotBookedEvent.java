package com.delivery.deliveryservice.infrastructure.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotBookedEvent {
    private UUID timeSlotId;
    private UUID bookingId;
    private Integer customerId;
    private String bookingTime;
}