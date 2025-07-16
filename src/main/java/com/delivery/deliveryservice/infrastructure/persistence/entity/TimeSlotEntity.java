package com.delivery.deliveryservice.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("time_slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotEntity {
    
    @Id
    private UUID id;
    
    private String date;
    
    @Column("start_time")
    private String startTime;
    
    @Column("end_time")
    private String endTime;
    
    @Column("delivery_mode")
    private String deliveryMode;
    
    private boolean booked;
    
    @Version
    private Long version;
}
