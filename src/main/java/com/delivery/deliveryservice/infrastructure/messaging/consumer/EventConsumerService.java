package com.delivery.deliveryservice.infrastructure.messaging.consumer;

import com.delivery.deliveryservice.application.service.NotificationService;
import com.delivery.deliveryservice.infrastructure.config.KafkaConfig;
import com.delivery.deliveryservice.infrastructure.messaging.event.BookingCreatedEvent;
import com.delivery.deliveryservice.infrastructure.messaging.event.TimeSlotBookedEvent;
import com.delivery.deliveryservice.infrastructure.messaging.event.TimeSlotCreatedEvent;
import com.delivery.deliveryservice.infrastructure.messaging.event.TimeSlotReleasedEvent;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class EventConsumerService {

    private final ReactiveKafkaConsumerTemplate<String, BookingCreatedEvent> bookingCreatedConsumer;
    private final ReactiveKafkaConsumerTemplate<String, TimeSlotCreatedEvent> timeSlotCreatedConsumer;
    private final ReactiveKafkaConsumerTemplate<String, TimeSlotBookedEvent> timeSlotBookedConsumer;
    private final ReactiveKafkaConsumerTemplate<String, TimeSlotReleasedEvent> timeSlotReleasedConsumer;

    private final NotificationService notificationService;
    
    private final AtomicBoolean closed = new AtomicBoolean(false);
    
    public EventConsumerService(
            @Value("${kafka.topics.booking-created}") String bookingCreatedTopic,
            @Value("${kafka.topics.time-slot-created}") String timeSlotCreatedTopic,
            @Value("${kafka.topics.time-slot-booked}") String timeSlotBookedTopic,
            @Value("${kafka.topics.time-slot-released}") String timeSlotReleasedTopic,
            KafkaConfig kafkaConfig, NotificationService notificationService) {
        this.notificationService = notificationService;

        this.bookingCreatedConsumer = kafkaConfig.reactiveKafkaConsumerTemplate(
                bookingCreatedTopic, BookingCreatedEvent.class);
        
        this.timeSlotCreatedConsumer = kafkaConfig.reactiveKafkaConsumerTemplate(
                timeSlotCreatedTopic, TimeSlotCreatedEvent.class);
        
        this.timeSlotBookedConsumer = kafkaConfig.reactiveKafkaConsumerTemplate(
                timeSlotBookedTopic, TimeSlotBookedEvent.class);
        
        this.timeSlotReleasedConsumer = kafkaConfig.reactiveKafkaConsumerTemplate(
                timeSlotReleasedTopic, TimeSlotReleasedEvent.class);
    }
    
    @EventListener(ApplicationStartedEvent.class)
    public void startConsumers() {
        consumeBookingCreatedEvents();
        consumeTimeSlotCreatedEvents();
        consumeTimeSlotBookedEvents();
        consumeTimeSlotReleasedEvents();
    }
    
    private void consumeBookingCreatedEvents() {
        bookingCreatedConsumer.receive()
                .doOnNext(record -> {
                    log.info("Received booking created event: {}", record.value());

                    // Envoyer une notification de confirmation au client
                    notificationService.sendBookingConfirmation(record.value()).doOnSuccess(
                            (resp) -> log.info("Booking Creation notification sent successfully")
                    ).doOnError(error -> log.error("Failed to send booking cancellation notification", error)).subscribe();
                    
                    record.receiverOffset().acknowledge();
                })
                .doOnError(e -> log.error("Error consuming booking created event", e))
                .subscribe();
    }
    
    private void consumeTimeSlotCreatedEvents() {
        timeSlotCreatedConsumer.receive()
                .doOnNext(record -> {
                    log.info("Received time slot created event: {}", record.value());
                    // Traiter l'événement ici
                    
                    record.receiverOffset().acknowledge();
                })
                .doOnError(e -> log.error("Error consuming time slot created event", e))
                .subscribe();
    }
    
    private void consumeTimeSlotBookedEvents() {
        timeSlotBookedConsumer.receive()
                .doOnNext(record -> {
                    log.info("Received time slot booked event: {}", record.value());
                    // Traiter l'événement ici
                    
                    record.receiverOffset().acknowledge();
                })
                .doOnError(e -> log.error("Error consuming time slot booked event", e))
                .subscribe();
    }
    
    private void consumeTimeSlotReleasedEvents() {
        timeSlotReleasedConsumer.receive()
                .doOnNext(record -> {
                    log.info("Received time slot released event: {}", record.value());
                    // Traiter l'événement ici
                    
                    record.receiverOffset().acknowledge();
                })
                .doOnError(e -> log.error("Error consuming time slot released event", e))
                .subscribe();
    }
    
    @PreDestroy
    public void close() {
        closed.set(true);
    }
}
