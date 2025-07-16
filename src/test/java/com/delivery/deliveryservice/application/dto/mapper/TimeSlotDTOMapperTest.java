package com.delivery.deliveryservice.application.dto.mapper;

import com.delivery.deliveryservice.application.dto.TimeSlotDTO;
import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.domain.model.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotDTOMapperTest {

    private TimeSlotDTOMapper mapper;
    private TimeSlot timeSlot;
    private TimeSlotDTO timeSlotDTO;
    private UUID timeSlotId;
    private String date;
    private String startTime;
    private String endTime;
    private DeliveryMode deliveryMode;

    @BeforeEach
    void setUp() {
        mapper = new TimeSlotDTOMapper();

        timeSlotId = UUID.randomUUID();
        date = String.valueOf(LocalDate.now().plusDays(1));
        startTime = String.valueOf(LocalTime.of(10, 0));
        endTime = String.valueOf(LocalTime.of(11, 0));
        deliveryMode = new DeliveryMode("DRIVE", "Drive", 7, true);

        timeSlot = new TimeSlot();
        timeSlot.setId(timeSlotId);
        timeSlot.setDate(date);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);
        timeSlot.setDeliveryMode(deliveryMode);
        timeSlot.setBooked(false);

        timeSlotDTO = new TimeSlotDTO();
        timeSlotDTO.setId(timeSlotId);
        timeSlotDTO.setDate(date);
        timeSlotDTO.setStartTime(startTime);
        timeSlotDTO.setEndTime(endTime);
        timeSlotDTO.setDeliveryMode("DRIVE");
        timeSlotDTO.setBooked(false);
    }

    @Test
    void toDTO_ShouldMapCorrectly() {
        TimeSlotDTO result = mapper.toDTO(timeSlot);

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
        TimeSlot result = mapper.toDomain(timeSlotDTO);

        // Assert
        assertEquals(timeSlotId, result.getId());
        assertEquals(date, result.getDate());
        assertEquals(startTime, result.getStartTime());
        assertEquals(endTime, result.getEndTime());
        assertEquals("DRIVE", result.getDeliveryMode().getName());
        assertFalse(result.isBooked());
    }
}