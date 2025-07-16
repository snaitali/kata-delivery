package com.delivery.deliveryservice.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {
    
    @Id
    private UUID id;
    
    @Column("customer_id")
    private Integer customerId;
    
    @Column("time_slot_id")
    private UUID timeSlotId;

    @Column("date")
    private String date;

    @Column("start_time")
    private String startTime;

    @Column("end_time")
    private String endTime;
    
    @Column("delivery_mode")
    private String deliveryMode;
    
    @Column("booking_time")
    private String bookingTime;
    
    private String status;

    @Version
    private Long version;
}
