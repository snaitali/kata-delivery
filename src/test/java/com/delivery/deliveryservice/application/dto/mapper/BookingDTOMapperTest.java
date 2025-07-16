package com.delivery.deliveryservice.application.dto.mapper;

import com.delivery.deliveryservice.application.dto.BookingRequestDTO;
import com.delivery.deliveryservice.application.dto.BookingResponseDTO;
import com.delivery.deliveryservice.domain.model.Booking;
import com.delivery.deliveryservice.domain.model.BookingStatus;
import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.domain.model.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookingDTOMapperTest {

    private BookingDTOMapper mapper;
    private Booking booking;
    private BookingResponseDTO bookingResponseDTO;
    private BookingRequestDTO bookingRequestDTO;
    private UUID bookingId;
    private Integer customerId;
    private UUID timeSlotId;
    private String bookingTime;
    private DeliveryMode deliveryMode;
    private TimeSlot timeSlot;

    @BeforeEach
    void setUp() {
        mapper = new BookingDTOMapper();
        
        bookingId = UUID.randomUUID();
        customerId = 1;
        timeSlotId = UUID.randomUUID();
        bookingTime = String.valueOf(LocalDateTime.now());
        
        deliveryMode = new DeliveryMode("DRIVE", "Drive", 7, true);
        
        timeSlot = new TimeSlot();
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
        
        bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setId(bookingId);
        bookingResponseDTO.setCustomerId(customerId);
        bookingResponseDTO.setTimeSlotId(timeSlotId);
        bookingResponseDTO.setDeliveryMode("DRIVE");
        bookingResponseDTO.setBookingTime(bookingTime);
        bookingResponseDTO.setStatus("CONFIRMED");
        bookingResponseDTO.setDate(timeSlot.getDate());
        bookingResponseDTO.setStartTime(timeSlot.getStartTime());
        bookingResponseDTO.setEndTime(timeSlot.getEndTime());

        bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setCustomerId(1);
        bookingRequestDTO.setDeliveryMode("DRIVE");
        bookingRequestDTO.setTimeSlotId(UUID.randomUUID());
    }

    @Test
    void toDTO_ShouldMapCorrectly() {
        // Act
        BookingResponseDTO result = mapper.toDTO(booking);
        
        // Assert
        assertEquals(bookingId, result.getId());
        assertEquals(customerId, result.getCustomerId());
        assertEquals(timeSlotId, result.getTimeSlotId());
        assertEquals("DRIVE", result.getDeliveryMode());
        assertEquals(bookingTime, result.getBookingTime());
        assertEquals("CONFIRMED", result.getStatus());
        assertEquals(timeSlot.getDate(), result.getDate());
        assertEquals(timeSlot.getStartTime(), result.getStartTime());
        assertEquals(timeSlot.getEndTime(), result.getEndTime());
    }

    @Test
    void toDomain_ShouldMapCorrectly() {
        // Act
        Booking result = mapper.toDomain(bookingRequestDTO);
        
        // Assert
        assertEquals(customerId, result.getCustomerId());
        assertNotNull(result.getTimeSlot());
        assertEquals("DRIVE", result.getDeliveryMode().getName());
    }
}
