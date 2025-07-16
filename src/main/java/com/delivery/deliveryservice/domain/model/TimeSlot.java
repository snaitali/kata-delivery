package com.delivery.deliveryservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {
    private UUID id;
    private String date;
    private String startTime;
    private String endTime;
    private DeliveryMode deliveryMode;
    private boolean booked;
    private Long version;
}
