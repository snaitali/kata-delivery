package com.delivery.deliveryservice.infrastructure.persistence.mapper;

import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.domain.model.TimeSlot;
import com.delivery.deliveryservice.infrastructure.persistence.entity.TimeSlotEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotMapperTest {

    private TimeSlotMapper mapper;
    private TimeSlot timeSlot;
    private TimeSlotEntity timeSlotEntity;
    private UUID timeSlotId;
    private String date;
    private String startTime;
    private String endTime;

    @BeforeEach
    void setUp() {
        mapper = new TimeSlotMapper();

        timeSlotId = UUID.randomUUID();
        date = String.valueOf(LocalDate.now().plusDays(1));
        startTime = String.valueOf(LocalTime.of(10, 0));
        endTime = String.valueOf(LocalTime.of(11, 0));
        DeliveryMode deliveryMode = new DeliveryMode("DRIVE", "Drive", 7, true);

        timeSlot = new TimeSlot();
        timeSlot.setId(timeSlotId);
        timeSlot.setDate(date);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);
        timeSlot.setDeliveryMode(deliveryMode);
        timeSlot.setBooked(false);

        timeSlotEntity = new TimeSlotEntity();
        timeSlotEntity.setId(timeSlotId);
        timeSlotEntity.setDate(date);
        timeSlotEntity.setStartTime(startTime);
        timeSlotEntity.setEndTime(endTime);
        timeSlotEntity.setDeliveryMode("DRIVE");
        timeSlotEntity.setBooked(false);
    }

    @Test
    void toEntity_ShouldMapCorrectly() {
        // Act
        TimeSlotEntity result = mapper.toEntity(timeSlot);

        // Assert
        assertEquals(timeSlotId, result.getId());
        assertEquals(date, result.getDate());
        assertEquals(startTime, result.getStartTime());
        assertEquals(endTime, result.getEndTime());
        assertEquals("DRIVE", result.getDeliveryMode());
        assertFalse(result.isBooked());
    }

    @Test
    void toDomain_ShouldMapCorrectly() {
        // Act
        TimeSlot result = mapper.toDomain(timeSlotEntity);

        // Assert
        assertEquals(timeSlotId, result.getId());
        assertEquals(date, result.getDate());
        assertEquals(startTime, result.getStartTime());
        assertEquals(endTime, result.getEndTime());
        assertEquals("DRIVE", result.getDeliveryMode().getName());
        assertFalse(result.isBooked());
    }
}