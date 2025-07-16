package com.delivery.deliveryservice.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("delivery_modes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryModeEntity {
    
    @Id
    @Column("mode")
    private String mode;
    
    private String description;
    
    @Column("max_days_in_advance")
    private int maxDaysInAdvance;
    
    @Column("allows_pre_booking")
    private boolean allowsPreBooking;
}
