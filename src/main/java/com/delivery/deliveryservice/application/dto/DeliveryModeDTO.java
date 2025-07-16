package com.delivery.deliveryservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryModeDTO {
    
    @NotBlank(message = "Delivery mode name is required")
    private String name;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotNull(message = "Max days in advance is required")
    @PositiveOrZero(message = "Max days in advance must be positive or zero")
    private Integer maxDaysInAdvance;
    
    private boolean allowsPreBooking;
}
