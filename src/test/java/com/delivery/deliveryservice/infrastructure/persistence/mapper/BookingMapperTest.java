package com.delivery.deliveryservice.infrastructure.persistence.mapper;

import com.delivery.deliveryservice.domain.model.Booking;
import com.delivery.deliveryservice.domain.model.BookingStatus;
import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.domain.model.TimeSlot;
import com.delivery.deliveryservice.infrastructure.persistence.entity.BookingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookingMapperTest {

    private BookingMapper mapper;
    private Booking booking;
    private BookingEntity bookingEntity;
    private UUID bookingId;
    private Integer customerId;
    private UUID timeSlotId;
    private String bookingTime;

    @BeforeEach
    void setUp() {
        mapper = new BookingMapper();
        
        bookingId = UUID.randomUUID();
        customerId = 1;
        timeSlotId = UUID.randomUUID();
        bookingTime = String.valueOf(LocalDateTime.now());

        DeliveryMode deliveryMode = new DeliveryMode("DRIVE", "Drive", 7, true);

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(timeSlotId);
        timeSlot.setDate(String.valueOf(LocalDate.now().plusDays(1)));
        timeSlot.setStartTime(String.valueOf(LocalTime.of(10, 0)));
        timeSlot.setEndTime(String.valueOf(LocalTime.of(11, 0)));
        timeSlot.setDeliveryMode(deliveryMode);
        timeSlot.setBooked(true);
        
        booking = new Booking();
        booking.setId(bookingId);
        booking.setCustomerId(customerId);
        booking.setTimeSlot(timeSlot);
        booking.setDeliveryMode(deliveryMode);
        booking.setBookingTime(bookingTime);
        booking.setStatus(BookingStatus.CONFIRMED);
        
        bookingEntity = new BookingEntity();
        bookingEntity.setId(bookingId);
        bookingEntity.setCustomerId(customerId);
        bookingEntity.setTimeSlotId(timeSlotId);
        bookingEntity.setId(bookingId);
        bookingEntity.setCustomerId(customerId);
        bookingEntity.setTimeSlotId(timeSlotId);
        bookingEntity.setDeliveryMode("DRIVE");
        bookingEntity.setBookingTime(bookingTime);
        bookingEntity.setStatus("CONFIRMED");
    }

    @Test
    void toEntity_ShouldMapCorrectly() {
        // Act
        BookingEntity result = mapper.toEntity(booking);

        // Assert
        assertEquals(bookingId, result.getId());
        assertEquals(customerId, result.getCustomerId());
        assertEquals(timeSlotId, result.getTimeSlotId());
        assertEquals("DRIVE", result.getDeliveryMode());
        assertEquals(bookingTime, result.getBookingTime());
        assertEquals("CONFIRMED", result.getStatus());
    }

    @Test
    void toDomain_ShouldMapCorrectly() {
        // Act
        Booking result = mapper.toDomain(bookingEntity);

        // Assert
        assertEquals(bookingId, result.getId());
        assertEquals(customerId, result.getCustomerId());
        assertNotNull(result.getTimeSlot());
        assertEquals("DRIVE", result.getDeliveryMode().getName());
        assertEquals(bookingTime, result.getBookingTime());
        assertEquals(BookingStatus.CONFIRMED, result.getStatus());
    }
}
