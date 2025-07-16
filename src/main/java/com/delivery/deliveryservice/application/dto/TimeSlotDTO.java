package com.delivery.deliveryservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDTO {
    
    private UUID id;
    
    @NotNull(message = "Date is required")
    private String date;
    
    @NotNull(message = "Start time is required")
    private String startTime;
    
    @NotNull(message = "End time is required")
    private String endTime;
    
    @NotBlank(message = "Delivery mode is required")
    private String deliveryMode;
    
    private boolean booked;
}
