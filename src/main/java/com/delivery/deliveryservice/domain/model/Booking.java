package com.delivery.deliveryservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private UUID id;
    private Integer customerId;
    private TimeSlot timeSlot;
    private DeliveryMode deliveryMode;
    private String bookingTime;
    private BookingStatus status;
    private Long version;
}
