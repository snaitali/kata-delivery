package com.delivery.deliveryservice.application.service;

import com.delivery.deliveryservice.domain.model.Customer;
import com.delivery.deliveryservice.domain.service.CustomerService;
import com.delivery.deliveryservice.infrastructure.messaging.event.BookingCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private NotificationService notificationService;

    private Integer customerId;
    private Customer customer;
    private BookingCreatedEvent bookingCreatedEvent;

    @BeforeEach
    void setUp() {
        customerId = 1;
        UUID bookingId = UUID.randomUUID();
        UUID timeSlotId = UUID.randomUUID();

        customer = new Customer();
        customer.setId(customerId);
        customer.setName("Souf NAIT");
        customer.setEmail("souf.nait@example.com");

        bookingCreatedEvent = new BookingCreatedEvent(
                bookingId,
                customerId,
                timeSlotId,
                "DRIVE",
                String.valueOf(LocalDate.now().plusDays(1)),
                String.valueOf(LocalTime.of(10, 0)),
                String.valueOf(LocalTime.of(11, 0)),
                String.valueOf(LocalDateTime.now())
        );
    }

    @Test
    void sendBookingConfirmation_ShouldSendNotification() {
        when(customerService.getCustomerById(customerId)).thenReturn(Mono.just(customer));

        StepVerifier.create(notificationService.sendBookingConfirmation(bookingCreatedEvent))
                .verifyComplete();

        verify(customerService, times(1)).getCustomerById(customerId);
    }
}