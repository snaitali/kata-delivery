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
public class BookingRequestDTO {
    
    @NotNull(message = "Customer ID is required")
    private Integer customerId;
    
    @NotNull(message = "Time slot ID is required")
    private UUID timeSlotId;
    
    @NotBlank(message = "Delivery mode is required")
    private String deliveryMode;
}
