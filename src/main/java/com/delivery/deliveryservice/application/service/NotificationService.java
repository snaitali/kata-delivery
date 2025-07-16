package com.delivery.deliveryservice.application.service;

import com.delivery.deliveryservice.domain.model.Customer;
import com.delivery.deliveryservice.domain.service.CustomerService;
import com.delivery.deliveryservice.infrastructure.messaging.event.BookingCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    
    private final CustomerService customerService;

    
    public Mono<Void> sendBookingConfirmation(BookingCreatedEvent event) {
        return customerService.getCustomerById(event.getCustomerId())
                .flatMap(customer -> {
                    String message = createBookingConfirmationMessage(customer, event);
                    return sendNotification(customer, message);
                })
                .then();
    }
    
    private String createBookingConfirmationMessage(Customer customer, BookingCreatedEvent event) {
        return String.format(
                "Bonjour %s, votre réservation a été confirmée.\n" +
                "Mode de livraison: %s\n" +
                "Date: %s\n" +
                "Créneau horaire: %s - %s\n" +
                "Numéro de réservation: %s",
                customer.getName(),
                event.getDeliveryMode(),
                event.getDate(),
                event.getStartTime(),
                event.getEndTime(),
                event.getBookingId()
        );
    }
    
    private Mono<Void> sendNotification(Customer customer, String message) {
        log.info("Sending notification to {}: {}", customer.getEmail(), message);

        // Simulation d'envoi d'email
        return Mono.fromRunnable(() -> {
            log.info("Email sent to: {}", customer.getEmail());
        });
    }
}
