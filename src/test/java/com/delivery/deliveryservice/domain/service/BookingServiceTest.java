package com.delivery.deliveryservice.domain.service;


import com.delivery.deliveryservice.domain.model.*;
import com.delivery.deliveryservice.domain.repository.BookingRepository;
import com.delivery.deliveryservice.infrastructure.messaging.event.BookingCreatedEvent;
import com.delivery.deliveryservice.infrastructure.messaging.event.TimeSlotBookedEvent;
import com.delivery.deliveryservice.infrastructure.messaging.service.KafkaMessagingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TimeSlotService timeSlotService;

    @Mock
    private CustomerService customerService;

    @Mock
    private KafkaMessagingService kafkaMessagingService;

    @InjectMocks
    private BookingService bookingService;

    private Integer customerId;
    private UUID timeSlotId;
    private UUID bookingId;
    private Customer customer;
    private TimeSlot timeSlot;
    private Booking booking;

    @BeforeEach
    void setUp() {
        
        customerId = 1;
        timeSlotId = UUID.randomUUID();
        bookingId = UUID.randomUUID();
        
        customer = new Customer();
        customer.setId(customerId);
        customer.setName("Soufiane NAIT");
        customer.setEmail("souf.nait@example.com");

        DeliveryMode deliveryMode = new DeliveryMode("DRIVE", "Drive", 7, true);
        
        timeSlot = new TimeSlot();
        timeSlot.setId(timeSlotId);
        timeSlot.setDate(String.valueOf(LocalDate.now().plusDays(1)));
        timeSlot.setStartTime(String.valueOf(LocalTime.of(10, 0)));
        timeSlot.setEndTime(String.valueOf(LocalTime.of(11, 0)));

        timeSlot.setDeliveryMode(deliveryMode);
        timeSlot.setBooked(false);

        booking = new Booking();
        booking.setId(bookingId);
        booking.setCustomerId(customerId);
        booking.setTimeSlot(timeSlot);
        booking.setDeliveryMode(deliveryMode);
        booking.setBookingTime(String.valueOf(LocalDateTime.now()));
        booking.setStatus(BookingStatus.CONFIRMED);
    }

    @Test
    void createBooking_ShouldCreateAndPublishEvents() {
        when(customerService.getCustomerById(customerId)).thenReturn(Mono.just(customer));
        when(timeSlotService.bookTimeSlot(timeSlotId)).thenReturn(Mono.just(timeSlot));
        when(bookingRepository.save(any(Booking.class))).thenReturn(Mono.just(booking));
        when(kafkaMessagingService.publishBookingCreatedEvent(any(BookingCreatedEvent.class)))
                .thenReturn(Mono.just(mock(SenderResult.class)));
        when(kafkaMessagingService.publishTimeSlotBookedEvent(any(TimeSlotBookedEvent.class)))
                .thenReturn(Mono.just(mock(SenderResult.class)));

        StepVerifier.create(bookingService.createBooking(customerId, timeSlotId, "DRIVE"))
                .expectNext(booking)
                .verifyComplete();

        verify(customerService, times(1)).getCustomerById(customerId);
        verify(timeSlotService, times(1)).bookTimeSlot(timeSlotId);
        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(kafkaMessagingService, times(1)).publishBookingCreatedEvent(any(BookingCreatedEvent.class));
        verify(kafkaMessagingService, times(1)).publishTimeSlotBookedEvent(any(TimeSlotBookedEvent.class));
    }

    @Test
    void getBookingsByCustomerId_ShouldReturnBookings() {
        when(bookingRepository.findByCustomerId(customerId)).thenReturn(Flux.just(booking));

        StepVerifier.create(bookingService.getBookingsByCustomerId(customerId))
                .expectNext(booking)
                .verifyComplete();

        verify(bookingRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void getBookingById_WhenExists_ShouldReturnBooking() {
        when(bookingRepository.findById(bookingId)).thenReturn(Mono.just(booking));

        StepVerifier.create(bookingService.getBookingById(bookingId))
                .expectNext(booking)
                .verifyComplete();

        verify(bookingRepository, times(1)).findById(bookingId);
    }

    @Test
    void getBookingById_WhenNotExists_ShouldReturnError() {
        UUID nonExistentId = UUID.randomUUID();
        when(bookingRepository.findById(nonExistentId)).thenReturn(Mono.empty());

        StepVerifier.create(bookingService.getBookingById(nonExistentId))
                .expectError(IllegalArgumentException.class)
                .verify();

        verify(bookingRepository, times(1)).findById(nonExistentId);
    }
}

