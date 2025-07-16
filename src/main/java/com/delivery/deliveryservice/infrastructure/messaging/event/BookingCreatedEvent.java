package com.delivery.deliveryservice.infrastructure.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingCreatedEvent {
    private UUID bookingId;
    private Integer customerId;
    private UUID timeSlotId;
    private String deliveryMode;
    private String date;
    private String startTime;
    private String endTime;
    private String bookingTime;
}