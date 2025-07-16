package com.delivery.deliveryservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMode {
    private String name;
    private String description;
    private int maxDaysInAdvance;
    private boolean allowsPreBooking;
    
    public boolean isValidForDate(String date) {
        if (!allowsPreBooking) {
            return date.equals(LocalDate.now().toString());
        }
        
        LocalDate maxDate = LocalDate.now().plusDays(maxDaysInAdvance);
        return !LocalDate.parse(date).isBefore(LocalDate.now()) && !LocalDate.parse(date).isAfter(maxDate);
    }
}
