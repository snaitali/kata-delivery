package com.delivery.deliveryservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
    
    private UUID id;
    private Integer customerId;
    private UUID timeSlotId;
    private String deliveryMode;
    private String date;
    private String startTime;
    private String endTime;
    private String bookingTime;
    private String status;
}
